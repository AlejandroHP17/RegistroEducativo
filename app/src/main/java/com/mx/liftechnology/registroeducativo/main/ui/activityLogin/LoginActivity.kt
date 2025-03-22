package com.mx.liftechnology.registroeducativo.main.ui.activityLogin

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mx.liftechnology.registroeducativo.main.ui.activityLogin.forgetPassword.ForgetPasswordScreen
import com.mx.liftechnology.registroeducativo.main.ui.activityLogin.login.LoginScreen
import com.mx.liftechnology.registroeducativo.main.ui.activityLogin.register.RegisterUserScreen
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.MainActivity
import com.mx.liftechnology.registroeducativo.main.ui.components.background
import com.mx.liftechnology.registroeducativo.main.util.navigation.LoginRoutes

/** LoginActivity - all the flow (login, register, forgotPassword)
 * AnimationHandler - Interface helps with the loading state
 * @author pelkidev
 * @since 1.0.0
 */
class LoginActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            Box( // Usa Box en lugar de Surface
                modifier = Modifier
                    .fillMaxSize()
                    .background(background()) // Aplica el fondo aquí
            ) {
                val navigationController = rememberNavController()
                NavHost(
                    navController = navigationController,
                    startDestination = LoginRoutes.LOGIN.route
                ){
                    composable(LoginRoutes.LOGIN.route){ LoginScreen(navigationController){navigate()} }
                    composable(LoginRoutes.REGISTER_USER.route){ RegisterUserScreen(navigationController) }
                    composable(LoginRoutes.FORGET_PASSWORD.route){ ForgetPasswordScreen(navigationController) }
                }
            }
        }
    }

    private fun navigate(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}

