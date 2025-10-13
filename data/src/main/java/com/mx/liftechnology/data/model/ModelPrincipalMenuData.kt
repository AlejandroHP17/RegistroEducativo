package com.mx.liftechnology.data.model

/**
 * Data model for a principal menu item.
 *
 * @property id The unique identifier for the menu item.
 * @property image The resource ID for the menu item's image.
 * @property titleCard The title of the menu item.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ModelPrincipalMenuData(
    val id: String,
    val image: Int?,
    val titleCard: String?
)