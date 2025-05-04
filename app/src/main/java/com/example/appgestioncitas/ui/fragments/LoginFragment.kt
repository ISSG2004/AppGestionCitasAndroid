package com.example.appgestioncitas.ui.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import com.example.appgestioncitas.R
import com.example.appgestioncitas.databinding.FragmentLoginBinding
import com.example.appgestioncitas.models.Usuario
import com.example.appgestioncitas.ui.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel = LoginFragmentViewModel()
    private val auth = FirebaseAuth.getInstance()

    private val responseLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                if (task.isSuccessful) {
                    val cuenta = task.result
                    if (cuenta != null) {
                        val credenciales = GoogleAuthProvider.getCredential(cuenta.idToken, null)
                        auth.signInWithCredential(credenciales)
                            .addOnCompleteListener { taskAuth ->
                                if (taskAuth.isSuccessful) {
                                    irMain()
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        "Error al iniciar sesión con Google",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Cuenta de Google nula",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    val exception = task.exception
                    if (exception is ApiException) {
                        Toast.makeText(
                            requireContext(),
                            "SignIn cancelado o fallido: ${exception.statusCode}",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Error inesperado: ${exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Inicio de sesión cancelado por el usuario",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.crearClienteLogGoogle(Usuario(
            auth.uid.toString(),
            auth.currentUser?.displayName.toString(),
            auth.currentUser?.email.toString(),
            auth.currentUser?.phoneNumber.toString(),
            ""),
            auth.uid.toString())
        _binding = null
    }

    private fun setListeners() {
        binding.registerLink.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, RegisterFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.googleSigninButton.setOnClickListener {
            loginConGoogle()
        }

        binding.loginButton.setOnClickListener {
            loginConEmail()
        }
    }

    private fun loginConGoogle() {
        val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.google_client_id))
            .requestEmail()
            .build()

        val googleClient = GoogleSignIn.getClient(requireActivity(), googleConf)
        googleClient.signOut()
        responseLauncher.launch(googleClient.signInIntent)
    }

    private fun loginConEmail() {
        val email = binding.emailInput.text.toString().trim()
        val password = binding.passwordInput.text.toString().trim()

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailInput.error = "Correo inválido"
            return
        }

        if (password.length < 6) {
            binding.passwordInput.error = "Contraseña muy corta"
            return
        }
        // Validar si el correo o el nombre de usuario ya existen
        viewModel.validarUsuario(Usuario("", "", email, "", password)) // Pasamos el usuario a validar

        // Observar la validación
        viewModel.usuarioValido.observe(viewLifecycleOwner, Observer { esValido ->
            if (esValido) {
                // Si el correo y el nombre de usuario son válidos, proceder a iniciar seesión
                auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        irMain()
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            requireContext(),
                            "Error: ${it.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            } else {
                Snackbar.make(binding.root, "El correo o la contraseña que estas introduciendo no corresponde con ningun usuario, intentelo de nuevo", Snackbar.LENGTH_SHORT).show()

            }
        })


    }

    private fun irMain() {
        startActivity(Intent(requireContext(), MainActivity::class.java))
        requireActivity().finish()
    }
}
