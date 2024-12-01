package com.mx.liftechnology.registroeducativo.main.ui.activitySplash

import android.content.Intent
import android.location.Location
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.mx.liftechnology.core.util.LocationHelper
import com.mx.liftechnology.registroeducativo.databinding.ActivitySplashBinding
import com.mx.liftechnology.registroeducativo.main.ui.activityLogin.LoginActivity
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.MainActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


/** SplashActivity
 * @author pelkidev
 * @since 1.0.0
 */
class SplashActivity : AppCompatActivity(), LocationHelper.LocationCallback {

    private var binding: ActivitySplashBinding? = null
    private val splashViewModel: SplashViewModel by viewModel()

    private lateinit var locationHelper: LocationHelper

    private val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        locationHelper.handlePermissionResult(isGranted, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // SplashScreen
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition{false}

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initObservers()

        // Location
        locationHelper = LocationHelper(this)
        locationHelper.requestLocation(permissionLauncher, this)

    }

   // User accept permission, navigate
    override fun onLocationResult(location: Location?) {
       splashViewModel.getNavigation()
    }



    // User denied permission, terminate app
    override fun onPermissionDenied() {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun initObservers(){
        /** Decide what activity, login or main
         * @author pelkidev
         * @since 1.0.0
         * */
        splashViewModel.navigate.observe(this) { data ->
            if(data) startActivity(Intent(this, MainActivity::class.java))
            else startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}