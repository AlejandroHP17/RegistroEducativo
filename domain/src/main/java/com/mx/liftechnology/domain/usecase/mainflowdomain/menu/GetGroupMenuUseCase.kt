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
 * Use case for getting the list of groups for the menu, selecting a default, and processing the information.
 *
 * @property menuRepository The repository for menu-related operations.
 * @property preference The use case for managing user preferences.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetGroupMenuUseCase(
    private val menuRepository: MenuRepository,
    private val preference: PreferenceUseCase,
) {

    /**
     * Executes the process of getting the groups, selecting a default, and processing the information.
     *
     * @return A [ModelState] containing the group information or an error.
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
     * Selects a group from the list. If the user is new, it selects the first one.
     * If the user has a previously selected group, it selects that one from preferences.
     *
     * @param convertedResult The list of groups to select from.
     * @return The selected [ModelDialogStudentGroupDomain].
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
     * Reassembles the data into a new [ModelDialogStudentGroupDomain] object.
     *
     * @param convertedResult The source [ModelDialogStudentGroupDomain] object.
     * @return The newly created [ModelDialogStudentGroupDomain] object.
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
     * Handles error responses from the menu repository.
     *
     * @param error The [FailureService] object representing the error.
     * @return A [ModelState] representing the specific error.
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