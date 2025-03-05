package com.mx.liftechnology.domain.usecase.mainflowdomain

import com.mx.liftechnology.domain.model.generic.ModelVoiceConstants
import java.text.SimpleDateFormat
import java.util.Locale

fun interface ValidateVoiceStudentUseCase {
    suspend fun buildModelStudent(data: String?): MutableMap<String, String>?
}

class ValidateVoiceStudentUseCaseImp : ValidateVoiceStudentUseCase {

    companion object RegexPatterns {
        const val NAME_REGEX = "Nombre\\s+([A-Za-zÁÉÍÓÚáéíóúüÜñÑ]+)"
        const val LAST_NAME_REGEX = "Apellido paterno\\s+([A-Za-zÁÉÍÓÚáéíóúüÜñÑ]+)"
        const val SECOND_LAST_NAME_REGEX = "Apellido Materno\\s+([A-Za-zÁÉÍÓÚáéíóúüÜñÑ]+)"
        const val CURP_REGEX = "([A-Z]{4}\\d{6}[HM][A-Z]{5}[A-Z0-9]{2})"
        const val BIRTHDAY_REGEX = "fecha de nacimiento\\s+([0-9]{1,2} de [A-Za-z]+ de [0-9]{4})"
        const val PHONE_NUMBER_REGEX = "Número de contacto\\s+([0-9 ]+)"
    }


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

    // Función para capitalizar nombres y apellidos
    private fun capitalizeWords(input: String): String {
        return input.lowercase().split(" ").joinToString(" ") { it.replaceFirstChar { c -> c.uppercase() } }
    }

    // Función para convertir fechas a formato "YYYY-MM-DD"
    private fun convertDate(textDate: String): String? {
        val inputFormat = SimpleDateFormat("d 'de' MMMM 'de' yyyy", Locale("es", "ES"))
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        return try {
            val date = inputFormat.parse(textDate)
            outputFormat.format(date)
        } catch (e: Exception) {
            null
        }
    }

    // Función para limpiar y validar número de contacto
    private fun formatPhoneNumber(phone: String): String {
        return phone.filter { it.isDigit() }.takeIf { it.length == 10 } ?: "Número inválido"
    }
}
