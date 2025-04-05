package com.mx.liftechnology.registroeducativo.main.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mx.liftechnology.domain.model.generic.ModelRegex
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_error
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_principal_text
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_secondary_text
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_white


@Preview(showBackground = true)
@Composable
fun TestBoxes(){
    var data by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column (
        modifier = Modifier.background(background())
    ){
        BoxEditTextEmail(
            value = ModelStateOutFieldText(valueText = data, isError = false, errorMessage = ""),
            enable = true,
            label = stringResource(id = R.string.form_generic_email)
        )
        { data = it}

        BoxEditTextPassword(
            value = ModelStateOutFieldText(valueText = data, isError = false, errorMessage = ""),
            statePass = passwordVisible,
            enable = true,
            label = stringResource(id = R.string.form_generic_password),
            onBoxChanged = { data = it },
            onStatePassChanged = { passwordVisible = it }
        )

        BoxEditTextGeneric(
            value = ModelStateOutFieldText(valueText = data, isError = false, errorMessage = ""),
            enable = true,
            label = stringResource(id = R.string.form_generic)
        )
        { data = it}

        BoxEditTextAllCaps(
            value = ModelStateOutFieldText(valueText = data, isError = false, errorMessage = ""),
            enable = true,
            label = stringResource(id = R.string.form_student_curp)
        )
        { data = it}

        BoxEditTextNumeric(
            value = ModelStateOutFieldText(valueText = data, isError = false, errorMessage = ""),
            enable = true,
            label = stringResource(id = R.string.form_student_phone_number)
        )
        { data = it}

        BoxEditTextCalendar(
            value = ModelStateOutFieldText(valueText = data, isError = false, errorMessage = ""),
            enable = true,
            label = stringResource(id = R.string.form_generic)
        )
        {  }
    }
}

@Composable
fun BoxEditTextEmail(
    value:ModelStateOutFieldText,
    enable: Boolean,
    label: String,
    onBoxChanged:(String) ->  Unit){

    OutlinedTextField(
        value = value.valueText,
        onValueChange = { onBoxChanged(it)},
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(id = R.dimen.margin_between)),
        enabled = enable,
        label = { Text(
            text = label,
            color = if(value.isError) color_error else color_principal_text) },
        isError = value.isError,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        maxLines = 1,
        shape = RoundedCornerShape(8.dp),
        colors = personalizeColors()
    )

    if (value.isError) {
        Text(
            text = value.errorMessage,
            color = color_error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }
}


@Composable
fun BoxEditTextPassword(
    value:ModelStateOutFieldText,
    statePass : Boolean,
    enable:Boolean,
    label: String,
    onBoxChanged:(String) ->  Unit,
    onStatePassChanged:(Boolean) ->  Unit)
{

    OutlinedTextField(
        value = value.valueText,
        onValueChange = { onBoxChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(id = R.dimen.margin_between)),
        enabled = enable,
        label = {
            Text(
                text = label,
                color = if (value.isError) color_error else color_principal_text
            )
        },
        trailingIcon = {
            val icon = if (statePass) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }
            Icon(
                imageVector = icon,
                contentDescription = if (statePass) "Ocultar contraseña" else "Mostrar contraseña",
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clickable { onStatePassChanged(  !statePass )}
            )
        },
        isError = value.isError,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next
        ),
        visualTransformation = if (statePass) VisualTransformation.None else PasswordVisualTransformation(),
        maxLines = 1,
        shape = RoundedCornerShape(8.dp),
        colors = personalizeColors()
    )

    if (value.isError) {
        Text(
            text = value.errorMessage,
            color = color_error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }
}

@Composable
fun BoxEditTextGeneric(
    value:ModelStateOutFieldText,
    enable: Boolean,
    label: String,
    onBoxChanged:(String) ->  Unit){

    OutlinedTextField(
        value = value.valueText,
        onValueChange = { newValue ->
            if (newValue.isEmpty() || ModelRegex.SIMPLE_TEXT.matches(newValue)) {
                onBoxChanged(newValue)
            } },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(id = R.dimen.margin_between)),
        enabled = enable,
        label = { Text(
            text = label,
            color = if(value.isError) color_error else color_principal_text) },
        isError = value.isError,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        maxLines = 1,
        shape = RoundedCornerShape(8.dp),
        colors = personalizeColors()
    )

    if (value.isError) {
        Text(
            text = value.errorMessage,
            color = color_error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }

    CustomSpace(dimensionResource(R.dimen.margin_between))
}

@Composable
fun BoxEditTextAllCaps(
    value:ModelStateOutFieldText,
    enable: Boolean,
    label: String,
    onBoxChanged:(String) ->  Unit){

    OutlinedTextField(
        value = value.valueText,
        onValueChange = { newValue ->
            if (newValue.isEmpty() || ModelRegex.SIMPLE_TEXT.matches(newValue)) {
                onBoxChanged(newValue.uppercase())
            } },

        modifier = Modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(id = R.dimen.margin_between)),
        enabled = enable,
        label = { Text(
            text = label,
            color = if(value.isError) color_error else color_principal_text) },
        isError = value.isError,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Characters,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        maxLines = 1,
        shape = RoundedCornerShape(8.dp),
        colors = personalizeColors()
    )

    if (value.isError) {
        Text(
            text = value.errorMessage,
            color = color_error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }

    CustomSpace(dimensionResource(R.dimen.margin_between))
}


