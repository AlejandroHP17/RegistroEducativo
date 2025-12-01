/**
 * @file Define el modelo de datos para la respuesta de inicio de sesión.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.model.auth

/**
 * Modelo de datos que representa la respuesta de autenticación después de un inicio de sesión exitoso.
 * Contiene los tokens necesarios para autenticar las peticiones subsecuentes al servidor.
 *
 * **Propósito:**
 * Este modelo almacena los tokens de autenticación recibidos del servidor después de un login exitoso.
 * Estos tokens se utilizan para autenticar las peticiones HTTP posteriores.
 *
 * **Uso:**
 * Este modelo se utiliza en:
 * - [LoginRepository] para almacenar la respuesta del servidor
 * - [TokenProvider] para guardar y recuperar los tokens
 * - Mappers para convertir entre modelos de red y modelos de dominio
 *
 * @property accessToken El token de acceso utilizado para autenticar peticiones. No puede ser nulo.
 * @property refreshToken El token de refresco utilizado para obtener un nuevo accessToken cuando expire. No puede ser nulo.
 * @property tokenType El tipo de token (generalmente "Bearer"). Puede ser nulo.
 *
 * @see com.mx.liftechnology.core.network.provider.TokenProvider Para ver cómo se utilizan estos tokens.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class LoginData(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String?,
)
