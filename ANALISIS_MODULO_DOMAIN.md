# Análisis del Módulo DOMAIN - Arquitectura Android

> **Análisis realizado por**: Experto Senior en Arquitectura Android  
> **Fecha**: Enero 2025  
> **Estado**: 🔴 **CRÍTICO** - Violaciones graves de Clean Architecture

## 📋 Resumen Ejecutivo

El módulo `domain` es el **corazón de la aplicación** y debe contener **únicamente lógica de negocio pura**, independiente de frameworks y otras capas. Sin embargo, el análisis revela **violaciones críticas** de los principios de Clean Architecture que comprometen la mantenibilidad, testabilidad y escalabilidad del proyecto.

### Estado Actual
- **Total de Use Cases**: 47
- **Use Cases documentados**: 46 (98% de cobertura) ✅
- **Dependencias incorrectas**: 🔴 **CRÍTICO** - Domain depende de Data
- **Interfaces de repositorio**: ❌ Ubicadas en módulo incorrecto (data)
- **Testing**: ❌ No implementado
- **Violaciones de arquitectura**: 214 imports de data/core en domain

---

## 🔴 Problemas Críticos de Arquitectura

### 1. Violación de Dependencias (Clean Architecture)

#### ❌ Problema Crítico #1: Domain depende de Data

**Evidencia:**
```kotlin
// domain/build.gradle.kts
dependencies {
    implementation(project(":core"))
    implementation(project(":data"))  // ❌ VIOLACIÓN CRÍTICA
}
```

**Impacto:**
- ❌ **Violación del principio de inversión de dependencias**
- ❌ **Domain no puede ser testeado independientemente**
- ❌ **Acoplamiento fuerte entre capas**
- ❌ **Imposible reutilizar lógica de negocio en otros proyectos**

**Regla de Clean Architecture:**
> Las dependencias deben apuntar **hacia adentro**. Domain NO debe depender de Data.

#### ❌ Problema Crítico #2: Modelos de Dominio importan Modelos de Data

**Ejemplos encontrados:**

```kotlin
// domain/model/auth/UserDomain.kt
import com.mx.liftechnology.data.model.auth.ModelGetUserData  // ❌

// domain/model/student/StudentDomain.kt
import com.mx.liftechnology.data.model.student.StudentData  // ❌
import com.mx.liftechnology.core.network.api.StudentApi  // ❌

// domain/model/registerschool/ResultSchoolDomain.kt
import com.mx.liftechnology.data.model.schoolCycle.ModelCCTData  // ❌
```

**Impacto:**
- ❌ **Acoplamiento directo con la capa de datos**
- ❌ **Cambios en Data afectan Domain**
- ❌ **Imposible testear modelos de dominio aisladamente**

#### ❌ Problema Crítico #3: Funciones de Mapeo en Modelos de Dominio

**Ejemplo:**
```kotlin
// domain/model/auth/UserDomain.kt
fun ModelGetUserData.toDomain(): UserDomain {  // ❌
    // Mapeo aquí
}
```

**Problema:**
- Las funciones de mapeo **deben estar en la capa de datos**, no en domain
- Domain no debe conocer la existencia de modelos de data

**Solución:**
```kotlin
// data/mapper/AuthMapper.kt
fun ModelGetUserData.toDomain(): UserDomain {
    // Mapeo aquí
}
```

#### ❌ Problema Crítico #4: Use Cases importan Repositorios de Data

**Ejemplos:**
```kotlin
// domain/usecase/auth/LoginUseCase.kt
import com.mx.liftechnology.data.repository.auth.LoginRepository  // ❌
import com.mx.liftechnology.data.util.ErrorResult  // ❌
import com.mx.liftechnology.data.util.ModelResult  // ❌
```

**Problema:**
- Use Cases deben depender de **interfaces definidas en domain**, no de implementaciones de data
- `ModelResult` y tipos de error deben estar en domain, no en data

---

## 📁 Estructura y Organización

### 2.1 Organización de Paquetes

