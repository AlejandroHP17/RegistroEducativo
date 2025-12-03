package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.api.StudentApi
import com.mx.liftechnology.data.repositoryImpl.student.DeleteStudentRepositoryImpl
import com.mx.liftechnology.data.repositoryImpl.student.EditStudentRepositoryImpl
import com.mx.liftechnology.data.repositoryImpl.student.GetStudentRepositoryImpl
import com.mx.liftechnology.data.repositoryImpl.student.RegisterStudentRepositoryImpl
import com.mx.liftechnology.domain.repository.student.DeleteStudentRepository
import com.mx.liftechnology.domain.repository.student.EditStudentRepository
import com.mx.liftechnology.domain.repository.student.GetStudentRepository
import com.mx.liftechnology.domain.repository.student.RegisterStudentRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit

val studentDataCoreModule = module {
    /**
     * Provides an instance of [StudentApi].
     */
    factory { get<Retrofit>().create(StudentApi::class.java) }

    /**
     * Provides a singleton instance of [GetStudentRepository].
     */
    singleOf(::DeleteStudentRepositoryImpl) {
        bind<DeleteStudentRepository>()
    }

    /**
     * Provides a singleton instance of [GetStudentRepository].
     */
    singleOf(::EditStudentRepositoryImpl) {
        bind<EditStudentRepository>()
    }

    /**
     * Provides a singleton instance of [GetStudentRepository].
     */
    singleOf(::GetStudentRepositoryImpl) {
        bind<GetStudentRepository>()
    }

    /**
     * Provides a singleton instance of [RegisterStudentRepository].
     */
    singleOf(::RegisterStudentRepositoryImpl) {
        bind<RegisterStudentRepository>()
    }
}