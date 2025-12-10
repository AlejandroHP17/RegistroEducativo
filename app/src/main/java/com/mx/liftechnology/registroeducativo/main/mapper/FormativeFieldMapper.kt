package com.mx.liftechnology.registroeducativo.main.mapper

import com.mx.liftechnology.domain.model.evaluation.ListWorkStudentFormativeFieldDomain
import com.mx.liftechnology.domain.model.evaluation.WorkTypeByFormativeFieldDomain
import com.mx.liftechnology.domain.model.evaluation.WorkTypeFormativeFieldDomain
import com.mx.liftechnology.domain.model.formativeFields.WotyFofiDomain
import com.mx.liftechnology.domain.model.formativeFields.ListWorkTypesDomain
import com.mx.liftechnology.registroeducativo.main.model.formativeFields.FormativeFieldDomainPar
import com.mx.liftechnology.domain.model.generic.ModelCustomSpinner
import com.mx.liftechnology.registroeducativo.main.model.share.ModelComplexCard
import com.mx.liftechnology.registroeducativo.main.model.share.CustomCard
import com.mx.liftechnology.registroeducativo.main.model.share.ModelSubComplexCard

/**
 * Mapper para convertir modelos del dominio de materias formativas a modelos de UI.
 * Incluye validaciones para asegurar la integridad de los datos.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
object FormativeFieldMapper {

    /**
     * Convierte una lista de materias del dominio a una lista de ModelCustomCard para la UI.
     * Valida que cada materia tenga un ID válido antes de mapearla.
     *
     * @param formativeFields La lista de materias del dominio a convertir.
     * @return Una lista de ModelCustomCard formateada para mostrar en la UI.
     */
    fun mapFormativeFieldListToCustomCard(formativeFields: List<FormativeFieldDomainPar>?, isVisibleMenu: Boolean): List<CustomCard> {
        if (formativeFields == null) return emptyList()
        
        return formativeFields.mapNotNull { formativeField ->
            val id = formativeField.formativeFieldId
            if (id == null || id <= 0) {
                // Log o manejo de error si es necesario
                null
            } else {
                CustomCard(
                    id = id,
                    numberList = "",
                    nameCard = formativeField.name?.takeIf { it.isNotBlank() } ?: "Sin nombre",
                    isVisibleMenu = isVisibleMenu
                )
            }
        }
    }

    /**
     * Convierte ModelWorkTypeByFormativeField a una lista de ModelCustomSpinner.
     * Valida que la lista de workTypes no esté vacía.
     *
     * @param workTypeByFormativeField El objeto que contiene los tipos de trabajo.
     * @return Una lista de ModelCustomSpinner o null si no hay tipos de trabajo válidos.
     */
    fun WorkTypeByFormativeFieldDomain.toCustomSpinnerList(): List<ModelCustomSpinner>? {
        val workTypes = this.workTypes
        if (workTypes.isNullOrEmpty()) return null
        
        return workTypes.mapNotNull { workType ->
            val id = workType.workTypeId
            val name = workType.workTypeName
            
            if (id == null || id <= 0 || name.isNullOrBlank()) {
                null
            } else {
                ModelCustomSpinner(
                    id = id,
                    value = name
                )
            }
        }.takeIf { it.isNotEmpty() }
    }

    /**
     * Convierte ModelWotyFofiData a una lista de ModelComplexCard para la UI.
     * Valida que cada campo formativo tenga datos válidos.
     *
     * @param wotyFofiData Los datos de trabajo por campo formativo.
     * @return Una lista de ModelComplexCard formateada para mostrar en la UI.
     */
    fun WotyFofiDomain.toComplexCardUI(): List<ModelComplexCard> {
        val formativeFields = this.formativeFields
        if (formativeFields.isNullOrEmpty()) return emptyList()
        
        return formativeFields.mapNotNull { formativeField ->
            val fieldId = formativeField.formativeFieldId
            val fieldName = formativeField.formativeFieldName
            val workTypes = formativeField.listWorkTypes
            
            if (fieldId == null || fieldId <= 0 || fieldName.isNullOrBlank()) {
                null
            } else {
                ModelComplexCard(
                    idTitle = fieldId,
                    nameTitle = fieldName,
                    isShowTitle = true,
                    isExpandedTitle = false,
                    list = workTypes?.mapNotNull { it.toData() }?.takeIf { it.isNotEmpty() }
                )
            }
        }
    }

    /**
     * Convierte ResponseWorkTypesData a ModelSubComplexCard.
     * Valida que los datos requeridos estén presentes.
     *
     * @param workTypesData Los datos del tipo de trabajo.
     * @return Un ModelSubComplexCard o null si los datos no son válidos.
     */
    fun ListWorkTypesDomain.toData(): ModelSubComplexCard? {
        val workTypeId = this.workTypeId
        val workTypeName = this.workTypeName
        
        if (workTypeId == null || workTypeId <= 0 || workTypeName.isNullOrBlank()) {
            return null
        }
        
        return ModelSubComplexCard(
            idSubTitle = workTypeId,
            nameSubTitle = workTypeName,
            isShowSubTitle = false,
            isExpandedSubTitle = false,
            list = null,
            date = null
        )
    }

    /**
     * Convierte ModelWorkTypeFormativeField a una lista de ModelComplexCard.
     * Valida que cada trabajo tenga datos válidos.
     *
     * @param workTypeFormativeField Los datos de trabajo por campo formativo.
     * @return Una lista de ModelComplexCard formateada para mostrar en la UI.
     */
    fun WorkTypeFormativeFieldDomain.toComplexCardUI(): List<ModelComplexCard> {
        val listWorks = this.listWorks
        if (listWorks.isNullOrEmpty()) return emptyList()
        
        return listWorks.mapNotNull { workType ->
            val workId = workType.workId
            val workName = workType.workName
            val listWorks = workType.listWorks
            
            if (workId == null || workId <= 0 || workName.isNullOrBlank()) {
                null
            } else {
                ModelComplexCard(
                    idTitle = workId,
                    nameTitle = workName,
                    isShowTitle = true,
                    isExpandedTitle = false,
                    list = listWorks?.mapNotNull { it.toData() }?.takeIf { it.isNotEmpty() }
                )
            }
        }
    }

    /**
     * Convierte ModelListWorkStudentFormativeField a ModelSubComplexCard.
     * Valida que los datos requeridos estén presentes.
     *
     * @param workStudentFormativeField Los datos del trabajo del estudiante.
     * @return Un ModelSubComplexCard o null si los datos no son válidos.
     */
    fun ListWorkStudentFormativeFieldDomain.toData(): ModelSubComplexCard? {
        val workStudentId = this.workStudentId
        val workStudentName = this.workStudentName
        
        if (workStudentId == null || workStudentId <= 0 || workStudentName.isNullOrBlank()) {
            return null
        }
        
        return ModelSubComplexCard(
            idSubTitle = workStudentId,
            nameSubTitle = workStudentName,
            isShowSubTitle = true,
            isExpandedSubTitle = false,
            list = null,
            date = this.workStudentDate
        )
    }
}

