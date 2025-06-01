package com.example.appgestioncitas.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appgestioncitas.R
import com.example.appgestioncitas.databinding.CitaLayoutBinding
import com.example.appgestioncitas.databinding.NegocioLayoutBinding
import com.example.appgestioncitas.models.Cita
import com.example.appgestioncitas.models.Negocio

class MainAdapter(
    var listaNegocios: List<Negocio>,
    private val onItemClick: (Negocio) -> Unit
) : RecyclerView.Adapter<MainAdapter.MainHolder>() {

    class MainHolder(v:View) :
        RecyclerView.ViewHolder(v) {
        private var binding= NegocioLayoutBinding.bind(v)
        fun bind(negocio: Negocio,onItemClick: (Negocio) -> Unit) {
            binding.tvNegocio.text=negocio.nombre
            binding.btnVerCitas.setOnClickListener {
                onItemClick(negocio)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val vista= LayoutInflater.from(parent.context).inflate(R.layout.negocio_layout,parent,false)
        return MainHolder(vista)
    }

    override fun getItemCount() = listaNegocios.size

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bind(listaNegocios[position],onItemClick)
    }

    fun actualizarDatos(listaActualizada: List<Negocio>) {
        listaNegocios = listaActualizada
        notifyDataSetChanged()
    }
}

