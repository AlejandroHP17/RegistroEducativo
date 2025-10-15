package com.mx.liftechnology.domain.usecase.mainflowdomain.menu

import com.mx.liftechnology.core.network.apiCall.flowMain.RequestGroup
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowMain.menu.MenuRepository
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.ErrorUnauthorizedState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.model.menu.ModelDialogStudentGroupDomain
import com.mx.liftechnology.domain.model.menu.ModelInfoStudentGroupDomain
import com.mx.liftechnology.domain.model.menu.RGTtoConvertModelDialogStudentGroupDomains

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
     * @return Un [ModelState] que contiene la información del grupo o un error.
     */
    suspend operator fun invoke(): ModelState<ModelInfoStudentGroupDomain, String> {
        val userId = preference.getPreferenceInt(ModelPreference.ID_USER)
        val roleId = preference.getPreferenceInt(ModelPreference.ID_ROLE)

        val request = RequestGroup(
            teacherId = roleId,
            userId = userId,
        )

        return runCatching { menuRepository.executeGetGroup(request) }.fold(
            onSuccess = { result ->
                when (result) {
                    is ResultSuccess -> {
                        val convertedResult = result.data.RGTtoConvertModelDialogStudentGroupDomains
                        if (convertedResult.isNotEmpty()) {
                            SuccessState(
                                ModelInfoStudentGroupDomain(
                                    listSchool = convertedResult,
                                    infoSchoolSelected = selectOneGroup(convertedResult)
                                )
                            )
                        } else {
                            ErrorState(ModelCodeError.ERROR_CRITICAL)
                        }
                    }

                    is ResultError -> handleResponse(result.error)
                }
            },
            onFailure = { ErrorState(ModelCodeError.ERROR_UNKNOWN) }
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
            if (preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP) == -1) {
                val item = itemParent.firstOrNull { it.item?.teacherSchoolCycleGroupId != null }
                item?.item?.teacherSchoolCycleGroupId?.let { id ->
                    preference.savePreferenceInt(
                        ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP,
                        id
                    )
                }
                buildOneInformation(item)
            } else {
                val itemImprovised = itemParent.firstOrNull { onlyData ->
                    preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP) == onlyData.item?.teacherSchoolCycleGroupId
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
            nameItem = "${convertedResult?.item?.cct} - ${convertedResult?.item?.group}${convertedResult?.item?.name} - ${convertedResult?.item?.shift}",
            listItemPartial = convertedResult?.listItemPartial,
            itemPartial = convertedResult?.itemPartial,
            namePartial = convertedResult?.namePartial,
        )
        return modelResponse
    }


    /**
     * Maneja las respuestas de error del repositorio del menú.
     *
     * @param error El objeto [FailureService] que representa el error.
     * @return Un [ModelState] que representa el error específico.
     */
    private fun handleResponse(error: FailureService): ModelState<ModelInfoStudentGroupDomain, String> {
        return when (error) {
            is FailureService.BadRequest -> ErrorState(ModelCodeError.ERROR_INCOMPLETE_DATA)
            is FailureService.Unauthorized -> {
                preference.cleanPreference()
                ErrorUnauthorizedState(ModelCodeError.ERROR_UNAUTHORIZED)
            }

            is FailureService.NotFound -> ErrorState(ModelCodeError.ERROR_DATA)
            is FailureService.Timeout -> ErrorState(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }
}