# Análisis del Módulo DOMAIN

> **Última actualización**: Enero 2025  
> **Estado**: 🟡 En progreso - Documentación mejorada significativamente

## Resumen Ejecutivo

El módulo `domain` es el corazón de la aplicación y contiene la lógica de negocio pura, independiente de las capas de `data` y `app`. Este documento identifica áreas de mejora en nomenclatura, estructura, organización y mejores prácticas.

### Estado Actual
- **Total de Use Cases**: 47
- **Use Cases documentados**: 46 (98% de cobertura)
- **Documentación**: ✅ Mejorada significativamente con KDoc completo
- **Arquitectura**: ⚠️ Pendiente - Dependencias incorrectas (domain → data)
- **Testing**: ❌ No implementado

---

## 1. Nomenclatura y Convenciones

### 1.1 Problemas Identificados

#### ❌ Inconsistencias en Nombres de Use Cases
- **Problema**: Mezcla de convenciones en nombres
  - `LoginUseCase` vs `GetDataUserUseCase` (diferentes patrones)
  - `ValidateFieldsLoginFlowUseCase` (muy largo)
  - `GetListWorkEvaluationFormativeFieldUseCase` (extremadamente largo)
  - `RegisterFormativeFieldsBulkUseCase` vs `RegisterStudentUseCase` (inconsistencia en "Bulk")

**Recomendación**: Estandarizar a un patrón consistente:
- `*UseCase` para todos los casos de uso
- Nombres descriptivos pero concisos
- Usar verbos claros: `Get`, `Create`, `Update`, `Delete`, `Validate`

Ejemplos:
- `LoginUseCase` ✅
- `GetUserDataUseCase` ✅
- `ValidateLoginFieldsUseCase` ✅ (en lugar de `ValidateFieldsLoginFlowUseCase`)
- `GetWorkEvaluationListUseCase` ✅ (en lugar de `GetListWorkEvaluationFormativeFieldUseCase`)

#### ❌ Nomenclatura de Modelos de Dominio
- **Problema**: Todos los modelos usan el prefijo `Model*Domain`
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

#### ❌ Nomenclatura de Interfaces de Repositorio
- **Problema**: Las interfaces de repositorio están en el módulo `data`, pero deberían estar en `domain`
- Actualmente: `data.repository.*`
- Debería ser: `domain.repository.*`

**Recomendación**: Mover interfaces de repositorio a `domain`:
```
domain/
├── model/
├── repository/          # Interfaces de repositorio
│   ├── StudentRepository.kt
│   ├── AuthRepository.kt
│   └── ...
└── usecase/
```

---

## 2. Estructura y Organización

### 2.1 Organización de Paquetes

