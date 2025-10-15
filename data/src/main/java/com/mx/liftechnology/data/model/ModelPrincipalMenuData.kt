/**
 * @file Define el modelo de datos para los ítems del menú principal.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.model

/**
 * Modelo de datos para un ítem del menú principal.
 * Se utiliza para representar las opciones en las pantallas de menú, como el registro o el control.
 *
 * @property id El identificador único del ítem del menú.
 * @property image El recurso de imagen asociado al ítem.
 * @property titleCard El título que se mostrará en la tarjeta del ítem.
 * @author Pelkidev
 * @version 1.0.0
 */
data class ModelPrincipalMenuData(
    val id: String,
    val image: Int?,
    val titleCard: String?
)