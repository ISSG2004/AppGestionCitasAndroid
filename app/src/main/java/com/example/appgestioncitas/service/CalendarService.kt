package com.example.appgestioncitas.service
import com.example.appgestioncitas.providers.AuthProviderCalendar
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.model.*
import com.google.api.client.util.DateTime
import java.util.*
object CalendarService {
    // Inicializa el cliente de Google Calendar usando las credenciales del AuthProviderCalendar
    private val calendarService: Calendar = Calendar.Builder(
        AuthProviderCalendar.getHttpTransport(),
        AuthProviderCalendar.getJsonFactory(),
        AuthProviderCalendar.getCredentials()
    ).setApplicationName("AppPeluqueria").build()

    /**
     * Crea un evento en el calendario del usuario actual
     *
     * @param titulo Título del evento
     * @param descripcion Descripción del evento
     * @param fecha Fecha del evento en formato "YYYY-MM-DD"
     * @param horaInicio Hora de inicio en formato "HH:mm"
     * @param horaFin Hora de finalización en formato "HH:mm"
     */
    fun crearEvento(
        titulo: String,
        descripcion: String,
        fecha: String,
        horaInicio: String,
        horaFin: String
    ) {
        // Combinar fecha y hora para generar el formato requerido por Google (RFC 3339)
        val startDateTime = DateTime("${fecha}T${horaInicio}:00-05:00") // GMT-5 (ajustá según zona horaria)
        val endDateTime = DateTime("${fecha}T${horaFin}:00-05:00")

        // Crear objeto Event y setear los datos
        val evento = Event()
            .setSummary(titulo)
            .setDescription(descripcion)
            .setStart(EventDateTime().setDateTime(startDateTime))
            .setEnd(EventDateTime().setDateTime(endDateTime))

        // Insertar el evento en el calendario "primary" (el principal del usuario)
        val eventoCreado = calendarService.events().insert("primary", evento).execute()

        println("✅ Evento creado correctamente: ${eventoCreado.htmlLink}")
    }

    /**
     * Lista los próximos 10 eventos del calendario del usuario
     */
    fun listarEventosProximos() {
        val ahora = DateTime(System.currentTimeMillis())

        // Solicitud de eventos ordenados por hora de inicio
        val eventos = calendarService.events().list("primary")
            .setMaxResults(10)
            .setTimeMin(ahora)
            .setOrderBy("startTime")
            .setSingleEvents(true)
            .execute()

        val items = eventos.items
        if (items.isEmpty()) {
            println("No hay eventos próximos.")
        } else {
            println("📅 Próximos eventos:")
            for (evento in items) {
                val inicio = evento.start.dateTime ?: evento.start.date
                println("• ${evento.summary} - ${inicio}")
            }
        }
    }
}