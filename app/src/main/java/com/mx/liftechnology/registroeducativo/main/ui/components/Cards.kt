package com.mx.liftechnology.registroeducativo.main.ui.components

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.mx.liftechnology.core.network.callapi.ResponseGetListAssessmentType
import com.mx.liftechnology.data.model.ModelPrincipalMenuData
import com.mx.liftechnology.domain.model.ModelDatePeriodDomain
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.menu.ModelDialogStudentGroupDomain
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.share.ModelCustomCard
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_azul_link
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_principal_text
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_white
import com.mx.liftechnology.registroeducativo.main.viewextensions.stringToModelStateOutFieldText
import java.time.LocalDate

@Preview(showBackground = true)
@Composable
fun CustomCardView() {
    Column {
        CustomCard(
            item = ModelCustomCard(
                id = "1",
                numberList = "1",
                nameCard = "Curp",
            ),
            onItemMore = {},
            onItemClick = {}
        )

        GridItem(
            item = ModelPrincipalMenuData(
                id = "1",
                image = com.mx.liftechnology.data.R.drawable.ic_students,
                titleCard = "texto"
            ),
            {}
        )

        DialogGroupItem(
            item = ModelDialogStudentGroupDomain(
                selected = true,
                item = null,
                nameItem = "nombre"
            ),
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

@Composable
fun CustomCard(
    item: ModelCustomCard,
    onItemClick: (ModelCustomCard) -> Unit,
    onItemMore: (ModelCustomCard) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(20),
        colors = CardDefaults.cardColors(containerColor = color_azul_link)
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
                colors = CardDefaults.cardColors(containerColor = color_white),
                onClick = { onItemClick(item) }
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

                    Icon(
                        painter = painterResource(id = R.drawable.ic_more_vert),
                        contentDescription = "More Options",

                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.touch_google))
                            .padding(dimensionResource(id = R.dimen.margin_12dp))
                            .clickable { onItemMore(item) }
                    )
                }
            }
        }
    }
}

@Composable
fun GridItem(
    item: ModelPrincipalMenuData,
    onItemClick: (ModelPrincipalMenuData) -> Unit,
) { // Recibe un ID de recurso
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onItemClick(item) },
        colors = CardDefaults.cardColors(
            containerColor = color_white, // Color de fondo
            contentColor = color_white
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

@Composable
fun DialogGroupItem(
    item: ModelDialogStudentGroupDomain,
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
            colors = RadioButtonDefaults.colors(selectedColor = color_principal_text)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "${item.item?.cct.orEmpty()} ${item.item?.group.orEmpty()} ${item.item?.name.orEmpty()}",
            fontSize = 16.sp
        )
    }
}

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
        var selectedOption by remember { mutableStateOf(listWorkMethods[0]) }

        Column(modifier = Modifier.weight(5f)) {
            CustomSpace(dimensionResource(R.dimen.margin_between))
            SpinnerMixOutlinedTextField(
                options = listWorkMethods,
                selectedOption = name,
                label = stringResource(R.string.register_subject_evaluation),
                onOptionSelected = {
                    selectedOption = it
                    onNameChange(it)
                }
            )
        }

        Box(modifier = Modifier.weight(2f)) {
            // Campo de texto para el porcentaje
            BoxEditTextNumeric(
                value = percent,
                enable = true,
                label = stringResource(id = R.string.register_subject_percent),
            )
            {
                onPercentChange(it)
            }
        }
    }
    CustomSpace(dimensionResource(R.dimen.margin_divided))
}

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
            ){}
        }


        Box(modifier = Modifier.weight(2f)) {
            BoxEditTextScore(
                value = score,
                enable = true,
                label = stringResource(id = R.string.assignment_score),
            )
            { onScoreChange(it) }
        }
    }
    CustomSpace(dimensionResource(R.dimen.margin_divided))
}

@Composable
fun RegisterPartialListItem(
    index: Int,
    date: ModelDatePeriodDomain,
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
        }
    )


    BoxEditTextCalendar(
        value = date.date,
        enable = false,
        label = stringResource(id = R.string.register_partial_periods, index + 1),
    )
    { showDatePicker = true }

    CustomSpace(dimensionResource(R.dimen.margin_divided))


}