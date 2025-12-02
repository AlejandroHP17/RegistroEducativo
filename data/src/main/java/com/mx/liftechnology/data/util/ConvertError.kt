/**
 * @file Proporciona funciones de conversión de errores de la capa de datos a errores de UI.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.util

import com.mx.liftechnology.core.util.models.LocalModelError
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.core.util.models.UserError

/**
 * Función de extensión que convierte un [com.mx.liftechnology.core.util.models.ModelError] (de la capa de datos) a un [com.mx.liftechnology.core.util.models.UserError] (para la UI).
 *
 * **Propósito:**
 * Esta función actúa como un puente entre la capa de datos y la capa de presentación,
 * traduciendo errores técnicos en errores que pueden ser mostrados al usuario.
 *
 * **Nota importante:**
 * Esta función proporciona un mapeo básico de errores. Para un mapeo más completo y detallado,
 * se recomienda usar [com.mx.liftechnology.registroeducativo.main.mapper.ErrorMapper] en el módulo `app`,
 * que proporciona un mapeo más exhaustivo de todos los tipos de errores.
 *
 * **Uso:**
 * ```kotlin
 * when (val result = repository.someOperation()) {
 *     is SuccessResult -> { /* manejar éxito */ }
 *     is ErrorResult -> {
 *         val userError = result.error.convertToUI()
 *         // Mostrar error al usuario según userError
 *     }
 * }
 * ```
 *
 * **Mapeo actual:**
 * - `LocalModelError.USER_INCOMPLETE_DATA` → `SHOW_INCOMPLETE_ERROR`
 * - `NetworkModelError.NOT_FOUND` → `SHOW_SPECIFIC_ERROR`
 * - `NetworkModelError.UNAUTHORIZED` → `UNAUTHORIZED`
 * - Otros errores → `LOGS` (no se muestran al usuario)
 *
 * @receiver El error de la capa de datos a convertir.
 * @return Un [com.mx.liftechnology.core.util.models.UserError] que indica cómo debe mostrarse el error al usuario.
 *
 * @see com.mx.liftechnology.core.util.models.ModelError Para ver los tipos de errores disponibles.
 * @see com.mx.liftechnology.core.util.models.UserError Para ver los tipos de errores de UI disponibles.
 * @see com.mx.liftechnology.registroeducativo.main.mapper.ErrorMapper Para un mapeo más completo.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun ModelError.convertToUI(): UserError {
    return when (this) {
        is LocalModelError ->
            when (this) {
                LocalModelError.USER_INCOMPLETE_DATA -> UserError.SHOW_INCOMPLETE_ERROR
                else -> UserError.LOGS
            }

        is NetworkModelError ->
            when (this) {
                NetworkModelError.NOT_FOUND -> UserError.SHOW_SPECIFIC_ERROR // 404
                NetworkModelError.UNAUTHORIZED -> UserError.UNAUTHORIZED // 401
                else -> UserError.LOGS
            }
    }
}