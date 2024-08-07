package com.mx.liftechnology.registroeducativo.data.local.repository

import android.content.Context
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.model.dataclass.ModelAdapterMenu

class MenuRepository (private val context: Context) {
    fun getItems(): List<ModelAdapterMenu> {
        val listMenuItems = context.resources.getStringArray(R.array.menu_items)
        return listMenuItems.mapIndexed { index, description ->
            ModelAdapterMenu(index + 1, index + 1, description)
        }
    }
}