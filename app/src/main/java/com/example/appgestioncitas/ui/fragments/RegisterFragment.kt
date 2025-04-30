package com.example.appgestioncitas.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.appgestioncitas.R
import com.example.appgestioncitas.databinding.FragmentRegisterBinding
import com.example.appgestioncitas.models.Usuario
import com.example.appgestioncitas.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel : RegisterFragmentViewModel by viewModels()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Funcionalidad aquí debajo
        setListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null  // Evita memory leaks
    }
    private fun setListeners() {
        binding.loginButton.setOnClickListener {
            loginConEmail()
        }
    }
    private fun loginConEmail() {
        // Obtenemos los datos de los campos de entrada
        val email = binding.emailInput.text.toString().trim()
        val password = binding.passwordInput.text.toString().trim()
        val userName = binding.usernameInput.text.toString().trim()
        val telefono = binding.phoneInput.text.toString().trim()

        // Validación de campos vacíos
        if (email.isEmpty()) {
            binding.emailInput.error = "Correo vacío"
            return
        }
        if (password.isEmpty()) {
            binding.passwordInput.error = "Contraseña vacía"
            return
        }
        if (telefono.isEmpty()) {
            binding.phoneInput.error = "Teléfono vacío"
            return
        }
        if (userName.isEmpty()) {
            binding.usernameInput.error = "Nombre de usuario vacío"
            return
        }

        // Validar formato del correo
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailInput.error = "Correo inválido"
            return
        }

        // Validar longitud de la contraseña
        if (password.length < 6) {
            binding.passwordInput.error = "Contraseña muy corta"
            return
        }

        // Validar si el correo o el nombre de usuario ya existen
        viewModel.validarUsuario(Usuario("", userName, email, telefono, password)) // Pasamos el usuario a validar

        // Observar la validación
        viewModel.usuarioValido.observe(viewLifecycleOwner, Observer { esValido ->
            if (esValido) {
                // Si el correo y el nombre de usuario son válidos, proceder a crear el usuario
                crearUsuarioEnFirebase(email, password, userName, telefono)
            } else {
                // Si el correo o el nombre de usuario ya están registrados, mostrar emergente con error

            }
        })
    }

    // Función para crear el usuario en Firebase
    private fun crearUsuarioEnFirebase(email: String, password: String, userName: String, telefono: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                // Usuario creado con éxito
                val uidCliente = authResult.user?.uid ?: return@addOnSuccessListener
                val cliente = Usuario(uidCliente, userName, email, telefono, password)
                viewModel.crearCliente(cliente, uidCliente)
                auth.signInWithEmailAndPassword(email,password)
                    .addOnSuccessListener {
                        irMain()
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                    }
            }
            .addOnFailureListener { exception ->
                // Si hubo un error en la creación del usuario
                Toast.makeText(requireContext(), "Error al crear el usuario: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }



    private fun irMain() {
        startActivity(Intent(requireContext(), MainActivity::class.java))
        requireActivity().finish()
    }
}
