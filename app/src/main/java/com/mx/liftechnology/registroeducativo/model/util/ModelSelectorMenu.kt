package com.mx.liftechnology.registroeducativo.model.util

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class ModelSelectorMenu(val value: Int) : Parcelable {
   CALENDAR(1),
   STUDENT(2),
   SCHOOL(3),
   EXPORT(4),
   PERIOD(5),
   CONFIG(6)
}
