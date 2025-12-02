package com.mx.liftechnology.core.util.location

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mx.liftechnology.core.util.location.MessageLocationHelper.DENIED
import com.mx.liftechnology.core.util.location.MessageLocationHelper.LOCATION_ERROR
import com.mx.liftechnology.core.util.location.MessageLocationHelper.MESSAGE
import com.mx.liftechnology.core.util.location.MessageLocationHelper.NEGATIVE_BUTTON
import com.mx.liftechnology.core.util.location.MessageLocationHelper.POSITIVE_BUTTON
import com.mx.liftechnology.core.util.location.MessageLocationHelper.TITLE
import com.mx.liftechnology.core.util.location.MessageLocationHelper.UNAVAILABLE
import com.mx.liftechnology.core.util.location.MessageLocationHelper.UNKNOWN_ERROR
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * Resultado de una operación de ubicación.
 * Puede ser un éxito con una [Location] o un error con un mensaje.
 */
sealed class LocationResult {
    data class Success(val location: Location) : LocationResult()
    data class Error(val message: String) : LocationResult()
}

private object MessageLocationHelper{
    const val TITLE = "Permiso de Ubicación"
    const val MESSAGE = "Esta aplicación necesita acceso a tu ubicación para funcionar correctamente."
    const val POSITIVE_BUTTON = "Conceder permiso"
    const val NEGATIVE_BUTTON = "Cancelar"
    const val DENIED = "Permiso de ubicación no concedido"
    const val UNAVAILABLE = "La ubicación no está disponible"
    const val LOCATION_ERROR = "Error al obtener ubicación:"
    const val UNKNOWN_ERROR = "Error desconocido"
}

/**
 * Clase de ayuda para gestionar operaciones relacionadas con la ubicación, como la obtención de permisos y la última localización conocida.
 *
 * @property context El contexto de la aplicación, necesario para acceder a los servicios de ubicación.
 * @author Pelkidev
 * @version 1.0.0
 */
class LocationHelper(private val context: Context) {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    /**
     * Verifica si la aplicación tiene el permiso de ubicación concedido.
     *
     * @return `true` si el permiso está concedido, `false` en caso contrario.
     */
    private fun hasLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Interfaz de callback para notificar el resultado de la obtención de la ubicación.
     */
    interface LocationCallback {
        /**
         * Se llama cuando se obtiene un resultado de ubicación.
         * @param location La ubicación obtenida, o `null` si no está disponible.
         */
        fun onLocationResult(location: Location?)

        /**
         * Se llama si el permiso de ubicación es denegado por el usuario.
         */
        fun onPermissionDenied()
    }

    /**
     * Solicita la ubicación del dispositivo, gestionando los permisos necesarios.
     *
     * @param permissionLauncher El `ActivityResultLauncher` para la solicitud de permisos.
     * @param callback El callback para notificar el resultado.
     */
    fun requestLocation(
        permissionLauncher: ActivityResultLauncher<String>,
        callback: LocationCallback
    ) {
        if (!hasLocationPermission()) {
            if (context is Activity) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                ) {
                    showPermissionRationaleDialog(permissionLauncher)
                } else {
                    permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            } else {
                // Manejo alternativo si el contexto no es una Actividad
            }

        } else {
            getLastKnownLocation(callback)
        }
    }

    private fun showPermissionRationaleDialog(permissionLauncher: ActivityResultLauncher<String>) {
        AlertDialog.Builder(context)
            .setTitle(TITLE)
            .setMessage(MESSAGE)
            .setPositiveButton(POSITIVE_BUTTON) { _, _ ->
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            .setNegativeButton(NEGATIVE_BUTTON) { _, _ ->
            }
            .show()
    }

    private fun getLastKnownLocation(callback: LocationCallback) {
        if (hasLocationPermission()) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                callback.onLocationResult(location)
            }.addOnFailureListener {
                callback.onLocationResult(null)
            }
        }
    }

    /**
     * Obtiene la ubicación actual utilizando una función suspendida, ideal para corutinas.
     * Retorna un [LocationResult] que encapsula el resultado de la operación.
     *
     * @return [LocationResult.Success] con la ubicación si está disponible,
     *         [LocationResult.Error] si hay un error (permiso denegado, ubicación nula, etc.).
     */
    suspend fun getCurrentLocation(): LocationResult = suspendCancellableCoroutine { continuation ->
        if (!hasLocationPermission()) {
            continuation.resume(LocationResult.Error(DENIED))
            return@suspendCancellableCoroutine
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                continuation.resume(LocationResult.Success(location))
            } else {
                continuation.resume(LocationResult.Error(UNAVAILABLE))
            }
        }.addOnFailureListener { exception ->
            continuation.resume(LocationResult.Error("$LOCATION_ERROR ${exception.message ?: UNKNOWN_ERROR}"))
        }
    }
}

