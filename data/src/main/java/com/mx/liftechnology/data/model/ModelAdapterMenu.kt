package com.mx.liftechnology.data.model

/** Model - Include all the elements to control in the adapter
 * @author pelkidev
 * @since 1.0.0
 */
data class ModelAdapterMenu(
    val id: String,
    val image: Int?,
    val titleCard: String?,
    val isTouch: Boolean = true
)