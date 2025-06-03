package com.example.appgestioncitas.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appgestioncitas.R
import com.example.appgestioncitas.databinding.FragmentCitasNuevasBinding
import com.example.appgestioncitas.ui.adapters.CitasPendientesAdapter


class CitasNuevasFragment : Fragment() {

    private var _binding: FragmentCitasNuevasBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CitasPendientesViewModel by viewModels()
    private lateinit var adapter: CitasPendientesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCitasNuevasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CitasPendientesAdapter(mutableListOf()) { cita ->
            // Acción cuando se hace click sobre una cita (si aplica)
        }

        binding.rvCitaPendiente.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCitaPendiente.adapter = adapter

        viewModel.citasPendientes.observe(viewLifecycleOwner) { lista ->
            adapter.actualizarDatos(lista)
        }

        viewModel.cargarCitasPendientes() // Método en el ViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun cancelarCita(citaId: Int) {
        //Logica para cancelar cita
    }
}

