package com.example.appgestioncitas.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.appgestioncitas.R
import com.example.appgestioncitas.databinding.FragmentRegisterBinding
import com.example.appgestioncitas.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel = RegisterFragmentViewModel()
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

        }
    }
    private fun loginConEmail() {
        //añadir las validaciones de los campos
        //añadir en el repo que recorra la bbdd y revise si existe ya el mail y el username

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
                Toast.makeText(
                    requireContext(),
                    "Error: ${it.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun irMain() {
        startActivity(Intent(requireContext(), MainActivity::class.java))
        requireActivity().finish()
    }
}
