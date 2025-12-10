package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.domain.usecase.workType.GetListWorkEvaluationFormativeFieldUseCase
import com.mx.liftechnology.domain.usecase.workType.GetListByFieldTypeStudentUseCase
import com.mx.liftechnology.domain.usecase.workType.GetListWotyFofiUseCase
import com.mx.liftechnology.domain.usecase.workType.GetListEvaluationsStudentUseCase
import com.mx.liftechnology.registroeducativo.main.ui.workType.wotyByStudent.WotyByStudentViewModel
import com.mx.liftechnology.registroeducativo.main.ui.workType.wotyByFormativeField.WotyByFormativeFieldViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/**
 * Módulo de Koin para dependencias relacionadas con tipos de trabajo (work types).
 * 
 * Este módulo proporciona las instancias necesarias para:
 * - Obtención de tipos de trabajo por campo formativo
 * - Obtención de evaluaciones de estudiantes por tipo de trabajo
 * - Obtención de tipos de trabajo por estudiante
 * - Visualización de tipos de trabajo agrupados por estudiante o campo formativo
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val workTypeModule = module {

    /**
     * Proporciona una instancia singleton de [GetListWotyFofiUseCase].
     * Caso de uso para obtener la lista de tipos de trabajo por campo formativo.
     */
    singleOf(::GetListWotyFofiUseCase)
    
    /**
     * Proporciona una instancia singleton de [GetListEvaluationsStudentUseCase].
     * Caso de uso para obtener la lista de evaluaciones de estudiantes.
     */
    singleOf(::GetListEvaluationsStudentUseCase)

    /**
     * Proporciona una instancia de [WotyByStudentViewModel].
     * ViewModel para la pantalla de tipos de trabajo agrupados por estudiante.
     */
    viewModelOf(::WotyByStudentViewModel)

    /**
     * Proporciona una instancia singleton de [GetListWorkEvaluationFormativeFieldUseCase].
     * Caso de uso para obtener la lista de evaluaciones de trabajo por campo formativo.
     */
    singleOf(::GetListWorkEvaluationFormativeFieldUseCase)
    
    /**
     * Proporciona una instancia singleton de [GetListByFieldTypeStudentUseCase].
     * Caso de uso para obtener la lista de estudiantes por tipo de campo formativo.
     */
    singleOf(::GetListByFieldTypeStudentUseCase)

    /**
     * Proporciona una instancia de [WotyByFormativeFieldViewModel].
     * ViewModel para la pantalla de tipos de trabajo agrupados por campo formativo.
     */
    viewModelOf(::WotyByFormativeFieldViewModel)
}