#### ✅ Bien Organizado
```
domain/src/main/java/com/mx/liftechnology/domain/
├── model/                    # Modelos de dominio
│   ├── auth/
│   ├── evaluation/
│   ├── formativeFields/
│   ├── generic/
│   ├── registerschool/
│   ├── schoolCycle/
│   └── student/
├── usecase/                  # Casos de uso
│   ├── auth/
│   ├── evaluation/
│   ├── formativeField/
│   ├── schoolCycle/
│   └── student/
└── util/                     # Utilidades
```

#### ⚠️ Áreas de Mejora

**Problema 1**: Falta carpeta `repository/` para interfaces
```
domain/
├── model/
├── usecase/
├── repository/          # ❌ FALTA - Interfaces de repositorio deben estar aquí
└── util/
```

**Problema 2**: Modelos genéricos mezclados con específicos
```
model/
├── generic/                  # Modelos genéricos
│   ├── ModelCodeError.kt
│   ├── ModelCodeInputs.kt
│   └── ...
└── student/                  # Modelos específicos
```

**Recomendación**: Reorganizar por tipo:
```
model/
├── common/                   # Modelos compartidos
│   ├── Error.kt
│   ├── Result.kt
│   └── Validation.kt
├── auth/
├── student/
└── ...
```

---

## 🏗️ Arquitectura y Patrones

### 3.1 Use Cases

#### ✅ Buenas Prácticas Aplicadas
- ✅ Encapsulación de lógica de negocio
- ✅ Uso de operador `invoke` para ejecución
- ✅ Validaciones de negocio
- ✅ Documentación KDoc completa (98%)

#### ❌ Problemas Arquitectónicos

**Problema 1**: Use Cases dependen de implementaciones, no interfaces

**Antes (Incorrecto):**
```kotlin
class LoginUseCase(
    private val repositoryLogin: LoginRepository,  // ❌ Interface en data
    private val locationHelper: LocationHelper,    // ✅ OK (core)
    private val deviceIdHelper: DeviceIdHelper,    // ✅ OK (core)
    private val preference: PreferenceUseCase,     // ✅ OK (core)
    private val getDataUserUseCase: GetDataUserUseCase
)
```

**Después (Correcto):**
```kotlin
// domain/repository/AuthRepository.kt
interface AuthRepository {
    suspend fun login(...): Result<User>
    suspend fun getUserData(): Result<User>
}

// domain/usecase/auth/LoginUseCase.kt
class LoginUseCase(
    private val authRepository: AuthRepository,  // ✅ Interface en domain
    private val locationHelper: LocationHelper,
    private val deviceIdHelper: DeviceIdHelper,
    private val preference: PreferenceUseCase,
    private val getDataUserUseCase: GetDataUserUseCase
)
```

**Problema 2**: Use Cases que solo delegan al repositorio

**Ejemplo:**
```kotlin
class DeleteStudentUseCase(
    private val deleteStudentRepository: DeleteStudentRepository
) {
    suspend operator fun invoke(studentId: Int): ModelResult<String, ModelError> {
        return deleteStudentRepository.delete(studentId)  // ❌ Solo delega
    }
}
```

**Recomendación:**
- Si solo delega, **eliminar el Use Case** y usar el repositorio directamente en ViewModel
- O **agregar lógica de negocio** (validaciones, transformaciones, etc.)

**Problema 3**: Uso de `ModelResult` de data en lugar de domain

**Antes:**
```kotlin
import com.mx.liftechnology.data.util.ModelResult  // ❌
import com.mx.liftechnology.data.util.ModelError   // ❌
```

**Después:**
```kotlin
// domain/model/common/Result.kt
sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
}

// domain/model/common/DomainError.kt
sealed class DomainError {
    object UserIncompleteData : DomainError()
    object NetworkError : DomainError()
    // ...
}
```

### 3.2 Modelos de Dominio

#### ❌ Problemas Identificados

**Problema 1**: Modelos con muchos campos opcionales

