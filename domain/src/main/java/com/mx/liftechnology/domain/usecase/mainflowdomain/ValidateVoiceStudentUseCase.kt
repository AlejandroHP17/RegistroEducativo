/**
 * @file Proporciona el caso de uso para validar y procesar los datos de un estudiante a partir de una entrada de voz.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.usecase.mainflowdomain

import com.mx.liftechnology.domain.model.generic.ModelVoiceConstants
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Interfaz para el caso de uso que valida y procesa los datos de un estudiante a partir de una cadena de texto (voz).
 * Define el contrato para construir un modelo de estudiante a partir de una entrada de texto plano.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface ValidateVoiceStudentUseCase {
    /**
     * Construye un mapa de datos de estudiante a partir de una cadena de texto.
     * La función procesa el texto para extraer y formatear los campos relevantes del estudiante.
     *
     * @param data La cadena de texto cruda, generalmente proveniente de un reconocimiento de voz.
     * @return Un mapa mutable que contiene la información del estudiante procesada, o `null` si la entrada es nula o vacía.
     */
    suspend fun buildModelStudent(data: String?): MutableMap<String, String>?
}

/**
 * Implementación de [ValidateVoiceStudentUseCase].
 * Utiliza expresiones regulares para extraer y formatear los datos del estudiante.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class ValidateVoiceStudentUseCaseImp : ValidateVoiceStudentUseCase {

    /**
     * Objeto que contiene las expresiones regulares para procesar la entrada de voz.
     */
    companion object RegexPatterns {
        const val NAME_REGEX = "Nombre\\s+([A-Za-zÁÉÍÓÚáéíóúüÜñÑ]+)"
        const val LAST_NAME_REGEX = "Apellido paterno\\s+([A-Za-zÁÉÍÓÚáéíóúüÜñÑ]+)"
        const val SECOND_LAST_NAME_REGEX = "Apellido Materno\\s+([A-Za-zÁÉÍÓÚáéíóúüÜñÑ]+)"
        const val CURP_REGEX = "([A-Z]{4}\\d{6}[HM][A-Z]{5}[A-Z0-9]{2})"
        const val BIRTHDAY_REGEX = "fecha de nacimiento\\s+([0-9]{1,2} de [A-Za-z]+ de [0-9]{4})"
        const val PHONE_NUMBER_REGEX = "Número de contacto\\s+([0-9 ]+)"
    }

    /**
     * {@inheritDoc}
     */
    override suspend fun buildModelStudent(data: String?): MutableMap<String, String>? {
        return data?.let {
            val regexPatterns = mapOf(
                ModelVoiceConstants.NAME to NAME_REGEX,
                ModelVoiceConstants.LAST_NAME to LAST_NAME_REGEX,
                ModelVoiceConstants.SECOND_LAST_NAME to SECOND_LAST_NAME_REGEX,
                ModelVoiceConstants.BIRTHDAY to BIRTHDAY_REGEX,
                ModelVoiceConstants.PHONE_NUMBER to PHONE_NUMBER_REGEX
            )

            val resultMap = mutableMapOf<String, String>()

            val cleanCurpData = data.replace(" ", "") // Elimina todos los espacios
            val curpMatch = Regex(CURP_REGEX, RegexOption.IGNORE_CASE).find(cleanCurpData)

            curpMatch?.let {
                resultMap[ModelVoiceConstants.CURP] = it.value.uppercase()
            }

            val cleanData = curpMatch?.let { data.replace(it.value.toRegex(), " ") } ?: data

            regexPatterns.forEach { (key, pattern) ->
                val regex = Regex(pattern, RegexOption.IGNORE_CASE)
                val match = regex.find(cleanData)
                val value = match?.groupValues?.get(1)?.trim()

                if (!value.isNullOrEmpty()) {
                    resultMap[key] = when (key) {
                        ModelVoiceConstants.NAME, ModelVoiceConstants.LAST_NAME,ModelVoiceConstants.SECOND_LAST_NAME -> capitalizeWords(value)
                        ModelVoiceConstants.PHONE_NUMBER -> formatPhoneNumber(value)
                        ModelVoiceConstants.BIRTHDAY -> convertDate(value) ?: "Fecha inválida"
                        else -> value
                    }
                }
            }
            resultMap
        }
    }

    /**
     * Capitaliza la primera letra de cada palabra en una cadena de texto.
     * @param input La cadena a capitalizar.
     * @return La cadena con la primera letra de cada palabra en mayúscula.
     */
    private fun capitalizeWords(input: String): String {
        return input.lowercase().split(" ").joinToString(" ") { it.replaceFirstChar { c -> c.uppercase() } }
    }

    /**
     * Convierte una fecha en formato "d de MMMM de yyyy" a "yyyy-MM-dd".
     * @param textDate La fecha en formato de texto.
     * @return La fecha convertida, o `null` si el formato es inválido.
     */
    private fun convertDate(textDate: String): String? {
        val inputFormat = SimpleDateFormat("d 'de' MMMM 'de' yyyy", Locale("es", "ES"))
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        return try {
            val date = inputFormat.parse(textDate)
            outputFormat.format(date!!)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Formatea un número de teléfono para que contenga exactamente 10 dígitos.
     * @param phone El número de teléfono a formatear.
     * @return El número de 10 dígitos, o "Número inválido" si no cumple con el formato.
     */
    private fun formatPhoneNumber(phone: String): String {
        return phone.filter { it.isDigit() }.takeIf { it.length == 10 } ?: "Número inválido"
    }
}