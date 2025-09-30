package com.mx.liftechnology.registroeducativo.main.ui.principal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import com.mx.liftechnology.registroeducativo.main.ui.flowLogin.forgetPassword.ForgetPasswordScreen
import com.mx.liftechnology.registroeducativo.main.ui.flowLogin.login.LoginScreen
import com.mx.liftechnology.registroeducativo.main.ui.flowLogin.register.RegisterUserScreen
import com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.calendar.CalendarScreen
import com.mx.liftechnology.registroeducativo.main.ui.flowMain.menu.MenuScreen
import com.mx.liftechnology.registroeducativo.main.ui.flowMain.partial.RegisterPartialScreen
import com.mx.liftechnology.registroeducativo.main.ui.flowMain.profile.ProfileScreen
import com.mx.liftechnology.registroeducativo.main.ui.flowMain.school.RegisterSchoolScreen
import com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.student.list.ListStudentScreen
import com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.student.register.RegisterStudentScreen
import com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.subject.assignment.AssignmentScreen
import com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.subject.list.ListSubjectScreen
import com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.subject.register.RegisterSubjectScreen
import com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.subject.registerassignment.RegisterAssignmentScreen
import com.mx.liftechnology.registroeducativo.main.ui.flowSplash.SplashScreen
import com.mx.liftechnology.registroeducativo.main.ui.components.ShowCustomAnimated
import com.mx.liftechnology.registroeducativo.main.util.navigation.LoginRoutes
import com.mx.liftechnology.registroeducativo.main.util.navigation.MainRoutes

@Composable
fun AppNavHost(
    sharedViewModel: SharedViewModel,
    restoreActivity : () -> Unit
) {
    val navigationController = rememberNavController()
    val uiState by sharedViewModel.uiState.collectAsStateWithLifecycle()
    var isBlocked by remember { mutableStateOf(false) }



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
                    backStackEntry = backStackEntry,
                    sharedViewModel = sharedViewModel,
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

        // 🔒 Overlay bloqueador
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

    // 1) Bloquear inmediatamente cuando cambia el destino
    DisposableEffect(navigationController) {
        val listener = NavController.OnDestinationChangedListener { _, _, _ ->
            // Al iniciar un cambio de destino, bloquea
            isBlocked = true
        }
        navigationController.addOnDestinationChangedListener(listener)
        onDispose { navigationController.removeOnDestinationChangedListener(listener) }
    }

// 2) Desbloquear cuando el destino actual esté RESUMED
    val currentEntry by navigationController.currentBackStackEntryAsState()

    DisposableEffect(currentEntry) {
        // Estado inicial según el lifecycle actual
        isBlocked = currentEntry?.lifecycle?.currentState != Lifecycle.State.RESUMED

        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> isBlocked = false
                // En estos estados mantenemos el bloqueo
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
