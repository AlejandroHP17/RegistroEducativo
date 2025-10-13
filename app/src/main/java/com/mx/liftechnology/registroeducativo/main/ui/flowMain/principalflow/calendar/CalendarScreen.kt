package com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateSpinnerUI
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelCustomCalendar
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentHeaderBackWithout
import com.mx.liftechnology.registroeducativo.main.ui.components.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.DatePickerScreen
import com.mx.liftechnology.registroeducativo.main.ui.components.SegmentedControl
import com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.BodyListGeneric
import org.koin.androidx.compose.koinViewModel

/**
 * The Calendar screen.
 *
 * @param navController The navigation controller.
 * @param calendarViewModel The ViewModel for this screen.
 */
@Composable
fun CalendarScreen (
    navController: NavHostController,
    calendarViewModel: CalendarViewModel = koinViewModel()
){

    val uiState by calendarViewModel.uiState.collectAsStateWithLifecycle()
    val dataState by calendarViewModel.dataState.collectAsStateWithLifecycle()
    val dataState2 by calendarViewModel.dataState2.collectAsStateWithLifecycle()

    var selectedIndex by remember { mutableStateOf(0) }


    LaunchedEffect (Unit){
        calendarViewModel.getSubject()
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

        BodyCalendarScreen()
        CustomSpace(dimensionResource(id = R.dimen.margin_divided))
        BodySelectScreen(
            selectedIndex = selectedIndex,
            itemSelected = { selectedIndex = it }
        )
        CustomSpace(dimensionResource(id = R.dimen.margin_divided))
        if(selectedIndex == 0){
            BodyListGeneric(
                items = dataState.subjectListUI,
                callbacks = ModelStateSpinnerUI(
                    onItemClick = {},
                    onEdit = {},
                    onDelete = { }
                )
            )
        }else{
            BodyListGeneric(
                items = dataState2.studentListUI,
                callbacks = ModelStateSpinnerUI(
                    onItemClick = {},
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
){
    val calendar = ModelCustomCalendar(
        rangeYears = null,
        rangeDate  = null,
        date = "".stringToModelStateOutFieldText()
    )


    DatePickerScreen(
        dialogState = calendar,
        onDateSelected = {}
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