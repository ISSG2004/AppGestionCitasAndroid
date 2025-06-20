package com.example.appgestioncitas.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appgestioncitas.R
import com.example.appgestioncitas.databinding.CitaLayoutBinding
import com.example.appgestioncitas.databinding.CitaNegocioLayoutBinding
import com.example.appgestioncitas.databinding.NegocioLayoutBinding
import com.example.appgestioncitas.models.Cita
import com.example.appgestioncitas.models.Negocio
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CitasNegocioAdapter(
    var listaCitas: List<Cita>,
    private val onItemClick: (Cita) -> Unit
) : RecyclerView.Adapter<CitasNegocioAdapter.MainHolder>() {

    class MainHolder(v:View) :
        RecyclerView.ViewHolder(v) {
        private var binding= CitaNegocioLayoutBinding.bind(v)

        fun bind(cita: Cita, onItemClick: (Cita) -> Unit) {
            val formatoEntrada = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val formatoSalida = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

            val fecha = try {
                formatoEntrada.parse(cita.fecha_cita)
            } catch (e: Exception) {
                null
            }
            val fechaFormateada = fecha?.let { formatoSalida.format(it) } ?: cita.fecha_cita

            binding.tvHoraCita.text = fechaFormateada

            binding.btnReservar.setOnClickListener {
                onItemClick(cita)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val vista= LayoutInflater.from(parent.context).inflate(R.layout.cita_negocio_layout,parent,false)
        return MainHolder(vista)
    }

    override fun getItemCount() = listaCitas.size

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bind(listaCitas[position],onItemClick)
    }

    fun actualizarDatos(listaActualizada: List<Cita>) {
        listaCitas = listaActualizada
        notifyDataSetChanged()
    }
}

