package com.example.appgestioncitas.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appgestioncitas.R
import com.example.appgestioncitas.databinding.FragmentCitasNuevasBinding
import com.example.appgestioncitas.databinding.FragmentCitasTerminadasBinding
import com.example.appgestioncitas.models.Cita
import com.example.appgestioncitas.ui.adapters.CitasPendientesAdapter
import com.example.appgestioncitas.ui.adapters.CitasTerminadasAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime


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
            valorarCita(cita)
        }

        binding.rvCitasPasadas.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCitasPasadas.adapter = adapter
        viewModel.citasPasadas.observe(viewLifecycleOwner) { lista ->
            adapter.actualizarDatos(lista)
        }

        startCitaPolling()
    }

    private fun startCitaPolling() {
        lifecycleScope.launch {
            while (isAdded) {
                viewModel.cargarCitas()

                val listaCitas = viewModel.citas.value ?: emptyList()
                val ahora = LocalDateTime.now()

                for (cita in listaCitas) {
                    val fechaHoraCita = cita.fecha_cita?.let {
                        try {
                            LocalDateTime.parse(it)
                        } catch (e: Exception) {
                            null
                        }
                    }

                    if (fechaHoraCita != null && fechaHoraCita.isBefore(ahora) && cita.estado != "pasada") {
                        cita.estado = "pasada"
                        viewModel.editarEstadoCita(requireContext(), cita)
                    }
                }

                viewModel.cargarCitasTerminadas()

                delay(10_000) // cada 30 segs
            }
        }
    }
    private fun valorarCita(cita: Cita) {
        Toast.makeText(requireContext(), "Función no disponible", Toast.LENGTH_SHORT).show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
