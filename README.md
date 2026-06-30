# Registro Educativo

[![Kotlin](https://img.shields.io/badge/Kotlin-2.2-7F52FF?logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-Material%203-4285F4)](https://developer.android.com/jetpack/compose)
[![Clean Architecture](https://img.shields.io/badge/Architecture-Clean%20%2B%20MVVM-1a567a)]()
[![Retrofit](https://img.shields.io/badge/Retrofit-3.0-3E8E41)](https://square.github.io/retrofit/)
[![Koin](https://img.shields.io/badge/Koin-4.1-6C63FF)](https://insert-koin.io/)

App **Android** para que profesores gestionen alumnos, materias, evaluaciones, parciales y calendario escolar desde el móvil, conectada a un backend REST.

> Proyecto de portafolio · [Alejandro Hernández](https://github.com/AlejandroHP17) · [LinkedIn](https://www.linkedin.com/in/alejandro-hern%C3%A1ndez-pelcastre/)

## Funcionalidades

- Autenticación (login, registro, recuperación de contraseña)
- Registro y consulta de alumnos
- Gestión de campos formativos, escuelas y parciales
- Evaluaciones por alumno y por materia
- Calendario escolar
- Perfil de usuario y sesión segura

## Stack tecnológico

| Categoría | Tecnología |
|-----------|------------|
| Lenguaje | Kotlin |
| UI | Jetpack Compose, Material 3 |
| Arquitectura | Clean Architecture, MVVM, multi-módulo |
| Red | Retrofit, OkHttp, Gson |
| DI | Koin |
| Estado | StateFlow, Coroutines |
| Seguridad | EncryptedSharedPreferences |
| Extras | Lottie, reconocimiento de voz, geolocalización |

## Arquitectura

Proyecto modular en cuatro capas:

```
app        → UI Compose, ViewModels, navegación
domain     → 46+ casos de uso, modelos de dominio
data       → Repositorios, mappers, modelos API
core       → Retrofit, preferencias, utilidades compartidas
```

## Cómo ejecutar

**Requisitos:** Android Studio Hedgehog+, JDK 17+, Android SDK 24+

```bash
git clone https://github.com/AlejandroHP17/RegistroEducativo.git
cd RegistroEducativo
./gradlew installDebug
```

Configura `BASE_URL` en `core/.../network/environment/Environment.kt` si usas un backend propio.

## Documentación técnica

Para detalle de módulos, convenciones y notas de arquitectura:

→ [docs/ARCHITECTURE-NOTES.md](docs/ARCHITECTURE-NOTES.md)

## Autor

**Alejandro Hernández Pelcastre** — [GitHub](https://github.com/AlejandroHP17) · [LinkedIn](https://www.linkedin.com/in/alejandro-hern%C3%A1ndez-pelcastre/)

## Licencia

MIT — ver [LICENSE](LICENSE).
