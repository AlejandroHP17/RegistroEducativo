package com.mx.liftechnology.registroeducativo.main.ui.principal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mx.liftechnology.registroeducativo.main.ui.auth.forgetPassword.ForgetPasswordScreen
import com.mx.liftechnology.registroeducativo.main.ui.auth.login.LoginScreen
import com.mx.liftechnology.registroeducativo.main.ui.auth.register.RegisterUserScreen
import com.mx.liftechnology.registroeducativo.main.ui.calendar.CalendarScreen
import com.mx.liftechnology.registroeducativo.main.ui.components.ShowCustomAnimated
import com.mx.liftechnology.registroeducativo.main.ui.evaluation.RegisterEvaluationScreen
import com.mx.liftechnology.registroeducativo.main.ui.formativeFields.list.ListSubjectScreen
import com.mx.liftechnology.registroeducativo.main.ui.formativeFields.register.RegisterSubjectScreen
import com.mx.liftechnology.registroeducativo.main.ui.formativeFields.wotyfofi.AssignmentSubjectScreen
import com.mx.liftechnology.registroeducativo.main.ui.profile.ProfileScreen
import com.mx.liftechnology.registroeducativo.main.ui.schoolCycle.menu.MenuScreen
import com.mx.liftechnology.registroeducativo.main.ui.schoolCycle.partial.RegisterPartialScreen
import com.mx.liftechnology.registroeducativo.main.ui.schoolCycle.school.RegisterSchoolScreen
import com.mx.liftechnology.registroeducativo.main.ui.splash.SplashScreen
import com.mx.liftechnology.registroeducativo.main.ui.student.list.ListStudentScreen
import com.mx.liftechnology.registroeducativo.main.ui.student.register.RegisterStudentScreen
import com.mx.liftechnology.registroeducativo.main.ui.student.wotyfofi.WotyFofiStudentScreen
import com.mx.liftechnology.registroeducativo.main.util.navigation.LoginRoutes
import com.mx.liftechnology.registroeducativo.main.util.navigation.MainRoutes

/**
 * Host de navegación principal de la aplicación.
 * Define el grafo de navegación y las pantallas correspondientes a cada ruta.
 *
 * @param sharedViewModel El ViewModel compartido para la comunicación entre pantallas.
 * @param restoreActivity Lambda para reiniciar la actividad principal, útil para flujos como el cierre de sesión.
 * @author Pelkidev
 * @version 1.0.0
 */
@Composable
fun AppNavHost(
    sharedViewModel: SharedViewModel,
    restoreActivity : () -> Unit
) {
    val navigationController = rememberNavController()
    val uiState by sharedViewModel.uiState.collectAsStateWithLifecycle()
    var isBlocked by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.sessionExpired) {
        if(uiState.sessionExpired){
            sharedViewModel.sessionExpired()
            navigationController.navigate(LoginRoutes.LOGIN.route)
        }

    }

    Box(modifier = Modifier.fillMaxSize()) {

        NavHost(navController = navigationController, startDestination = "splash") {
            composable("splash") {
                SplashScreen(
                    onNavigateToMain = { navigationController.navigate(MainRoutes.Menu.route) { popUpTo("splash") { inclusive = true } } },
                    onNavigateToLogin = { navigationController.navigate(LoginRoutes.LOGIN.route) { popUpTo("splash") { inclusive = true } } },
                    onPermissionDenied = { }
                )
            }

            // Flujo de Login
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

            // Flujo Principal
            composable(
                route = MainRoutes.Menu.route,
                arguments = listOf(
                    navArgument("reload") {
                        type = NavType.BoolType
                        defaultValue = false
                        nullable = false
                    }
                )){ MenuScreen(
                reload = it.arguments?.getBoolean("reload") ?: false,
                navController = navigationController,
                sharedViewModel = sharedViewModel,
                onCloseSession = {navigationController.navigate(LoginRoutes.LOGIN.route){popUpTo(MainRoutes.Menu.route) { inclusive = true } }}
            ) }
            composable(MainRoutes.ListStudent.route){ ListStudentScreen(navigationController) }
            composable(MainRoutes.ListSubject.route){ ListSubjectScreen(navigationController) }
            composable(MainRoutes.Calendar.route){ CalendarScreen(navigationController) }

            composable(MainRoutes.RegisterSchool.route){ RegisterSchoolScreen(
                navController = navigationController,
                sharedViewModel = sharedViewModel
            ) }

            composable(MainRoutes.RegisterSubject.route){ RegisterSubjectScreen(
                navController = navigationController,
                sharedViewModel = sharedViewModel) }
            composable(MainRoutes.RegisterPartial.route){ RegisterPartialScreen(
                navController = navigationController,
                sharedViewModel = sharedViewModel) }
            composable(MainRoutes.Profile.route){ ProfileScreen(
                navController = navigationController,
                sharedViewModel = sharedViewModel,
                onCloseSession = { restoreActivity() }
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
                    backStackEntry = backStackEntry,
                    sharedViewModel = sharedViewModel,
                )
            }

            composable(
                route = MainRoutes.AssignmentStudent.route,
                arguments = listOf(navArgument("student") {
                    nullable = true
                    defaultValue = ""
                })
            ) { backStackEntry ->
                WotyFofiStudentScreen(
                    navController = navigationController,
                    backStackEntry = backStackEntry
                )
            }

            composable(
                route = MainRoutes.AssignmentSubject.route,
                arguments = listOf(navArgument("subject") {
                    nullable = true
                    defaultValue = ""
                })
            ) { backStackEntry ->
                AssignmentSubjectScreen(
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
                RegisterEvaluationScreen(
                    navController = navigationController,
                    backStackEntry = backStackEntry,
                    sharedViewModel = sharedViewModel,
                )
            }
        }

        // Toast global que se muestra por encima de toda la navegación
        ShowCustomAnimated(
            message = stringResource(id = uiState.controlToast.messageToast),
            isVisible = uiState.controlToast.showToast,
            typeToast = uiState.controlToast.typeToast,
            onDismiss = {
                sharedViewModel.hideToast()
            }
        )

        if (isBlocked) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        awaitPointerEventScope {
                            while (true) {
                                awaitPointerEvent()
                            }
                        }
                    }
            )
        }
    }

    DisposableEffect(navigationController) {
        val listener = NavController.OnDestinationChangedListener { _, _, _ ->
            isBlocked = true
        }
        navigationController.addOnDestinationChangedListener(listener)
        onDispose { navigationController.removeOnDestinationChangedListener(listener) }
    }

    val currentEntry by navigationController.currentBackStackEntryAsState()

    DisposableEffect(currentEntry) {
        isBlocked = currentEntry?.lifecycle?.currentState != Lifecycle.State.RESUMED

        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> isBlocked = false
                Lifecycle.Event.ON_CREATE,
                Lifecycle.Event.ON_START,
                Lifecycle.Event.ON_PAUSE,
                Lifecycle.Event.ON_STOP -> isBlocked = true
                else -> { /* no-op */ }
            }
        }
        currentEntry?.lifecycle?.addObserver(observer)
        onDispose { currentEntry?.lifecycle?.removeObserver(observer) }
    }


}
