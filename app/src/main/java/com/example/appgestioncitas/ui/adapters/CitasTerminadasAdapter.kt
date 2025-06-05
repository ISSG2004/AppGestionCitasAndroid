package com.example.appgestioncitas.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appgestioncitas.data.repository.RepositoryNegocio
import com.example.appgestioncitas.databinding.CitaLayoutBinding
import com.example.appgestioncitas.models.Cita

class CitasTerminadasAdapter(
    private var listaCitas: List<Cita>,
    private val onItemClick: (Cita) -> Unit
    ): RecyclerView.Adapter<CitasTerminadasAdapter.CitasPasadasHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitasPasadasHolder {
        val binding = CitaLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CitasPasadasHolder(binding)
    }

    override fun onBindViewHolder(holder: CitasPasadasHolder, position: Int) {
        holder.bind(listaCitas[position], onItemClick)
    }

    override fun getItemCount(): Int = listaCitas.size

    fun actualizarDatos(nuevaLista: List<Cita>) {
        listaCitas = nuevaLista
        notifyDataSetChanged()
    }

    class CitasPasadasHolder(private val binding: CitaLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cita: Cita, onItemClick: (Cita) -> Unit) {
            // Procesar fecha y hora
            val fechaHora = cita.fecha_cita
            if (fechaHora != null && fechaHora.contains("T")) {
                val (fecha, horaCompleta) = fechaHora.split("T")
                val hora = horaCompleta.take(5) // HH:mm
                binding.tvFechaCita.text = fecha
                binding.tvHoraFecha.text = hora
            } else {
                binding.tvFechaCita.text = "Fecha inválida"
                binding.tvHoraFecha.text = ""
            }

            // Cargar nombre del negocio
            cita.negocio_id?.let { negocioId ->
                val repository = RepositoryNegocio()
                repository.cargarNegocio(negocioId.toString()) { negocio ->
                    binding.tvNegocioCita.text = negocio?.nombre ?: "Negocio no encontrado"
                }
            }

            binding.btnAnular.setOnClickListener {
                onItemClick(cita)
            }
        }
    }

}