# Análisis del Módulo CORE

## Resumen Ejecutivo

El módulo `core` contiene funcionalidades transversales y herramientas compartidas por toda la aplicación, incluyendo configuración de red, gestión de preferencias y utilidades. Este documento identifica áreas de mejora en nomenclatura, estructura y mejores prácticas.

---

## 1. Nomenclatura y Convenciones

### 1.1 Problemas Identificados

#### ❌ Inconsistencias en Nombres de Archivos
- **Problema**: Mezcla de convenciones en nombres de archivos
  - `consoleExtensions.kt` (camelCase) vs `LocationHelper.kt` (PascalCase)
  - `ModelPreference.kt` vs `ModelSelectorForm.kt` (diferentes patrones)
  - `preferenceModule.kt` (camelCase) vs `NetworkModule.kt` (PascalCase)

**Recomendación**: Estandarizar a PascalCase para todos los archivos:
- `ConsoleExtensions.kt`
- `PreferenceModule.kt`

#### ❌ Nomenclatura de Clases y Objetos
- **Problema**: Inconsistencias en nombres
  - `ModelPreference` (prefijo "Model" innecesario para constantes)
  - `ModelSelectorForm` (debería ser más descriptivo)
  - `PreferenceRepository` vs `PreferenceUseCase` (confusión entre responsabilidades)

**Recomendación**: 
- Renombrar `ModelPreference` a `PreferenceKeys` o `PreferenceConstants`
- Renombrar `ModelSelectorForm` a `FormSelector` o `SelectorForm`
- Clarificar la diferencia entre `PreferenceRepository` y `PreferenceUseCase`

#### ❌ Nomenclatura de API Calls
- **Problema**: Nombres inconsistentes
  - `GetDataUserApiCall` vs `LoginApiCall` (diferentes patrones)
  - Algunos usan `Get*`, otros `Register*`, otros solo el nombre

**Recomendación**: Estandarizar a un patrón consistente:
- `Get*Api` para operaciones GET
- `Post*Api` para operaciones POST
- `Put*Api` para operaciones PUT
- `Delete*Api` para operaciones DELETE

O mejor aún, usar interfaces con nombres descriptivos:
```kotlin
interface AuthApi {
    suspend fun login(request: LoginRequest): Response<LoginResponse>
    suspend fun register(request: RegisterRequest): Response<RegisterResponse>
    suspend fun getUserData(): Response<UserDataResponse>
}
```

---

## 2. Estructura y Organización

### 2.1 Organización de Paquetes

#### ✅ Bien Organizado
```
core/src/main/java/com/mx/liftechnology/core/
├── model/                    # Modelos compartidos
├── network/                  # Configuración de red
│   ├── apiCall/             # Llamadas a API
│   ├── environment/         # Configuración de entorno
│   └── NetworkModule.kt     # Módulo de DI
├── preference/              # Gestión de preferencias
└── util/                    # Utilidades
```

#### ⚠️ Áreas de Mejora

**Problema 1**: Los API Calls están organizados por dominio, pero cada uno es un archivo separado
```
apiCall/
├── auth/
│   ├── GetDataUserApiCall.kt
│   ├── LoginApiCall.kt
│   └── RegisterUserApiCall.kt
├── student/
│   ├── DeleteStudentApiCall.kt
│   ├── EditStudentApiCall.kt
│   └── ...
```

**Recomendación**: Agrupar por dominio en interfaces:
```kotlin
// AuthApi.kt
interface AuthApi {
    suspend fun login(request: LoginRequest): Response<LoginResponse>
    suspend fun register(request: RegisterRequest): Response<RegisterResponse>
    suspend fun getUserData(): Response<UserDataResponse>
}

// StudentApi.kt
interface StudentApi {
    suspend fun getStudents(cycleSchoolId: Int): Response<List<StudentResponse>>
    suspend fun registerStudent(request: RegisterStudentRequest): Response<StudentResponse>
    suspend fun editStudent(id: Int, request: EditStudentRequest): Response<StudentResponse>
    suspend fun deleteStudent(id: Int): Response<Unit>
}
```

