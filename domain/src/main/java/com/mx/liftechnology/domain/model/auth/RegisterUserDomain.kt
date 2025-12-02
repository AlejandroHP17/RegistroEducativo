package com.mx.liftechnology.domain.model.auth

/**
 * Modelo de datos que representa la respuesta del servidor después de registrar un nuevo usuario.
 * Contiene la información del usuario recién creado.
 *
 * **Propósito:**
 * Este modelo almacena los datos del usuario que fueron creados en el servidor después de un registro exitoso.
 *
 * **Uso:**
 * Este modelo se utiliza en:
 * - [RegisterUserRepository] para almacenar la respuesta del servidor
 * - Mappers para convertir entre modelos de red y modelos de dominio
 * - Casos de uso para confirmar el registro exitoso
 *
 * @property email El correo electrónico del usuario registrado. Campo requerido.
 * @property firstName El nombre del usuario.
 * @property lastName El apellido del usuario.
 * @property accessLevel El nivel de acceso asignado al usuario. Campo requerido.
 * @property isActive Indica si la cuenta del usuario está activa.
 * @property userId El ID único del usuario recién creado. Campo requerido.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RegisterUserDomain(
    val email: String,
    val firstName: String?,
    val lastName: String?,
    val accessLevel: Int,
    val isActive: Boolean?,
    val userId: Int
)