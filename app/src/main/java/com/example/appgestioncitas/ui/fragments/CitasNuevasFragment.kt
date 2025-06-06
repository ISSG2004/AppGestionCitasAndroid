package com.example.appgestioncitas.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appgestioncitas.R
import com.example.appgestioncitas.databinding.FragmentCitasNuevasBinding
import com.example.appgestioncitas.models.Cita
import com.example.appgestioncitas.ui.adapters.CitasPendientesAdapter
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime


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
            cancelarCita(cita)
        }

        binding.rvCitaPendiente.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCitaPendiente.adapter = adapter

        viewModel.citasPendientes.observe(viewLifecycleOwner) { lista ->
            //Log.d("CitasFragment", "Lista recibida: ${lista.size}")
            adapter.actualizarDatos(lista)
        }

        startCitaPolling()
        viewModel.cargarCitasPendientes() // Método en el ViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun cancelarCita(cita: Cita) {
        cita.id_usuario = ""
        cita.estado = "disponible"
        viewModel.editarEstadoCita(requireContext(),cita)
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

                delay(30_000) // cada 30segs
            }
        }
    }
}

