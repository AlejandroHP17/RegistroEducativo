package com.mx.liftechnology.registroeducativo.main.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mx.liftechnology.core.network.callapi.ResponseGetListAssessmentType
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_error
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_principal_text

@Preview(showBackground = true)
@Composable
fun SpinnerScreen() {
    val options = listOf("Opción 1", "Opción 2", "Otro")
    val options2 = listOf(ResponseGetListAssessmentType(assessmentTypeId = 1, description = "hola", teacherSchoolCycleGroupId = 1))
    var selectedOption by remember { mutableStateOf(options[0]) }

    Column {
        SpinnerOutlinedTextField(
            options = options,
            selectedOption = selectedOption,
            read = false,
            label = "test",
            error = ModelStateOutFieldText(false, ""),
            onOptionSelected = { selectedOption = it }
        )

        SpinnerMixOutlinedTextField(
            options = options2,
            selectedOption = selectedOption,
            label = "test",
            error = ModelStateOutFieldText(false, ""),
            onOptionSelected = {  }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpinnerOutlinedTextField(
    options: List<String>,
    selectedOption: String,
    read: Boolean,
    label: String,
    error: ModelStateOutFieldText,
    onOptionSelected: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) } // Controla si el menú está abierto
    var selectedText by remember { mutableStateOf(selectedOption) } // Texto seleccionado

    Column {
        ExposedDropdownMenuBox(
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.margin_between)),
            expanded = expanded,
            onExpandedChange = {
                if (!read) {
                    expanded = !expanded
                }
            } // Abre/cierra el menú al hacer clic
        ) {
            OutlinedTextField(
                value = selectedText,
                onValueChange = {}, // Deshabilitado para evitar edición manual
                enabled = false,
                label = {
                    Text(
                        text = label,
                        color = color_principal_text
                    )
                },
                modifier = Modifier
                    .menuAnchor() // Ancla el menú al TextField
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
                    onDismissRequest = { expanded = false } // Cierra el menú si se toca fuera
                ) {
                    options.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                selectedText = option
                                onOptionSelected(option)
                                expanded = false // Cierra el menú después de seleccionar
                            }
                        )
                    }
                }
            }

        }

        if (error.isError) {
            Text(
                text = error.errorMessage,
                color = color_error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
        CustomSpace(dimensionResource(R.dimen.margin_between))
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpinnerMixOutlinedTextField(
    options: List<ResponseGetListAssessmentType?>,
    selectedOption: String,
    label: String,
    error: ModelStateOutFieldText,
    onOptionSelected: (ResponseGetListAssessmentType?) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) } // Controla si el menú está abierto
    var selectedOption by remember { mutableStateOf(selectedOption) } // Texto seleccionado
    var isEditable by remember { mutableStateOf(true) } //habilita la edicion de texto

    if (selectedOption == "Nuevo") selectedOption = ""

    Column {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedOption,
                onValueChange = {
                    if (isEditable) selectedOption = it
                    onOptionSelected(ResponseGetListAssessmentType(
                        assessmentTypeId = -1,
                        description = it,
                        teacherSchoolCycleGroupId = options.firstOrNull()?.teacherSchoolCycleGroupId
                    ))
                }, // Deshabilitado para evitar edición manual
                readOnly = !isEditable,
                label = {
                    Text(
                        text = label,
                        color = color_principal_text
                    )
                },
                modifier = Modifier
                    .menuAnchor() // Ancla el menú al TextField
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
                onDismissRequest = { expanded = false } // Cierra el menú si se toca fuera
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option?.description ?: "Nuevo") },
                        onClick = {
                            onOptionSelected(option)

                            selectedOption = option?.description ?: "Nuevo"
                            isEditable = option?.description == "Nuevo"
                            expanded = false // Cierra el menú después de seleccionar
                        }
                    )
                }
            }
        }

        if (error.isError) {
            Text(
                text = error.errorMessage,
                color = color_error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }

}