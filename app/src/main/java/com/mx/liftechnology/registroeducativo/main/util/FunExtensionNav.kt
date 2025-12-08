package com.mx.liftechnology.registroeducativo.main.util

import androidx.navigation.NavHostController

/**
 * Navega a una ruta con un modo de lanzamiento de `singleTop` y restauración de estado.
 *
 * @param route La ruta de destino.
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
 * @param route La ruta de destino.
 */
fun NavHostController.navigateWithParams(route: String ) {
    this.navigate(route) {
        launchSingleTop = true
        restoreState = true
    }
}