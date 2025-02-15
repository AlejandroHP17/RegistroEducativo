package com.mx.liftechnology.domain.usecase.flowmenu

import com.mx.liftechnology.core.model.modelBase.EmptyState
import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.ErrorStateUser
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.core.network.callapi.CredentialsGroup
import com.mx.liftechnology.core.network.callapi.ResponseGroupTeacher
import com.mx.liftechnology.core.network.util.FailureService
import com.mx.liftechnology.core.network.util.ResultError
import com.mx.liftechnology.core.network.util.ResultSuccess
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.model.ModelAdapterMenu
import com.mx.liftechnology.data.repository.mainFlow.MenuLocalRepository
import com.mx.liftechnology.data.repository.mainFlow.MenuRepository
import com.mx.liftechnology.domain.model.menu.ModelInfoMenu
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface MenuUseCase {
    suspend fun getMenu(schoolYear:Boolean):ModelState<List<ModelAdapterMenu>,String>
    suspend fun getGroup(): ModelState<ModelInfoMenu, String>
}

/** MenuUseCase - Get the list of menu and process the information
 * @author pelkidev
 * @date 28/08/2023
 * @param localRepository connect with the repository
 * */
class MenuUseCaseImp(
    private val localRepository: MenuLocalRepository,
    private val menuRepository: MenuRepository,
    private val preference: PreferenceUseCase
) : MenuUseCase{

    override suspend fun getMenu(schoolYear:Boolean): ModelState<List<ModelAdapterMenu>,String> {
        return withContext(Dispatchers.IO) {
            try {
                val list = localRepository.getItems(schoolYear)
                if (list.isEmpty()) {
                    EmptyState(ModelCodeError.ERROR_EMPTY)
                } else {

                    SuccessState(list)
                }
            } catch (e: Exception) {
                ErrorState(ModelCodeError.ERROR_CATCH)
            }
        }
    }

    private fun validateCycleGroup(data: ResponseGroupTeacher?) {
        data?.teacherSchoolCycleGroupId.let {
            if(preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP) == -1 )
            preference.savePreferenceInt( ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP, it)
        }
    }

    override suspend fun getGroup() : ModelState<ModelInfoMenu, String>{
        val userId= preference.getPreferenceInt(ModelPreference.ID_USER)
        val roleId= preference.getPreferenceInt(ModelPreference.ID_ROLE)

        val request = CredentialsGroup(
            profesor_id = roleId,
            user_id = userId,
        )

        return when (val result = menuRepository.executeGetGroup(request)) {
            is ResultSuccess -> {
                if(result.data?.size!=0){
                    validateCycleGroup(result.data?.firstOrNull())
                    SuccessState(buildInformation(result.data))
                }else{
                    ErrorStateUser(ModelCodeError.ERROR_CRITICAL)
                }
            }
            is ResultError -> handleResponse(result.error)
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }

    private fun buildInformation(data: List<ResponseGroupTeacher?>?): ModelInfoMenu{
        val modelResponse = ModelInfoMenu(
            listSchool = data,
            infoSchoolSelected = data?.firstOrNull()!!,
            infoShowSchool = "${data.firstOrNull()?.cct} - ${data.firstOrNull()?.group}${data.firstOrNull()?.name} - ${data.firstOrNull()?.shift}")
        return modelResponse
    }

    /** handleResponse - Validate the code response, and assign the correct function of that
     * @author pelkidev
     * @since 1.0.0
     * @param error in order to validate the code and if is success, return the body
     * if not return the correct error
     * @return ModelState
     */
    private fun handleResponse(error: FailureService): ModelState<ModelInfoMenu, String> {
        return when (error) {
            is FailureService.BadRequest -> ErrorState(ModelCodeError.ERROR_INCOMPLETE_DATA)
            is FailureService.Unauthorized -> ErrorState(ModelCodeError.ERROR_UNAUTHORIZED)
            is FailureService.NotFound -> ErrorState(ModelCodeError.ERROR_DATA)
            is FailureService.Timeout -> ErrorState(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }
}


