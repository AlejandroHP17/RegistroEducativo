package com.mx.liftechnology.registroeducativo.main.ui.activitySplash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.mx.liftechnology.registroeducativo.databinding.ActivitySplashBinding
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.MainActivity

/** SplashActivity
 * @author pelkidev
 * @since 1.0.0
 */
class SplashActivity : AppCompatActivity() {

    private var binding: ActivitySplashBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition{false}

        binding = ActivitySplashBinding.inflate(layoutInflater) // Inflar el layout con binding
        setContentView(binding?.root) // Establecer el contenido de la vista

        navigate()
    }

    private fun navigate(){
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null // Limpiar el binding al destruir la actividad
    }
}