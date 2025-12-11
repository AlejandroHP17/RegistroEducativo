package com.mx.liftechnology.registroeducativo.main.util

import com.mx.liftechnology.domain.model.schoolCycle.CCTPeriodCatalogDomain
import com.mx.liftechnology.domain.model.generic.ModelCustomSpinner
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

/**
 * Extensiones de funciones para conversión y transformación de datos relacionados con ciclos escolares.
 *
 * @author Pelkidev
 * @version 1.0.0
 */

/**
 * Convierte una lista de catálogos de períodos CCT en el ID del período que coincide con el ciclo y tipo especificados.
 *
 * @param cycle El número del ciclo como String.
 * @param type El nombre del tipo de período.
 * @return El ID del período encontrado, o 0 si no se encuentra ninguna coincidencia.
 */
fun List<CCTPeriodCatalogDomain>?.toSelectPeriod(cycle: String, type: String): Int {
    return this!!
        .firstOrNull { it.typeName == type && it.periodNumber == cycle.toInt() }
        ?.id?: 0
}

/**
 * Filtra y transforma una lista de catálogos de períodos CCT en una lista de spinners personalizados,
 * agrupados por tipo de período.
 *
 * @param typeName El nombre del tipo de período a filtrar.
 * @return Una lista de [ModelCustomSpinner] con los números de período únicos del tipo especificado.
 */
fun List<CCTPeriodCatalogDomain>.getPeriodsByType(typeName: String): List<ModelCustomSpinner> {
    return this
        .filter { it.typeName == typeName }
        .map { it.periodNumber }
        .distinct()
        .map { number ->
            ModelCustomSpinner(
                value = number.toString(),
                id = number
            )
        }
}

/**
 * Converts a [LocalDate] to milliseconds.
 */
fun LocalDate.toMillis(): Long {
    return this.atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
}

/**
 * Converts a string to a [LocalDate].
 */
fun String.toLocalDate(): LocalDate {
    return LocalDate.parse(this, DateTimeFormatter.ISO_LOCAL_DATE)
}

fun getCandidate(utcTimeMillis: Long): LocalDate =
    Instant.ofEpochMilli(utcTimeMillis)
        .atZone(ZoneOffset.UTC)
        .toLocalDate()