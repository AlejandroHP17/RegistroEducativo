package com.mx.liftechnology.registroeducativo.main.mapper

import com.mx.liftechnology.data.model.formativeField.ModelListWorkStudentFormativeField
import com.mx.liftechnology.data.model.formativeField.ModelWorkTypeByFormativeField
import com.mx.liftechnology.data.model.formativeField.ModelWorkTypeFormativeField
import com.mx.liftechnology.data.model.formativeField.ModelWotyFofiData
import com.mx.liftechnology.data.model.formativeField.ResponseWorkTypesData
import com.mx.liftechnology.domain.model.formativeFields.ModelFormatFormativeFieldsDomain
import com.mx.liftechnology.domain.model.generic.ModelCustomSpinner
import com.mx.liftechnology.domain.model.registerschool.ModelSpinnerSchoolDomain
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.SpinnerSchoolUi
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelComplexCard
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelCustomCard
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelSubComplexCard

/**
 * Mapper centralizado para convertir modelos del dominio a modelos de UI.
 * Este mapper mantiene la separación de capas, centralizando toda la lógica de transformación.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
object DomainToUIMapper {

    /**
     * Convierte una lista de materias del dominio a una lista de ModelCustomCard para la UI.
     *
     * @param subjects La lista de materias del dominio a convertir.
     * @return Una lista de ModelCustomCard formateada para mostrar en la UI.
     */
    fun mapSubjectListToCustomCard(subjects: List<ModelFormatFormativeFieldsDomain>?): List<ModelCustomCard> {
        return subjects?.map {
            ModelCustomCard(
                id = it.formativeFieldId?:0,
                numberList = "",
                nameCard = "${it.name}"
            )
        } ?: emptyList()
    }

    fun String.toModelCustomSpinner(): ModelCustomSpinner {
        return ModelCustomSpinner(
            value = this,
            id = this.toInt()
        )
    }

    fun ModelSpinnerSchoolDomain?.toUi(): SpinnerSchoolUi {
        return SpinnerSchoolUi(
            type = this?.type?.map { ModelCustomSpinner(id = 0, value = it.value) },
            cycle = this?.cycle?.map { ModelCustomSpinner(id = 0, value = it.value) },
            grade = this?.grade?.map { ModelCustomSpinner(id = 0, value = it.value) },
            group = this?.group?.map { ModelCustomSpinner(id = 0, value = it.value) }
        )
    }

    fun ModelWorkTypeByFormativeField.toCustomSpinnerList(): List<ModelCustomSpinner>? {
        return this.workTypes.map { workType ->
            ModelCustomSpinner(
                id = workType.workTypeId,
                value = workType.workTypeName
            )
        }
    }
    
    fun ModelWotyFofiData.toComplexCardUI():List<ModelComplexCard>{
        return this.formativeFields.mapNotNull { formativeField ->
            ModelComplexCard(
                idTitle = formativeField.formativeFieldId,
                nameTitle = formativeField.formativeFieldName,
                isShowTitle = true,
                isExpandedTitle = false,
                list = formativeField.listWorkTypes.mapNotNull { workType ->
                    workType.toData() }
            )
        }

    }

    // --- Extensión para el nivel intermedio ---
    fun ResponseWorkTypesData.toData(): ModelSubComplexCard {
        return ModelSubComplexCard(
            idSubTitle = this.workTypeId,
            nameSubTitle = this.workTypeName,
            isShowSubTitle = false,
            isExpandedSubTitle = false,
            list = null,
            date = null
        )
    }

    fun ModelWorkTypeFormativeField.toComplexCardUI():List<ModelComplexCard>{
        return this.listWorks.mapNotNull { workType ->
            ModelComplexCard(
                idTitle = workType.workId,
                nameTitle = workType.workName,
                isShowTitle = true,
                isExpandedTitle = false,
                list = workType.listWorks.mapNotNull { it.toData() }
            )
        }
    }

    fun ModelListWorkStudentFormativeField.toData(): ModelSubComplexCard {
        return ModelSubComplexCard(
            idSubTitle = this.workStudentId,
            nameSubTitle = this.workStudentName,
            isShowSubTitle = true,
            isExpandedSubTitle = false,
            list = null,
            date = this.workStudentDate
        )
    }
}

