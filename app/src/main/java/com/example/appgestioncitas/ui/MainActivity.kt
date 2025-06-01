package com.example.appgestioncitas.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appgestioncitas.R
import com.example.appgestioncitas.databinding.ActivityMainBinding
import com.example.appgestioncitas.models.Negocio
import com.example.appgestioncitas.ui.adapters.MainAdapter

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private val adapter = MainAdapter(mutableListOf()) { negocio ->
        irCitas(negocio)
    }

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
        viewModel.cargarNegocios()
        setListeners()
        setRecycler()
    }

    private fun setRecycler() {
        binding.recyclerNegocios.adapter=adapter
        binding.recyclerNegocios.layoutManager=LinearLayoutManager(this)
        viewModel.cargarNegocios()
        viewModel.negocios.observe(this) { lista ->
            adapter.actualizarDatos(lista)
        }
    }

    private fun setListeners() {

    }
    private fun irCitas(negocio: Negocio) {
       val intent = Intent(this, CitasNegocioActivity::class.java)
        intent.putExtra("negocio", negocio)
        startActivity(intent)

    }

}