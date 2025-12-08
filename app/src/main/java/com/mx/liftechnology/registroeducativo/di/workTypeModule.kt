package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.domain.usecase.workType.GetListWorkEvaluationFormativeFieldUseCase
import com.mx.liftechnology.domain.usecase.workType.GetListByFieldTypeStudentUseCase
import com.mx.liftechnology.domain.usecase.workType.GetListWotyFofiUseCase
import com.mx.liftechnology.domain.usecase.workType.GetListEvaluationsStudentUseCase
import com.mx.liftechnology.registroeducativo.main.ui.workType.wotyFofiStudent.WotyFofiStudentViewModel
import com.mx.liftechnology.registroeducativo.main.ui.workType.wotyfofi.WotyFofiViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


/**
 * @author Pelkidev
 * @version 1.0.0
 */
val workTypeModule = module {

    /**
     * Proporciona una instancia singleton de [GetListWotyFofiUseCase].
     * Proporciona una instancia singleton de [GetListEvaluationsStudentUseCase].
     */
    singleOf(::GetListWotyFofiUseCase)
    singleOf(::GetListEvaluationsStudentUseCase)

    /**
     * Proporciona una instancia de [WotyFofiStudentViewModel].
     */
    viewModelOf(::WotyFofiStudentViewModel)


    /**
     * Proporciona una instancia de [GetListByFieldTypeStudentUseCase].
     */
    singleOf(::GetListWorkEvaluationFormativeFieldUseCase)
    singleOf(::GetListByFieldTypeStudentUseCase)


    /**
     * Proporciona una instancia de [WotyFofiViewModel].
     */
    viewModelOf(::WotyFofiViewModel)
}