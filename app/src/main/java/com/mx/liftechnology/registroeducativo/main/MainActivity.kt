package com.mx.liftechnology.registroeducativo.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.ui.Modifier
import com.mx.liftechnology.registroeducativo.main.ui.components.background
import com.mx.liftechnology.registroeducativo.main.ui.principal.AppNavHost
import com.mx.liftechnology.registroeducativo.main.ui.principal.SharedViewModel
import com.mx.liftechnology.registroeducativo.main.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

/**
 * Actividad principal de la aplicación.
 * Esta actividad aloja el grafo de navegación principal y sirve como punto de entrada a la interfaz de usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class MainActivity : AppCompatActivity() {

    /**
     * Se llama cuando la actividad se crea por primera vez. Se encarga de configurar el contenido de la UI.
     *
     * @param savedInstanceState Si la actividad se está reinicializando después de haber sido cerrada,
     * este Bundle contiene los datos más recientes que suministró en [onSaveInstanceState].
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val sharedViewModel: SharedViewModel = koinViewModel()
            AppTheme{
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.systemBars)
                        .background(background())
                ) {
                    AppNavHost(
                        sharedViewModel = sharedViewModel,
                        restoreActivity = { restoreActivity()}
                    )
                }
            }
        }
    }

    /**
     * Reinicia la actividad actual. Útil para aplicar cambios de estado globales, como el cierre de sesión.
     */
    private fun restoreActivity(){
        val intent = intent
        finish()
        startActivity(intent)
    }
}