**Problema 2**: Utilidades mezcladas sin categorización clara
```
util/
├── consoleExtensions.kt
├── LocationHelper.kt
├── ModelSelectorForm.kt
├── SessionManager.kt
└── VoiceRecognitionManager.kt
```

**Recomendación**: Organizar por categoría:
```
util/
├── extension/
│   └── ConsoleExtensions.kt
├── location/
│   └── LocationHelper.kt
├── session/
│   └── SessionManager.kt
├── voice/
│   └── VoiceRecognitionManager.kt
└── form/
    └── FormSelector.kt
```

---

## 3. Arquitectura y Patrones

### 3.1 Network Module

#### ✅ Buenas Prácticas Aplicadas
- Uso de Retrofit para comunicación HTTP
- Interceptores para autenticación y logging
- Configuración de timeouts
- Uso de Gson para serialización

#### ⚠️ Áreas de Mejora

**Problema 1**: Configuración de logging en producción
```kotlin
single {
    val logging = HttpLoggingInterceptor { message ->
        if (!message.startsWith("<!DOCTYPE html>")) {
            timber.log.Timber.d(message)
        }
    }.apply {
        level = HttpLoggingInterceptor.Level.BODY  // ⚠️ Siempre en BODY
    }
    logging
}
```

**Recomendación**: Configurar según el tipo de build:
```kotlin
single {
    val logging = HttpLoggingInterceptor { message ->
        if (!message.startsWith("<!DOCTYPE html>")) {
            timber.log.Timber.d(message)
        }
    }.apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }
    logging
}
```

**Problema 2**: Timeouts hardcodeados
```kotlin
.connectTimeout(30, TimeUnit.SECONDS)
.readTimeout(30, TimeUnit.SECONDS)
.writeTimeout(30, TimeUnit.SECONDS)
```

**Recomendación**: Mover a configuración:
```kotlin
object NetworkConfig {
    const val CONNECT_TIMEOUT_SECONDS = 30L
    const val READ_TIMEOUT_SECONDS = 30L
    const val WRITE_TIMEOUT_SECONDS = 30L
}
```

**Problema 3**: Falta de manejo de errores de red centralizado
- Cada API Call maneja errores de forma diferente

**Recomendación**: Crear un interceptor o wrapper para manejo de errores:
```kotlin
class ErrorHandlingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        
        if (!response.isSuccessful) {
            // Manejo centralizado de errores
            handleError(response)
        }
        
        return response
    }
}
```

### 3.2 Preference Management

#### ✅ Buenas Prácticas Aplicadas
- Uso de `EncryptedSharedPreferences` para seguridad
- Abstracción con `PreferenceRepository` y `PreferenceUseCase`
- Uso de constantes para keys

#### ⚠️ Áreas de Mejora

**Problema 1**: Confusión entre `PreferenceRepository` y `PreferenceUseCase`
- Ambos parecen hacer lo mismo

**Recomendación**: Clarificar responsabilidades:
- `PreferenceRepository`: Acceso directo a SharedPreferences
- `PreferenceUseCase`: Lógica de negocio relacionada con preferencias

O mejor aún, eliminar una de las capas si no es necesaria.

**Problema 2**: `ModelPreference` como objeto con constantes
- El nombre "Model" es confuso para constantes

**Recomendación**: Renombrar a `PreferenceKeys`:
```kotlin
object PreferenceKeys {
    const val ACCESS_TOKEN = "access_token"
    const val REFRESH_TOKEN = "refresh_token"
    // ...
}
```

**Problema 3**: Falta de tipos seguros para preferencias
- Todas las preferencias se manejan como strings

**Recomendación**: Crear un sistema de tipos seguros:
```kotlin
sealed class Preference<T> {
    abstract val key: String
    abstract fun get(prefs: SharedPreferences): T?
    abstract fun set(prefs: SharedPreferences, value: T)
    
    object AccessToken : Preference<String>() {
        override val key = "access_token"
        override fun get(prefs: SharedPreferences) = prefs.getString(key, null)
        override fun set(prefs: SharedPreferences, value: String) {
            prefs.edit().putString(key, value).apply()
        }
    }
}
```

