/**
 * @file Define el modelo de datos para un estudiante en la capa de datos.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.model.student

/**
 * Modelo de datos que representa a un estudiante en la capa de datos.
 * Este modelo se utiliza para almacenar y transferir información de estudiantes
 * entre la capa de red (API) y la capa de dominio.
 *
 * **Propósito:**
 * Este modelo actúa como un DTO (Data Transfer Object) que representa la estructura
 * de datos de un estudiante tal como viene del servidor o se envía al servidor.
 *
 * **Uso:**
 * Este modelo se utiliza principalmente en:
 * - Repositorios para almacenar datos recibidos de la API
 * - Mappers para convertir entre modelos de red y modelos de dominio
 *
 * @property curp La CURP (Clave Única de Registro de Población) del estudiante.
 * @property name El nombre del estudiante.
 * @property lastName El apellido paterno del estudiante.
 * @property secondLastName El apellido materno del estudiante.
 * @property birthday La fecha de nacimiento del estudiante en formato String.
 * @property phoneNumber El número de teléfono del estudiante.
 * @property userId El ID del usuario asociado al estudiante en el sistema.
 * @property studentId El ID único del estudiante.
 * @property isActive Indica si el estudiante está activo en el sistema.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class StudentData(
    val curp: String?,
    val name: String?,
    val lastName: String?,
    val secondLastName: String?,
    val birthday: String?,
    val phoneNumber: String?,
    val userId: Int?,
    val studentId: Int?,
    val isActive: Boolean?,
)