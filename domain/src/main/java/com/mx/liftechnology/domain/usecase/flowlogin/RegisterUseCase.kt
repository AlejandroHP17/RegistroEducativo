package com.mx.liftechnology.domain.usecase.flowlogin

import com.mx.liftechnology.core.model.ModelApi.GenericResponse
import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelCodeSuccess
import com.mx.liftechnology.core.model.modelBase.ModelRegex
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.data.repository.loginFlow.RegisterRepository

class RegisterUseCase(
    private val registerRepository: RegisterRepository
) {

    /** Request to Register
     * @author pelkidev
     * @since 1.0.0
     * */
    suspend fun putRegister(
        email: String,
        pass: String,
        activatationCode: String
    )
            : ModelState<GenericResponse<String>> {
        return runCatching {
            registerRepository.executeRegister(
                email,
                pass,
                activatationCode
            )
        }
            .fold(
                onSuccess = { data ->
                    if (data != null) SuccessState(data)
                    else ErrorState(1)
                },
                onFailure = { exception ->
                    ErrorState(1)
                }
            )
    }


    /** Validate Email
     * @author pelkidev
     * @since 1.0.0
     * */
    fun validateEmail(email: String?): ModelState<Int> {
        val patEmail = ModelRegex.EMAIL
        return when {
            email.isNullOrEmpty() -> {
                ErrorState(ModelCodeError.ET_EMPTY)
            }

            !patEmail.matches(email) -> {
                ErrorState(ModelCodeError.ET_FORMAT)
            }

            else -> {
                SuccessState(ModelCodeSuccess.ET_FORMAT)
            }
        }
    }

    /** Validate Pass
     * @author pelkidev
     * @since 1.0.0
     * */
    fun validatePass(pass: String?): ModelState<Int> {
        return when {
            pass.isNullOrEmpty() -> {
                ErrorState(ModelCodeError.ET_EMPTY)
            }

            !validPass(pass) -> {
                ErrorState(ModelCodeError.ET_INCORRECT_FORMAT)
            }

            else -> {
                SuccessState(ModelCodeSuccess.ET_FORMAT)
            }
        }
    }

    private fun validPass(pass: String): Boolean {
        val regex = ModelRegex.PASS
        return regex.matches(pass)
    }


    /** Validate RepeatPass
     * @author pelkidev
     * @since 1.0.0
     * */
    fun validateRepeatPass(pass: String?, repeatPass: String?): ModelState<Int> {
        return when {
            repeatPass.isNullOrEmpty() -> {
                ErrorState(ModelCodeError.ET_EMPTY)
            }

            repeatPass != pass -> {
                ErrorState(ModelCodeError.ET_DIFFERENT)
            }

            else -> {
                SuccessState(ModelCodeSuccess.ET_FORMAT)
            }
        }
    }



    /** Validate Code
     * @author pelkidev
     * @since 1.0.0
     * */
    fun validateCode(code: String?): ModelState<Int> {
        return when {
            code.isNullOrEmpty() -> {
                ErrorState(ModelCodeError.ET_EMPTY)
            }

            else -> {
                SuccessState(ModelCodeSuccess.ET_FORMAT)
            }
        }
    }

}