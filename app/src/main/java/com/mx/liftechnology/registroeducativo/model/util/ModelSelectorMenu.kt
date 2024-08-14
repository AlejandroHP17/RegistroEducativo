package com.mx.liftechnology.registroeducativo.model.util

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class ModelSelectorMenu(val value: Int) : Parcelable {
   CALENDAR(1),
   STUDENT(2),
   SUBJECT(3),
   SCHOOL(4),
   PERIOD(5),
   EXPORT(6),
   CONFIG(7)
}
