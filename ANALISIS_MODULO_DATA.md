# Análisis del Módulo DATA

## Resumen Ejecutivo

El módulo `data` es responsable de la gestión de datos, implementando los repositorios que acceden a fuentes de datos (API remota, base de datos local, etc.). Este documento identifica áreas de mejora en nomenclatura, estructura, organización y mejores prácticas.

---

## 1. Nomenclatura y Convenciones

### 1.1 Problemas Identificados

#### ❌ Inconsistencias en Nombres de Repositorios
- **Problema**: Mezcla de convenciones en nombres
  - `GetStudentRepository` vs `RegisterStudentRepository` (diferentes prefijos)
  - `GetListFormativeFieldRepository` vs `GetListWotyFofiRepository` (inconsistencia en "List")
  - `DeleteFormativeFieldRepository` vs `EditStudentRepository` (diferentes verbos)

**Recomendación**: Estandarizar a un patrón consistente:
- `*Repository` para interfaces
- `*RepositoryImpl` para implementaciones
- Usar verbos consistentes: `Get`, `Create`, `Update`, `Delete`

#### ❌ Nomenclatura de Modelos de Datos
- **Problema**: Todos los modelos usan el prefijo `Model*Data`
  - `ModelStudentData`
  - `ModelLoginData`
  - `ModelFormativeFieldData`

**Recomendación**: Simplificar nombres (el prefijo "Model" es redundante):
- `StudentData`
- `LoginData`
- `FormativeFieldData`

O mejor aún, usar nombres más descriptivos:
- `StudentEntity`
- `LoginResponse`
- `FormativeFieldEntity`

#### ❌ Nomenclatura de Mappers
- **Problema**: Nombres inconsistentes
  - `StudentDataToDomainMapper` (muy largo)
  - `AuthDataToDomainMapper`
  - `CalendarDataToDomainMapper`

**Recomendación**: Simplificar y estandarizar:
- `StudentMapper`
- `AuthMapper`
- `CalendarMapper`

O usar funciones de extensión directamente en los modelos.

---

## 2. Estructura y Organización

### 2.1 Organización de Paquetes

#### ✅ Bien Organizado
```
data/src/main/java/com/mx/liftechnology/data/
├── mapper/                    # Mappers de datos a dominio
├── model/                     # Modelos de datos
│   ├── auth/
│   ├── evaluation/
│   ├── formativeField/
│   ├── schoolCycle/
│   └── student/
├── repository/                # Repositorios
│   ├── auth/
│   ├── evaluation/
│   ├── formativeField/
│   ├── schoolCycle/
│   └── student/
└── util/                      # Utilidades
```

#### ⚠️ Áreas de Mejora

**Problema 1**: Repositorios muy granulares
- Cada operación tiene su propio repositorio
- `GetStudentRepository`, `RegisterStudentRepository`, `EditStudentRepository`, `DeleteStudentRepository`

**Recomendación**: Agrupar por entidad:
```kotlin
// StudentRepository.kt
interface StudentRepository {
    suspend fun getStudents(cycleSchoolId: Int): ModelResult<List<StudentData>, NetworkModelError>
    suspend fun getStudent(id: Int): ModelResult<StudentData, NetworkModelError>
    suspend fun createStudent(request: CreateStudentRequest): ModelResult<StudentData, NetworkModelError>
    suspend fun updateStudent(id: Int, request: UpdateStudentRequest): ModelResult<StudentData, NetworkModelError>
    suspend fun deleteStudent(id: Int): ModelResult<Unit, NetworkModelError>
}
```

**Problema 2**: Mappers como objetos con funciones de extensión
- Cada mapper es un objeto con múltiples funciones

**Recomendación**: Usar funciones de extensión directamente en los modelos:
```kotlin
// StudentData.kt
fun StudentData.toDomain(): StudentDomain {
    return StudentDomain(...)
}

fun List<StudentData>.toDomain(): List<StudentDomain> {
    return map { it.toDomain() }
}
```

O mantener mappers pero simplificarlos:
```kotlin
// StudentMapper.kt
object StudentMapper {
    fun StudentData.toDomain() = StudentDomain(...)
    fun List<StudentData>.toDomain() = map { it.toDomain() }
}
```

---

## 3. Arquitectura y Patrones

