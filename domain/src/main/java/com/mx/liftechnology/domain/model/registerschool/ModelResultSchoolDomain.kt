package com.mx.liftechnology.domain.model.registerschool

import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseCctSchool

/**
 * Data model representing the result of a school search in the domain layer.
 *
 * @property spinners The data required for the spinners in the school registration form.
 * @property result The raw school data received from the API.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ModelResultSchoolDomain(
    val spinners : ModelSpinnerSchoolDomain,
    val result :  ResponseCctSchool?
)
