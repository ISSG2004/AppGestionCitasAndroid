package com.example.appgestioncitas.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import com.example.appgestioncitas.R
import com.example.appgestioncitas.databinding.ActivityCitasBinding
import com.example.appgestioncitas.ui.fragments.CitasNuevasFragment
import com.example.appgestioncitas.ui.fragments.CitasTerminadasFragment
import com.example.appgestioncitas.ui.fragments.LoginFragment

class CitasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCitasBinding

    //Instancias únicas de fragmentos
    private val fragmentCitasPendientes = CitasNuevasFragment()
    private val fragmentCitasPasadas = CitasTerminadasFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCitasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setListeners()
        setFragmentPendientes() //Carga inicial
        configurarBottomMenu(R.id.nav_events)
    }


    private fun setListeners() {
        binding.btnPendientes.setOnClickListener {
            setFragmentPendientes()
        }
        binding.btnPasadas.setOnClickListener {
            setFragmentPasadas()
        }
    }

    private fun setFragmentPendientes() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            replace(R.id.fcb_citas_usuario, fragmentCitasPendientes, "pendientes")
        }
        actualizarEstadoBotones("pendientes")
    }

    private fun setFragmentPasadas() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            replace(R.id.fcb_citas_usuario, fragmentCitasPasadas, "pasadas")
        }
        actualizarEstadoBotones("pasadas")
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
    private fun actualizarEstadoBotones(tag: String) {
        when (tag) {
            "pendientes" -> {
                binding.btnPendientes.isEnabled = false
                binding.btnPasadas.isEnabled = true
            }
            "pasadas" -> {
                binding.btnPendientes.isEnabled = true
                binding.btnPasadas.isEnabled = false
            }
        }
    }
}