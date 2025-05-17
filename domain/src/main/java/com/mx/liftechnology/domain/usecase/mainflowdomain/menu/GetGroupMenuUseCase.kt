package com.mx.liftechnology.domain.usecase.mainflowdomain.menu

import com.mx.liftechnology.core.network.callapi.CredentialsGroup
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.mainflowdata.MenuRepository
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

class GetGroupMenuUseCase(
    private val menuRepository: MenuRepository,
    private val preference: PreferenceUseCase,
) {

    /** getGroup - Get the list of groups, select a default and process the information
     * @author pelkidev
     * @return ModelState
     * */
    suspend operator fun invoke(): ModelState<ModelInfoStudentGroupDomain, String> {
        val userId = preference.getPreferenceInt(ModelPreference.ID_USER)
        val roleId = preference.getPreferenceInt(ModelPreference.ID_ROLE)

        val request = CredentialsGroup(
            teacherId = roleId,
            userId = userId,
        )

        return runCatching { menuRepository.executeGetGroup(request) }.fold(
            onSuccess = { result ->
                when (result) {
                    is ResultSuccess -> {
                        /* Convierte al modelo base para recopilar toda la informacion de escuelas y parciales
                        *  esta primera sección llena la parte de escuelas*/
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

    /** SelectOneGroup - Select a group, if the user is new the first one,
     *  if the user is old, select group with help of the preference
     * @author pelkidev
     * @return ModelState
     * */
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

    /** Reasign data in the new model
     * @author Alejandro Hernandez Pelcastre
     * @since 1.0.0
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


    /** handleResponse - Validate the code response, and assign the correct function of that
     * @author pelkidev
     * @since 1.0.0
     * @param error in order to validate the code and if is success, return the body
     * if not return the correct error
     * @return ModelState
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