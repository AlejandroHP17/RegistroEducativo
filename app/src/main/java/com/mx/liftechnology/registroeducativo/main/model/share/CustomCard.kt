package com.mx.liftechnology.registroeducativo.main.model.share

/**
 * Modelo de datos para una tarjeta personalizada en la UI.
 * Se utiliza para representar ítems en listas genéricas.
 *
 * @property id El identificador único del ítem.
 * @property numberList El número de lista que se muestra en la tarjeta.
 * @property nameCard El nombre o texto principal de la tarjeta.
 * @author Pelkidev
 * @version 1.0.0
 */
data class CustomCard(
    val id: Int,
    val numberList: String?,
    val nameCard: String?,
    val isVisibleMenu: Boolean = true
)
