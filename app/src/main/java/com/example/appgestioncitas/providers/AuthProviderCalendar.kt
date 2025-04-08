package com.example.appgestioncitas.providers
import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.firebase.auth.FirebaseAuth
import java.io.File
import java.io.FileReader
object AuthProviderCalendar {
    private val jsonFactory: JsonFactory = GsonFactory.getDefaultInstance()
    private val httpTransport = GoogleNetHttpTransport.newTrustedTransport()

    // Obtener UID del usuario autenticado por Firebase
    private fun getCurrentFirebaseUid(): String {
        val user = FirebaseAuth.getInstance().currentUser
        return user?.uid ?: throw Exception("Usuario no autenticado en Firebase.")
    }

    // Obtener credenciales OAuth para el usuario actual
    fun getCredentials(): Credential {
        val firebaseUid = getCurrentFirebaseUid()

        val credentialsFile = File("credentials.json") // Archivo descargado de Google Cloud Console
        val clientSecrets = GoogleClientSecrets.load(jsonFactory, FileReader(credentialsFile))

        val flow = GoogleAuthorizationCodeFlow.Builder(
            httpTransport, jsonFactory, clientSecrets,
            listOf("https://www.googleapis.com/auth/calendar")
        )
            .setDataStoreFactory(FileDataStoreFactory(File("tokens/$firebaseUid")))
            .setAccessType("offline")
            .build()

        return AuthorizationCodeInstalledApp(flow, LocalServerReceiver()).authorize(firebaseUid)
    }

    fun getHttpTransport() = httpTransport
    fun getJsonFactory() = jsonFactory
}