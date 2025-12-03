package com.mx.liftechnology.registroeducativo.di
/**
 * @file Define el módulo de Koin para dependencias relacionadas con asignaciones.
 * @author Pelkidev
 * @version 1.0.0
 */


import com.mx.liftechnology.domain.usecase.formativeField.GetListByFieldTypeStudentUseCase
import com.mx.liftechnology.domain.usecase.formativeField.GetListWotyFofiUseCase
import com.mx.liftechnology.domain.usecase.evaluation.GetWorkTypeByFormativeFieldUseCase
import com.mx.liftechnology.domain.usecase.student.GetListEvaluationsStudentUseCase
import com.mx.liftechnology.registroeducativo.main.ui.workType.wotyfofi.WotyFofiViewModel
import com.mx.liftechnology.registroeducativo.main.ui.workType.wotyFofiStudent.WotyFofiStudentViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


/**
 * Módulo de Koin para dependencias relacionadas con asignaciones.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val wotyFofiModule = module {

    singleOf(::GetListWotyFofiUseCase)
    singleOf(::GetWorkTypeByFormativeFieldUseCase)
    singleOf(::GetListEvaluationsStudentUseCase)
    singleOf(::GetListByFieldTypeStudentUseCase)


    /**
     * Proporciona una instancia de [WotyFofiStudentViewModel].
     */
    viewModelOf(::WotyFofiStudentViewModel)
    /**
     * Proporciona una instancia de [WotyFofiViewModel].
     */
    viewModelOf(::WotyFofiViewModel)
}
