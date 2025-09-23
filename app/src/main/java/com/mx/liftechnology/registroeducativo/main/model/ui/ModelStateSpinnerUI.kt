package com.mx.liftechnology.registroeducativo.main.model.ui

import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.share.ModelCustomCard

data class ModelStateSpinnerUI(
    val onItemClick:(ModelCustomCard) -> Unit,
    val onEdit: (ModelCustomCard) -> Unit,
    val onDelete: (ModelCustomCard) -> Unit
)
