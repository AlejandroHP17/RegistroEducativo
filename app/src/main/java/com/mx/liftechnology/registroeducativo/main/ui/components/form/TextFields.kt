package com.mx.liftechnology.registroeducativo.main.ui.components.form

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
import com.mx.liftechnology.domain.util.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorError
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorPrincipalText
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorSecondaryText
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorWhite
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.background

/**
 * A composable function for previewing the text fields in this file.
 */
@Preview(showBackground = true)
@Composable
fun TestBoxes() {
    var data by remember { mutableStateOf("".stringToModelStateOutFieldText()) }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.background(background())
    ) {
        TextFieldEmail(
            modelText = data,
            enableBox = true,
            label = stringResource(id = R.string.form_generic_email)
        )
        { data = it }

        TextFieldPassword(
            modelText = data,
            statePass = passwordVisible,
            enable = true,
            label = stringResource(id = R.string.form_generic_password),
            onBoxChanged = { data = it },
            onStatePassChanged = { passwordVisible = it }
        )

        TextFieldGeneric(
            modelText = data,
            enable = true,
            label = stringResource(id = R.string.tools_generic),
            regex = ModelRegex.SIMPLE_TEXT
        )
        { data = it }

        TextFieldAllCaps(
            modelText = data,
            enable = true,
            label = stringResource(id = R.string.form_student_curp)
        )
        { data = it }

        TextFieldCapitalLetter(
            modelText = data,
            enable = true,
            label = stringResource(id = R.string.form_student_name)
        ) {}

        TextFieldNumeric(
            modelText = data,
            enable = true,
            label = stringResource(id = R.string.form_student_phone_number),
            maxNumberCharacter = 5
        )
        { data = it }


        TextFieldCalendar(
            value =data,
            enable = true,
            label = stringResource(id = R.string.tools_generic)
        )
        { }

        TextFieldSet(
            modelText = data,
            label = stringResource(id = R.string.form_student_phone_number),
        )
    }
}


/**
 * An outlined text field for email input.
 *
 * @param modelText The state of the text field.
 * @param enableBox Whether the text field is enabled.
 * @param label The label for the text field.
 * @param onBoxChanged A lambda to be invoked when the text field value changes.
 */
