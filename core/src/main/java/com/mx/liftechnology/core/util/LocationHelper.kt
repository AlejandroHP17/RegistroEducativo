package com.mx.liftechnology.core.util

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Helper class for handling location-related operations.
 *
 * @property context The application context.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class LocationHelper(private val context: Context) {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    /**
     * Callback interface for location results.
     */
    interface LocationCallback {
        /**
         * Called when a location result is available.
         * @param location The location, or null if not available.
         */
        fun onLocationResult(location: Location?)

        /**
         * Called when the location permission is denied.
         */
        fun onPermissionDenied()
    }

    /**
     * Requests the device's location, handling permissions as needed.
     *
     * @param permissionLauncher The launcher for the permission request.
     * @param callback The callback to be invoked with the result.
     */
    fun requestLocation(
        permissionLauncher: ActivityResultLauncher<String>,
        callback: LocationCallback
    ) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
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
                // Handle non-Activity context
            }

        } else {
            getLastKnownLocation(callback)
        }
    }

    private fun showPermissionRationaleDialog(permissionLauncher: ActivityResultLauncher<String>) {
        AlertDialog.Builder(context)
            .setTitle("Permiso de Ubicación")
            .setMessage("Esta aplicación necesita acceso a tu ubicación para funcionar correctamente.")
            .setPositiveButton("Conceder permiso") { _, _ ->
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            .setNegativeButton("Cancelar") { _, _ ->
            }
            .show()
    }

    /**
     * Handles the result of a permission request.
     *
     * @param isGranted True if the permission was granted, false otherwise.
     * @param callback The callback to be invoked.
     */
    fun handlePermissionResult(isGranted: Boolean, callback: LocationCallback) {
        if (isGranted) {
            getLastKnownLocation(callback)
        } else {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(
                    context as Activity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                showSettingsDialog()
            } else {
                callback.onPermissionDenied()
            }
        }
    }

    private fun showSettingsDialog() {
        AlertDialog.Builder(context)
            .setTitle("Permiso Necesario")
            .setMessage("Para usar esta aplicación, habilita el permiso de ubicación desde Configuración.")
            .setPositiveButton("Abrir Configuración") { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.fromParts("package", context.packageName, null)
                context.startActivity(intent)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun getLastKnownLocation(callback: LocationCallback) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                callback.onLocationResult(location)
            }.addOnFailureListener {
                callback.onLocationResult(null)
            }
        }
    }

    /**
     * Gets the current location using a suspend function.
     *
     * @return The current [Location], or null if not available.
     * @throws SecurityException if location permission is not granted.
     * @throws NullPointerException if the location is null.
     */
    suspend fun getCurrentLocation(): Location? = suspendCancellableCoroutine { continuation ->
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            continuation.resumeWithException(SecurityException("Location permission not granted"))
            return@suspendCancellableCoroutine
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                continuation.resume(location)
            } else {
                continuation.resumeWithException(NullPointerException("Location is null"))
            }
        }.addOnFailureListener { exception ->
            continuation.resumeWithException(exception)
        }
    }
}