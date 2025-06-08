package com.example.appgestioncitas.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appgestioncitas.R
import com.example.appgestioncitas.databinding.ActivityPerfilBinding
import com.example.appgestioncitas.models.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

class PerfilActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPerfilBinding
    private val viewModel: PerfilViewModel by viewModels()
    private lateinit var usuario: Usuario
    private lateinit var uidFirebase: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Usa binding.main en lugar de findViewById para evitar null
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        uidFirebase = FirebaseAuth.getInstance().currentUser?.uid.toString()
        setListeners()
        configurarBottomMenu(R.id.nav_account)
    }

    private fun setListeners() {
        viewModel.cargarUsuario(uidFirebase) { usuario ->
            if (usuario != null) {
                this.usuario = usuario


                Picasso.get()
                    .load("https://dummyimage.com/200x200/000/fff&text=${usuario.userName?.substring(0, 2)?.uppercase()}")
                    .into(binding.imgPerfil)

                binding.tvUsername.setText(usuario.userName)
                binding.tvCorreo.setText(usuario.correo)
                binding.tvTelefono.setText(usuario.telefono)

                binding.btnCerrarSesion.setOnClickListener {
                    FirebaseAuth.getInstance().signOut()
                    val intent = Intent(this, LoginRegisterActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }

                binding.btnEditar.setOnClickListener {
                    Toast.makeText(this, "Función no disponible", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "No se pudo cargar el usuario", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun configurarBottomMenu(currentId: Int) {
        binding.bottomNavigation.selectedItemId = currentId

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    if (currentId != R.id.nav_home) {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                    true
                }
                R.id.nav_events -> {
                    if (currentId != R.id.nav_events) {
                        startActivity(Intent(this, CitasActivity::class.java))
                        finish()
                    }
                    true
                }
                R.id.nav_account -> {
                    if (currentId != R.id.nav_account) {
                        startActivity(Intent(this, PerfilActivity::class.java))
                        finish()
                    }
                    true
                }
                else -> false
            }
        }
    }
}
