package com.example.appgestioncitas.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appgestioncitas.R
import com.example.appgestioncitas.databinding.ActivityCitasNegocioBinding
import com.example.appgestioncitas.models.Negocio
import com.example.appgestioncitas.ui.adapters.CitasAgrupadasAdapter

class CitasNegocioActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCitasNegocioBinding
    private lateinit var adapter: CitasAgrupadasAdapter
    val negocio = intent.getSerializableExtra("negocio") as? Negocio

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
    }
    private fun setRecycler() {
        adapter = CitasAgrupadasAdapter()
        binding.recyclerCitasNegocio.layoutManager = LinearLayoutManager(this)
        binding.recyclerCitasNegocio.adapter = adapter
    }
}