package com.mx.liftechnology.registroeducativo.main.ui.activityMain

/**
 * Principal activity, the purpose of the app is to help in the study´s field
 * It contains a Menu with different access to student, school, calendar, subject, etc.
 * All this functionality improve the time and order the data to evaluate an school
 * Also has options, configuration and a zone of export to get the data in other kind of
 * Platform to evaluate an student.
 * */

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mx.liftechnology.registroeducativo.main.ui.activityLogin.LoginActivity
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.menu.MenuScreen
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.partial.RegisterPartialScreen
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.profile.ProfileScreen
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.school.RegisterSchoolScreen
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.student.list.ListStudentScreen
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.student.register.RegisterStudentScreen
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.subject.list.ListSubjectScreen
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.subject.register.RegisterSubjectScreen
import com.mx.liftechnology.registroeducativo.main.ui.components.background
import com.mx.liftechnology.registroeducativo.main.util.navigation.MainRoutes

/** MainActivity
 * @author pelkidev
 * @since 1.0.0
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(background()) // Aplica el fondo aquí
            ) {
                val navigationController = rememberNavController()
                NavHost(
                    navController = navigationController,
                    startDestination = MainRoutes.Menu.route
                ){
                    composable(MainRoutes.Menu.route){ MenuScreen(navigationController) }
                    composable(MainRoutes.RegisterSchool.route){ RegisterSchoolScreen(navigationController) }
                    composable(MainRoutes.ListStudent.route){ ListStudentScreen(navigationController) }
                    composable(MainRoutes.ListSubject.route){ ListSubjectScreen(navigationController) }
                    composable(MainRoutes.RegisterPartial.route){ RegisterPartialScreen(navigationController) }
                    composable(MainRoutes.Profile.route){ ProfileScreen(navigationController) {navigate()} }


                    composable(
                        route = MainRoutes.RegisterStudent.route,
                        arguments = listOf(navArgument("student") {
                            nullable = true
                            defaultValue = ""
                        })
                    ) { backStackEntry ->
                        RegisterStudentScreen(
                            navController = navigationController,
                            backStackEntry = backStackEntry
                        )
                    }

                    composable(MainRoutes.RegisterSubject.route){ RegisterSubjectScreen(navigationController) }

                }
            }
        }

    }


    private fun navigate(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

}