```kotlin
data class StudentDomain(
    val studentId : Int?,      // ❌ ¿Por qué opcional?
    val curp : String?,
    val birthday : String?,    // ❌ Debería ser LocalDate
    val phoneNumber : String?,
    val userId : Int?,
    val name : String?,        // ❌ ¿Nombre opcional?
    val lastName : String?,
    val secondLastName : String?
)
```

**Recomendación:**
```kotlin
data class Student(
    val id: Int,                    // ✅ Requerido
    val name: String,               // ✅ Requerido
    val lastName: String,           // ✅ Requerido
    val secondLastName: String? = null,  // ✅ Opcional con default
    val curp: String? = null,
    val birthday: LocalDate? = null,     // ✅ Tipo específico
    val phoneNumber: String? = null,
    val userId: Int? = null
)
```

**Problema 2**: Uso de `String?` para fechas

**Recomendación:**
```kotlin
import java.time.LocalDate

data class Student(
    val birthday: LocalDate? = null,  // ✅ Tipo específico
    // ...
)
```

**Problema 3**: Modelos de dominio con dependencias de Android

```kotlin
import android.os.Parcelable  // ❌ Domain no debe depender de Android
import kotlinx.parcelize.Parcelize

@Parcelize
data class StudentDomain(...) : Parcelable  // ❌
```

**Problema:**
- Domain debe ser **puro Kotlin**, sin dependencias de Android
- Parcelable debe aplicarse en la capa de presentación (app)

**Solución:**
```kotlin
// domain/model/student/Student.kt
data class Student(...)  // ✅ Sin Parcelable

// app/model/StudentUi.kt
@Parcelize
data class StudentUi(...) : Parcelable  // ✅ Parcelable en UI
```

---

## 🔄 Dependencias entre Capas

### 4.1 Estado Actual

#### ❌ Violaciones Críticas

**Problema**: El módulo `domain` depende de `data` y `core`

```kotlin
// domain/build.gradle.kts
dependencies {
    implementation(project(":core"))  // ⚠️ Aceptable si es solo utilidades
    implementation(project(":data"))  // ❌ PROHIBIDO
}
```

**Regla de Clean Architecture:**
```
app → domain ← data
     ↑
    core (utilidades compartidas)
```

**Dependencias correctas:**
- ✅ `app` → `domain`, `data`, `core`
- ✅ `data` → `domain`, `core`
- ✅ `domain` → `core` (solo utilidades, no frameworks)
- ❌ `domain` → `data` (PROHIBIDO)

### 4.2 Solución Propuesta

**Paso 1**: Crear interfaces de repositorio en domain
```
domain/
└── repository/
    ├── AuthRepository.kt
    ├── StudentRepository.kt
    ├── FormativeFieldRepository.kt
    └── ...
```

**Paso 2**: Mover tipos comunes a domain
```
domain/
└── model/
    └── common/
        ├── Result.kt
        ├── DomainError.kt
        └── Validation.kt
```

**Paso 3**: Eliminar dependencia de data en domain
```kotlin
// domain/build.gradle.kts
dependencies {
    // implementation(project(":data"))  // ❌ ELIMINAR
    implementation(project(":core"))     // ✅ Solo utilidades puras
}
```

**Paso 4**: Implementar interfaces en data
```kotlin
// data/repository/AuthRepositoryImpl.kt
class AuthRepositoryImpl(
    private val api: AuthApi
) : AuthRepository {  // ✅ Implementa interfaz de domain
    // ...
}
```

---

## 📝 Nomenclatura y Convenciones

### 5.1 Problemas Identificados

#### ❌ Inconsistencias en Nombres de Use Cases
- `LoginUseCase` vs `GetDataUserUseCase` (diferentes patrones)
- `ValidateFieldsLoginFlowUseCase` (muy largo)
- `GetListWorkEvaluationFormativeFieldUseCase` (extremadamente largo)
- `RegisterFormativeFieldsBulkUseCase` vs `RegisterStudentUseCase` (inconsistencia en "Bulk")

**Recomendación**: Estandarizar a un patrón consistente:
- `*UseCase` para todos los casos de uso
- Nombres descriptivos pero concisos
- Usar verbos claros: `Get`, `Create`, `Update`, `Delete`, `Validate`

