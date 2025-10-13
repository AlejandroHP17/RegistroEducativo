# Registro Educativo

**Registro Educativo** es una aplicación de Android diseñada para facilitar la gestión académica de los profesores. Permite llevar un registro detallado de alumnos, materias, calificaciones y asistencia, todo desde la comodidad de un dispositivo móvil.

## Arquitectura y Tecnologías

El proyecto sigue una arquitectura limpia y modular, inspirada en los principios de **Clean Architecture**. Está construido con **Kotlin** y utiliza **Jetpack Compose** para la interfaz de usuario, garantizando una experiencia moderna y reactiva.

La inyección de dependencias se gestiona con **Koin**, una solución ligera y pragmática que facilita la gestión de las dependencias en toda la aplicación. Para la comunicación con el servidor, se utiliza **Retrofit**, una de las librerías más populares y robustas para realizar peticiones HTTP en Android.

### Módulos del Proyecto

El proyecto está organizado en cuatro módulos principales, cada uno con una responsabilidad específica, lo que promueve una clara separación de intereses y facilita el mantenimiento y la escalabilidad de la aplicación.

#### 1. Módulo `app`

Este es el módulo principal de la aplicación. Contiene toda la lógica relacionada con la interfaz de usuario (UI), incluyendo:

*   **Vistas de Compose**: Todas las pantallas y componentes reutilizables que conforman la UI de la aplicación.
*   **ViewModels**: Clases que exponen el estado de la UI y gestionan la lógica de presentación.
*   **Inyección de dependencias (DI)**: Módulos de Koin que configuran y proporcionan las dependencias necesarias para la capa de presentación.
*   **Navegación**: Lógica de navegación entre las diferentes pantallas, gestionada con **Jetpack Navigation para Compose**.

#### 2. Módulo `core`

Este módulo contiene las funcionalidades transversales y las herramientas que se utilizan en toda la aplicación. Sus principales responsabilidades incluyen:

*   **Comunicación de red**: Configuración de Retrofit, interceptores de autenticación y logging, y las definiciones de las llamadas a la API.
*   **Gestión de preferencias**: Clases para interactuar con SharedPreferences de forma segura, utilizando **EncryptedSharedPreferences**.
*   **Utilidades**: Clases de ayuda para tareas comunes, como la gestión de permisos, la obtención de la ubicación y el reconocimiento de voz.

#### 3. Módulo `data`

Este módulo se encarga de la gestión de los datos de la aplicación. Su principal responsabilidad es implementar los repositorios que acceden a las diferentes fuentes de datos, tanto locales como remotas.

*   **Repositorios**: Implementaciones de las interfaces definidas en el módulo `domain`, que se comunican con las fuentes de datos (API remota, base de datos local, etc.).
*   **Modelos de datos**: Clases que representan los datos tal como se reciben de las fuentes de datos.
*   **Manejo de errores**: Clases y utilidades para gestionar los errores que pueden ocurrir durante la comunicación con las fuentes de datos.

#### 4. Módulo `domain`

Este es el corazón de la aplicación y contiene la lógica de negocio. Es completamente independiente de las capas de `data` y `app`, lo que garantiza que la lógica de negocio no se vea afectada por los detalles de implementación de la UI o el acceso a datos.

*   **Casos de uso (Use Cases)**: Clases que encapsulan una única regla de negocio. Son los responsables de orquestar la ejecución de las acciones que puede realizar el usuario.
*   **Modelos de dominio**: Representaciones de los datos que son independientes de las fuentes de datos y de la interfaz de usuario.
*   **Interfaces de repositorio**: Contratos que definen cómo se debe acceder a los datos, sin especificar los detalles de la implementación.

## Contribuciones

Las contribuciones son bienvenidas. Si deseas colaborar en el proyecto, por favor, abre un *issue* para discutir los cambios que te gustaría hacer, o envía un *pull request* con tus mejoras.

## Licencia

Este proyecto está bajo la Licencia MIT. Consulta el archivo `LICENSE` para más detalles.
