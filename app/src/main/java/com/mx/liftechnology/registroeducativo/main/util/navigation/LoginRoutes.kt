package com.mx.liftechnology.registroeducativo.main.util.navigation

/**
 * An enum that represents the routes for the login flow.
 *
 * @property route The route string.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
enum class LoginRoutes(val route: String) {
    /** The login screen. */
    LOGIN("login"),

    /** The "forget password" screen. */
    FORGET_PASSWORD("forgetPassword"),

    /** The user registration screen. */
    REGISTER_USER("registerUser")
}