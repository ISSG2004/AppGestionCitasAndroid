# AppGestionCitasAndroid

Aplicación Android nativa desarrollada en Kotlin para la gestión de citas, utilizando Firebase Authentication, Firebase Realtime Database y Google Sign-In.

## 📱 Características

- Autenticación con Firebase (correo y Google Sign-In)
- Gestión en tiempo real de citas con Firebase Realtime Database
- UI moderna con Material Design y ViewBinding
- Manejo de imágenes con Picasso
- Compatibilidad desde Android 10 (API 29) en adelante

## 🧰 Tecnologías y librerías

- Kotlin
- Android SDK (compileSdk 35, targetSdk 34, minSdk 29)
- Firebase Authentication y Realtime Database
- Google Play Services Auth
- Picasso
- AndroidX: Core, AppCompat, ConstraintLayout, Material Components
- ViewBinding

## 🚀 Instalación

### Requisitos

- Android Studio (Giraffe o superior recomendado)
- JDK 17+
- Conexión a internet para descargar dependencias

### Método 1: Ejecutar desde código

```bash
git clone https://github.com/ISSG2004/AppGestionCitasAndroid.git
cd AppGestionCitasAndroid
```

- Abrir el proyecto en Android Studio.
- Esperar a que sincronice Gradle.
- Conectar dispositivo Android o iniciar emulador.
- Ejecutar la app con Run (▶️).

### Método 2: Instalar APK

- Descargar el APK disponible en la sección de releases.
- Copiar el APK al dispositivo.
- Permitir instalación desde fuentes desconocidas.
- Instalar y abrir la aplicación.

## 📂 Estructura del proyecto

```
AppGestionCitasAndroid/
├── app/
│   ├── src/main/java/com/example/appgestioncitas/  # Código Kotlin
│   ├── src/main/res/                               # Recursos XML
│   └── src/main/AndroidManifest.xml
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## 🔧 Configuración Firebase

- Crear proyecto en Firebase Console.
- Habilitar Authentication (correo y Google).
- Configurar Realtime Database.
- Registrar SHA-1 para Google Sign-In.
- Añadir `google-services.json` en carpeta `app/`.

## 🧪 Testing

- JUnit para pruebas unitarias.
- Espresso para UI tests.

## 📄 Licencia

Proyecto bajo MIT License.
