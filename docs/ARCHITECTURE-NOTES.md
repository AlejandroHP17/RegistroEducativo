# Notas de arquitectura — Registro Educativo

Documentación técnica interna para desarrolladores. El [README principal](../README.md) resume el proyecto para una primera lectura.

## Módulos del proyecto

### `app` — Presentación

- Vistas Compose, ViewModels, navegación
- Mappers UI, temas Material 3
- Módulos Koin de presentación

```
app/
├── di/
├── framework/
└── main/
    ├── ui/
    ├── model/
    ├── mapper/
    ├── util/
    └── navigation/
```

### `core` — Transversal

- Retrofit, interceptores, APIs
- EncryptedSharedPreferences
- Permisos, ubicación, reconocimiento de voz, sesión

```
core/
├── network/
├── preference/
└── util/
```

### `data` — Datos

- Implementaciones de repositorios
- Modelos API y mappers hacia dominio
- Manejo de errores de red

```
data/
├── repository/
├── model/
├── mapper/
└── util/
```

### `domain` — Dominio

- Casos de uso (46+)
- Modelos de dominio
- Interfaces de repositorio (objetivo: centralizar contratos aquí)

```
domain/
├── usecase/
├── model/
├── repository/
└── util/
```

## Estructura completa

```
RegistroEducativo/
├── app/
├── core/
├── data/
├── domain/
├── gradle/
│   └── libs.versions.toml
└── README.md
```

## Estado del proyecto

| Módulo | Documentación | Arquitectura | Testing |
|--------|---------------|--------------|---------|
| domain | 98% (46/47 use cases) | Refactor pendiente | Pendiente |
| data | 90% | Mejorable | Pendiente |
| core | 85% | Estable | Pendiente |
| app | 75% | Mejorable | Pendiente |

### Mejoras recientes

- KDoc completo en 46 de 47 casos de uso
- Corrección de documentación en `EditStudentUseCase`
- Formato estandarizado en use cases

## Análisis por módulo

Documentos de revisión detallada:

- [ANALISIS_MODULO_DOMAIN.md](../ANALISIS_MODULO_DOMAIN.md)
- [ANALISIS_MODULO_DATA.md](../ANALISIS_MODULO_DATA.md)
- [ANALISIS_MODULO_CORE.md](../ANALISIS_MODULO_CORE.md)
- [ANALISIS_MODULO_APP.md](../ANALISIS_MODULO_APP.md)

## Roadmap técnico

### Alta prioridad

1. Eliminar dependencia de `domain` hacia `data`
2. Mover interfaces de repositorio a `domain`
3. Mover `ModelResult` y tipos de error a `domain`
4. Refactorizar modelos de dominio (sin dependencias Android/data)
5. Agrupar repositorios por entidad
6. Tests unitarios e integración

### Media prioridad

1. Reorganizar paquetes
2. Simplificar mappers con extension functions
3. Manejo centralizado de errores
4. Validadores separados
5. Logging según build type

### Baja prioridad

1. Previews en componentes Compose
2. Valores por defecto en modelos
3. Caché local

## Convenciones

- PascalCase para clases, camelCase para funciones
- Código y comentarios en español
- KDoc en APIs públicas
- Tests en lógica de negocio y UI crítica

## Notas de versión (Ene 2025)

- Documentación KDoc en 9 use cases adicionales
- Estandarización de formato en módulo `domain`
- Planificado: inversión de dependencias en repositorios
