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
import com.example.appgestioncitas.databinding.ActivityMainBinding
import com.example.appgestioncitas.models.Negocio
import com.example.appgestioncitas.ui.adapters.MainAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private val adapter = MainAdapter(mutableListOf()) { negocio -> irCitas(negocio) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setRecycler()
        configurarBottomMenu(R.id.nav_home)
        startCitaPolling()
    }

    private fun setRecycler() {
        binding.recyclerNegocios.adapter=adapter
        binding.recyclerNegocios.layoutManager=LinearLayoutManager(this)
        viewModel.cargarNegocios()
        viewModel.negocios.observe(this) { lista ->
            adapter.actualizarDatos(lista)
        }
    }


    private fun irCitas(negocio: Negocio) {
        val intent = Intent(this, CitasNegocioActivity::class.java)
        intent.putExtra("negocio", negocio)
        startActivity(intent)
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
                        Toast.makeText(this, "Perfil aún no disponible", Toast.LENGTH_SHORT).show()
                    }
                    true
                }
                else -> false
            }
        }
    }

    private fun startCitaPolling() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                while (true) {
                    withContext(Dispatchers.IO) {
                        viewModel.cargarCitas()

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
                                viewModel.editarEstadoCita(this@MainActivity, cita)
                            }
                        }
                    }
                    delay(10_000)
                }
            }
        }
    }

}