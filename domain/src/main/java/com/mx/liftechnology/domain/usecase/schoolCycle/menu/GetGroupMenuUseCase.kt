package com.mx.liftechnology.domain.usecase.schoolCycle.menu

import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.schoolCycle.menu.MenuRepository
import com.mx.liftechnology.data.util.Error
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.LocalError
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.SuccessResult
import com.mx.liftechnology.domain.model.schoolCycle.ModelDialogStudentGroupDomain
import com.mx.liftechnology.domain.model.schoolCycle.ModelInfoStudentGroupDomain
import com.mx.liftechnology.domain.model.schoolCycle.RGTtoConvertModelDialogStudentGroupDomains

/**
 * Caso de uso para obtener la lista de grupos del menú, seleccionar uno por defecto y procesar la información.
 *
 * @property menuRepository El repositorio para las operaciones relacionadas con el menú.
 * @property preference El caso de uso para gestionar las preferencias del usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetGroupMenuUseCase(
    private val menuRepository: MenuRepository,
    private val preference: PreferenceUseCase,
) {

    /**
     * Ejecuta el proceso de obtención de grupos, selección de uno por defecto y procesamiento de la información.
     *
     * @return Un [ModelResult] que contiene la información del grupo o un error.
     */
    suspend operator fun invoke(): ModelResult<ModelInfoStudentGroupDomain, Error> {
        val userId = preference.getPreferenceInt(ModelPreference.ID_USER_LEVEL)
        if (userId == null) ErrorResult(LocalError.USER_INCOMPLETE_DATA)
        return runCatching { menuRepository.executeGetCycleSchool( userId!!) }.fold(
            onSuccess = { result ->
                when (result) {
                    is SuccessResult -> {
                        val convertedResult = result.data.RGTtoConvertModelDialogStudentGroupDomains
                        if (convertedResult.isNotEmpty()) {
                            SuccessResult(
                                ModelInfoStudentGroupDomain(
                                    listSchool = convertedResult,
                                    infoSchoolSelected = selectOneGroup(convertedResult)
                                )
                            )
                        } else {
                            ErrorResult(NetworkError.EMPTY)
                        }
                    }

                    is ErrorResult -> {
                        if(result.error == NetworkError.UNAUTHORIZED) preference.cleanPreference()
                        ErrorResult(result.error)
                    }
                }
            },
            onFailure = { ErrorResult(NetworkError.UNKNOWN)}
        )

    }

    /**
     * Selecciona un grupo de la lista. Si el usuario es nuevo, selecciona el primero.
     * Si el usuario tiene un grupo seleccionado previamente, lo selecciona desde las preferencias.
     *
     * @param convertedResult La lista de grupos para seleccionar.
     * @return El [ModelDialogStudentGroupDomain] seleccionado.
     */
    private fun selectOneGroup(convertedResult: List<ModelDialogStudentGroupDomain>): ModelDialogStudentGroupDomain {
        return convertedResult.let { itemParent ->
            if (preference.getPreferenceInt(ModelPreference.ID_CYCLE_SCHOOL) == -1) {
                val item = itemParent.firstOrNull { it.item?.cycleSchoolId != null }
                item?.item?.cycleSchoolId?.let { id ->
                    preference.savePreferenceInt(
                        ModelPreference.ID_CYCLE_SCHOOL,
                        id
                    )
                }
                buildOneInformation(item)
            } else {
                val itemImprovised = itemParent.firstOrNull { onlyData ->
                    preference.getPreferenceInt(ModelPreference.ID_CYCLE_SCHOOL) == onlyData.item?.cycleSchoolId
                }
                buildOneInformation(itemImprovised)
            }
        }
    }

    /**
     * Reconstruye los datos en un nuevo objeto [ModelDialogStudentGroupDomain].
     *
     * @param convertedResult El objeto [ModelDialogStudentGroupDomain] de origen.
     * @return El nuevo objeto [ModelDialogStudentGroupDomain] creado.
     */
    private fun buildOneInformation(convertedResult: ModelDialogStudentGroupDomain?): ModelDialogStudentGroupDomain {
        val modelResponse = ModelDialogStudentGroupDomain(
            selected = convertedResult?.selected,
            item = convertedResult?.item,
            nameItem = convertedResult?.nameItem,
            listItemPartial = convertedResult?.listItemPartial,
            itemPartial = convertedResult?.itemPartial,
            namePartial = convertedResult?.namePartial,
        )
        return modelResponse
    }
}