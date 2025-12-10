package com.mx.liftechnology.registroeducativo.main.ui.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mx.liftechnology.domain.util.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.SpinnerUiCallbacks
import com.mx.liftechnology.registroeducativo.main.model.share.CustomCalendar
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.ComponentHeaderBackWithout
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.calendars.DatePickerScreen
import com.mx.liftechnology.registroeducativo.main.ui.components.buttons.SegmentedControl
import com.mx.liftechnology.registroeducativo.main.ui.generic.BodyListGeneric
import com.mx.liftechnology.registroeducativo.main.util.navigateWithParams
import com.mx.liftechnology.registroeducativo.main.util.navigation.AppRoutes
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

/**
 * Pantalla de calendario.
 * 
 * Muestra un calendario interactivo y permite visualizar listas de campos formativos
 * o estudiantes según la selección del usuario.
 *
 * @param navController El controlador de navegación para gestionar los desplazamientos.
 * @param calendarViewModel El ViewModel para esta pantalla.
 */
@Composable
fun CalendarScreen (
    navController: NavHostController,
    calendarViewModel: CalendarViewModel = koinViewModel()
){

    val calendarUiState by calendarViewModel.calendarUiState.collectAsStateWithLifecycle()
    val dataFormativeFieldState by calendarViewModel.dataFormativeFieldState.collectAsStateWithLifecycle()
    val dataStudentState by calendarViewModel.dataStudentState.collectAsStateWithLifecycle()

    var selectedIndex by remember { mutableIntStateOf(0) }


    LaunchedEffect (Unit){
        calendarViewModel.getFormativeFields()
        calendarViewModel.getListStudent()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.margin_outer))
    ) {
        HeaderCalendarScreen(
            navigate =  {navController.popBackStack()}
        )

        BodyCalendarScreen(
            range = calendarViewModel.rangeDates(),
            onDateSelected = {calendarViewModel.setRangeDate(it) }
        )
        CustomSpace(dimensionResource(id = R.dimen.margin_divided))
        BodySelectScreen(
            selectedIndex = selectedIndex,
            itemSelected = { selectedIndex = it }
        )
        CustomSpace(dimensionResource(id = R.dimen.margin_divided))
        if(selectedIndex == 0){
            BodyListGeneric(
                items = dataFormativeFieldState.formativeFieldsListUI,
                callbacks = SpinnerUiCallbacks(
                    onItemClick = {
                        navController.navigateWithParams(
                            AppRoutes.Main.wotyFormativeField(
                                calendarViewModel.getFormativeField(it),
                                calendarUiState.date
                            )
                        )
                    },
                    onEdit = {},
                    onDelete = { }
                )
            )
        }else{
            BodyListGeneric(
                items = dataStudentState.studentListUI,
                callbacks = SpinnerUiCallbacks(
                    onItemClick = {
                        navController.navigateWithParams(
                            AppRoutes.Main.wotyStudent(
                                calendarViewModel.getStudent(it),
                                calendarUiState.date
                            )
                        )
                    },
                    onEdit = {},
                    onDelete = { }
                )
            )
        }

    }
}

/**
 * The header of the Calendar screen.
 *
 * @param navigate A lambda to be invoked when the back button is clicked.
 */
@Composable
fun HeaderCalendarScreen(
    navigate : () -> Unit
){
    ComponentHeaderBackWithout(
        title = stringResource(R.string.calendar_name),
        onReturnClick = { navigate()})
}

/**
 * The body of the Calendar screen.
 */
@Composable
fun BodyCalendarScreen(
    range : String?,
    onDateSelected: (date: LocalDate) -> Unit
){
    val calendar = CustomCalendar(
        rangeYears = null,
        rangeDate  = range,
        date = "".stringToModelStateOutFieldText()
    )

    DatePickerScreen(
        dialogState = calendar,
        onDateSelected = {
            onDateSelected(it)
        }
    )
}

/**
 * The body of the select screen.
 *
 * @param selectedIndex The index of the currently selected option.
 * @param itemSelected A lambda to be invoked when an option is selected.
 */
@Composable
fun BodySelectScreen(
    selectedIndex : Int,
    itemSelected:(Int) -> Unit
) {

    SegmentedControl(
        options = listOf("Campo formativo", "Estudiante"),
        selectedIndex = selectedIndex,
        onOptionSelected = {
            itemSelected(it)
        }
    )
}