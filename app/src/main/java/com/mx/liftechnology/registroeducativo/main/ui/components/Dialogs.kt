package com.mx.liftechnology.registroeducativo.main.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mx.liftechnology.domain.model.menu.ModelDialogStudentGroupDomain
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelMenuUIState
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_action

@Preview(showBackground = true)
@Composable
fun AlertDialogPreview(){
    AlertDialogMenu(ModelMenuUIState(),{}){}
}

@Composable
fun AlertDialogMenu(
    uiState: ModelMenuUIState,
    itemSelectedReturn: (ModelDialogStudentGroupDomain) -> Unit,
    dismiss: () -> Unit
) {
    val openDialog = remember { mutableStateOf(true) }
    var itemSelected = remember { mutableStateOf<ModelDialogStudentGroupDomain?>(null) }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
                dismiss()
            },
            title = { Text("Selecciona tu ciclo escolar") },
            text = {
                DialogGroupList(uiState.studentGroupList) { selectedItem ->
                    itemSelected.value = selectedItem
                }
            },
            confirmButton = {
                ButtonAction(
                    containerColor = color_action,
                    text = stringResource(R.string.select)
                ) {
                    itemSelected.value?.let { itemSelectedReturn(it) }
                    openDialog.value = false
                    dismiss()
                }
            },
            dismissButton = {}
        )
    }
}
