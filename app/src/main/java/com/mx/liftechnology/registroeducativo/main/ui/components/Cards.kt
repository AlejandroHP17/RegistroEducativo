package com.mx.liftechnology.registroeducativo.main.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetListAssessmentType
import com.mx.liftechnology.data.model.ModelPrincipalMenuData
import com.mx.liftechnology.domain.model.ModelDatePeriodDomain
import com.mx.liftechnology.domain.model.generic.ModelRegex
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.util.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateSpinnerUI
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelAssignmentUiCallbacks
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelComplexCard
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelCustomCard
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorAzulLink
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorPrincipalText
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorWhite
import java.time.LocalDate

/**
 * A composable function for previewing the cards in this file.
 */
@Preview(showBackground = true)
@Composable
fun CustomCardView() {
    Column {
        CustomCard(
            item = ModelCustomCard(
                id = 1,
                numberList = "1",
                nameCard = "Curp",
            ),
            callbacks = ModelStateSpinnerUI(
                onItemClick = {},
                onEdit = {},
                onDelete = {}
            )
        )

        ComplexCard(
            item = ModelComplexCard(
                idTitle = 1,
                nameTitle = "1",
                isShowTitle = true,
                isExpandedTitle = true,
                list = null
            ),
            complexCallbacks = ModelAssignmentUiCallbacks(
                onExpandedTitle = {},
                onExpandedSubTitle = {},
                onItemClick = {}
            )
        )


        GridItem(
            item = ModelPrincipalMenuData(
                id = "1",
                image = com.mx.liftechnology.data.R.drawable.ic_students,
                titleCard = "texto"
            ),
            onItemClick = {}
        )

        DialogGroupItem(
            text = "nombre",
            isSelected = true,
            onSelected = {}
        )

        EvaluationPercentItem(
            listWorkMethods = listOf(
                ResponseGetListAssessmentType(
                    assessmentTypeId = 1,
                    description = "hola",
                    teacherSchoolCycleGroupId = 1
                )
            ),
            name = "Hola".stringToModelStateOutFieldText(),
            percent = "hola".stringToModelStateOutFieldText(),
            onNameChange = {},
            onPercentChange = {}
        )

        RegisterPartialListItem(
            index = 1,
            date = ModelDatePeriodDomain(
                position = 1,
                date = "hola".stringToModelStateOutFieldText(),
                partialCycleGroup = 0
            ),
            onDateChange = {}
        )

        EvaluationStudentItem(
            nameStudent = "Alejandro".stringToModelStateOutFieldText(),
            score = "10.0".stringToModelStateOutFieldText(),
            onScoreChange =  {},
        )
    }
}

/**
 * A custom card component with a title, a number, and a dropdown menu.
 *
 * @param item The data for the card.
 * @param callbacks The callbacks for the card.
 */
@Composable
fun CustomCard(
    item: ModelCustomCard,
    callbacks: ModelStateSpinnerUI
) {

    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(
            bottomStart = 8.dp,
            topEnd = 8.dp,
            bottomEnd = 8.dp,
            topStart = 8.dp
        ),
        colors = CardDefaults.cardColors(containerColor = colorAzulLink)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Text(
                text = (item.numberList ?: "0").toString(),
                fontSize = dimensionResource(id = R.dimen.text_size_form).value.sp,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.margin_8dp))
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.dimen_card))
                    .padding(bottom = dimensionResource(id = R.dimen.margin_divided)),
                shape = RoundedCornerShape(
                    bottomStart = 8.dp,
                    topEnd = 8.dp

                ),
                colors = CardDefaults.cardColors(containerColor = colorWhite),
                onClick = { callbacks.onItemClick(item) }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = dimensionResource(id = R.dimen.margin_16dp)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = item.nameCard ?: "",
                        fontSize = 16.sp,
                        modifier = Modifier.weight(1f)
                    )

                    Box {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_more_vert),
                            contentDescription = "More Options",
                            modifier = Modifier
                                .size(dimensionResource(id = R.dimen.touch_google))
                                .padding(dimensionResource(id = R.dimen.margin_12dp))
                                .clickable { expanded = true }
                        )

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Editar") },
                                onClick = {
                                    expanded = false
                                    callbacks.onEdit(item)
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Eliminar") },
                                onClick = {
                                    expanded = false
                                    callbacks.onDelete(item)
                                }
                            )
                        }
                    }

                }
            }
        }
    }
}

/**
 * A complex card component with an expandable title and a list of subtitles.
 *
 * @param item The data for the card.
 * @param complexCallbacks The callbacks for the card.
 */