**Ejemplos:**
- ✅ `LoginUseCase`
- ✅ `GetUserDataUseCase`
- ✅ `ValidateLoginFieldsUseCase` (en lugar de `ValidateFieldsLoginFlowUseCase`)
- ✅ `GetWorkEvaluationListUseCase` (en lugar de `GetListWorkEvaluationFormativeFieldUseCase`)

#### ❌ Nomenclatura de Modelos de Dominio
- Todos los modelos usan el prefijo `Model*Domain`
  - `ModelStudentDomain`
  - `ModelFormatFormativeFieldsDomain`
  - `ModelSpinnerSchoolDomain`

**Recomendación**: Simplificar nombres (el prefijo "Model" es redundante):
- `Student`
- `FormativeField`
- `SchoolSpinner`

O mantener el sufijo `Domain` si ayuda a diferenciar:
- `StudentDomain`
- `FormativeFieldDomain`
- `SchoolSpinnerDomain`

---

## 🧪 Testing

### 6.1 Estado Actual

#### ❌ Problema Crítico
- **No se encontraron tests para Use Cases**
- **No se encontraron tests para validaciones**
- **No se encontraron tests para modelos**

**Impacto:**
- ❌ Imposible validar lógica de negocio
- ❌ Alto riesgo de regresiones
- ❌ Refactorización peligrosa

### 6.2 Recomendación

**Implementar tests unitarios:**

```kotlin
// domain/src/test/java/.../usecase/auth/LoginUseCaseTest.kt
class LoginUseCaseTest {
    @Test
    fun `login returns success when credentials are valid`() = runTest {
        // Given
        val mockRepository = mockk<AuthRepository> {
            coEvery { login(any(), any(), any(), any(), any()) } returns 
                Result.Success(User(...))
        }
        val useCase = LoginUseCase(mockRepository, ...)
        
        // When
        val result = useCase("email@test.com", "password", false)
        
        // Then
        assertTrue(result is Result.Success)
    }
    
    @Test
    fun `login returns error when email is blank`() = runTest {
        // Given
        val useCase = LoginUseCase(...)
        
        // When
        val result = useCase("", "password", false)
        
        // Then
        assertTrue(result is Result.Error)
        assertEquals(DomainError.UserIncompleteData, result.error)
    }
}
```

---

## 📊 Métricas y Estadísticas

### 7.1 Cobertura de Documentación
- **Use Cases documentados**: 46/47 (98%) ✅
- **Modelos documentados**: ~80%
- **Interfaces documentadas**: ~60%

### 7.2 Violaciones de Arquitectura
- **Imports de data en domain**: 214 ❌
- **Imports de core en domain**: Aceptable (utilidades)
- **Dependencias incorrectas**: 1 crítica (domain → data)

### 7.3 Complejidad
- **Use Cases totales**: 47
- **Use Cases que solo delegan**: ~15 (32%)
- **Use Cases con lógica de negocio**: ~32 (68%)

---

## 🎯 Recomendaciones Prioritarias

### 🔴 Alta Prioridad (Crítico)

1. **Eliminar dependencia de `domain` hacia `data`**
   - Mover interfaces de repositorio a `domain/repository/`
   - Crear tipos `Result` y `Error` en domain
   - Mover funciones de mapeo a data/mapper

2. **Refactorizar modelos de dominio**
   - Eliminar dependencias de Android (Parcelable)
   - Eliminar imports de modelos de data
   - Usar tipos específicos (LocalDate en lugar de String)

3. **Crear sistema de tipos común en domain**
   - `domain/model/common/Result.kt`
   - `domain/model/common/DomainError.kt`
   - `domain/model/common/Validation.kt`

4. **Mover interfaces de repositorio a domain**
   - Crear `domain/repository/` con todas las interfaces
   - Implementar interfaces en `data/repository/`

### 🟡 Media Prioridad

