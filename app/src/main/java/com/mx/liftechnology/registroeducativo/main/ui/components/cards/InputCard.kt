package com.mx.liftechnology.registroeducativo.main.ui.components.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mx.liftechnology.domain.model.formativeFields.WorkTypeDomain
import com.mx.liftechnology.domain.model.generic.ModelRegex
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.schoolCycle.DatePeriodDomain
import com.mx.liftechnology.domain.util.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.ui.components.calendars.DateRangePickerDialog
import com.mx.liftechnology.registroeducativo.main.ui.components.form.DropdownTextFieldEditable
import com.mx.liftechnology.registroeducativo.main.ui.components.form.TextFieldCalendar
import com.mx.liftechnology.registroeducativo.main.ui.components.form.TextFieldGeneric
import com.mx.liftechnology.registroeducativo.main.ui.components.form.TextFieldNumeric
import com.mx.liftechnology.registroeducativo.main.ui.components.form.TextFieldScore
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.CustomSpace
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

/**
 * A composable function for previewing the cards in this file.
 */
@Preview(showBackground = true)
@Composable
private fun InputCardView() {
    Column {
        EvaluationPercentItem(
            listWorkMethods = listOf(
                WorkTypeDomain(
                    workTypeId = 1,
                    name = "hola"
                )
            ),
            name = "Hola".stringToModelStateOutFieldText(),
            percent = "hola".stringToModelStateOutFieldText(),
            onNameChange = {},
            onPercentChange = {}
        )

        RegisterPartialListItem(
            index = 1,
            date = DatePeriodDomain(
                position = 1,
                date = "hola".stringToModelStateOutFieldText(),
                partialCycleGroup = 0
            ),
            rangeDate = null,
            onDateChange = {}
        )

        EvaluationStudentItem(
            nameStudent = "Alejandro".stringToModelStateOutFieldText(),
            score = "10.0".stringToModelStateOutFieldText(),
            onScoreChange = {},
        )
    }
}


/**
 * An item for displaying an evaluation percentage.
 *
 * @param listWorkMethods The list of work methods to choose from.
 * @param name The name of the evaluation.
 * @param percent The percentage of the evaluation.
 * @param onNameChange A lambda to be invoked when the name changes.
 * @param onPercentChange A lambda to be invoked when the percentage changes.
 */
@Composable
fun EvaluationPercentItem(
    listWorkMethods: List<WorkTypeDomain?>,
    name: ModelStateOutFieldText,
    percent: ModelStateOutFieldText,
    onNameChange: (WorkTypeDomain?) -> Unit,
    onPercentChange: (ModelStateOutFieldText) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.margin_divided))
    ) {
        Column(modifier = Modifier.weight(5f)) {
            CustomSpace(dimensionResource(R.dimen.margin_between))
            DropdownTextFieldEditable(
                options = listWorkMethods,
                selectedOption = name,
                label = stringResource(R.string.form_formative_field_evaluation),
                onOptionSelected = {
                    onNameChange(it)
                }
            )
        }

        Box(modifier = Modifier.weight(2f)) {
            TextFieldNumeric(
                modelText = percent,
                enable = true,
                maxNumberCharacter = 4,
                label = stringResource(id = R.string.form_formative_field_percent),
                onBoxChanged = { onPercentChange(it) }
            )
        }
    }
    CustomSpace(dimensionResource(R.dimen.margin_divided))
}

/**
 * An item for displaying a student's evaluation.
 *
 * @param nameStudent The name of the student.
 * @param score The student's score.
 * @param onScoreChange A lambda to be invoked when the score changes.
 */
@Composable
fun EvaluationStudentItem(
    nameStudent: ModelStateOutFieldText,
    score: ModelStateOutFieldText,
    onScoreChange: (String) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.margin_divided))
    ) {
        Box(modifier = Modifier.weight(5f)) {
            TextFieldGeneric(
                modelText = nameStudent,
                enable = false,
                label = stringResource(id = R.string.tools_empty),
                regex = ModelRegex.SIMPLE_TEXT,
            ) {}
        }


        Box(modifier = Modifier.weight(2f)) {
            TextFieldScore(
                value = score,
                enable = true,
                label = stringResource(id = R.string.form_assignment_score),
            )
            { onScoreChange(it) }
        }
    }
    CustomSpace(dimensionResource(R.dimen.margin_divided))
}

/**
 * A list item for registering a partial.
 *
 * @param index The index of the item.
 * @param date The data for the date period.
 * @param onDateChange A lambda to be invoked when the date changes.
 */
@Composable
fun RegisterPartialListItem(
    index: Int,
    date: DatePeriodDomain,
    isActive: Boolean = true,
    rangeDate: List<Pair<LocalDate, LocalDate>?>?,
    onDateChange: (Pair<LocalDate, LocalDate>) -> Unit,
) {
    val dates = Instant.ofEpochMilli(1L)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDates by remember { mutableStateOf(dates to dates) }

    DateRangePickerDialog(
        index = index,
        showDialog = showDatePicker,
        onDismiss = { showDatePicker = false },
        rangeDate = rangeDate,
        onDateSelected = { startDate, endDate ->
            selectedDates = startDate to endDate
            onDateChange(selectedDates)
        })


    TextFieldCalendar(
        value = date.date,
        enable = false,
        label = stringResource(id = R.string.form_partial_periods, index + 1),
    )
    {
        if (isActive) showDatePicker = true
    }

    CustomSpace(dimensionResource(R.dimen.margin_divided))


}