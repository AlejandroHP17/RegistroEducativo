package com.mx.liftechnology.registroeducativo.main.ui.principal

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
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.background
import com.mx.liftechnology.registroeducativo.main.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

/**
 * The main activity of the application.
 * This activity hosts the main navigation graph and serves as the entry point for the user interface.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class MainActivity : AppCompatActivity() {

    /**
     * Se llama cuando la actividad se crea por primera vez.
     *
     * @param savedInstanceState Si la actividad se está reinicializando después de haber sido cerrada previamente, este Bundle contiene los datos que suministró más recientemente en [onSaveInstanceState].
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

    private fun restoreActivity(){
        val intent = intent
        finish()
        startActivity(intent)
    }
}