### 3.3 Utilidades

#### ✅ Buenas Prácticas Aplicadas
- Helpers bien encapsulados
- Uso de corrutinas donde es apropiado

#### ⚠️ Áreas de Mejora

**Problema 1**: `SessionManager` muy simple
```kotlin
class SessionManager {
    private val _sessionExpired = MutableSharedFlow<Boolean>()
    val sessionExpired = _sessionExpired.asSharedFlow()
    
    suspend fun notifySessionExpired() {
        _sessionExpired.emit(true)
    }
    
    suspend fun resetSessionExpired() {
        _sessionExpired.emit(false)
    }
}
```

**Recomendación**: Expandir funcionalidad o considerar si es necesario:
- Agregar lógica de validación de sesión
- Agregar manejo de refresh tokens
- Considerar usar un Use Case en lugar de un Manager

**Problema 2**: `LocationHelper` puede tener problemas de permisos
- No se ve manejo explícito de permisos

**Recomendación**: Agregar validación de permisos:
```kotlin
suspend fun getCurrentLocation(): LocationResult {
    if (!hasLocationPermission()) {
        return LocationResult.Error("Location permission not granted")
    }
    // ...
}
```

**Problema 3**: Extensiones en `consoleExtensions.kt`
- El nombre no es descriptivo

**Recomendación**: Renombrar a `LogExtensions.kt` o `TimberExtensions.kt` si usa Timber.

---

## 4. Manejo de Errores

### 4.1 Estado Actual

#### ⚠️ Problemas Identificados

**Problema 1**: Manejo de errores disperso
- Cada interceptor maneja errores de forma diferente
- No hay un sistema centralizado

**Recomendación**: Crear un sistema centralizado:
```kotlin
object NetworkErrorHandler {
    fun handleError(exception: Exception): NetworkModelError {
        return when (exception) {
            is SocketTimeoutException -> NetworkModelError.TIMEOUT
            is UnknownHostException -> NetworkModelError.NO_INTERNET
            is HttpException -> mapHttpError(exception)
            else -> NetworkModelError.UNKNOWN
        }
    }
}
```

**Problema 2**: `ConnectionErrorInterceptor` no está claro su propósito
- El nombre sugiere que maneja errores, pero no está claro cómo

**Recomendación**: Documentar mejor o renombrar para clarificar su propósito.

---

## 5. Seguridad

### 5.1 Estado Actual

#### ✅ Buenas Prácticas Aplicadas
- Uso de `EncryptedSharedPreferences` para datos sensibles
- Interceptor de autenticación para tokens

#### ⚠️ Áreas de Mejora

**Problema 1**: Tokens almacenados en SharedPreferences
- Aunque están encriptados, considerar usar `AndroidKeystore` para mayor seguridad

**Recomendación**: Para tokens muy sensibles, considerar:
```kotlin
class SecureTokenStorage(private val context: Context) {
    private val keyStore = KeyStore.getInstance("AndroidKeyStore")
    
    fun saveToken(token: String) {
        // Usar AndroidKeystore
    }
}
```

**Problema 2**: IMEI usado para identificación
```kotlin
imei = Build.FINGERPRINT + Build.ID
```
- Esto puede no ser único y puede cambiar

**Recomendación**: Usar un identificador más confiable:
```kotlin
val deviceId = Settings.Secure.getString(
    context.contentResolver,
    Settings.Secure.ANDROID_ID
)
```

---

## 6. Testing

### 6.1 Estado Actual

#### ❌ Problema Crítico
- **No se encontraron tests para utilidades**
- **No se encontraron tests para módulos de red**
- **No se encontraron tests para gestión de preferencias**

