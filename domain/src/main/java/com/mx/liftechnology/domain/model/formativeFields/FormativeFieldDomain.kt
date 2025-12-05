package com.mx.liftechnology.domain.model.formativeFields

/**
 * @file Define el modelo de datos para un campo formativo.
 * @author Pelkidev
 * @version 1.0.0
 */
/**
 * Modelo de datos que representa un campo formativo en la capa de datos.
 * Un campo formativo es una asignatura o materia que se imparte en el ciclo escolar.
 *
 * **Propósito:**
 * Este modelo actúa como un DTO (Data Transfer Object) que representa la estructura
 * de datos de un campo formativo tal como viene del servidor.
 *
 * **Uso:**
 * Este modelo se utiliza en:
 * - Repositorios para almacenar datos recibidos de la API
 * - Mappers para convertir entre modelos de red y modelos de dominio
 * - Casos de uso para listar y gestionar campos formativos
 *
 * @property name El nombre del campo formativo (ej: "Matemáticas", "Español").
 * @property code El código único del campo formativo.
 * @property formativeFieldID El ID único del campo formativo en el sistema.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class FormativeFieldDomain(
    val name: String?,
    val code: String?,
    val formativeFieldID: Int?
)