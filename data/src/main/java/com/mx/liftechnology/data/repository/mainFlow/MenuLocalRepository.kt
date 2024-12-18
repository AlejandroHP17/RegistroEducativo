package com.mx.liftechnology.data.repository.mainFlow

import android.content.Context
import com.mx.liftechnology.core.model.ModelAdapterMenu
import com.mx.liftechnology.data.R
import com.mx.liftechnology.data.model.ModelSelectorMenu

/** MenuLocalRepository - Build the element list of menu (home)
 * @author pelkidev
 * @since 1.0.0
 * @param context use for read strings
 * @return listMenuItems contains the list of menu
 * */
class MenuLocalRepository(private val context: Context) {
    fun getItems(schoolYear:Boolean): List<ModelAdapterMenu> {

        val listMenuItems = context.resources.getStringArray(R.array.menu_items)
        val imageResources = arrayOf(
            R.drawable.ic_assessment,
            R.drawable.ic_control,
            R.drawable.ic_perfil,
            R.drawable.ic_config
        )
        val idBase = ModelSelectorMenu.EVALUATION.value
        return listMenuItems.mapIndexed { index, description ->
            if(description == listMenuItems[0]) ModelAdapterMenu(index + idBase, imageResources[index], description, schoolYear)
            else ModelAdapterMenu(index + idBase, imageResources[index], description, true )
        }
    }
}