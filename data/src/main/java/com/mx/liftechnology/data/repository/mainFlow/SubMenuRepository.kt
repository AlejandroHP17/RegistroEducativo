package com.mx.liftechnology.data.repository.mainFlow

import android.content.Context
import com.mx.liftechnology.core.model.ModelAdapterMenu
import com.mx.liftechnology.data.R
import com.mx.liftechnology.data.model.ModelSelectorMenu

/** SubMenuRepository - Build the element list of menu (home)
 * @author pelkidev
 * @since 1.0.0
 * @param context use for read strings
 * @return listMenuItems contains the list of menu
 * */
class SubMenuRepository(private val context: Context) {
    fun getItems(school:Boolean): List<ModelAdapterMenu> {

        val listMenuItems = context.resources.getStringArray(R.array.sub_menu_items)
        val imageResources = arrayOf(
            R.drawable.ic_school,
            R.drawable.ic_students,
            R.drawable.ic_subject,
            R.drawable.ic_partial
        )
        val idBase = ModelSelectorMenu.SCHOOL.value
        return listMenuItems.mapIndexed { index, description ->
            if(description == listMenuItems[1] || description == listMenuItems[2]) ModelAdapterMenu(index + idBase, imageResources[index], description, school)
            else ModelAdapterMenu(index + idBase, imageResources[index], description, true )
        }
    }
}