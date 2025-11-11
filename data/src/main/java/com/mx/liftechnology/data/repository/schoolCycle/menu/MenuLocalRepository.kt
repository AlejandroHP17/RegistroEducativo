/**
 * @file Define el repositorio local para la funcionalidad del menú principal.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.schoolCycle.menu

import android.content.Context
import com.mx.liftechnology.data.R
import com.mx.liftechnology.data.model.schoolCycle.ModelPrincipalMenuData

/**
 * Repositorio local para acceder a los datos del menú desde los recursos de la aplicación.
 * Esta clase se encarga de construir la lista de elementos del menú principal.
 *
 * @property context El contexto de la aplicación, necesario para acceder a los recursos.
 * @author Pelkidev
 * @version 1.0.0
 */
class MenuLocalRepository(private val context: Context) {
    /**
     * Obtiene la lista de ítems para el menú de control.
     *
     * @return Una lista de [ModelPrincipalMenuData] para el menú de control.
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
     * Obtiene la lista de ítems para el menú de registro.
     *
     * @return Una lista de [ModelPrincipalMenuData] para el menú de registro.
     */
    fun getControlRegister(): List<ModelPrincipalMenuData> {
        val listMenuItems = context.resources.getStringArray(R.array.menu_items_register)
        val imageResources = arrayOf(
            R.drawable.ic_students,
            R.drawable.ic_subject,
            R.drawable.ic_calendars,
            R.drawable.ic_partial,
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