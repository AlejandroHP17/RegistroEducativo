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

class LocationHelper(private val context: Context) {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    /** Interface - send the result to the activity
     * @author pelkidev
     * @since 1.0.0
     */
    interface LocationCallback {
        fun onLocationResult(location: Location?)
        fun onPermissionDenied()
    }

    /** Verify the permissions
     * @author pelkidev
     * @since 1.0.0
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
            /** Permission reject before, show the dialog */
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    context as Activity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                showPermissionRationaleDialog(permissionLauncher)
            } else {
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        } else {
            /** Permission accept before, get the location */
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