package com.mx.liftechnology.registroeducativo.main.model.share

/**
 * Modelos de datos para representar estructuras de tarjetas complejas anidadas.
 * 
 * Estos modelos se utilizan para representar una jerarquía de datos:
 * - Campos formativos (ModelComplexCard)
 * - Tipos de trabajo (ModelSubComplexCard)
 * - Evaluaciones (ModelSubSubComplexCard)
 *
 * @author Pelkidev
 * @version 1.0.0
 */

/**
 * Representa una tarjeta compleja de nivel superior para campos formativos.
 *
 * @property idTitle El ID del título del campo formativo.
 * @property nameTitle El nombre del campo formativo.
 * @property isShowTitle Indica si el título debe mostrarse.
 * @property isExpandedTitle Indica si el título está expandido.
 * @property list La lista de sub-tarjetas (tipos de trabajo) asociadas.
 */
data class ModelComplexCard(
    val idTitle : Int?,
    val nameTitle : String?,
    val isShowTitle: Boolean = false,
    val isExpandedTitle : Boolean = false,
    val list : List<ModelSubComplexCard?>?,
)

/**
 * Representa una tarjeta compleja de nivel medio para tipos de trabajo.
 *
 * @property idSubTitle El ID del subtítulo del tipo de trabajo.
 * @property nameSubTitle El nombre del tipo de trabajo.
 * @property isShowSubTitle Indica si el subtítulo debe mostrarse.
 * @property isExpandedSubTitle Indica si el subtítulo está expandido.
 * @property list La lista de sub-sub-tarjetas (evaluaciones) asociadas.
 * @property date La fecha asociada al tipo de trabajo.
 */
data class ModelSubComplexCard(
    val idSubTitle : Int?,
    val nameSubTitle : String?,
    val isShowSubTitle: Boolean = false,
    val isExpandedSubTitle : Boolean = false,
    val list : List<ModelSubSubComplexCard?>?,
    val date : String?,
)

/**
 * Representa una tarjeta compleja de nivel inferior para evaluaciones.
 *
 * @property idDescription El ID de la descripción de la evaluación.
 * @property nameDescription El nombre o descripción de la evaluación.
 * @property grade La calificación de la evaluación.
 * @property isShowDescription Indica si la descripción debe mostrarse.
 */
data class ModelSubSubComplexCard(
    val idDescription : Int,
    val nameDescription : String?,
    val grade : Double?,
    val isShowDescription: Boolean = false
)