### 3.1 Repositorios

#### ✅ Buenas Prácticas Aplicadas
- Uso de interfaces (`fun interface`)
- Implementaciones separadas
- Uso de `ModelResult` para manejo de errores
- Mapeo de datos a dominio

#### ⚠️ Áreas de Mejora

**Problema 1**: Repositorios como `fun interface`
```kotlin
fun interface LoginRepository {
    suspend fun executeLogin(...): ModelResult<ModelLoginData, NetworkModelError>
}
```

**Recomendación**: Usar interfaces normales si hay múltiples métodos:
```kotlin
interface LoginRepository {
    suspend fun login(...): ModelResult<LoginData, NetworkModelError>
    suspend fun logout(): ModelResult<Unit, NetworkModelError>
}
```

**Problema 2**: Nombres de métodos inconsistentes
- `executeLogin`, `executeRegisterUser`, `executeGetListStudent`
- El prefijo "execute" es redundante

**Recomendación**: Simplificar nombres:
- `login`, `register`, `getStudents`

**Problema 3**: Manejo de errores repetitivo
```kotlin
return try {
    val response = loginApiCall.callApi(request)
    if (response.isSuccessful && response.body()?.data != null) {
        SuccessResult(response.body()?.data!!.mapperToGetLoginData())
    } else {
        ErrorResult(NetworkException.handleException(HttpException(response)))
    }
} catch (e: Exception) {
    ErrorResult(NetworkException.handleException(e))
}
```

**Recomendación**: Crear una función de extensión para simplificar:
```kotlin
suspend fun <T, R> Call<T>.executeOrError(
    mapper: (T) -> R
): ModelResult<R, NetworkModelError> {
    return try {
        val response = this.execute()
        if (response.isSuccessful && response.body() != null) {
            SuccessResult(mapper(response.body()!!))
        } else {
            ErrorResult(NetworkException.handleException(HttpException(response)))
        }
    } catch (e: Exception) {
        ErrorResult(NetworkException.handleException(e))
    }
}

// Uso:
override suspend fun login(...): ModelResult<LoginData, NetworkModelError> {
    return authApi.login(request).executeOrError { it.toLoginData() }
}
```

**Problema 4**: Uso de `!!` (not-null assertion)
```kotlin
SuccessResult(response.body()?.data!!.mapperToGetLoginData())
```

**Recomendación**: Evitar `!!` y usar manejo seguro:
```kotlin
val body = response.body()?.data
if (body != null) {
    SuccessResult(body.mapperToGetLoginData())
} else {
    ErrorResult(NetworkModelError.EMPTY)
}
```

### 3.2 Modelos de Datos

#### ✅ Buenas Prácticas Aplicadas
- Modelos de datos separados de modelos de dominio
- Uso de data classes

#### ⚠️ Áreas de Mejora

**Problema 1**: Modelos con muchos campos opcionales
```kotlin
data class ModelStudentData(
    val curp: String?,
    val name: String?,
    val lastName: String?,
    val secondLastName: String?,
    val birthday: String?,
    val phoneNumber: String?,
    val userId: Int?,
    val studentId: Int?,
    val isActive: Boolean?,
)
```

**Recomendación**: Considerar usar valores por defecto o crear modelos específicos para diferentes contextos:
```kotlin
data class StudentData(
    val studentId: Int,
    val name: String,
    val lastName: String,
    val secondLastName: String? = null,
    val curp: String? = null,
    val birthday: String? = null,
    val phoneNumber: String? = null,
    val userId: Int? = null,
    val isActive: Boolean = true,
)
```

**Problema 2**: Falta de validación en modelos
- No se valida que los datos sean correctos

**Recomendación**: Agregar validaciones básicas:
```kotlin
data class StudentData(...) {
    init {
        require(studentId > 0) { "Student ID must be positive" }
        require(name.isNotBlank()) { "Name cannot be blank" }
    }
}
```

### 3.3 Mappers

#### ✅ Buenas Prácticas Aplicadas
- Mappers centralizados
- Uso de funciones de extensión

#### ⚠️ Áreas de Mejora

**Problema 1**: Mappers muy grandes
- `StudentDataToDomainMapper` tiene múltiples funciones

