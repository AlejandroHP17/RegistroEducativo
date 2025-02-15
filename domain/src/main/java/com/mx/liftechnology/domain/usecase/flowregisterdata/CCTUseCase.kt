package com.mx.liftechnology.domain.usecase.flowregisterdata

import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.network.callapi.ResponseCctSchool
import com.mx.liftechnology.data.repository.mainFlow.CCTRepository

interface CCTUseCase {
    suspend fun getSchoolCCT(cct: String): ModelState<ResponseCctSchool?, String>
}

class CCTUseCaseImp(
    private val cctRepository : CCTRepository
) : CCTUseCase {


    /** Validate CCT
     * @author pelkidev
     * @since 1.0.0
     * */
    override suspend fun getSchoolCCT(cct: String): ModelState<ResponseCctSchool?, String> {
        return cctRepository.executeSchoolCCT(cct)
    }

}