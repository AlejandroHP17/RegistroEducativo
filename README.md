# Registro Educativo

**Registro Educativo** es una aplicación de Android diseñada para facilitar la gestión académica de los profesores. Permite llevar un registro detallado de alumnos, materias, calificaciones y asistencia, todo desde la comodidad de un dispositivo móvil.

## 📋 Tabla de Contenidos

- [Arquitectura y Tecnologías](#arquitectura-y-tecnologías)
- [Módulos del Proyecto](#módulos-del-proyecto)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Análisis y Mejoras](#análisis-y-mejoras)
- [Configuración](#configuración)
- [Contribuciones](#contribuciones)
- [Licencia](#licencia)

## 🏗️ Arquitectura y Tecnologías

El proyecto sigue una **arquitectura limpia y modular**, inspirada en los principios de **Clean Architecture** y el patrón **MVVM (Model-View-ViewModel)**. Está construido con **Kotlin** y utiliza **Jetpack Compose** para la interfaz de usuario, garantizando una experiencia moderna y reactiva.

### Stack Tecnológico

- **Lenguaje**: Kotlin
- **UI**: Jetpack Compose
- **Arquitectura**: Clean Architecture + MVVM
- **Inyección de Dependencias**: Koin
- **Networking**: Retrofit + OkHttp
- **Navegación**: Jetpack Navigation Compose
- **Gestión de Estado**: StateFlow / Flow
- **Corrutinas**: Kotlin Coroutines
- **Serialización**: Gson
- **Almacenamiento Local**: EncryptedSharedPreferences

## 📦 Módulos del Proyecto

El proyecto está organizado en **cuatro módulos principales**, cada uno con una responsabilidad específica, lo que promueve una clara separación de intereses y facilita el mantenimiento y la escalabilidad de la aplicación.

### 1. Módulo `app` (Capa de Presentación)

Este es el módulo principal de la aplicación. Contiene toda la lógica relacionada con la interfaz de usuario (UI).

**Responsabilidades:**
- ✅ **Vistas de Compose**: Pantallas y componentes reutilizables
- ✅ **ViewModels**: Gestión del estado de la UI y lógica de presentación
- ✅ **Inyección de Dependencias**: Módulos de Koin para la capa de presentación
- ✅ **Navegación**: Lógica de navegación entre pantallas
- ✅ **Mappers UI**: Transformación de modelos de dominio a modelos de UI
- ✅ **Temas y Estilos**: Configuración de Material Design 3

**Estructura:**
```
app/
├── di/                    # Módulos de inyección de dependencias
├── framework/             # Configuración de la aplicación
├── main/
│   ├── ui/               # Pantallas y componentes Compose
│   ├── model/            # Modelos de UI
│   ├── mapper/           # Mappers UI
│   ├── util/             # Utilidades de UI
│   └── navigation/       # Configuración de navegación
```

### 2. Módulo `core` (Funcionalidades Transversales)

Este módulo contiene las funcionalidades compartidas y herramientas utilizadas en toda la aplicación.

**Responsabilidades:**
- ✅ **Comunicación de Red**: Configuración de Retrofit, interceptores y API Calls
- ✅ **Gestión de Preferencias**: Acceso seguro a SharedPreferences (EncryptedSharedPreferences)
- ✅ **Utilidades**: Helpers para permisos, ubicación, reconocimiento de voz
- ✅ **Gestión de Sesión**: Manejo de sesión de usuario
- ✅ **Configuración de Entorno**: Variables de entorno y configuración

**Estructura:**
```
core/
├── network/              # Configuración de red
│   ├── apiCall/         # Interfaces de API
│   └── NetworkModule.kt # Módulo de DI
├── preference/          # Gestión de preferencias
└── util/               # Utilidades compartidas
```

### 3. Módulo `data` (Capa de Datos)

Este módulo se encarga de la gestión de datos y la comunicación con fuentes de datos externas.

**Responsabilidades:**
- ✅ **Repositorios**: Implementaciones de repositorios que acceden a fuentes de datos
- ✅ **Modelos de Datos**: Representación de datos tal como se reciben de las APIs
- ✅ **Mappers**: Transformación de modelos de datos a modelos de dominio
- ✅ **Manejo de Errores**: Utilidades para gestionar errores de red y datos

**Estructura:**
```
data/
├── repository/          # Implementaciones de repositorios
├── model/              # Modelos de datos
├── mapper/             # Mappers de datos a dominio
└── util/               # Utilidades de manejo de errores
```

### 4. Módulo `domain` (Capa de Dominio)

Este es el corazón de la aplicación y contiene la lógica de negocio pura, independiente de otras capas.

**Responsabilidades:**
- ✅ **Casos de Uso (Use Cases)**: Encapsulación de reglas de negocio
- ✅ **Modelos de Dominio**: Representaciones de datos independientes de fuentes externas
- ✅ **Interfaces de Repositorio**: Contratos para acceso a datos (deben estar aquí)
- ✅ **Validaciones**: Lógica de validación de negocio

**Estructura:**
```
domain/
├── usecase/            # Casos de uso
├── model/              # Modelos de dominio
├── repository/         # Interfaces de repositorio (debe estar aquí)
└── util/               # Utilidades de dominio
```

## 📁 Estructura del Proyecto

```
RegistroEducativo/
├── app/                    # Módulo de presentación
│   ├── src/main/java/.../
│   │   └── registroeducativo/
│   │       ├── di/         # Módulos Koin
│   │       ├── framework/  # Configuración app
│   │       └── main/       # UI, ViewModels, etc.
│   └── build.gradle.kts
├── core/                   # Módulo de funcionalidades transversales
│   ├── src/main/java/.../
│   │   └── core/
│   │       ├── network/    # Red y APIs
│   │       ├── preference/ # Preferencias
│   │       └── util/       # Utilidades
│   └── build.gradle.kts
├── data/                   # Módulo de datos
│   ├── src/main/java/.../
│   │   └── data/
│   │       ├── repository/ # Implementaciones
│   │       ├── model/      # Modelos de datos
│   │       └── mapper/     # Mappers
│   └── build.gradle.kts
├── domain/                 # Módulo de dominio
│   ├── src/main/java/.../
│   │   └── domain/
│   │       ├── usecase/    # Casos de uso
│   │       ├── model/      # Modelos de dominio
│   │       └── repository/ # Interfaces (debe estar aquí)
│   └── build.gradle.kts
├── gradle/                 # Configuración de Gradle
│   └── libs.versions.toml  # Versiones de dependencias
└── README.md
```

## 📊 Estado del Proyecto

### Progreso de Mejoras

| Módulo | Documentación | Arquitectura | Testing | Estado General |
|--------|--------------|--------------|---------|----------------|
| **domain** | ✅ 98% (46/47 Use Cases) | ⚠️ Mejoras pendientes | ❌ Pendiente | 🟡 En progreso |
| **data** | ✅ Buena | ⚠️ Mejoras pendientes | ❌ Pendiente | 🟡 En progreso |
| **core** | ✅ Buena | ✅ Buena | ❌ Pendiente | 🟢 Estable |
| **app** | ✅ Buena | ✅ Buena | ❌ Pendiente | 🟢 Estable |

### Últimas Mejoras Implementadas

- ✅ **Documentación completa de Use Cases** - Se agregó KDoc completo a 9 Use Cases que carecían de documentación
- ✅ **Corrección de documentación** - Se corrigió documentación incorrecta en `EditStudentUseCase`
- ✅ **Estandarización de formato** - Todos los Use Cases ahora siguen el mismo formato de documentación

---

## 🔍 Análisis y Mejoras

Se ha realizado un análisis completo de cada módulo del proyecto, identificando áreas de mejora en nomenclatura, estructura, organización y mejores prácticas. Los documentos de análisis detallados están disponibles:

- 📄 [Análisis del Módulo APP](ANALISIS_MODULO_APP.md) - Mejoras en UI, ViewModels, componentes Compose
- 📄 [Análisis del Módulo CORE](ANALISIS_MODULO_CORE.md) - Mejoras en red, preferencias, utilidades
- 📄 [Análisis del Módulo DATA](ANALISIS_MODULO_DATA.md) - Mejoras en repositorios, mappers, modelos
- 📄 [Análisis del Módulo DOMAIN](ANALISIS_MODULO_DOMAIN.md) - Mejoras en Use Cases, modelos, arquitectura

### Resumen de Mejoras Prioritarias

#### ✅ Mejoras Completadas Recientemente
1. **Documentación de Use Cases** - Se ha agregado documentación completa (KDoc) a 46 de 47 Use Cases (98% de cobertura), incluyendo:
   - Descripción de clases y responsabilidades
   - Documentación de parámetros y valores de retorno
   - Posibles errores y ejemplos de uso
   - Use Cases documentados: `DeleteStudentUseCase`, `DeleteFormativeFieldsUseCase`, `GetDataUserUseCase`, `GetWorkTypeByFormativeFieldUseCase`, `GetListWotyFofiUseCase`, `GetListEvaluationsStudentUseCase`, `GetListByFieldTypeStudentUseCase`, `RegisterFormativeFieldsBulkUseCase`, `EditStudentUseCase`

#### 🔴 Alta Prioridad
1. **Estandarizar nomenclatura** en todos los módulos
2. **Mover interfaces de repositorio** de `data` a `domain` (inversión de dependencias)
3. **Eliminar dependencias** de `domain` hacia `data`
4. **Agrupar repositorios** por entidad en lugar de por operación
5. **Implementar tests** unitarios e integración

#### 🟡 Media Prioridad
1. **Reorganizar estructura** de archivos y paquetes
2. **Simplificar mappers** usando funciones de extensión
3. **Crear sistema centralizado** de manejo de errores
4. **Mejorar validaciones** con validadores separados
5. **Configurar logging** según tipo de build

#### 🟢 Baja Prioridad
1. ~~**Mejorar documentación** con ejemplos~~ ✅ **EN PROGRESO** - 98% de Use Cases documentados
2. **Agregar previews** a componentes Compose
3. **Optimizar modelos** con valores por defecto
4. **Considerar caché local** para mejor rendimiento

## ⚙️ Configuración

### Requisitos Previos

- Android Studio Hedgehog | 2023.1.1 o superior
- JDK 17 o superior
- Android SDK API 24+ (mínimo), API 34+ (target)
- Gradle 8.0+

### Configuración del Proyecto

1. Clonar el repositorio:
```bash
git clone <repository-url>
cd RegistroEducativo
```

2. Abrir el proyecto en Android Studio

3. Sincronizar dependencias de Gradle

4. Configurar variables de entorno (si es necesario):
   - Crear archivo `local.properties` si no existe
   - Configurar `BASE_URL` en `core/src/main/java/.../core/network/environment/Environment.kt`

5. Ejecutar la aplicación:
   - Seleccionar dispositivo o emulador
   - Ejecutar `./gradlew installDebug` o usar Android Studio

## 🤝 Contribuciones

Las contribuciones son bienvenidas. Si deseas colaborar en el proyecto:

1. **Revisa los documentos de análisis** para entender las mejoras propuestas
2. **Abre un issue** para discutir cambios importantes
3. **Crea una rama** para tu feature o fix
4. **Sigue las convenciones** de código establecidas
5. **Envía un pull request** con una descripción clara de los cambios

### Convenciones de Código

- **Nomenclatura**: PascalCase para clases, camelCase para funciones y variables
- **Idioma**: Código y comentarios en español
- **Documentación**: KDoc para todas las clases y funciones públicas
- **Testing**: Tests unitarios para lógica de negocio, tests de UI para componentes críticos

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Consulta el archivo `LICENSE` para más detalles.

---

## 📝 Notas de Versión

### Versión Actual - Enero 2025

#### Mejoras Implementadas
- ✅ **Documentación de Use Cases**: Se agregó documentación KDoc completa a 9 Use Cases del módulo `domain`
- ✅ **Corrección de documentación**: Se corrigió documentación incorrecta en `EditStudentUseCase`
- ✅ **Estandarización**: Todos los Use Cases ahora siguen un formato consistente de documentación

#### Próximas Mejoras Planificadas
- 🔄 Mover interfaces de repositorio de `data` a `domain` (inversión de dependencias)
- 🔄 Implementar tests unitarios para Use Cases
- 🔄 Estandarizar nomenclatura en todos los módulos
- 🔄 Agregar tests de integración

---

**Desarrollado con ❤️ usando Clean Architecture y las mejores prácticas de Android**
