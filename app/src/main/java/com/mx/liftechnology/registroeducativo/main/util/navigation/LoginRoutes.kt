package com.mx.liftechnology.registroeducativo.main.util.navigation

/**
 * Enum que representa las rutas para el flujo de login.
 * Cada ruta corresponde a una pantalla específica dentro de este flujo.
 *
 * @property route La ruta como un String, utilizada por el `NavController`.
 * @author Pelkidev
 * @version 1.0.0
 */
enum class LoginRoutes(val route: String) {
    /** La pantalla de inicio de sesión. */
    LOGIN("login"),

    /** La pantalla de recuperación de contraseña. */
    FORGET_PASSWORD("forgetPassword"),

    /** La pantalla de registro de usuario. */
    REGISTER_USER("registerUser")
}