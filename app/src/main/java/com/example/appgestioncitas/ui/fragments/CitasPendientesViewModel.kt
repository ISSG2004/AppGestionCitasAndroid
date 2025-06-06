package com.example.appgestioncitas.ui.fragments

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appgestioncitas.data.repository.RepositoryCitas
import com.example.appgestioncitas.models.Cita
import com.google.firebase.auth.FirebaseAuth

class CitasPendientesViewModel : ViewModel() {

    private val repository = RepositoryCitas()
    private val _citasPendientes = MutableLiveData<List<Cita>>()
    val citasPendientes: LiveData<List<Cita>> get() = _citasPendientes
    private val _citasPasadas = MutableLiveData<List<Cita>>()
    val citasPasadas: LiveData<List<Cita>> get() = _citasPasadas
    private val _citas = MutableLiveData<List<Cita>>()
    val citas: LiveData<List<Cita>> get() = _citas
    fun cargarCitasPendientes() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid

        if (uid == null) {
            //Log.w("CitasPendientesVM", "Usuario no autenticado, UID es null")
            _citasPendientes.postValue(emptyList())
            return
        }

        repository.cargarCitas { listaCompleta ->
            //Log.d("CitasPendientesVM", "Total citas cargadas: ${listaCompleta.size}, UID actual: $uid")

            val pendientes = listaCompleta.filter {
                it.estado == "ocupada" && it.id_usuario == uid
            }

            //Log.d("CitasPendientesVM", "Citas pendientes encontradas: ${pendientes.size}")
            _citasPendientes.postValue(pendientes)
        }
    }
    fun cargarCitasTerminadas() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid

        if (uid == null) {
            //Log.w("CitasPendientesVM", "Usuario no autenticado, UID es null")
            _citasPasadas.postValue(emptyList())
            return
        }

        repository.cargarCitas { listaCompleta ->
            //Log.d("CitasPendientesVM", "Total citas cargadas: ${listaCompleta.size}, UID actual: $uid")

            val pasadas = listaCompleta.filter {
                it.estado == "pasada" && it.id_usuario == uid
            }

            //Log.d("CitasPendientesVM", "Citas pendientes encontradas: ${pendientes.size}")
            _citasPasadas.postValue(pasadas)
        }
    }
    fun editarEstadoCita(context: Context, cita: Cita) {
        repository.editarCita(context,cita)
    }
    fun cargarCitas() {
        repository.cargarCitas { citas ->
            _citas.postValue(citas)
        }
    }


}
