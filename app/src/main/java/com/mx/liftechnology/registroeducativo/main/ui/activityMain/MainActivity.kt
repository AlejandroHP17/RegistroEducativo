package com.mx.liftechnology.registroeducativo.main.ui.activityMain

/**
 * Principal activity, the purpose of the app is to help in the study´s field
 * It contains a Menu with different access to student, school, calendar, subject, etc.
 * All this functionality improve the time and order the data to evaluate an school
 * Also has options, configuration and a zone of export to get the data in other kind of
 * Platform to evaluate an student.
 * */

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
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.menu.MenuScreen
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.register.partial.RegisterPartialScreen
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.register.school.RegisterSchoolScreen
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
                    startDestination = MainRoutes.MENU.route
                ){
                    composable(MainRoutes.MENU.route){ MenuScreen(navigationController) }
                    composable(MainRoutes.REGISTER_SCHOOL.route){ RegisterSchoolScreen(navigationController) }
                    composable(MainRoutes.REGISTER_STUDENT.route){ RegisterStudentScreen(navigationController) }
                    composable(MainRoutes.LIST_STUDENT.route){ ListStudentScreen(navigationController) }
                    composable(MainRoutes.LIST_SUBJECT.route){ ListSubjectScreen(navigationController) }
                    composable(MainRoutes.REGISTER_SUBJECT.route){ RegisterSubjectScreen(navigationController) }
                    composable(MainRoutes.REGISTER_PARTIAL.route){ RegisterPartialScreen(navigationController) }
                }
            }
        }

    }
}
