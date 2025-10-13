package com.mx.liftechnology.registroeducativo.main.util

import androidx.navigation.NavHostController

/**
 * Navigates to a route with a single top launch mode and state restoration.
 *
 * @param route The destination route.
 */
fun NavHostController.navigateWithState(route: String) {
    this.navigate(route) {
        launchSingleTop = true
        restoreState = true
    }
}

/**
 * Navigates to a route with parameters, a single top launch mode, and state restoration.
 *
 * @param route The destination route.
 */
fun NavHostController.navigateWithParams(route: String ) {
    this.navigate(route) {
        launchSingleTop = true
        restoreState = true
    }
}