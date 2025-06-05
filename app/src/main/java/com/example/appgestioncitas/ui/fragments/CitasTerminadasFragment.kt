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
import com.example.appgestioncitas.databinding.FragmentCitasTerminadasBinding
import com.example.appgestioncitas.ui.adapters.CitasPendientesAdapter
import com.example.appgestioncitas.ui.adapters.CitasTerminadasAdapter


class CitasTerminadasFragment : Fragment() {
    private var _binding: FragmentCitasTerminadasBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CitasPendientesViewModel by viewModels()
    private lateinit var adapter: CitasTerminadasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentCitasTerminadasBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CitasTerminadasAdapter(mutableListOf()) { cita ->
            //cancelarCita(cita)
        }

        binding.rvCitasPasadas.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCitasPasadas.adapter = adapter

        viewModel.citasPendientes.observe(viewLifecycleOwner) { lista ->
            //Log.d("CitasFragment", "Lista recibida: ${lista.size}")
            adapter.actualizarDatos(lista)
        }


        viewModel.cargarCitasPendientes() // Método en el ViewModel
    }

}