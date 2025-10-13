package com.mx.liftechnology.data.repository.flowMain.menu

import android.content.Context
import com.mx.liftechnology.data.R
import com.mx.liftechnology.data.model.ModelPrincipalMenuData

/**
 * Local repository for accessing menu data from resources.
 *
 * @property context The application context.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class MenuLocalRepository(private val context: Context) {
    /**
     * Gets the list of control menu items.
     *
     * @return A list of [ModelPrincipalMenuData] for the control menu.
     */
    fun getControlMenu(): List<ModelPrincipalMenuData> {

        val listMenuItems = context.resources.getStringArray(R.array.menu_items_control)
        val imageResources = arrayOf(
            R.drawable.ic_control,
            R.drawable.ic_perfil
        )
        return listMenuItems.mapIndexed { index, description ->
            ModelPrincipalMenuData(
                id = description,
                image = imageResources[index],
                titleCard = description
            )
        }
    }

    /**
     * Gets the list of register menu items.
     *
     * @return A list of [ModelPrincipalMenuData] for the register menu.
     */
    fun getControlRegister(): List<ModelPrincipalMenuData> {
        val listMenuItems = context.resources.getStringArray(R.array.menu_items_register)
        val imageResources = arrayOf(
            R.drawable.ic_students,
            R.drawable.ic_subject,
            R.drawable.ic_partial,
            R.drawable.ic_calendars,
            R.drawable.ic_export
        )
        return listMenuItems.mapIndexed { index, description ->
             ModelPrincipalMenuData(
                id = description,
                image = imageResources[index],
                titleCard = description
            )
        }
    }
}