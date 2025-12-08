package com.mx.liftechnology.domain.usecase.menu

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.LocalModelError
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.schoolCycle.DialogStudentGroupDomain
import com.mx.liftechnology.domain.model.schoolCycle.InfoStudentGroupDomain
import com.mx.liftechnology.domain.model.schoolCycle.toDialogStudentGroupDomainList
import com.mx.liftechnology.domain.repository.schoolCycle.SchoolCycleRepository

/**
 * Caso de uso para obtener la lista de grupos del menú, seleccionar uno por defecto y procesar la información.
 *
 * @property schoolCycleRepository El repositorio para las operaciones relacionadas con ciclos escolares.
 * @property preference El caso de uso para gestionar las preferencias del usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetGroupMenuUseCase(
    private val schoolCycleRepository: SchoolCycleRepository,
    private val preference: PreferenceUseCase,
) {

    /**
     * Ejecuta el proceso de obtención de grupos, selección de uno por defecto y procesamiento de la información.
     *
     * @return Un [ModelResult] que contiene la información del grupo o un error.
     */
    suspend operator fun invoke(): ModelResult<InfoStudentGroupDomain, ModelError> {
        val userId = preference.getIdUserLevel()
            ?: return ErrorResult(LocalModelError.USER_INCOMPLETE_DATA)
        
        val result = schoolCycleRepository.getCycleSchool(userId)
        return when (result) {
            is SuccessResult -> {
                val convertedResult = result.data.toDialogStudentGroupDomainList
                SuccessResult(
                    InfoStudentGroupDomain(
                        listSchool = convertedResult,
                        infoSchoolSelected = selectOneGroup(convertedResult)
                    )
                )
            }
            is ErrorResult -> result
        }
    }

    /**
     * Selecciona un grupo de la lista. Si el usuario es nuevo, selecciona el primero.
     * Si el usuario tiene un grupo seleccionado previamente, lo selecciona desde las preferencias.
     *
     * @param convertedResult La lista de grupos para seleccionar.
     * @return El [DialogStudentGroupDomain] seleccionado.
     */
    private fun selectOneGroup(convertedResult: List<DialogStudentGroupDomain>): DialogStudentGroupDomain {
        return convertedResult.let { itemParent ->
            if (preference.getIdCycleSchool() == -1) {
                val item = itemParent.firstOrNull { it.item?.cycleSchoolId != null }
                item?.item?.cycleSchoolId?.let { id ->
                    preference.setIdCycleSchool(id)
                }
                buildOneInformation(item)
            } else {
                val itemImprovised = itemParent.firstOrNull { onlyData ->
                    preference.getIdCycleSchool() == onlyData.item?.cycleSchoolId
                }
                buildOneInformation(itemImprovised)
            }
        }
    }

    /**
     * Reconstruye los datos en un nuevo objeto [DialogStudentGroupDomain].
     *
     * @param convertedResult El objeto [DialogStudentGroupDomain] de origen.
     * @return El nuevo objeto [DialogStudentGroupDomain] creado.
     */
    private fun buildOneInformation(convertedResult: DialogStudentGroupDomain?): DialogStudentGroupDomain {
        val modelResponse = DialogStudentGroupDomain(
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