**Recomendación**: Implementar tests:
- Tests unitarios para utilidades
- Tests de integración para módulos de red (usando MockWebServer)
- Tests unitarios para gestión de preferencias

---

## 7. Documentación

### 7.1 Estado Actual

#### ✅ Bien Documentado
- KDoc en la mayoría de clases
- Comentarios descriptivos en módulos

#### ⚠️ Áreas de Mejora

**Problema**: Algunos archivos no tienen documentación completa

**Recomendación**: Agregar documentación a:
- Extensiones
- Helpers
- Interceptores

---

## 8. Configuración de Entorno

### 8.1 Estado Actual

#### ⚠️ Problemas Identificados

**Problema**: `Environment.kt` probablemente tiene la URL hardcodeada

**Recomendación**: Usar BuildConfig para diferentes entornos:
```kotlin
object Environment {
    val BASE_URL: String
        get() = BuildConfig.BASE_URL
    
    val API_VERSION: String
        get() = BuildConfig.API_VERSION
}
```

Y en `build.gradle.kts`:
```kotlin
buildTypes {
    debug {
        buildConfigField("String", "BASE_URL", "\"https://dev.api.example.com\"")
    }
    release {
        buildConfigField("String", "BASE_URL", "\"https://api.example.com\"")
    }
}
```

---

## 9. Recomendaciones Prioritarias

### 🔴 Alta Prioridad
1. **Estandarizar nomenclatura de archivos** (PascalCase)
2. **Agrupar API Calls en interfaces** por dominio
3. **Configurar logging según tipo de build** (DEBUG/RELEASE)
4. **Clarificar responsabilidades** de PreferenceRepository vs PreferenceUseCase
5. **Implementar tests** para utilidades y módulos de red

### 🟡 Media Prioridad
1. **Reorganizar utilidades** por categoría
2. **Crear sistema centralizado de manejo de errores**
3. **Renombrar ModelPreference** a PreferenceKeys
4. **Agregar validación de permisos** en LocationHelper
5. **Mejorar seguridad** de almacenamiento de tokens

### 🟢 Baja Prioridad
1. **Expandir funcionalidad de SessionManager**
2. **Mejorar documentación** de extensiones y helpers
3. **Optimizar configuración de timeouts**
4. **Revisar y optimizar** interceptores

---

## 10. Ejemplos de Refactorización

### Ejemplo 1: Agrupar API Calls

**Antes:**
```kotlin
// LoginApiCall.kt
interface LoginApiCall {
    @POST("auth/login")
    suspend fun callApi(@Body request: RequestLogin): Response<ResponseLogin>
}

// RegisterUserApiCall.kt
interface RegisterUserApiCall {
    @POST("auth/register")
    suspend fun callApi(@Body request: RequestRegisterUser): Response<ResponseRegisterUser>
}
```

**Después:**
```kotlin
// AuthApi.kt
interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
    
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>
    
    @GET("auth/user")
    suspend fun getUserData(): Response<UserDataResponse>
}
```

### Ejemplo 2: Renombrar ModelPreference

**Antes:**
```kotlin
object ModelPreference {
    const val ACCESS_TOKEN = "access_token"
    const val REFRESH_TOKEN = "refresh_token"
}
```

**Después:**
```kotlin
object PreferenceKeys {
    const val ACCESS_TOKEN = "access_token"
    const val REFRESH_TOKEN = "refresh_token"
}
```

### Ejemplo 3: Configurar Logging por Build Type

**Antes:**
```kotlin
single {
    HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}
```

**Después:**
```kotlin
single {
    HttpLoggingInterceptor { message ->
        if (!message.startsWith("<!DOCTYPE html>")) {
            Timber.d(message)
        }
    }.apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }
}
```

---

## Conclusión

El módulo CORE está bien estructurado pero necesita mejoras en:
- **Nomenclatura consistente**
- **Organización de API Calls**
- **Configuración por entorno**
- **Manejo centralizado de errores**
- **Testing**

Las mejoras propuestas mejorarán la mantenibilidad, seguridad y testabilidad del código.

