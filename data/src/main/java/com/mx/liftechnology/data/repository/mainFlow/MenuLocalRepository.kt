package com.mx.liftechnology.data.repository.mainFlow

import android.content.Context
import com.mx.liftechnology.data.R
import com.mx.liftechnology.data.model.ModelAdapterMenu

/** MenuLocalRepository - Build the element list of menu (home)
 * @author pelkidev
 * @since 1.0.0
 * @param context use for read strings
 * @return listMenuItems contains the list of menu
 * */
class MenuLocalRepository(private val context: Context) {
    fun getControlMenu(): List<ModelAdapterMenu> {

        val listMenuItems = context.resources.getStringArray(R.array.menu_items_control)
        val imageResources = arrayOf(
            R.drawable.ic_control,
            R.drawable.ic_perfil
        )
        return listMenuItems.mapIndexed { index, description ->
            ModelAdapterMenu(
                description,
                imageResources[index],
                description,
                true
            )
        }
    }

    fun getControlRegister(): List<ModelAdapterMenu> {
        val listMenuItems = context.resources.getStringArray(R.array.menu_items_register)
        val imageResources = arrayOf(
            R.drawable.ic_school,
            R.drawable.ic_students,
            R.drawable.ic_subject,
            R.drawable.ic_partial
        )
        return listMenuItems.mapIndexed { index, description ->
             ModelAdapterMenu(
                description,
                imageResources[index],
                description,
                true
            )
        }
    }

    fun getControlEvaluation(): List<ModelAdapterMenu> {
        val listMenuItems = context.resources.getStringArray(R.array.menu_items_evaluate)
        val imageResources = arrayOf(
            R.drawable.ic_calendars,
            R.drawable.ic_students,
            R.drawable.ic_subject,
            R.drawable.ic_export
        )
        return listMenuItems.mapIndexed { index, description ->
             ModelAdapterMenu(
                description,
                imageResources[index],
                description,
                true
            )
        }
    }
}