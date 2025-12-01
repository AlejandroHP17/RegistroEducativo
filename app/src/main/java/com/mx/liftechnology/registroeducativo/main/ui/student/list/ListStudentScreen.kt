package com.mx.liftechnology.registroeducativo.main.ui.student.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.SpinnerUiCallbacks
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ListStudentUiData
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.generic.GenericEmptyScreen
import com.mx.liftechnology.registroeducativo.main.ui.generic.GenericListScreen
import com.mx.liftechnology.registroeducativo.main.util.navigateWithParams
import com.mx.liftechnology.registroeducativo.main.util.navigation.AppRoutes
import org.koin.androidx.compose.koinViewModel

/**
 * Pantalla de lista de estudiantes.
 *
 * @param navController El controlador de navegación.
 * @param listStudentViewModel El ViewModel para esta pantalla.
 */
@Composable
fun ListStudentScreen(
    navController: NavHostController,
    listStudentViewModel: ListStudentViewModel = koinViewModel(),
) {

    val uiState by listStudentViewModel.uiState.collectAsStateWithLifecycle()
    val dataState by listStudentViewModel.dataState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        listStudentViewModel.getListStudent()
    }

    if (dataState.studentList.isNullOrEmpty()) {
        GenericEmptyScreen(
            image = painterResource(id = R.drawable.ic_empty_student),
            title = stringResource(R.string.empty_student_1),
            description = stringResource(R.string.empty_student_2),
            button = stringResource(R.string.add_button),
            onReturnClick = { navController.popBackStack() },
            onActionClick = {
                navController.navigateWithParams(
                    AppRoutes.Main.registerStudent(null)
                )
            }
        )
    } else {
        GenericListScreen(
            title = stringResource(R.string.get_student_name),
            textButton = stringResource(R.string.add_button),
            items = dataState.studentListUI,
            onReturnClick = { navController.popBackStack() },
            callbacks = SpinnerUiCallbacks(
                onItemClick = {
                    navController.navigateWithParams(
                        AppRoutes.Main.assignmentStudent(
                            listStudentViewModel.getStudent(it)
                        )
                    )
                },
                onEdit = {
                    navController.navigateWithParams(
                        AppRoutes.Main.registerStudent(
                            listStudentViewModel.getStudent(it)
                        )
                    )
                },
                onDelete = {
                    listStudentViewModel.deleteStudent(it)
                }
            ),
            onAction = {
                navController.navigateWithParams(
                    AppRoutes.Main.registerStudent(null)
                )
            }
        )
    }
    LoadingAnimation(uiState.uiState == ModelStateUIEnum.LOADING)
}

@Preview(showBackground = true)
@Composable
private fun ListStudentPreview(){
    val dataState = ListStudentUiData(
        studentList = listOf(ModelStudentDomain(
            studentId = 1,
            curp = "curp",
            birthday = "date",
            phoneNumber = "phone",
            userId = 1,
            name = "Test",
            lastName = "Prueba",
            secondLastName = "2"
        ))
    )

    if (dataState.studentList.isNullOrEmpty()) {
        GenericEmptyScreen(
            image = painterResource(id = R.drawable.ic_empty_student),
            title = stringResource(R.string.empty_student_1),
            description = stringResource(R.string.empty_student_2),
            button = stringResource(R.string.add_button),
            onReturnClick = { },
            onActionClick = {}
        )
    } else {
        GenericListScreen(
            title = stringResource(R.string.get_student_name),
            textButton = stringResource(R.string.add_button),
            items = dataState.studentListUI,
            onReturnClick = {  },
            callbacks = SpinnerUiCallbacks(
                onItemClick = {},
                onEdit = {},
                onDelete = {}
            ),
            onAction = {}
        )
    }
}