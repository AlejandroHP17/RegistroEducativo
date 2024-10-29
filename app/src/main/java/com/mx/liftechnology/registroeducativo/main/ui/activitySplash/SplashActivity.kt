package com.mx.liftechnology.registroeducativo.main.ui.activitySplash

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.mx.liftechnology.core.util.LocationHelper
import com.mx.liftechnology.registroeducativo.databinding.ActivitySplashBinding
import com.mx.liftechnology.registroeducativo.main.ui.activityLogin.LoginActivity


/** SplashActivity
 * @author pelkidev
 * @since 1.0.0
 */
class SplashActivity : AppCompatActivity(), LocationHelper.LocationCallback {

    private var binding: ActivitySplashBinding? = null

    private lateinit var locationHelper: LocationHelper

    private val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        locationHelper.handlePermissionResult(isGranted, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition{false}

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        locationHelper = LocationHelper(this)
        locationHelper.requestLocation(permissionLauncher, this)

    }

    // Implementación de la interfaz LocationCallback
    override fun onLocationResult(location: Location?) {
        location?.let {
            Log.d("SplashActivity", "Lat: ${location.latitude}, Long: ${location.longitude}")
        } ?: Log.e("SplashActivity", "No se pudo obtener la ubicación.")

        navigate()
    }

    override fun onPermissionDenied() {
        Log.e("SplashActivity", "Permiso de ubicación denegado.")
        finish()
    }

    /** Decide what activity, login or main
     * @author pelkidev
     * @since 1.0.0
     * */
    private fun navigate(){
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null // Limpiar el binding al destruir la actividad
    }


}