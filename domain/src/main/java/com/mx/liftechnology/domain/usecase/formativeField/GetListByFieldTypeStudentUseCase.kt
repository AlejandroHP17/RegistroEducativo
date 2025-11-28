package com.mx.liftechnology.domain.usecase.formativeField

import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.model.formativeField.ModelByFieldTypeStudentData
import com.mx.liftechnology.data.repository.formativeField.GetListByFieldTypeStudentRepository
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.LocalModelError
import com.mx.liftechnology.data.util.ModelError
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.SuccessResult

class GetListByFieldTypeStudentUseCase(
    private val getListByFieldTypeStudentRepository: GetListByFieldTypeStudentRepository,
    private val preference : PreferenceUseCase
) {

    suspend operator fun invoke(workTypeId: Int?, workName: String?, workDate: String?): ModelResult<ModelByFieldTypeStudentData, ModelError> {
        val formativeFieldId = preference.getPreferenceInt(ModelPreference.ID_FORMATIVE_FIELD) ?: return ErrorResult(
            LocalModelError.USER_INCOMPLETE_DATA
        )

        if(workTypeId == null)return ErrorResult(LocalModelError.USER_INCOMPLETE_DATA)

        return runCatching { getListByFieldTypeStudentRepository.executeGetListByFieldTypeStudent(
            formativeFieldId = formativeFieldId,
            workTypeId = workTypeId,
            workName = workName,
            workDate = workDate
            ) }.fold(
            onSuccess = { result ->
                when (result) {
                    is SuccessResult -> {
                        SuccessResult(result.data)
                    }

                    is ErrorResult -> {
                        ErrorResult(result.error)
                    }
                }
            },
            onFailure = { ErrorResult(NetworkModelError.UNKNOWN) }
        )
    }
}