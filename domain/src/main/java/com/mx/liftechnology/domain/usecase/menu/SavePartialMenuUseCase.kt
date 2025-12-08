package com.mx.liftechnology.domain.usecase.menu

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.domain.model.schoolCycle.DialogGroupPartialDomain
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * @file Define el caso de uso para guardar el parcial activo en las preferencias del usuario.
 * @author Pelkidev
 * @version 1.0.0
 */
/**
 * Caso de uso para guardar el parcial actual en las preferencias del usuario.
 * Su lógica principal es determinar cuál es el parcial activo basado en la fecha actual
 * y persistir su información para ser usada en otras partes de la aplicación.
 *
 * @property preference El caso de uso para gestionar las preferencias del usuario.
 * @author Pelkidev
 * @version 1.0.0
 */
class SavePartialMenuUseCase (
    private val preference: PreferenceUseCase
)  {
    /**
     * Guarda el parcial actual.
     * Busca en la lista de parciales cuál contiene la fecha actual. Si ninguno coincide,
     * selecciona el último parcial de la lista como el activo por defecto.
     * Si se encuentra un parcial, guarda su ID y rango de fechas en las preferencias.
     *
     * @param listPartial La lista de parciales disponibles para la selección.
     * @return El [DialogGroupPartialDomain] que fue seleccionado como activo, o `null` si la lista es nula o vacía.
     */
    operator fun invoke (
        listPartial :
        List<DialogGroupPartialDomain>?
    ): DialogGroupPartialDomain?  {

        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        // Intenta encontrar el parcial cuyo rango de fechas contenga la fecha actual.
        // Si no encuentra ninguno, toma el último de la lista como fallback.
        val element = listPartial?.firstOrNull { item ->
            val start = LocalDate.parse(item.startDate, formatter)
            val end = LocalDate.parse(item.endDate, formatter)
            currentDate in start..end
        } ?: listPartial?.lastOrNull()

        element?.let {
            val result = ("${it.startDate}/${it.endDate}")
            preference.setRangeDatesPartial(result)
            preference.setIdPartial(it.partialId ?: -1)
        } ?: run {
            preference.setRangeDatesPartial(null)
            preference.setIdPartial(-1)
        }
        return element
    }
}