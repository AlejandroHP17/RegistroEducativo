package com.mx.liftechnology.domain.usecase.flowregisterdata

import java.text.SimpleDateFormat
import java.util.Locale

fun interface ValidateVoiceStudentUseCase {
    suspend fun buildModelStudent(data: String?): MutableMap<String, String>?
}

class ValidateVoiceStudentUseCaseImp : ValidateVoiceStudentUseCase {

    object RegexPatterns {
        const val NAME_REGEX = "Nombre\\s+([A-Za-zÁÉÍÓÚáéíóúüÜñÑ]+)"
        const val LAST_NAME_REGEX = "Apellido paterno\\s+([A-Za-zÁÉÍÓÚáéíóúüÜñÑ]+)"
        const val SECOND_LAST_NAME_REGEX = "Apellido Materno\\s+([A-Za-zÁÉÍÓÚáéíóúüÜñÑ]+)"
        const val CURP_REGEX = "curp\\s+([A-Z0-9]{18})" // CURP debe ser exactamente 18 caracteres
        const val BIRTHDAY_REGEX = "fecha de nacimiento\\s+([0-9]{1,2} de [A-Za-z]+ de [0-9]{4})"
        const val PHONE_NUMBER_REGEX = "Numero de contacto\\s+([0-9 ]+)"
    }

    override suspend fun buildModelStudent(data: String?): MutableMap<String, String>? {
        return data?.let {
            val regexPatterns = mapOf(
                "nombre" to RegexPatterns.NAME_REGEX,
                "apellido paterno" to RegexPatterns.LAST_NAME_REGEX,
                "apellido materno" to RegexPatterns.SECOND_LAST_NAME_REGEX,
                "curp" to RegexPatterns.CURP_REGEX,
                "fecha de nacimiento" to RegexPatterns.BIRTHDAY_REGEX,
                "número de contacto" to RegexPatterns.PHONE_NUMBER_REGEX
            )

            mutableMapOf<String, String>().apply {
                regexPatterns.forEach { (key, pattern) ->
                    val regex = Regex(pattern, RegexOption.IGNORE_CASE)
                    val match = regex.find(data)
                    val value = match?.groupValues?.get(1)?.trim()

                    if (!value.isNullOrEmpty()) {
                        this[key] = when (key) {
                            "nombre", "apellido paterno", "apellido materno" -> capitalizeWords(value)
                            "curp" -> value.replace(" ", "").uppercase()
                            "número de contacto" -> formatPhoneNumber(value)
                            "fecha de nacimiento" -> convertDate(value) ?: "Fecha inválida"
                            else -> value
                        }
                    }
                }
            }
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