#### ✅ Bien Organizado
```
domain/src/main/java/com/mx/liftechnology/domain/
├── model/                    # Modelos de dominio
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

**Problema 1**: Modelos genéricos mezclados con modelos específicos
```
model/
├── generic/                  # Modelos genéricos
│   ├── ModelCodeError.kt
│   ├── ModelCodeInputs.kt
│   ├── ModelCustomSpinner.kt
│   └── ...
└── student/                  # Modelos específicos
```

**Recomendación**: Reorganizar por tipo:
```
model/
├── common/                   # Modelos compartidos
│   ├── Error.kt
│   ├── Input.kt
│   └── Spinner.kt
├── student/
├── evaluation/
└── ...
```

**Problema 2**: Use Cases muy granulares
- Cada operación tiene su propio Use Case
- Algunos Use Cases son muy simples (solo llaman al repositorio)

**Recomendación**: Agrupar Use Cases relacionados cuando tenga sentido:
```kotlin
// StudentUseCase.kt
class StudentUseCase(
    private val repository: StudentRepository
) {
    suspend fun getStudents(cycleSchoolId: Int): ModelResult<List<Student>, ModelError>
    suspend fun getStudent(id: Int): ModelResult<Student, ModelError>
    suspend fun createStudent(request: CreateStudentRequest): ModelResult<Student, ModelError>
    suspend fun updateStudent(id: Int, request: UpdateStudentRequest): ModelResult<Student, ModelError>
    suspend fun deleteStudent(id: Int): ModelResult<Unit, ModelError>
}
```

O mantener separados pero simplificarlos si solo llaman al repositorio.

---

## 3. Arquitectura y Patrones

### 3.1 Use Cases

#### ✅ Buenas Prácticas Aplicadas
- Encapsulación de lógica de negocio
- Uso de `ModelResult` para resultados
- Operador `invoke` para ejecución
- Validaciones de negocio

#### ⚠️ Áreas de Mejora

**Problema 1**: Use Cases que solo delegan al repositorio
```kotlin
class GetListStudentUseCase(
    private val getStudentRepository: GetStudentRepository
) {
    suspend operator fun invoke(cycleSchoolId: Int): ModelResult<List<ModelStudentDomain>, ModelError> {
        return runCatching {
            getStudentRepository.executeGetListStudent(cycleSchoolId)
        }.fold(
            onSuccess = { it },
            onFailure = { ErrorResult(NetworkModelError.UNKNOWN) }
        )
    }
}
```

**Recomendación**: Si un Use Case solo delega, considerar:
- Eliminarlo y usar el repositorio directamente en el ViewModel
- O agregar lógica de negocio (validaciones, transformaciones, etc.)

**Problema 2**: Validaciones mezcladas con lógica de negocio
- Algunos Use Cases tienen validaciones, otros no
- Las validaciones están en diferentes lugares

**Recomendación**: Crear Use Cases de validación separados o métodos de validación:
```kotlin
class RegisterStudentUseCase(
    private val repository: StudentRepository,
    private val validator: StudentValidator
) {
    suspend operator fun invoke(request: RegisterStudentRequest): ModelResult<Student, ModelError> {
        // Validar primero
        val validationResult = validator.validate(request)
        if (validationResult is ErrorResult) {
            return validationResult
        }
        
        // Ejecutar lógica de negocio
        return repository.createStudent(request)
    }
}
```

**Problema 3**: Uso de `runCatching` innecesario
```kotlin
return runCatching {
    getStudentRepository.executeGetListStudent(cycleSchoolId)
}.fold(
    onSuccess = { it },
    onFailure = { ErrorResult(NetworkModelError.UNKNOWN) }
)
```

**Recomendación**: Si el repositorio ya retorna `ModelResult`, no es necesario `runCatching`:
```kotlin
return getStudentRepository.getStudents(cycleSchoolId)
```

**Problema 4**: Dependencias de capas inferiores
- Algunos Use Cases dependen directamente de modelos de `data`:
```kotlin
import com.mx.liftechnology.data.model.auth.ModelGetUserData
```

**Recomendación**: Los Use Cases solo deben depender de modelos de dominio:
```kotlin
import com.mx.liftechnology.domain.model.auth.User
```

### 3.2 Modelos de Dominio

#### ✅ Buenas Prácticas Aplicadas
- Modelos independientes de otras capas
- Uso de data classes
- Parcelable para navegación

#### ⚠️ Áreas de Mejora

**Problema 1**: Modelos con muchos campos opcionales
```kotlin
data class ModelStudentDomain(
    val studentId : Int?,
    val curp : String?,
    val birthday : String?,
    val phoneNumber : String?,
    val userId : Int?,
    val name : String?,
    val lastName : String?,
    val secondLastName : String?
)
```

**Recomendación**: Considerar qué campos son realmente opcionales:
```kotlin
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
```

**Problema 2**: Uso de `String?` para fechas
- Las fechas se manejan como strings

**Recomendación**: Usar tipos específicos:
```kotlin
import java.time.LocalDate

data class Student(
    val birthday: LocalDate? = null,
    // ...
)
```

**Problema 3**: Funciones de extensión para mapeo en modelos de dominio
```kotlin
fun List<ModelStudentData?>?.toModelStudentList(): List<ModelStudentDomain> {
    // ...
}
```

**Recomendación**: Mover estas funciones a mappers en la capa `data`:
```kotlin
// En data/mapper/StudentMapper.kt
fun List<StudentData>?.toDomain(): List<Student> {
    return this?.mapNotNull { it.toDomain() } ?: emptyList()
}
```

### 3.3 Validaciones

#### ✅ Buenas Prácticas Aplicadas
- Use Cases de validación separados
- Validaciones de campos

#### ⚠️ Áreas de Mejora

**Problema 1**: Validaciones mezcladas con lógica de negocio
- Algunos Use Cases validan, otros no

**Recomendación**: Crear validadores separados:
```kotlin
class StudentValidator {
    fun validateName(name: String): ValidationResult {
        return when {
            name.isBlank() -> ValidationResult.Error("Name cannot be blank")
            name.length < 2 -> ValidationResult.Error("Name too short")
            else -> ValidationResult.Success
        }
    }
    
