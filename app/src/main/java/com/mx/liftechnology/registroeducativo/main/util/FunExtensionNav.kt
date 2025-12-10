package com.mx.liftechnology.registroeducativo.main.util

import android.net.Uri
import android.os.Bundle
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
 * que incluyen parámetros en la URL. La ruta completa se pasa directamente a navigate(),
 * y los query parameters adicionales deben ser parseados manualmente en la pantalla de destino.
 *
 * **Importante:** Si la ruta contiene query parameters adicionales que no están definidos
 * en la ruta del composable, estos deben ser parseados manualmente usando [extractQueryParam]
 * desde la ruta completa. La ruta completa se guarda temporalmente para permitir el parseo.
 *
 * @param route La ruta de destino con parámetros a la que navegar (puede incluir query parameters).
 */
fun NavHostController.navigateWithParams(route: String) {
    // Guardar la ruta completa temporalmente para parseo posterior
    // Esto se hace a través de un listener que se ejecuta después de la navegación
    val listener = object : androidx.navigation.NavController.OnDestinationChangedListener {
        override fun onDestinationChanged(
            controller: androidx.navigation.NavController,
            destination: androidx.navigation.NavDestination,
            arguments: Bundle?
        ) {
            // Guardar la ruta completa en savedStateHandle del destino actual
            try {
                val currentEntry = controller.currentBackStackEntry
                currentEntry?.savedStateHandle?.set("full_route", route)
            } catch (e: Exception) {
                // Si falla, continuar sin guardar
            }
            controller.removeOnDestinationChangedListener(this)
        }
    }
    this.addOnDestinationChangedListener(listener)
    
    this.navigate(route) {
        launchSingleTop = true
        restoreState = true
    }
}

/**
 * Extrae un query parameter de una ruta completa.
 *
 * @param route La ruta completa con query parameters.
 * @param paramName El nombre del parámetro a extraer.
 * @return El valor del parámetro o null si no existe.
 */
fun extractQueryParam(route: String, paramName: String): String? {
    return if (route.contains("?")) {
        val queryString = route.substringAfter("?")
        val uri = Uri.parse("app://?$queryString")
        uri.getQueryParameter(paramName)
    } else {
        null
    }
}