**Recomendación**: Dividir o usar funciones de extensión en los modelos:
```kotlin
// StudentData.kt
fun StudentData.toDomain(): StudentDomain {
    return StudentDomain(
        studentId = studentId,
        name = name,
        // ...
    )
}

fun List<StudentData>.toDomain(): List<StudentDomain> {
    return map { it.toDomain() }
}
```

**Problema 2**: Nombres de funciones inconsistentes
- `mapperToModelStudent`, `mapperToModelListStudent`, `mapperToGetLoginData`

**Recomendación**: Estandarizar a `toDomain()`:
```kotlin
fun StudentData.toDomain(): StudentDomain
fun List<StudentData>.toDomain(): List<StudentDomain>
fun LoginData.toDomain(): LoginDomain
```

**Problema 3**: Falta de manejo de nulos
- Los mappers no manejan casos donde los datos pueden ser nulos

**Recomendación**: Agregar manejo seguro de nulos:
```kotlin
fun StudentData?.toDomain(): StudentDomain? {
    return this?.let {
        StudentDomain(
            studentId = studentId,
            name = name ?: "",
            // ...
        )
    }
}
```

---

## 4. Manejo de Errores

### 4.1 Estado Actual

#### ✅ Buenas Prácticas Aplicadas
- Uso de `ModelResult` para resultados
- Separación entre errores de red y errores locales
- `NetworkException.handleException` para mapeo de errores

#### ⚠️ Áreas de Mejora

**Problema 1**: Manejo de errores repetitivo en cada repositorio
- Cada repositorio tiene el mismo código de manejo de errores

**Recomendación**: Crear funciones de extensión o wrappers:
```kotlin
suspend fun <T> safeApiCall(
    apiCall: suspend () -> Response<T>
): ModelResult<T, NetworkModelError> {
    return try {
        val response = apiCall()
        if (response.isSuccessful && response.body() != null) {
            SuccessResult(response.body()!!)
        } else {
            ErrorResult(NetworkException.handleException(HttpException(response)))
        }
    } catch (e: Exception) {
        ErrorResult(NetworkException.handleException(e))
    }
}
```

**Problema 2**: `ExceptionHandler` y `ConvertError` no están claros
- No se ve cómo se usan estos archivos

**Recomendación**: Documentar o consolidar en un solo lugar.

---

## 5. Testing

### 5.1 Estado Actual

#### ❌ Problema Crítico
- **No se encontraron tests para repositorios**
- **No se encontraron tests para mappers**
- **No se encontraron tests para modelos**

**Recomendación**: Implementar tests:
- Tests unitarios para repositorios (usando mocks de API)
- Tests unitarios para mappers
- Tests de integración para flujos completos

Ejemplo:
```kotlin
class StudentRepositoryTest {
    @Test
    fun `getStudents returns success when API call succeeds`() = runTest {
        // Given
        val mockResponse = listOf(createMockStudentData())
        val mockApi = mockk<StudentApi> {
            coEvery { getStudents(any()) } returns Response.success(mockResponse)
        }
        val repository = StudentRepositoryImpl(mockApi)
        
        // When
        val result = repository.getStudents(1)
        
        // Then
        assertTrue(result is SuccessResult)
    }
}
```

---

## 6. Caché y Persistencia Local

### 6.1 Estado Actual

#### ⚠️ Problema Identificado

**Problema**: No se ve implementación de caché local
- Los repositorios solo hacen llamadas a la API
- No hay persistencia local de datos

**Recomendación**: Considerar implementar:
- Room para base de datos local
- Caché en memoria para datos temporales
- Estrategia de sincronización

Ejemplo:
```kotlin
class StudentRepositoryImpl(
    private val api: StudentApi,
    private val localDataSource: StudentLocalDataSource
) : StudentRepository {
    
    override suspend fun getStudents(cycleSchoolId: Int): ModelResult<List<StudentData>, NetworkModelError> {
        // Primero intentar obtener de caché local
        val cached = localDataSource.getStudents(cycleSchoolId)
        if (cached.isNotEmpty()) {
            return SuccessResult(cached)
        }
        
        // Si no hay caché, obtener de API
        return when (val result = api.getStudents(cycleSchoolId).executeOrError { it }) {
            is SuccessResult -> {
                localDataSource.saveStudents(result.data)
                result
            }
            is ErrorResult -> result
        }
    }
}
```

