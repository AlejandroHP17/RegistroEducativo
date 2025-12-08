package com.mx.liftechnology.data.repositoryImpl.menu

import android.content.Context
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.LocalModelError
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.data.R
import com.mx.liftechnology.domain.model.schoolCycle.PrincipalMenuDomain
import com.mx.liftechnology.domain.repository.menu.MenuLocalRepository

/**
 * @file Define el repositorio local para la funcionalidad del menú principal.
 * @author Pelkidev
 * @version 1.0.0
 */
/**
 * Repositorio local para acceder a los datos del menú desde los recursos de la aplicación.
 * Esta clase se encarga de construir la lista de elementos del menú principal.
 *
 * @property context El contexto de la aplicación, necesario para acceder a los recursos.
 * @author Pelkidev
 * @version 1.0.0
 */
class MenuLocalRepositoryImpl(private val context: Context
) : MenuLocalRepository {
    /**
     * Obtiene la lista de ítems para el menú de control.
     *
     * @return Una lista de [com.mx.liftechnology.domain.model.schoolCycle.PrincipalMenuDomain] para el menú de control.
     */
    override suspend fun getControlMenu(): ModelResult<List<PrincipalMenuDomain>, ModelError> {
        val listMenuItems = context.resources.getStringArray(R.array.menu_items_control)
        val imageResources = arrayOf(
            R.drawable.ic_control,
            R.drawable.ic_perfil
        )
        return try {
            SuccessResult(
                listMenuItems.mapIndexed { index, description ->
                    PrincipalMenuDomain(
                        id = description,
                        image = imageResources[index],
                        titleCard = description
                    )
                }
            )
        }catch (e: Exception){
            ErrorResult(LocalModelError.CATCH)
        }
    }


    /**
     * Obtiene la lista de ítems para el menú de registro.
     *
     * @return Una lista de [PrincipalMenuDomain] para el menú de registro.
     */
    override suspend fun getControlRegister(): ModelResult<List<PrincipalMenuDomain>, ModelError> {
        val listMenuItems = context.resources.getStringArray(R.array.menu_items_register)
        val imageResources = arrayOf(
            R.drawable.ic_students,
            R.drawable.ic_formative_field,
            R.drawable.ic_calendars,
            R.drawable.ic_partial,
            R.drawable.ic_export
        )
        return try{
            SuccessResult(listMenuItems.mapIndexed { index, description ->
                PrincipalMenuDomain(
                    id = description,
                    image = imageResources[index],
                    titleCard = description
                )
            })
        }catch (e: Exception){
            ErrorResult(LocalModelError.CATCH)
        }
    }
}