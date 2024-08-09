package com.mx.liftechnology.registroeducativo.model.util

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class ModelSelectorForm(val value: Int) : Parcelable {
   NAME(1),
   LASTNAME(2),
   SECONDLASTNAME(3),
   CURP(4),
   PHONE(5),
   BIRTHDAY(6),
}
