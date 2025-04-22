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
import com.example.appgestioncitas.R
import com.example.appgestioncitas.databinding.FragmentLoginBinding
import com.example.appgestioncitas.ui.LoginRegisterActivity
import com.example.appgestioncitas.ui.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val auth = FirebaseAuth.getInstance()
    private val responseLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val cuenta = task.getResult(ApiException::class.java)
                val credenciales = GoogleAuthProvider.getCredential(cuenta.idToken, null)
                FirebaseAuth.getInstance().signInWithCredential(credenciales)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            irMain()
                        } else {
                            Toast.makeText(requireContext(), "Error al iniciar con Google", Toast.LENGTH_SHORT).show()
                        }
                    }
            } catch (e: ApiException) {
                Toast.makeText(requireContext(), "Fallo en GoogleSignIn: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "Inicio cancelado por el usuario", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null  // Evita memory leaks
    }
    private fun setListeners() {
        binding.registerLink.setOnClickListener {
            // Navegar a la pantalla de registro
            val fragmentRegistro = RegisterFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, fragmentRegistro)
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

        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                irMain()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Error: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }



    private fun irMain() {
        startActivity(Intent(requireContext(), MainActivity::class.java))
        requireActivity().finish()
    }

}