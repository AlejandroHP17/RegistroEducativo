package com.mx.liftechnology.registroeducativo.main.ui.activityMain.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.share.ModelCustomCalendar
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentHeaderBackWithout
import com.mx.liftechnology.registroeducativo.main.ui.components.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.DatePickerScreen
import com.mx.liftechnology.registroeducativo.main.ui.components.SegmentedControl
import org.koin.androidx.compose.koinViewModel

@Composable
fun CalendarScreen (
    navController: NavHostController,
    calendarViewModel: CalendarViewModel = koinViewModel()
){
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
        BodySelectScreen()
    }
}

@Composable
fun HeaderCalendarScreen(
    navigate : () -> Unit
){
    ComponentHeaderBackWithout(
        title = stringResource(R.string.calendar_name),
        onReturnClick = { navigate()}
    )
}

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

@Composable
fun BodySelectScreen() {
    var selectedIndex by remember { mutableStateOf(0) }

    SegmentedControl(
        options = listOf("Campo formativo", "Estudiante"),
        selectedIndex = selectedIndex,
        onOptionSelected = {  selectedIndex = it }
    )
}