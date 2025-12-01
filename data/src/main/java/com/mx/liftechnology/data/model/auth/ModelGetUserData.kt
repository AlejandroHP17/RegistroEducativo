/**
 * @file Define el modelo de datos para la información de un usuario autenticado.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.model.auth

/**
 * Modelo de datos que representa la información de un usuario autenticado en el sistema.
 * Este modelo contiene los datos del usuario obtenidos después de un inicio de sesión exitoso.
 *
 * **Propósito:**
 * Este modelo almacena la información del usuario autenticado, incluyendo sus datos personales
 * y permisos en el sistema.
 *
 * **Uso:**
 * Este modelo se utiliza en:
 * - [GetDataUserRepository] para almacenar la respuesta del servidor
 * - Mappers para convertir entre modelos de red y modelos de dominio
 * - Casos de uso para proporcionar información del usuario a la capa de presentación
 *
 * @property email El correo electrónico del usuario. Campo requerido.
 * @property name El nombre del usuario.
 * @property lastName El apellido del usuario.
 * @property phone El número de teléfono del usuario.
 * @property isActive Indica si la cuenta del usuario está activa.
 * @property userId El ID único del usuario en el sistema. Campo requerido.
 * @property accessLevelId El ID del nivel de acceso del usuario, que determina sus permisos en el sistema.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ModelGetUserData(
    val email: String,
    val name: String?,
    val lastName: String?,
    val phone: String?,
    val isActive: Boolean?,
    val userId: Int,
    val accessLevelId: Int?,
)
