package com.mx.liftechnology.registroeducativo.main.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetListAssessmentType
import com.mx.liftechnology.domain.model.generic.ModelCustomSpinner
import com.mx.liftechnology.domain.model.generic.ModelRegex
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.util.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorError
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorPrincipalText

/**
 * A composable function for previewing the spinners in this file.
 */
@Preview(showBackground = true)
@Composable
fun SpinnerScreen() {
    val options = listOf(
        ModelCustomSpinner(
            value ="Opción 1",
            id= 1),
        ModelCustomSpinner(
            value ="Opción 2",
            id= 2),
        ModelCustomSpinner(
            value ="Otro",
            id= 3),
        )
    val options2 = listOf(ResponseGetListAssessmentType(assessmentTypeId = 1, description = "hola", teacherSchoolCycleGroupId = 1))
    var selectedOption by remember { mutableStateOf(options[0]) }

    Column {
        SpinnerOutlinedTextField(
            options = options,
            selectedOption = selectedOption.value.stringToModelStateOutFieldText(),
            read = false,
            label = "test",
            onOptionSelected = { selectedOption = it }
        )

        SpinnerMixOutlinedTextField(
            options = options2,
            selectedOption = selectedOption.value.stringToModelStateOutFieldText(),
            label = "test",
            onOptionSelected = {  }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpinnerTextField(
    options: List<ModelCustomSpinner>,
    selectedOption: ModelStateOutFieldText,
    read: Boolean,
    label: String,
    onOptionSelected: (ModelCustomSpinner) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(selectedOption) }
    
    // Actualiza el texto seleccionado cuando cambia el selectedOption externo
    LaunchedEffect(selectedOption) {
        selectedText = selectedOption
    }

    Column {
        ExposedDropdownMenuBox(
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.margin_between)),
            expanded = expanded,
            onExpandedChange = {
                if (!read) {
                    expanded = !expanded
                }
            }
        ) {
            OutlinedTextField(
                value = selectedText.valueText,
                onValueChange = {},
                enabled = false,
                label = {
                    Text(
                        text = label,
                        color = colorPrincipalText
                    )
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = "Expandir"
                    )
                },
                maxLines = 1,
                shape = RoundedCornerShape(8.dp),
                colors = personalizeColors()
            )

            if (!read) {
                ExposedDropdownMenu(

                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    options.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option.value!!) },
                            onClick = {
                                selectedText = option.value.stringToModelStateOutFieldText()
                                onOptionSelected(option)
                                expanded = false
                            }
                        )
                    }
                }
            }

        }

        if (selectedOption.isError) {
            Text(
                text = selectedText.errorMessage,
                color = colorError,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
        CustomSpace(dimensionResource(R.dimen.margin_between))
    }
}

/**
 * An outlined text field with a dropdown menu.
 *
 * @param options The list of options to display.
 * @param selectedOption The currently selected option.
 * @param read Whether the text field is read-only.
 * @param label The label for the text field.
 * @param onOptionSelected A lambda to be invoked when an option is selected.
 */
@Deprecated("se migra a SpinnerTextField")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpinnerOutlinedTextField(
    options: List<ModelCustomSpinner>,
    selectedOption: ModelStateOutFieldText,
    read: Boolean,
    label: String,
    onOptionSelected: (ModelCustomSpinner) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(selectedOption) }

    Column {
        ExposedDropdownMenuBox(
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.margin_between)),
            expanded = expanded,
            onExpandedChange = {
                if (!read) {
                    expanded = !expanded
                }
            }
        ) {
            OutlinedTextField(
                value = selectedText.valueText,
                onValueChange = {}, 
                enabled = false,
                label = {
                    Text(
                        text = label,
                        color = colorPrincipalText
                    )
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = "Expandir"
                    )
                },
                maxLines = 1,
                shape = RoundedCornerShape(8.dp),
                colors = personalizeColors()
            )

            if (!read) {
                ExposedDropdownMenu(

                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    options.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option.value!!) },
                            onClick = {
                                selectedText = option.value.stringToModelStateOutFieldText()
                                onOptionSelected(option)
                                expanded = false
                            }
                        )
                    }
                }
            }

        }

        if (selectedOption.isError) {
            Text(
                text = selectedText.errorMessage,
                color = colorError,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
        CustomSpace(dimensionResource(R.dimen.margin_between))
    }
}

/**
 * An outlined text field with a dropdown menu that allows for custom input.
 *
 * @param options The list of options to display.
 * @param selectedOption The currently selected option.
 * @param label The label for the text field.
 * @param onOptionSelected A lambda to be invoked when an option is selected.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpinnerMixOutlinedTextField(
    options: List<ResponseGetListAssessmentType?>,
    selectedOption: ModelStateOutFieldText,
    label: String,
    onOptionSelected: (ResponseGetListAssessmentType?) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptions by remember { mutableStateOf(selectedOption) }
    var isEditable by remember { mutableStateOf(true) }

    if (selectedOption.valueText == "Nuevo") selectedOptions = "".stringToModelStateOutFieldText()

    Column {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedOption.valueText,
                onValueChange = { newValue ->
                    if (isEditable && (newValue.isEmpty() || ModelRegex.SIMPLE_TEXT.matches(newValue))){
                        selectedOptions = ModelStateOutFieldText(valueText = newValue, isError = selectedOption.isError, errorMessage = selectedOption.errorMessage)
                    }
                    onOptionSelected(
                        ResponseGetListAssessmentType(
                        assessmentTypeId = -1,
                        description = newValue,
                        teacherSchoolCycleGroupId = options.firstOrNull()?.teacherSchoolCycleGroupId
                    )
                    )
                }, 
                readOnly = !isEditable,
                label = {
                    Text(
                        text = label,
                        color = colorPrincipalText
                    )
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences),
                modifier = Modifier
                    .menuAnchor() 
                    .fillMaxWidth()
                    .clickable { expanded = true },
                trailingIcon = {
                    Icon(
                        imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = "Expandir"
                    )
                },
                maxLines = 1,
                shape = RoundedCornerShape(8.dp),
                colors = personalizeColors()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option?.description ?: "Nuevo") },
                        onClick = {
                            selectedOptions = (option?.description).stringToModelStateOutFieldText()
                            onOptionSelected(option?.copy(description = if(option.description == "Nuevo") "" else selectedOptions.valueText))
                            isEditable = option?.description == "Nuevo"
                            expanded = false
                        }
                    )
                }
            }
        }

        if (selectedOption.isError) {
            Text(
                text = selectedOption.errorMessage,
                color = colorError,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}