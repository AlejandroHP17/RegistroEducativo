package com.mx.liftechnology.registroeducativo.data.local.repository

import android.content.Context
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.model.dataclass.ModelAdapterMenu

class MenuRepository (private val context: Context) {
    fun getItems(): List<ModelAdapterMenu> {
        val listMenuItems = context.resources.getStringArray(R.array.menu_items)
        val imageResources = arrayOf(
            R.drawable.ic_calendar,
            R.drawable.ic_students,
            R.drawable.ic_subject,
            R.drawable.ic_school,
            R.drawable.ic_period,
            R.drawable.ic_export,
            R.drawable.ic_config
        )

        return listMenuItems.mapIndexed { index, description ->
            ModelAdapterMenu(index + 1, imageResources[index], description)
        }
    }
}