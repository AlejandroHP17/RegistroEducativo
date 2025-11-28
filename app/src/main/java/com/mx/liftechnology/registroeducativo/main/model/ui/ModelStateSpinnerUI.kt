package com.mx.liftechnology.registroeducativo.main.model.ui

import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelCustomCard

/**
 * Representa los callbacks para las interacciones con un spinner o una lista de tarjetas.
 *
 * @property onItemClick Lambda que se invoca al hacer clic en un ítem.
 * @property onEdit Lambda que se invoca al seleccionar la opción de editar.
 * @property onDelete Lambda que se invoca al seleccionar la opción de eliminar.
 * @author Pelkidev
 * @version 1.0.0
 */
data class SpinnerUiCallbacks(
    val onItemClick: (ModelCustomCard) -> Unit,
    val onEdit: (ModelCustomCard) -> Unit,
    val onDelete: (ModelCustomCard) -> Unit
)
