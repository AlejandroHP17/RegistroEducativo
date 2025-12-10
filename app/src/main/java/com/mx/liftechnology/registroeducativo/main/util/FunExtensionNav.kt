package com.mx.liftechnology.registroeducativo.main.util

import androidx.navigation.NavHostController

/**
 * Extensiones de funciones para la navegación con configuraciones optimizadas.
 *
 * Estas funciones proporcionan métodos de navegación que incluyen configuraciones
 * comunes como `launchSingleTop` y `restoreState` para mejorar la experiencia del usuario
 * y evitar la creación de múltiples instancias de la misma pantalla.
 *
 * @author Pelkidev
 * @version 1.0.0
 */

/**
 * Navega a una ruta con un modo de lanzamiento de `singleTop` y restauración de estado.
 *
 * **Configuración aplicada:**
 * - `launchSingleTop = true`: Evita crear múltiples instancias de la misma pantalla en la pila
 * - `restoreState = true`: Restaura el estado guardado de la pantalla si ya existe
 *
 * @param route La ruta de destino a la que navegar.
 */
fun NavHostController.navigateWithState(route: String) {
    this.navigate(route) {
        launchSingleTop = true
        restoreState = true
    }
}

/**
 * Navega a una ruta con parámetros, un modo de lanzamiento de `singleTop` y restauración de estado.
 *
 * **Configuración aplicada:**
 * - `launchSingleTop = true`: Evita crear múltiples instancias de la misma pantalla en la pila
 * - `restoreState = true`: Restaura el estado guardado de la pantalla si ya existe
 *
 * **Nota:** Esta función es similar a [navigateWithState], pero está diseñada para rutas
 * que incluyen parámetros en la URL.
 *
 * @param route La ruta de destino con parámetros a la que navegar.
 */
fun NavHostController.navigateWithParams(route: String ) {
    this.navigate(route) {
        launchSingleTop = true
        restoreState = true
    }
}