@Composable
fun BoxEditTextNumeric(
    value:ModelStateOutFieldText,
    enable: Boolean,
    label: String,
    onBoxChanged:(String) ->  Unit){

    Column {
        OutlinedTextField(
            value = value.valueText,
            onValueChange = { onBoxChanged(it)},
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(id = R.dimen.margin_between)),
            enabled = enable,
            label = { Text(
                text = label,
                color = if(value.isError) color_error else color_principal_text) },
            isError = value.isError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            maxLines = 1,
            shape = RoundedCornerShape(8.dp),
            colors = personalizeColors()
        )

        if (value.isError) {
            Text(
                text = value.errorMessage,
                color = color_error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
    CustomSpace(dimensionResource(R.dimen.margin_between))
}

@Composable
fun BoxEditTextScore(
    value:ModelStateOutFieldText,
    enable: Boolean,
    label: String,
    onBoxChanged:(String) ->  Unit){

    var isEdited by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    // Detectar el primer enfoque
    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            if (interaction is FocusInteraction.Focus && !isEdited) {
                onBoxChanged("") // Limpiar campo
                isEdited = true
            }
        }
    }

    Column {
        OutlinedTextField(
            value = value.valueText,
            onValueChange = { rawInput ->
                val newValue = rawInput.replace(',', '.')
                if (newValue.isEmpty() || ModelRegex.SCORE.matches(newValue)) {
                    onBoxChanged(newValue)
                }
            },
            interactionSource = interactionSource,
            modifier = Modifier.fillMaxWidth()
                .fillMaxWidth()
                .padding(top = dimensionResource(id = R.dimen.margin_between)),
            enabled = enable,
            label = { Text(
                text = label,
                color = if(value.isError) color_error else color_principal_text) },
            isError = value.isError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            maxLines = 1,
            shape = RoundedCornerShape(8.dp),
            colors = personalizeColors(),
        )

        if (value.isError) {
            Text(
                text = value.errorMessage,
                color = color_error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
    CustomSpace(dimensionResource(R.dimen.margin_between))
}

@Composable
fun BoxEditTextCalendar(
    value:ModelStateOutFieldText,
    enable: Boolean,
    label: String,
    onBoxChanged:() ->  Unit){

    Column {
        OutlinedTextField(
            value = value.valueText,
            onValueChange = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(id = R.dimen.margin_between)),
            enabled = enable,
            label = { Text(
                text = label,
                color = if(value.isError) color_error else color_principal_text) },
            isError = value.isError,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "calendar",
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clickable { onBoxChanged()}
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            maxLines = 1,
            shape = RoundedCornerShape(8.dp),
            colors = personalizeColors()
        )

        if (value.isError) {
            Text(
                text = value.errorMessage,
                color = color_error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }

        CustomSpace(dimensionResource(R.dimen.margin_between))
    }


}



@Composable
fun personalizeColors(): TextFieldColors {
    return TextFieldDefaults.colors(

        focusedTextColor = color_principal_text,
        unfocusedTextColor = color_principal_text,
        disabledTextColor = color_secondary_text,
        errorTextColor = color_error,

        focusedContainerColor = color_white,
        unfocusedContainerColor = color_white,
        disabledContainerColor = color_white,
        errorContainerColor = color_white,

        cursorColor = color_principal_text,
        errorCursorColor = color_principal_text,

        focusedIndicatorColor = color_principal_text,
        unfocusedIndicatorColor = color_secondary_text,
        disabledIndicatorColor = color_secondary_text,
        errorIndicatorColor = color_error,

        focusedLeadingIconColor = color_principal_text,
        unfocusedLeadingIconColor = color_principal_text,
        disabledLeadingIconColor = color_principal_text,
        errorLeadingIconColor = color_error,

        focusedTrailingIconColor = color_principal_text,
        unfocusedTrailingIconColor = color_principal_text,
        disabledTrailingIconColor = color_principal_text,
        errorTrailingIconColor = color_error,

        focusedLabelColor = color_principal_text,
        unfocusedLabelColor = color_principal_text,
        disabledLabelColor = color_principal_text,
        errorLabelColor = color_error,
        focusedPlaceholderColor = color_principal_text,
        unfocusedPlaceholderColor = color_principal_text,
        disabledPlaceholderColor = color_principal_text,
        errorPlaceholderColor = color_error,
        focusedSupportingTextColor = color_principal_text,
        unfocusedSupportingTextColor = color_principal_text,
        disabledSupportingTextColor = color_principal_text,
        errorSupportingTextColor = color_error,
        focusedPrefixColor = color_principal_text,
        unfocusedPrefixColor = color_principal_text,
        disabledPrefixColor = color_principal_text,
        errorPrefixColor = color_error,
        focusedSuffixColor = color_principal_text,
        unfocusedSuffixColor = color_principal_text,
        disabledSuffixColor = color_principal_text,
        errorSuffixColor = color_error,

        )
}