---

## 7. Documentación

### 7.1 Estado Actual

#### ✅ Bien Documentado
- KDoc en interfaces de repositorios
- Comentarios en implementaciones

#### ⚠️ Áreas de Mejora

**Problema**: Algunos mappers no tienen documentación

**Recomendación**: Agregar documentación a todas las funciones públicas.

---

## 8. Recomendaciones Prioritarias

### 🔴 Alta Prioridad
1. **Agrupar repositorios por entidad** (StudentRepository, AuthRepository, etc.)
2. **Simplificar nombres de métodos** (eliminar "execute")
3. **Estandarizar nombres de modelos** (eliminar prefijo "Model")
4. **Crear funciones de extensión** para simplificar manejo de errores
5. **Implementar tests** para repositorios y mappers

### 🟡 Media Prioridad
1. **Simplificar mappers** usando funciones de extensión en modelos
2. **Estandarizar nombres de funciones de mapeo** a `toDomain()`
3. **Agregar validaciones** en modelos de datos
4. **Mejorar manejo de nulos** en mappers
5. **Considerar implementar caché local** para mejorar rendimiento

### 🟢 Baja Prioridad
1. **Mejorar documentación** de mappers
2. **Optimizar modelos** con valores por defecto
3. **Revisar y consolidar** utilidades de manejo de errores
4. **Agregar logging** para debugging

---

## 9. Ejemplos de Refactorización

### Ejemplo 1: Agrupar Repositorios

**Antes:**
```kotlin
// GetStudentRepository.kt
fun interface GetStudentRepository {
    suspend fun executeGetListStudent(cycleSchoolId: Int): ModelResult<List<ModelStudentData>, NetworkModelError>
}

// RegisterStudentRepository.kt
fun interface RegisterStudentRepository {
    suspend fun executeRegisterStudent(...): ModelResult<ModelStudentData?, NetworkModelError>
}
```

**Después:**
```kotlin
// StudentRepository.kt
interface StudentRepository {
    suspend fun getStudents(cycleSchoolId: Int): ModelResult<List<StudentData>, NetworkModelError>
    suspend fun getStudent(id: Int): ModelResult<StudentData, NetworkModelError>
    suspend fun createStudent(request: CreateStudentRequest): ModelResult<StudentData, NetworkModelError>
    suspend fun updateStudent(id: Int, request: UpdateStudentRequest): ModelResult<StudentData, NetworkModelError>
    suspend fun deleteStudent(id: Int): ModelResult<Unit, NetworkModelError>
}
```

### Ejemplo 2: Simplificar Mappers

**Antes:**
```kotlin
object StudentDataToDomainMapper {
    fun ResponseRegisterStudent.mapperToModelStudent(): ModelStudentData {
        return ModelStudentData(...)
    }
}
```

**Después:**
```kotlin
// StudentData.kt
fun StudentData.toDomain(): StudentDomain {
    return StudentDomain(
        studentId = studentId,
        name = name,
        lastName = lastName,
        // ...
    )
}
```

### Ejemplo 3: Simplificar Manejo de Errores

**Antes:**
```kotlin
override suspend fun executeLogin(...): ModelResult<ModelLoginData, NetworkModelError> {
    return try {
        val response = loginApiCall.callApi(request)
        if (response.isSuccessful && response.body()?.data != null) {
            SuccessResult(response.body()?.data!!.mapperToGetLoginData())
        } else {
            ErrorResult(NetworkException.handleException(HttpException(response)))
        }
    } catch (e: Exception) {
        ErrorResult(NetworkException.handleException(e))
    }
}
```

**Después:**
```kotlin
override suspend fun login(...): ModelResult<LoginData, NetworkModelError> {
    return authApi.login(request).executeOrError { it.toLoginData() }
}
```

---

## Conclusión

El módulo DATA está bien estructurado pero necesita mejoras en:
- **Agrupación de repositorios** por entidad
- **Simplificación de nombres** y métodos
- **Manejo de errores** más limpio y reutilizable
- **Testing** completo
- **Consideración de caché local** para mejor rendimiento

Las mejoras propuestas mejorarán la mantenibilidad, testabilidad y rendimiento del código.

