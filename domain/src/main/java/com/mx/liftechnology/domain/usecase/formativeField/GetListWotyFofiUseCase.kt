package com.mx.liftechnology.domain.usecase.formativeField

import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.model.formativeField.ModelWotyFofiData
import com.mx.liftechnology.data.repository.formativeField.GetListWotyFofiRepository
import com.mx.liftechnology.data.util.Error
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.LocalError
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.SuccessResult

class GetListWotyFofiUseCase(
    private val preference: PreferenceUseCase,
    private val getListWotyFofiRepository: GetListWotyFofiRepository
) {

    suspend operator fun invoke() : ModelResult<ModelWotyFofiData, Error> {
        val schoolCycleId = preference.getPreferenceInt(ModelPreference.ID_CYCLE_SCHOOL)

        if (schoolCycleId == null) return ErrorResult(
            LocalError.USER_INCOMPLETE_DATA
        )

        return runCatching {getListWotyFofiRepository.executeGetListWotyFofi(schoolCycleId)}.fold(
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
            onFailure = { ErrorResult(NetworkError.UNKNOWN) }
        )

    }
}