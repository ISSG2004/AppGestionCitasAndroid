package com.example.appgestioncitas.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appgestioncitas.R
import com.example.appgestioncitas.databinding.ActivityCitasNegocioBinding
import com.example.appgestioncitas.models.Cita
import com.example.appgestioncitas.models.Negocio
import com.example.appgestioncitas.ui.adapters.CitasNegocioAdapter

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
        cita.usuario_id = ""//rellenar con el id del usuario con un getInstance de firebase
        cita.estado = "ocupada"
        viewModel.editarEstadoCita(this,cita)
    }
}