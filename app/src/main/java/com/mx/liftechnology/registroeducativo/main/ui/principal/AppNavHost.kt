/**
 * @file Define el host de navegación principal de la aplicación.
 * @author Pelkidev
 * @version 1.0.0
 */
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
import com.mx.liftechnology.registroeducativo.main.ui.components.feedback.ShowCustomAnimated
import com.mx.liftechnology.registroeducativo.main.ui.evaluation.RegisterEvaluationScreen
import com.mx.liftechnology.registroeducativo.main.ui.formativeFields.list.ListFormativeFieldsScreen
import com.mx.liftechnology.registroeducativo.main.ui.formativeFields.register.RegisterFormativeFieldScreen
import com.mx.liftechnology.registroeducativo.main.ui.workType.wotyfofi.AssignmentFormativeFieldScreen
import com.mx.liftechnology.registroeducativo.main.ui.profile.ProfileScreen
import com.mx.liftechnology.registroeducativo.main.ui.menu.MenuScreen
import com.mx.liftechnology.registroeducativo.main.ui.schoolCycle.partial.RegisterPartialScreen
import com.mx.liftechnology.registroeducativo.main.ui.schoolCycle.school.RegisterSchoolScreen
import com.mx.liftechnology.registroeducativo.main.ui.splash.SplashScreen
import com.mx.liftechnology.registroeducativo.main.ui.student.list.ListStudentScreen
import com.mx.liftechnology.registroeducativo.main.ui.student.register.RegisterStudentScreen
import com.mx.liftechnology.registroeducativo.main.ui.workType.wotyFofiStudent.WotyFofiStudentScreen
import com.mx.liftechnology.registroeducativo.main.ui.control.ControlScreen
import com.mx.liftechnology.registroeducativo.main.util.navigation.AppRoutes

/**
 * Host de navegación principal de la aplicación.
 * 
 * **Responsabilidades:**
 * - Define el grafo de navegación completo de la aplicación
 * - Gestiona la navegación entre pantallas
 * - Maneja la expiración de sesión y redirige al login
 * - Muestra toasts globales
 * - Bloquea la interacción durante transiciones de navegación
 *
 * **Rutas principales:**
 * - **Splash**: Pantalla de inicio
 * - **Auth**: Login, registro, recuperación de contraseña
 * - **Main**: Menú, estudiantes, materias, calendario, perfil, etc.
 *
 * **Funcionalidades especiales:**
 * - Observa el estado de expiración de sesión y redirige automáticamente
 * - Muestra toasts globales que aparecen sobre toda la navegación
 * - Bloquea la interacción del usuario durante transiciones de pantalla
 *
 * @param sharedViewModel El ViewModel compartido para la comunicación entre pantallas y gestión de estado global.
 * @param restoreActivity Lambda para reiniciar la actividad principal, útil para flujos como el cierre de sesión.
 *
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
            navigationController.navigate(AppRoutes.Auth.LOGIN)
        }

    }

    Box(modifier = Modifier.fillMaxSize()) {

        NavHost(navController = navigationController, startDestination = AppRoutes.Splash.SPLASH) {
            composable(AppRoutes.Splash.SPLASH) {
                SplashScreen(
                    onNavigateToMain = { navigationController.navigate(AppRoutes.Main.MENU) { popUpTo(AppRoutes.Splash.SPLASH) { inclusive = true } } },
                    onNavigateToLogin = { navigationController.navigate(AppRoutes.Auth.LOGIN) { popUpTo(AppRoutes.Splash.SPLASH) { inclusive = true } } },
                    onPermissionDenied = { }
                )
            }

            // Flujo de Login
            composable(AppRoutes.Auth.LOGIN){ LoginScreen(
                navController = navigationController,
                sharedViewModel = sharedViewModel,
                onSuccess = {navigationController.navigate(AppRoutes.Main.MENU){popUpTo(AppRoutes.Auth.LOGIN) { inclusive = true } } },
                onSuccessAdmin = {navigationController.navigate(AppRoutes.Control.MENU){popUpTo(AppRoutes.Auth.LOGIN) { inclusive = true } } }
            )}
            composable(AppRoutes.Auth.REGISTER_USER){ RegisterUserScreen(
                navController = navigationController,
                sharedViewModel = sharedViewModel,
                ) }
            composable(AppRoutes.Auth.FORGET_PASSWORD){ ForgetPasswordScreen(navigationController) }

            // Flujo Admin
            composable(AppRoutes.Control.MENU){ ControlScreen(navigationController) }


            // Flujo Principal
            composable(
                route = AppRoutes.Main.MENU_WITH_RELOAD,
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
                onCloseSession = {navigationController.navigate(AppRoutes.Auth.LOGIN){popUpTo(AppRoutes.Main.MENU) { inclusive = true } }}
            ) }
            composable(AppRoutes.Main.LIST_STUDENT){ ListStudentScreen(navigationController) }
            composable(AppRoutes.Main.LIST_FORMATIVE_FIELDS){ ListFormativeFieldsScreen(navigationController) }
            composable(AppRoutes.Main.CALENDAR){ CalendarScreen(navigationController) }
            composable(AppRoutes.Main.API_CONTROL){ ControlScreen(navigationController) }

            composable(AppRoutes.Main.REGISTER_SCHOOL){ RegisterSchoolScreen(
                navController = navigationController,
                sharedViewModel = sharedViewModel
            ) }

            composable(AppRoutes.Main.REGISTER_FORMATIVE_FIELD){ RegisterFormativeFieldScreen(
                navController = navigationController,
                sharedViewModel = sharedViewModel) }
            composable(AppRoutes.Main.REGISTER_PARTIAL){ RegisterPartialScreen(
                navController = navigationController,
                sharedViewModel = sharedViewModel) }
            composable(AppRoutes.Main.PROFILE){ ProfileScreen(
                navController = navigationController,
                sharedViewModel = sharedViewModel,
                onCloseSession = { restoreActivity() }
            )}

            composable(
                route = AppRoutes.Main.REGISTER_STUDENT,
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
                route = AppRoutes.Main.WOTYFOFI_STUDENT,
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
                route = AppRoutes.Main.ASSIGNMENT_FORMATIVE_FIELD,
                arguments = listOf(navArgument("formativeField") {
                    nullable = true
                    defaultValue = ""
                })
            ) { backStackEntry ->
                AssignmentFormativeFieldScreen(
                    navController = navigationController,
                    backStackEntry = backStackEntry
                )
            }

            composable(
                route = AppRoutes.Main.REGISTER_ASSIGNMENT,
                arguments = listOf(navArgument("formativeField") {
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