    fun validateCurp(curp: String): ValidationResult {
        // Validación de CURP
    }
}
```

**Problema 2**: Validaciones duplicadas
- Mismas validaciones en diferentes lugares

**Recomendación**: Centralizar validaciones comunes:
```kotlin
object CommonValidators {
    fun validateEmail(email: String): ValidationResult
    fun validatePassword(password: String): ValidationResult
    fun validatePhone(phone: String): ValidationResult
}
```

---

## 4. Dependencias entre Capas

### 4.1 Estado Actual

#### ❌ Problema Crítico

**Problema**: El módulo `domain` depende de `data`
```kotlin
// En domain/usecase/auth/LoginUseCase.kt
import com.mx.liftechnology.data.model.auth.ModelGetUserData
import com.mx.liftechnology.data.repository.auth.LoginRepository
import com.mx.liftechnology.data.util.*
```

**Recomendación**: El módulo `domain` NO debe depender de `data`. Debe:
1. Definir interfaces de repositorio en `domain`
2. Usar solo modelos de dominio
3. `data` implementa las interfaces definidas en `domain`

Estructura correcta:
```
domain/
├── repository/              # Interfaces
│   ├── StudentRepository.kt
│   └── AuthRepository.kt
└── usecase/
    └── auth/
        └── LoginUseCase.kt  # Usa StudentRepository (interfaz)

data/
└── repository/
    └── StudentRepositoryImpl.kt  # Implementa StudentRepository
```

---

## 5. Testing

### 5.1 Estado Actual

#### ❌ Problema Crítico
- **No se encontraron tests para Use Cases**
- **No se encontraron tests para validaciones**
- **No se encontraron tests para modelos**

**Recomendación**: Implementar tests:
- Tests unitarios para Use Cases
- Tests unitarios para validadores
- Tests de integración para flujos completos

Ejemplo:
```kotlin
class LoginUseCaseTest {
    @Test
    fun `login returns success when credentials are valid`() = runTest {
        // Given
        val mockRepository = mockk<AuthRepository> {
            coEvery { login(any(), any(), any(), any(), any()) } returns 
                SuccessResult(LoginData(...))
        }
        val useCase = LoginUseCase(mockRepository, ...)
        
        // When
        val result = useCase("email@test.com", "password", false)
        
        // Then
        assertTrue(result is SuccessResult)
    }
    
