package com.mx.liftechnology.registroeducativo.main.ui.activityMain.register.partial

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.ui.components.ButtonAction
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentHeaderBack
import com.mx.liftechnology.registroeducativo.main.ui.components.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.components.TextBody
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_action
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterPartialScreen(
    navController: NavHostController,
    registerPartialViewModel: RegisterPartialViewModel = koinViewModel(),
) {

    val uiState by registerPartialViewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.margin_outer))
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        )
        {
            ComponentHeaderBack(
                title = stringResource(R.string.register_partial),
                body = stringResource(R.string.register_subject_name_description_2),
            ) { navController.popBackStack() }


            CustomSpace(dimensionResource(R.dimen.margin_between))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.margin_divided))
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    CustomSpace(dimensionResource(R.dimen.margin_outer))
                    TextBody(stringResource(R.string.register_partial_number_period))
                }

                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    /*SpinnerOutlinedTextField(
                        options = uiState.listOptions,
                        selectedOption = uiState.options,
                        read = uiState.read,
                        label = stringResource(id = R.string.register_subject_options),
                        error = uiState.isErrorSubject,
                        onOptionSelected = { registerSubjectViewModel.onOptionsChanged(it)}
                    )*/
                }
            }

            CustomSpace(dimensionResource(R.dimen.margin_between))

            /*if(uiState.options.isNotEmpty() && uiState.options.toInt() > 0){
                EvaluationPercentList(
                    listWorkMethods = uiState.listWorkMethods,
                    items = uiState.listAdapter!!,

                    onNameChange = { registerSubjectViewModel.onNameChange(it)},
                    onPercentChange = { registerSubjectViewModel.onPercentChange(it)})
            }*/

            Spacer(modifier = Modifier.weight(1f))

            ButtonAction(
                containerColor = color_action,
                text = stringResource(R.string.add_button),
                onActionClick = {
                //    registerSubjectViewModel.validateFieldsCompose()
                }
            )
            CustomSpace(dimensionResource(R.dimen.margin_divided))
        }
        LoadingAnimation(uiState.isLoading)
    }
}