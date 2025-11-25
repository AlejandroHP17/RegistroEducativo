package com.mx.liftechnology.registroeducativo.main.ui.formativeFields.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mx.liftechnology.data.model.formativeField.ModelWorkTypeData
import com.mx.liftechnology.domain.model.generic.ModelRegex.COMPLEX_TEXT
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelRegisterSubjectStateUI
import com.mx.liftechnology.registroeducativo.main.ui.components.BoxEditTextGeneric
import com.mx.liftechnology.registroeducativo.main.ui.components.ButtonAction
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentHeaderBack
import com.mx.liftechnology.registroeducativo.main.ui.components.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.EvaluationPercentList
import com.mx.liftechnology.registroeducativo.main.ui.components.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.components.SpinnerTextField
import com.mx.liftechnology.registroeducativo.main.ui.components.TextBody
import com.mx.liftechnology.registroeducativo.main.ui.principal.SharedViewModel
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorAction
import org.koin.androidx.compose.koinViewModel

/**
 * The Subject Registration screen.
 *
 * @param navController The navigation controller.
 * @param sharedViewModel The shared ViewModel.
 * @param registerFormativeFieldsViewModel The ViewModel for this screen.
 */
@Composable
fun RegisterSubjectScreen(
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
    registerFormativeFieldsViewModel: RegisterFormativeFieldsViewModel = koinViewModel(),
) {

    val uiState by registerFormativeFieldsViewModel.uiState.collectAsStateWithLifecycle()
    
    LaunchedEffect(Unit) {
        registerFormativeFieldsViewModel.getListWorkType()
    }
    LaunchedEffect(uiState.uiState) {
        if (uiState.uiState == ModelStateUIEnum.SUCCESS) navController.popBackStack()
    }

    LaunchedEffect (uiState.controlToast) {
        if (uiState.controlToast.showToast) sharedViewModel.modifyShowToast( uiState.controlToast)
        registerFormativeFieldsViewModel.modifyShowToast(false)
    }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.margin_outer))
    ) {
        val (header, body, column, action) = createRefs()

        Column(
            modifier = Modifier.constrainAs(header) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) { HeaderRegisterSubject(navigate = { navController.popBackStack() }) }

        Column(
            modifier = Modifier.constrainAs(body) {
                top.linkTo(header.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
            BodyRegisterSubject(
                uiState = uiState,
                onSubjectChanged = { registerFormativeFieldsViewModel.onSubjectChanged(it) },
                onOptionsChanged = { registerFormativeFieldsViewModel.onOptionsChanged(it) }
            )
        }

        Column(
            modifier = Modifier.constrainAs(column) {
                top.linkTo(body.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(action.top)
                height = Dimension.fillToConstraints
            }) {
            if (uiState.options.valueText.isNotEmpty() && uiState.options.valueText.toInt() > 0) {
                ColumnRegisterSubject(
                    uiState = uiState,
                    onNameChange = { registerFormativeFieldsViewModel.onNameChange(it) },
                    onPercentChange = { registerFormativeFieldsViewModel.onPercentChange(it) }
                )
            }
        }

        Column(
            modifier = Modifier.constrainAs(action) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
            ActionRegisterSubject { registerFormativeFieldsViewModel.validateFieldsCompose() }
        }
    }
    LoadingAnimation(uiState.uiState == ModelStateUIEnum.LOADING)
}

/**
 * The header of the Subject Registration screen.
 *
 * @param navigate A lambda to be invoked when the back button is clicked.
 */
@Composable
private fun HeaderRegisterSubject(
    navigate: () -> Unit,
) {
    ComponentHeaderBack(
        title = stringResource(R.string.register_subject_name),
        body = stringResource(R.string.register_subject_name_description),
        onReturnClick = { navigate() })
}

/**
 * The body of the Subject Registration screen.
 *
 * @param uiState The UI state for the screen.
 * @param onSubjectChanged A lambda to be invoked when the subject name changes.
 * @param onOptionsChanged A lambda to be invoked when the number of options changes.
 */
@Composable
private fun BodyRegisterSubject(
    uiState: ModelRegisterSubjectStateUI,
    onSubjectChanged: (ModelStateOutFieldText) -> Unit,
    onOptionsChanged: (String) -> Unit,
) {
    BoxEditTextGeneric(
        modelText = uiState.subject,
        enable = true,
        label = stringResource(id = R.string.form_subject_field),
        regex = COMPLEX_TEXT,
        onBoxChanged = {onSubjectChanged(it)}
    )

    CustomSpace(dimensionResource(R.dimen.margin_between))

    TextBody(stringResource(R.string.register_subject_name_description_2))

    CustomSpace(dimensionResource(R.dimen.margin_divided))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.margin_divided))
    ) {
        Column(
            modifier = Modifier.weight(5f)
        ) {
            CustomSpace(dimensionResource(R.dimen.margin_outer))
            TextBody(stringResource(R.string.register_subject_name_description_3))
        }

        Box(
            modifier = Modifier.weight(4f)
        ) {
            SpinnerTextField(
                options = uiState.listOptions,
                selectedOption = uiState.options,
                read = uiState.read,
                label = stringResource(id = R.string.form_subject_options),
                onOptionSelected = { onOptionsChanged(it.value!!) }
            )
        }
    }
    CustomSpace(dimensionResource(R.dimen.margin_between))
}

/**
 * The column of the Subject Registration screen.
 *
 * @param uiState The UI state for the screen.
 * @param onNameChange A lambda to be invoked when the name of an item changes.
 * @param onPercentChange A lambda to be invoked when the percentage of an item changes.
 */
@Composable
private fun ColumnRegisterSubject(
    uiState: ModelRegisterSubjectStateUI,
    onNameChange: (Pair<ModelWorkTypeData?, Int>) -> Unit,
    onPercentChange: (Pair<ModelStateOutFieldText, Int>) -> Unit,
) {
    EvaluationPercentList(
        listWorkMethods = uiState.listWorkMethods,
        items = uiState.listAdapter!!,
        onNameChange = { onNameChange(it) },
        onPercentChange = { onPercentChange(it) }
    )
}

/**
 * The action button of the Subject Registration screen.
 *
 * @param validateFieldsCompose A lambda to be invoked when the action button is clicked.
 */
@Composable
private fun ActionRegisterSubject(
    validateFieldsCompose: () -> Unit,
) {
    ButtonAction(
        containerColor = colorAction,
        text = stringResource(R.string.add_button),
        onActionClick = { validateFieldsCompose() }
    )
    CustomSpace(dimensionResource(R.dimen.margin_divided))
}

@Preview(showBackground = true)
@Composable()
private fun RegisterFormativeFieldsPreview(){
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.margin_outer))
    ) {
        val (header, body, column, action) = createRefs()

        Column(
            modifier = Modifier.constrainAs(header) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) { HeaderRegisterSubject(navigate = {} )}

        Column(
            modifier = Modifier.constrainAs(body) {
                top.linkTo(header.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
            BodyRegisterSubject(
                uiState = ModelRegisterSubjectStateUI(),
                onSubjectChanged = {  },
                onOptionsChanged = { }
            )
        }

        Column(
            modifier = Modifier.constrainAs(column) {
                top.linkTo(body.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(action.top)
                height = Dimension.fillToConstraints
            }) {

            ColumnRegisterSubject(
                uiState = ModelRegisterSubjectStateUI(),
                onNameChange = { },
                onPercentChange = { }
            )

        }

        Column(
            modifier = Modifier.constrainAs(action) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
            ActionRegisterSubject { }
        }
    }
}
