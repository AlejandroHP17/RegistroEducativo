/**
 * @file Define el modelo de dominio para un usuario autenticado.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.model.auth

import com.mx.liftechnology.data.model.auth.ModelGetUserData

/**
 * Modelo de dominio que representa la información de un usuario autenticado en el sistema.
 * Este modelo contiene los datos del usuario obtenidos después de un inicio de sesión exitoso.
 *
 * **Propósito:**
 * Este modelo representa la información del usuario en la capa de dominio, independiente
 * de cómo se almacena o transmite en la capa de datos.
 *
 * **Uso:**
 * Este modelo se utiliza en:
 * - Casos de uso para proporcionar información del usuario a la capa de presentación
 * - Lógica de negocio relacionada con usuarios
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
data class UserDomain(
    val email: String,
    val name: String?,
    val lastName: String?,
    val phone: String?,
    val isActive: Boolean?,
    val userId: Int,
    val accessLevelId: Int?,
)

/**
 * Función de extensión para convertir un [ModelGetUserData] (modelo de datos) a un [UserDomain] (modelo de dominio).
 *
 * @receiver El modelo de datos del usuario.
 * @return Un [UserDomain] equivalente.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun ModelGetUserData.toDomain(): UserDomain {
    return UserDomain(
        email = email,
        name = name,
        lastName = lastName,
        phone = phone,
        isActive = isActive,
        userId = userId,
        accessLevelId = accessLevelId
    )
}

