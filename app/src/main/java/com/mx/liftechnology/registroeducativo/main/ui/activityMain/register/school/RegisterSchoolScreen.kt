package com.mx.liftechnology.registroeducativo.main.ui.activityMain.register.school

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.ui.components.BoxEditTextGeneric
import com.mx.liftechnology.registroeducativo.main.ui.components.ButtonPair
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentHeaderBack
import com.mx.liftechnology.registroeducativo.main.ui.components.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.components.SpinnerOutlinedTextField
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_action
import org.koin.androidx.compose.koinViewModel

@Preview(showBackground = true)
@Composable
fun PreviewRegisterSchoolScreen() {
    val navController = rememberNavController()
    RegisterSchoolScreen(navController)
}

@Composable
fun RegisterSchoolScreen(
    navController: NavHostController,
    registerSchoolViewModel: RegisterSchoolViewModel = koinViewModel(),
){
    val uiState by registerSchoolViewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            navController.popBackStack()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.margin_outer))
        )
        {

            ComponentHeaderBack(
                title = stringResource(R.string.register_school, "Profesor"),
                body = stringResource(R.string.register_school_description)
            ) {
                navController.popBackStack()
            }

            BoxEditTextGeneric(
                value = uiState.cct,
                enable = true,
                label = stringResource(id = R.string.form_school_cct),
                error = uiState.isErrorCct
            ){
                registerSchoolViewModel.onCctChanged(it)
            }

            BoxEditTextGeneric(
                value = uiState.schoolName,
                enable = false,
                label = stringResource(id = R.string.form_school_name),
                error = uiState.isErrorGeneric
            ){}

            BoxEditTextGeneric(
                value = uiState.shift,
                enable = false,
                label = stringResource(id = R.string.form_school_shift),
                error = uiState.isErrorGeneric
            ){}

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.margin_divided))) {

                Box (
                    modifier = Modifier.weight(1f)
                ){
                    BoxEditTextGeneric(
                        value = uiState.type,
                        enable = false,
                        label = stringResource(id = R.string.form_school_type),
                        error = uiState.isErrorGeneric,
                    ){}
                }

                Box (
                    modifier = Modifier.weight(1f)
                ){

                    SpinnerOutlinedTextField(
                        options = uiState.spinner?.cycle?: emptyList(),
                        selectedOption = uiState.cycle,
                        read = uiState.read,
                        label = stringResource(id = R.string.form_school_term),
                        error = uiState.isErrorCycle,
                        onOptionSelected = { registerSchoolViewModel.onCycleChanged(it) }
                    )

                }
            }

            CustomSpace(dimensionResource(R.dimen.margin_between))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.margin_divided))) {

                Box (
                    modifier = Modifier.weight(1f)
                ){
                    SpinnerOutlinedTextField(
                        options = uiState.spinner?.grade?: emptyList(),
                        selectedOption = uiState.grade,
                        read = uiState.read,
                        label = stringResource(id = R.string.form_school_grade),
                        error = uiState.isErrorGrade,
                        onOptionSelected = { registerSchoolViewModel.onGradeChanged(it) }
                    )
                }

                Box (
                    modifier = Modifier.weight(1f)
                ){
                    SpinnerOutlinedTextField(
                        options = uiState.spinner?.group?: emptyList(),
                        selectedOption = uiState.group,
                        read = uiState.read,
                        label = stringResource(id = R.string.form_school_group),
                        error = uiState.isErrorGroup,
                        onOptionSelected = { registerSchoolViewModel.onGroupChanged(it) }
                    )
                }
            }



            Spacer(modifier = Modifier.weight(1f))

            ButtonPair(
                containerColor = color_action,
                text = stringResource(R.string.add_button) ,
                onActionClick = {registerSchoolViewModel.validateFieldsCompose()},
                onRecordClick = {}
            )
        }

        LoadingAnimation(uiState.isLoading)
    }
}