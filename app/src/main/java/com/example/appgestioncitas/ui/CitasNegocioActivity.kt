package com.example.appgestioncitas.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appgestioncitas.R
import com.example.appgestioncitas.databinding.ActivityCitasNegocioBinding
import com.example.appgestioncitas.models.Cita
import com.example.appgestioncitas.models.Negocio
import com.example.appgestioncitas.ui.adapters.CitasNegocioAdapter
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

class CitasNegocioActivity : AppCompatActivity() {
    private val viewModel : CitasNegocioViewModel by viewModels()
    private lateinit var binding: ActivityCitasNegocioBinding
    private val adapter = CitasNegocioAdapter(mutableListOf()){cita: Cita ->
        reservarCita(cita)
    }
    private lateinit var negocio: Negocio

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCitasNegocioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setRecycler()
        cargarNegocio()
        viewModel.cargarCitasDelNegocio(negocio.id)
        configurarBottomMenu(null)
        startCitaPolling()
    }

    private fun cargarNegocio() {
        negocio = (intent.getSerializableExtra("negocio") as? Negocio)!!
    }

    private fun setRecycler() {
        binding.recyclerCitasNegocio.adapter=adapter
        binding.recyclerCitasNegocio.layoutManager= LinearLayoutManager(this)
        //viewModel.cargarCitas()
        viewModel.citas.observe(this) { lista ->
            adapter.actualizarDatos(lista)
        }
    }

    private fun reservarCita(cita: Cita) {
        cita.id_usuario = FirebaseAuth.getInstance().currentUser?.uid.toString()
        cita.estado = "ocupada"
        viewModel.editarEstadoCita(this,cita)
    }
    private fun startCitaPolling() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                while (true) {
                    withContext(Dispatchers.IO) {
                        viewModel.cargarCitasDelNegocio(negocio.id)

                        val listaCitas = viewModel.citas.value ?: emptyList()
                        val ahora = LocalDateTime.now()

                        listaCitas.forEach { cita ->
                            val fechaHoraCita = runCatching {
                                cita.fecha_cita?.let { LocalDateTime.parse(it) }
                            }.getOrNull()

                            if (fechaHoraCita != null &&
                                fechaHoraCita.isBefore(ahora) &&
                                cita.estado != "pasada"
                            ) {
                                cita.estado = "pasada"
                                viewModel.editarEstadoCita(this@CitasNegocioActivity, cita)
                            }
                        }
                    }
                    delay(10_000)
                }
            }
        }
    }
    private fun configurarBottomMenu(currentId: Int?) {
        if (currentId != null) {
            binding.bottomNavigation.selectedItemId = currentId
        }

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
                    true
                }
                R.id.nav_account -> {
                    if (currentId != R.id.nav_account) {
                        Toast.makeText(this, "Perfil aún no disponible", Toast.LENGTH_SHORT).show()
                    }
                    true
                }
                else -> false
            }
        }
    }
}