@Composable
fun TextFieldEmail(
    modelText: ModelStateOutFieldText,
    enableBox: Boolean,
    label: String,
    onBoxChanged: (ModelStateOutFieldText) -> Unit,
) {
    var value by remember { mutableStateOf(modelText) }
    LaunchedEffect(Unit, modelText) {
        if(value != modelText)
            value = modelText
    }

    OutlinedTextField(
        value = value.valueText,
        onValueChange = { newValue ->
            if (ModelRegex.COMPLETE_TEXT.matches(newValue) || newValue.isEmpty()) {
                value = newValue.stringToModelStateOutFieldText()
                onBoxChanged(value)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(id = R.dimen.margin_between)),
        enabled = enableBox,
        label = {
            Text(
                text = label,
                color = if (value.isError) colorError else colorPrincipalText
            )
        },
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
            color = colorError,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }
}

/**
 * An outlined text field for password input.
 *
 * @param modelText The state of the text field.
 * @param statePass Whether the password is visible.
 * @param enable Whether the text field is enabled.
 * @param label The label for the text field.
 * @param onBoxChanged A lambda to be invoked when the text field value changes.
 * @param onStatePassChanged A lambda to be invoked when the password visibility state changes.
 */
@Composable
fun TextFieldPassword(
    modelText: ModelStateOutFieldText,
    statePass: Boolean,
    enable: Boolean,
    label: String,
    onBoxChanged: (ModelStateOutFieldText) -> Unit,
    onStatePassChanged: (Boolean) -> Unit,
) {
    var value by remember { mutableStateOf(modelText) }
    LaunchedEffect(Unit, modelText) {
        if(value != modelText)
            value = modelText
    }
    OutlinedTextField(
        value = value.valueText,
        onValueChange = { newValue ->
            value = newValue.stringToModelStateOutFieldText()
            onBoxChanged(value)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(id = R.dimen.margin_between)),
        enabled = enable,
        label = {
            Text(
                text = label,
                color = if (value.isError) colorError else colorPrincipalText
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
                    .clickable { onStatePassChanged(!statePass) }
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
            color = colorError,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }
}

/**
 * A generic outlined text field that capitalizes the first letter of each word.
 *
 * @param modelText The state of the text field.
 * @param enable Whether the text field is enabled.
 * @param label The label for the text field.
 * @param onBoxChanged A lambda to be invoked when the text field value changes.
 */
@Composable
fun TextFieldCapitalLetter(
    modelText: ModelStateOutFieldText,
    enable: Boolean,
    label: String,
    onBoxChanged: (ModelStateOutFieldText) -> Unit,
) {
    var value by remember { mutableStateOf(modelText) }
    LaunchedEffect(Unit, modelText) {
        if(value != modelText)
        value = modelText
    }

    OutlinedTextField(
        value = value.valueText,
        onValueChange = { newValue ->
            if (newValue.isEmpty() || ModelRegex.SIMPLE_TEXT.matches(newValue)) {
                value = newValue.stringToModelStateOutFieldText()
                onBoxChanged(value)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(id = R.dimen.margin_between)),
        enabled = enable,
        label = {
            Text(
                text = label,
                color = if (value.isError) colorError else colorPrincipalText
            )
        },
        isError = value.isError,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
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
            color = colorError,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }

    CustomSpace(dimensionResource(R.dimen.margin_between))
}

/**
 * A simple generic outlined text field.
 *
 * @param modelText The state of the text field.
 * @param enable Whether the text field is enabled.
 * @param label The label for the text field.
 * @param onBoxChanged A lambda to be invoked when the text field value changes.
 */
@Composable
fun TextFieldGeneric(
    modelText: ModelStateOutFieldText,
    enable: Boolean,
    label: String,
    regex: Regex,
    onBoxChanged: (ModelStateOutFieldText) -> Unit,
) {
    var value by remember { mutableStateOf(modelText) }
    LaunchedEffect(Unit, modelText) {
        if(value != modelText)
            value = modelText
    }
    OutlinedTextField(
        value = value.valueText,
        onValueChange = { newValue ->
            if (newValue.isEmpty() || regex.matches(newValue)) {
                value = newValue.stringToModelStateOutFieldText()
                onBoxChanged(value)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(id = R.dimen.margin_between)),
        enabled = enable,
        label = {
            Text(
                text = label,
                color = if (value.isError) colorError else colorPrincipalText
            )
        },
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
            color = colorError,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }

    CustomSpace(dimensionResource(R.dimen.margin_between))
}

@Composable
fun TextFieldSet(
    modelText: ModelStateOutFieldText,
    label: String
) {

    OutlinedTextField(
        value = modelText.valueText,
        onValueChange = { },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(id = R.dimen.margin_between)),
        enabled = false,
        label = {
            Text(
                text = label,
                color =  colorPrincipalText
            )
        },
        maxLines = 1,
        shape = RoundedCornerShape(8.dp),
        colors = personalizeColors()
    )

    CustomSpace(dimensionResource(R.dimen.margin_between))
}

/**
 * An outlined text field that converts all input to uppercase.
 *
 * @param modelText The state of the text field.
 * @param enable Whether the text field is enabled.
 * @param label The label for the text field.
 * @param onBoxChanged A lambda to be invoked when the text field value changes.
 */
@Composable
fun TextFieldAllCaps(
    modelText: ModelStateOutFieldText,
    enable: Boolean,
    label: String,
    onBoxChanged: (ModelStateOutFieldText) -> Unit,
) {
    var value by remember { mutableStateOf(modelText) }
    LaunchedEffect(Unit, modelText) {
        if(value != modelText)
            value = modelText
    }
    OutlinedTextField(
        value = value.valueText,
        onValueChange = { newValue ->
            val reformatValue = newValue.trim()
            if (reformatValue.isEmpty() || ModelRegex.TEXT_WITH_NUMBERS.matches(reformatValue)) {
                value = reformatValue.uppercase().stringToModelStateOutFieldText()
                onBoxChanged(value)
            }
        },

        modifier = Modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(id = R.dimen.margin_between)),
        enabled = enable,
        label = {
            Text(
                text = label,
                color = if (value.isError) colorError else colorPrincipalText
            )
        },
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
            color = colorError,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }

    CustomSpace(dimensionResource(R.dimen.margin_between))
}

/**
 * An outlined text field for numeric input.
 *
 * @param modelText The state of the text field.
 * @param enable Whether the text field is enabled.
 * @param label The label for the text field.
 * @param maxNumberCharacter The maximum number of characters allowed.
 * @param onBoxChanged A lambda to be invoked when the text field value changes.
 */
@Composable
fun TextFieldNumeric(
    modelText: ModelStateOutFieldText,
    enable: Boolean,
    label: String,
    maxNumberCharacter: Int,
    onBoxChanged: (ModelStateOutFieldText) -> Unit,
) {
    var value by remember { mutableStateOf(modelText) }
    LaunchedEffect(Unit, modelText) {
        if(value != modelText)
            value = modelText
    }

    OutlinedTextField(
        value = value.valueText,
        onValueChange = { newValue ->
            if (newValue.length <= maxNumberCharacter) {
                value = newValue.stringToModelStateOutFieldText()
                onBoxChanged(value)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(id = R.dimen.margin_between)),
        enabled = enable,
        label = {
            Text(
                text = label,
                color = if (value.isError) colorError else colorPrincipalText
            )
        },
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
            color = colorError,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }

    CustomSpace(dimensionResource(R.dimen.margin_between))
}

/**
 * An outlined text field for score input.
 *
 * @param value The state of the text field.
 * @param enable Whether the text field is enabled.
 * @param label The label for the text field.
 * @param onBoxChanged A lambda to be invoked when the text field value changes.
 */
@Composable
fun TextFieldScore(
    value: ModelStateOutFieldText,
    enable: Boolean,
    label: String,
    onBoxChanged: (String) -> Unit,
) {

    var isEdited by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            if (interaction is FocusInteraction.Focus && !isEdited) {
                onBoxChanged("")
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
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxWidth()
                .padding(top = dimensionResource(id = R.dimen.margin_between)),
            enabled = enable,
            label = {
                Text(
                    text = label,
                    color = if (value.isError) colorError else colorPrincipalText
                )
            },
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
                color = colorError,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
    CustomSpace(dimensionResource(R.dimen.margin_between))
}

/**
 * An outlined text field with a calendar icon.
 *
 * @param value The state of the text field.
 * @param enable Whether the text field is enabled.
 * @param label The label for the text field.
 * @param onBoxChanged A lambda to be invoked when the calendar icon is clicked.
 */
@Composable
fun TextFieldCalendar(
    value: ModelStateOutFieldText,
    enable: Boolean,
    label: String,
    onBoxChanged: () -> Unit,
) {

    Column {
        OutlinedTextField(
            value = value.valueText,
            onValueChange = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(id = R.dimen.margin_between)),
            enabled = enable,
            label = {
                Text(
                    text = label,
                    color = if (value.isError) colorError else colorPrincipalText
                )
            },
            isError = value.isError,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "calendar",
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clickable { onBoxChanged() }
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
                color = colorError,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }

        CustomSpace(dimensionResource(R.dimen.margin_between))
    }


}

/**
 * A composable function that returns the colors for the text fields.
 */
@Composable
fun personalizeColors(): TextFieldColors {
    return TextFieldDefaults.colors(

        focusedTextColor = colorPrincipalText,
        unfocusedTextColor = colorPrincipalText,
        disabledTextColor = colorSecondaryText,
        errorTextColor = colorError,

        focusedContainerColor = colorWhite,
        unfocusedContainerColor = colorWhite,
        disabledContainerColor = colorWhite,
        errorContainerColor = colorWhite,

        cursorColor = colorPrincipalText,
        errorCursorColor = colorPrincipalText,

        focusedIndicatorColor = colorPrincipalText,
        unfocusedIndicatorColor = colorSecondaryText,
        disabledIndicatorColor = colorSecondaryText,
        errorIndicatorColor = colorError,

        focusedLeadingIconColor = colorPrincipalText,
        unfocusedLeadingIconColor = colorPrincipalText,
        disabledLeadingIconColor = colorPrincipalText,
        errorLeadingIconColor = colorError,

        focusedTrailingIconColor = colorPrincipalText,
        unfocusedTrailingIconColor = colorPrincipalText,
        disabledTrailingIconColor = colorPrincipalText,
        errorTrailingIconColor = colorError,

        focusedLabelColor = colorPrincipalText,
        unfocusedLabelColor = colorPrincipalText,
        disabledLabelColor = colorPrincipalText,
        errorLabelColor = colorError,
        focusedPlaceholderColor = colorPrincipalText,
        unfocusedPlaceholderColor = colorPrincipalText,
        disabledPlaceholderColor = colorPrincipalText,
        errorPlaceholderColor = colorError,
        focusedSupportingTextColor = colorPrincipalText,
        unfocusedSupportingTextColor = colorPrincipalText,
        disabledSupportingTextColor = colorPrincipalText,
        errorSupportingTextColor = colorError,
        focusedPrefixColor = colorPrincipalText,
        unfocusedPrefixColor = colorPrincipalText,
        disabledPrefixColor = colorPrincipalText,
        errorPrefixColor = colorError,
        focusedSuffixColor = colorPrincipalText,
        unfocusedSuffixColor = colorPrincipalText,
        disabledSuffixColor = colorPrincipalText,
        errorSuffixColor = colorError,

        )
}