1. **Simplificar nombres de Use Cases** (más concisos)
2. **Eliminar Use Cases que solo delegan** al repositorio
3. **Simplificar nombres de modelos** (eliminar prefijo "Model")
4. **Crear validadores separados** para lógica de validación
5. **Centralizar validaciones comunes**

### 🟢 Baja Prioridad

1. **Reorganizar modelos genéricos** en carpeta `common`
2. **Optimizar modelos** con valores por defecto
3. **Revisar y simplificar** funciones de extensión
4. **Agregar más ejemplos** en documentación de Use Cases complejos

---

## 📚 Ejemplos de Refactorización

### Ejemplo 1: Mover Interfaces de Repositorio

**Antes:**
```kotlin
// data/repository/auth/LoginRepository.kt
fun interface LoginRepository {
    suspend fun executeLogin(...): ModelResult<ModelLoginData, NetworkModelError>
}
```

**Después:**
```kotlin
// domain/repository/AuthRepository.kt
interface AuthRepository {
    suspend fun login(...): Result<User>
    suspend fun getUserData(): Result<User>
    suspend fun register(...): Result<Boolean>
}

// data/repository/auth/AuthRepositoryImpl.kt
class AuthRepositoryImpl(
    private val api: AuthApi
) : AuthRepository {
    override suspend fun login(...): Result<User> {
        // Implementación
    }
}
```

### Ejemplo 2: Crear Sistema de Result en Domain

**Antes:**
```kotlin
import com.mx.liftechnology.data.util.ModelResult  // ❌
import com.mx.liftechnology.data.util.ModelError   // ❌
```

**Después:**
```kotlin
// domain/model/common/Result.kt
sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val error: DomainError) : Result<Nothing>()
}

// domain/model/common/DomainError.kt
sealed class DomainError {
    object UserIncompleteData : DomainError()
    object NetworkError : DomainError()
    object Unauthorized : DomainError()
    data class Unknown(val message: String) : DomainError()
}
```

### Ejemplo 3: Refactorizar Modelo de Dominio

**Antes:**
```kotlin
// domain/model/student/StudentDomain.kt
import com.mx.liftechnology.data.model.student.StudentData  // ❌
import android.os.Parcelable  // ❌

@Parcelize
data class StudentDomain(
    val studentId : Int?,
    val name : String?,
    val birthday : String?,
    // ...
) : Parcelable

fun StudentData?.toStudentDomain(): StudentDomain? {  // ❌
    // ...
}
```

**Después:**
```kotlin
// domain/model/student/Student.kt
import java.time.LocalDate  // ✅

data class Student(
    val id: Int,
    val name: String,
    val lastName: String,
    val secondLastName: String? = null,
    val curp: String? = null,
    val birthday: LocalDate? = null,
    val phoneNumber: String? = null,
    val userId: Int? = null
)

// data/mapper/StudentMapper.kt
fun StudentData.toDomain(): Student {  // ✅
    return Student(
        id = studentId ?: 0,
        name = name ?: "",
        // ...
    )
}
```

---

## 🎓 Conclusión

El módulo DOMAIN tiene **problemas críticos de arquitectura** que violan los principios fundamentales de Clean Architecture:

### Problemas Críticos
1. ❌ **Dependencias incorrectas** (domain depende de data)
2. ❌ **Interfaces de repositorio en capa incorrecta**
3. ❌ **Modelos de dominio acoplados a data**
4. ❌ **Falta de testing**

### Impacto
- **Mantenibilidad**: Baja - Cambios en data afectan domain
- **Testabilidad**: Muy baja - Imposible testear domain aisladamente
- **Escalabilidad**: Baja - Difícil agregar nuevas features
- **Reutilización**: Imposible - Domain no puede usarse en otros proyectos

### Prioridad de Acción
Las mejoras propuestas son **fundamentales** y deben implementarse **inmediatamente** para mantener la arquitectura limpia y los principios de Clean Architecture. Sin estas correcciones, el proyecto acumulará deuda técnica que será cada vez más costosa de resolver.

---

**Análisis realizado siguiendo las mejores prácticas de Clean Architecture y Android Architecture Guidelines.**

