package com.mx.liftechnology.registroeducativo.main.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import com.mx.liftechnology.registroeducativo.main.ui.activityLogin.forgetPassword.ForgetPasswordScreen
import com.mx.liftechnology.registroeducativo.main.ui.activityLogin.login.LoginScreen
import com.mx.liftechnology.registroeducativo.main.ui.activityLogin.register.RegisterUserScreen
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.menu.MenuScreen
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.partial.RegisterPartialScreen
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.profile.ProfileScreen
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.school.RegisterSchoolScreen
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.student.list.ListStudentScreen
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.student.register.RegisterStudentScreen
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.subject.assignment.AssignmentScreen
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.subject.list.ListSubjectScreen
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.subject.register.RegisterSubjectScreen
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.subject.registerassignment.RegisterAssignmentScreen
import com.mx.liftechnology.registroeducativo.main.ui.activitySplash.SplashScreen
import com.mx.liftechnology.registroeducativo.main.ui.components.ShowCustomAnimated
import com.mx.liftechnology.registroeducativo.main.util.navigation.LoginRoutes
import com.mx.liftechnology.registroeducativo.main.util.navigation.MainRoutes

@Composable
fun AppNavHost(
    sharedViewModel: SharedViewModel
) {
    val navigationController = rememberNavController()
    val uiState by sharedViewModel.uiState.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {

        NavHost(navController = navigationController, startDestination = "splash") {
            composable("splash") {
                SplashScreen(
                    onNavigateToMain = { navigationController.navigate(MainRoutes.Menu.route) { popUpTo("splash") { inclusive = true } } },
                    onNavigateToLogin = { navigationController.navigate(LoginRoutes.LOGIN.route) { popUpTo("splash") { inclusive = true } } },
                    onPermissionDenied = { }
                )
            }

            // Login flow
            composable(LoginRoutes.LOGIN.route){ LoginScreen(
                navController = navigationController,
                sharedViewModel = sharedViewModel,
                onSuccess = {navigationController.navigate(MainRoutes.Menu.route){popUpTo(LoginRoutes.LOGIN.route) { inclusive = true } } }
            )}
            composable(LoginRoutes.REGISTER_USER.route){ RegisterUserScreen(
                navController = navigationController,
                sharedViewModel = sharedViewModel,
                ) }
            composable(LoginRoutes.FORGET_PASSWORD.route){ ForgetPasswordScreen(navigationController) }

            // Main flow
            composable(MainRoutes.Menu.route){ MenuScreen(
                navController = navigationController,
                onCloseSession = {navigationController.navigate(LoginRoutes.LOGIN.route){popUpTo(MainRoutes.Menu.route) { inclusive = true } }}
            ) }
            composable(MainRoutes.RegisterSchool.route){ RegisterSchoolScreen(navigationController) }
            composable(MainRoutes.ListStudent.route){ ListStudentScreen(navigationController) }
            composable(MainRoutes.ListSubject.route){ ListSubjectScreen(navigationController) }
            composable(MainRoutes.RegisterSubject.route){ RegisterSubjectScreen(navigationController) }
            composable(MainRoutes.RegisterPartial.route){ RegisterPartialScreen(navigationController) }
            composable(MainRoutes.Profile.route){ ProfileScreen(
                navController = navigationController,
                sharedViewModel = sharedViewModel,
                onCloseSession = {navigationController.navigate(LoginRoutes.LOGIN.route){popUpTo(MainRoutes.Profile.route) { inclusive = true } } }
            )}


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

            composable(
                route = MainRoutes.Assignment.route,
                arguments = listOf(navArgument("subject") {
                    nullable = true
                    defaultValue = ""
                })
            ) { backStackEntry ->
                AssignmentScreen(
                    navController = navigationController,
                    backStackEntry = backStackEntry
                )
            }

            composable(
                route = MainRoutes.RegisterAssignment.route,
                arguments = listOf(navArgument("subject") {
                    nullable = true
                    defaultValue = ""
                })
            ) { backStackEntry ->
                RegisterAssignmentScreen(
                    navController = navigationController,
                    backStackEntry = backStackEntry
                )
            }
        }

        ShowCustomAnimated(
            message = stringResource(id = uiState.controlToast.messageToast),
            isVisible = uiState.controlToast.showToast,
            typeToast = uiState.controlToast.typeToast,
            onDismiss = {
                val control = ModelStateToastUI(
                    messageToast = uiState.controlToast.messageToast,
                    showToast = false,
                    typeToast = uiState.controlToast.typeToast
                )
                sharedViewModel.modifyShowToast(control)
            }
        )
    }



}
