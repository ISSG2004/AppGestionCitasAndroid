package com.example.appgestioncitas.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.appgestioncitas.R
import com.example.appgestioncitas.data.repository.RepositoryNegocio
import com.example.appgestioncitas.databinding.CitaLayoutBinding
import com.example.appgestioncitas.models.Cita
import com.example.appgestioncitas.ui.adapters.CitasNegocioAdapter.MainHolder

class CitasPendientesAdapter(
    var listaCitas: List<Cita>,
    private val onItemClick: (Cita) -> Unit
) : RecyclerView.Adapter<CitasPendientesAdapter.CitasPendientesHolder>() {

    class CitasPendientesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var binding = CitaLayoutBinding.bind(itemView)
        fun bind(cita: Cita,onItemClick: (Cita) -> Unit) {
            val repository = RepositoryNegocio()

            // Procesar fecha y hora
            val fechaHora = cita.fecha_cita
            if (fechaHora != null && fechaHora.contains("T")) {
                val (fecha, horaCompleta) = fechaHora.split("T")
                val hora = horaCompleta.take(5) // Solo HH:mm
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitasPendientesHolder {
        val vista= LayoutInflater.from(parent.context).inflate(R.layout.cita_layout,parent,false)
        return CitasPendientesHolder(vista)
    }

    override fun getItemCount(): Int = listaCitas.size

    override fun onBindViewHolder(holder: CitasPendientesHolder, position: Int) {
        holder.bind(listaCitas[position],onItemClick)
    }

    fun actualizarDatos(nuevaLista: List<Cita>) {
        listaCitas = nuevaLista
        notifyDataSetChanged()
    }
}