    @Test
    fun `login returns error when email is blank`() = runTest {
        // Given
        val useCase = LoginUseCase(...)
        
        // When
        val result = useCase("", "password", false)
        
        // Then
        assertTrue(result is ErrorResult)
        assertEquals(LocalModelError.USER_INCOMPLETE_DATA, (result as ErrorResult).error)
    }
}
```

---

## 6. Documentación

### 6.1 Estado Actual

#### ✅ Bien Documentado
- **47 Use Cases** en total
- **46 Use Cases** tienen documentación KDoc completa (98% de cobertura)
- Documentación incluye:
  - Descripción de la clase y responsabilidades
  - Documentación de propiedades y dependencias
  - Parámetros con tipos y descripción
  - Valores de retorno detallados
  - Posibles errores con tipos específicos
  - Ejemplos de uso cuando es relevante
- Comentarios descriptivos en código complejo

#### ✅ Mejoras Recientes
Se ha agregado documentación completa a los siguientes Use Cases que carecían de ella:
- `DeleteStudentUseCase`
- `DeleteFormativeFieldsUseCase`
- `GetDataUserUseCase`
- `GetWorkTypeByFormativeFieldUseCase`
- `GetListWotyFofiUseCase`
- `GetListEvaluationsStudentUseCase`
- `GetListByFieldTypeStudentUseCase`
- `RegisterFormativeFieldsBulkUseCase` (mejorada)
- `EditStudentUseCase` (corregida - cambiado "registro" por "edición")

#### ⚠️ Áreas de Mejora Pendientes

**Problema**: 1 Use Case aún podría necesitar revisión de documentación

**Recomendación**: 
- Revisar el Use Case restante para asegurar consistencia
- Considerar agregar más ejemplos de uso en casos complejos
- Documentar casos edge y comportamientos especiales

---

## 7. Recomendaciones Prioritarias

### 🔴 Alta Prioridad
1. **Mover interfaces de repositorio a `domain`** (inversión de dependencias)
2. **Eliminar dependencias de `domain` hacia `data`**
3. **Simplificar nombres de Use Cases** (más concisos)
4. **Eliminar Use Cases que solo delegan** al repositorio
5. **Implementar tests** para Use Cases y validaciones

### 🟡 Media Prioridad
1. **Simplificar nombres de modelos** (eliminar prefijo "Model")
2. **Crear validadores separados** para lógica de validación
3. **Centralizar validaciones comunes**
4. **Mejorar tipos de datos** (usar LocalDate en lugar de String)
5. **Agrupar Use Cases relacionados** cuando tenga sentido

### 🟢 Baja Prioridad
1. ~~**Mejorar documentación** de Use Cases~~ ✅ **COMPLETADO** - 98% de cobertura
2. **Reorganizar modelos genéricos** en carpeta `common`
3. **Optimizar modelos** con valores por defecto
4. **Revisar y simplificar** funciones de extensión
5. **Agregar más ejemplos** en documentación de Use Cases complejos

---

## 8. Ejemplos de Refactorización

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
    suspend fun login(...): ModelResult<Login, ModelError>
}

// data/repository/auth/AuthRepositoryImpl.kt
class AuthRepositoryImpl(
    private val api: AuthApi
) : AuthRepository {
    override suspend fun login(...): ModelResult<Login, ModelError> {
        // Implementación
    }
}
```

### Ejemplo 2: Simplificar Use Case

**Antes:**
```kotlin
class GetListStudentUseCase(
    private val getStudentRepository: GetStudentRepository
) {
    suspend operator fun invoke(cycleSchoolId: Int): ModelResult<List<ModelStudentDomain>, ModelError> {
        return runCatching {
            getStudentRepository.executeGetListStudent(cycleSchoolId)
        }.fold(
            onSuccess = { it },
            onFailure = { ErrorResult(NetworkModelError.UNKNOWN) }
        )
    }
}
```

**Después:**
```kotlin
// Si solo delega, eliminar y usar directamente en ViewModel
// O agregar lógica de negocio:

class GetStudentsUseCase(
    private val repository: StudentRepository
) {
    suspend operator fun invoke(cycleSchoolId: Int): ModelResult<List<Student>, ModelError> {
        // Validar que cycleSchoolId sea válido
        if (cycleSchoolId <= 0) {
            return ErrorResult(LocalModelError.INVALID_INPUT)
        }
        
        // Obtener estudiantes
        return repository.getStudents(cycleSchoolId)
    }
}
```

### Ejemplo 3: Crear Validador Separado

**Antes:**
```kotlin
class ValidateFieldsLoginFlowUseCase {
    fun validateEmailCompose(email: String): ModelStateOutFieldText {
        // Validación aquí
    }
    
    fun validatePassCompose(pass: String): ModelStateOutFieldText {
        // Validación aquí
    }
}
```

**Después:**
```kotlin
class LoginValidator {
    fun validateEmail(email: String): ValidationResult {
        return when {
            email.isBlank() -> ValidationResult.Error("Email cannot be blank")
            !email.contains("@") -> ValidationResult.Error("Invalid email format")
            else -> ValidationResult.Success
        }
    }
    
    fun validatePassword(password: String): ValidationResult {
        return when {
            password.isBlank() -> ValidationResult.Error("Password cannot be blank")
            password.length < 8 -> ValidationResult.Error("Password too short")
            else -> ValidationResult.Success
        }
    }
}
```

---

## Conclusión

El módulo DOMAIN tiene problemas críticos de arquitectura:
- **Dependencias incorrectas** (domain depende de data)
- **Interfaces de repositorio en capa incorrecta**
- **Use Cases que solo delegan** sin agregar valor
- **Falta de testing**

Las mejoras propuestas son fundamentales para mantener la arquitectura limpia y los principios de Clean Architecture.