@Composable
fun ComplexCard(
    item: ModelComplexCard?,
    complexCallbacks: ModelAssignmentUiCallbacks,
) {

    Card(
        modifier = Modifier
            .fillMaxWidth().wrapContentHeight(),
        shape = RoundedCornerShape(
            bottomStart = 8.dp,
            topEnd = 8.dp,
            bottomEnd = 8.dp,
            topStart = 8.dp
        ),
        colors = CardDefaults.cardColors(containerColor = colorAzulLink)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.margin_8dp)))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(bottom = dimensionResource(id = R.dimen.margin_divided)),
                shape = RoundedCornerShape(
                    bottomStart = 8.dp,
                    topEnd = 8.dp

                ),
                colors = CardDefaults.cardColors(containerColor = colorWhite),
                onClick = { complexCallbacks.onItemClick(item) }
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .clickable { complexCallbacks.onExpandedTitle(!((item?.isExpandedTitle)?:false)) }
                            .fillMaxWidth()
                            .padding(
                                start = dimensionResource(id = R.dimen.margin_16dp) ),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = item?.nameTitle ?: "",
                            fontSize = 16.sp,
                            modifier = Modifier.weight(1f)
                                .padding(top = dimensionResource(id = R.dimen.margin_12dp))
                        )

                        Icon(
                            painter = painterResource(id = R.drawable.ic_principal_drop),
                            contentDescription = "More Options",

                            modifier = Modifier
                                .size(dimensionResource(id = R.dimen.touch_google))
                                .padding(dimensionResource(id = R.dimen.margin_12dp))
                        )
                    }

                    AnimatedVisibility(visible = item?.isExpandedTitle ?: false) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp)
                        ) {
                            item?.list?.forEach{item ->
                                Row(
                                    modifier = Modifier
                                        .clickable { complexCallbacks.onExpandedSubTitle(!((item?.isExpandedSubTitle)?:false)) }
                                        .fillMaxWidth()
                                        .padding(
                                            start = dimensionResource(id = R.dimen.margin_16dp) ),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ){
                                    Text(
                                        text = item?.nameSubTitle ?: "",
                                        modifier = Modifier.padding(8.dp)
                                    )
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_drop_down),
                                        contentDescription = "More Options",

                                        modifier = Modifier
                                            .size(dimensionResource(id = R.dimen.touch_google))
                                            .padding(dimensionResource(id = R.dimen.margin_16dp))
                                    )
                                }

                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * A grid item component.
 *
 * @param item The data for the grid item.
 * @param onItemClick A lambda to be invoked when the grid item is clicked.
 */
@Composable
fun GridItem(
    item: ModelPrincipalMenuData,
    onItemClick: (ModelPrincipalMenuData) -> Unit,
) { 
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onItemClick(item) },
        colors = CardDefaults.cardColors(
            containerColor = colorWhite, 
            contentColor = colorWhite
        ),
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.margin_between)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = item.image!!),
                contentDescription = null,
                modifier = Modifier.size(150.dp)
            )

            CustomSpace(dimensionResource(id = R.dimen.margin_between))
            TextDescription(item.titleCard!!)
        }
    }
}

/**
 * A dialog item component with a radio button.
 *
 * @param text The text to display.
 * @param isSelected Whether the item is selected.
 * @param onSelected A lambda to be invoked when the item is selected.
 */
@Composable
fun DialogGroupItem(
    text: String,
    isSelected: Boolean,
    onSelected: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelected() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onSelected,
            colors = RadioButtonDefaults.colors(selectedColor = colorPrincipalText)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = text ,
            fontSize = 16.sp
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
    listWorkMethods: List<ResponseGetListAssessmentType?>,
    name: ModelStateOutFieldText,
    percent: ModelStateOutFieldText,
    onNameChange: (ResponseGetListAssessmentType?) -> Unit,
    onPercentChange: (String) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.margin_divided))
    ) {
        Column(modifier = Modifier.weight(5f)) {
            CustomSpace(dimensionResource(R.dimen.margin_between))
            SpinnerMixOutlinedTextField(
                options = listWorkMethods,
                selectedOption = name,
                label = stringResource(R.string.form_subject_evaluation),
                onOptionSelected = {
                    onNameChange(it)
                }
            )
        }

        Box(modifier = Modifier.weight(2f)) {
            BoxEditTextNumeric(
                value = percent,
                enable = true,
                maxNumberCharacter = 4,
                label = stringResource(id = R.string.form_subject_percent),
            )
            {
                onPercentChange(it)
            }
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
            BoxEditTextGeneric(
                value = nameStudent,
                enable = false,
                label = stringResource(id = R.string.tools_empty),
                regex = ModelRegex.SIMPLE_TEXT,
            ){}
        }


        Box(modifier = Modifier.weight(2f)) {
            BoxEditTextScore(
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
    date: ModelDatePeriodDomain,
    isActive: Boolean = true,
    onDateChange: (Pair<LocalDate?, LocalDate?>) -> Unit,
) {

    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDates by remember { mutableStateOf<Pair<LocalDate?, LocalDate?>>(null to null) }

    DateRangePickerDialog(
        showDialog = showDatePicker,
        onDismiss = { showDatePicker = false },
        onDateSelected = { startDate, endDate ->
            selectedDates = startDate to endDate
            onDateChange(selectedDates)
        })


    BoxEditTextCalendar(
        value = date.date,
        enable = false,
        label = stringResource(id = R.string.form_partial_periods, index + 1),
    )
    {
        if(isActive) showDatePicker = true
    }

    CustomSpace(dimensionResource(R.dimen.margin_divided))


}