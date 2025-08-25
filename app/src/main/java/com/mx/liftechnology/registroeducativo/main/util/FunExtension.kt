package com.mx.liftechnology.registroeducativo.main.util

import androidx.navigation.NavHostController

fun NavHostController.navigateWithState(route: String) {
    this.navigate(route) {
        launchSingleTop = true
        restoreState = true
    }
}

fun NavHostController.navigateWithParams(route: String ) {
    this.navigate(route) {
        launchSingleTop = true
        restoreState = true
    }
}