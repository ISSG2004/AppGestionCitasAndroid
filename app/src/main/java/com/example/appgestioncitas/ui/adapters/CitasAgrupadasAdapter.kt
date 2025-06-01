package com.example.appgestioncitas.ui.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.example.appgestioncitas.R
import com.example.appgestioncitas.databinding.ItemCitaSimpleBinding
import com.example.appgestioncitas.databinding.ItemFechaCitasBinding
import com.example.appgestioncitas.models.Cita
import com.google.android.material.card.MaterialCardView
import java.text.SimpleDateFormat
import java.util.*

class CitasAgrupadasAdapter : RecyclerView.Adapter<CitasAgrupadasAdapter.FechaViewHolder>() {

    private val citasAgrupadas = mutableMapOf<String, List<Cita>>()
    private val fechas = mutableListOf<String>()

    fun actualizarDatos(citas: List<Cita>) {
        citasAgrupadas.clear()
        fechas.clear()

        val sdfAgrupacion = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val sdfFirebase = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

        val agrupadas = citas.groupBy {
            val date = sdfFirebase.parse(it.fecha_cita)
            sdfAgrupacion.format(date!!)
        }

        citasAgrupadas.putAll(agrupadas)
        fechas.addAll(agrupadas.keys.sorted())

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FechaViewHolder {
        val binding = ItemFechaCitasBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FechaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FechaViewHolder, position: Int) {
        val fecha = fechas[position]
        val citas = citasAgrupadas[fecha] ?: emptyList()
        holder.bind(fecha, citas)
    }

    override fun getItemCount(): Int = fechas.size

    inner class FechaViewHolder(
        private val binding: ItemFechaCitasBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private var isExpanded = false

        fun bind(fecha: String, citas: List<Cita>) {
            binding.tvFecha.text = fecha
            binding.layoutCitas.removeAllViews()

            val sdfFirebase = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val sdfHora = SimpleDateFormat("HH:mm", Locale.getDefault())

            for (cita in citas) {
                val citaBinding = ItemCitaSimpleBinding.inflate(
                    LayoutInflater.from(binding.root.context),
                    binding.layoutCitas,
                    false
                )

                val hora = sdfFirebase.parse(cita.fecha_cita)
                val horaFormateada = sdfHora.format(hora!!)
                citaBinding.tvHora.text = "Hora: $horaFormateada"

                binding.layoutCitas.addView(citaBinding.root)
            }

            // Mostrar u ocultar según el estado
            binding.layoutCitas.visibility = if (isExpanded) ViewGroup.VISIBLE else ViewGroup.GONE

            // Click para expandir/colapsar
            binding.cardFecha.setOnClickListener {
                isExpanded = !isExpanded

                TransitionManager.beginDelayedTransition(binding.cardFecha, AutoTransition().apply {
                    duration = 200
                    interpolator = AccelerateDecelerateInterpolator()
                })

                binding.layoutCitas.visibility = if (isExpanded) ViewGroup.VISIBLE else ViewGroup.GONE
            }
        }
    }
}