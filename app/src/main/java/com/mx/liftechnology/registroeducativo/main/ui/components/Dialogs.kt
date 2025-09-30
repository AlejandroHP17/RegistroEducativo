package com.mx.liftechnology.registroeducativo.main.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mx.liftechnology.domain.model.menu.ModelDialogGroupPartialDomain
import com.mx.liftechnology.domain.model.menu.ModelDialogStudentGroupDomain
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelMenuDialogUI
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorAction
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorError
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorSuccess

@Preview(showBackground = true)
@Composable
fun AlertDialogPreview(){
    Column {
        AlertDialogMenu(ModelMenuDialogUI(),{},{},false){}
        CustomSpace(16.dp)
        AlertDialogConfirm({},{})
    }

}

@Composable
fun AlertDialogConfirm(
    itemSelectedReturn: (Boolean) -> Unit,
    dismiss: () -> Unit
){
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {

        AlertDialog(
            modifier = Modifier.padding(horizontal = 0.dp),
            onDismissRequest = {
                openDialog.value = false
                dismiss()
            },
            title = { TextTitleDialog(stringResource(R.string.dialog_wish_continue)) },
            text = { TextBody(stringResource(R.string.dialog_wish_continue_description)) },
            confirmButton = {
                ButtonAction(
                    containerColor = colorSuccess,
                    text = stringResource(R.string.save),
                    onActionClick = {
                        itemSelectedReturn(true)
                        openDialog.value = false
                        dismiss()
                    }
                )
            },
            dismissButton = {
                ButtonAction(
                    containerColor = colorError,
                    text = stringResource(R.string.cancel),
                    onActionClick = {
                        itemSelectedReturn(false)
                        openDialog.value = false
                        dismiss()
                    }
                )
            }
        )
    }
}


@Composable
fun AlertDialogMenu(
    uiDialog: ModelMenuDialogUI,
    itemSelectedReturn: (ModelDialogStudentGroupDomain) -> Unit,
    itemSelectedPartialReturn: (ModelDialogGroupPartialDomain?) -> Unit,
    selectType : Boolean,
    dismiss: () -> Unit
) {
    val openDialog = remember { mutableStateOf(true) }
    val itemSelected = remember { mutableStateOf<ModelDialogStudentGroupDomain?>(null) }
    val itemPartialSelected = remember { mutableStateOf<ModelDialogGroupPartialDomain?>(null) }

    if (openDialog.value) {

        if (selectType){
            AlertDialog(
                modifier = Modifier.padding(horizontal = 0.dp),
                onDismissRequest = {
                    openDialog.value = false
                    dismiss()
                },
                title = {TextTitleDialog("Selecciona tu ciclo escolar") },
                text = {
                    DialogGroupList(uiDialog.studentGroupList) { selectedItem ->
                        itemSelected.value = selectedItem
                    }
                },
                confirmButton = {
                    ButtonAction(
                        containerColor = colorAction,
                        text = stringResource(R.string.next),
                        onActionClick = {
                            itemSelected.value?.let { itemSelectedReturn(it) }
                        }
                    )
                },
                dismissButton = {}
            )
        }
        else {
            AlertDialog(
                modifier = Modifier.padding(horizontal = 0.dp),
                onDismissRequest = {
                    openDialog.value = false
                    dismiss()
                },
                title = { TextTitleDialog("Selecciona tu Parcial")
                },
                text = {
                    DialogPartialList(uiDialog.studentGroupItem.listItemPartial) { selectedItem ->
                        itemPartialSelected.value = selectedItem
                    }
                },
                confirmButton = {
                    ButtonAction(
                        containerColor = colorAction,
                        text = stringResource(R.string.select),
                        onActionClick = {
                            itemPartialSelected.value?.let { itemSelectedPartialReturn(it) }
                            openDialog.value = false
                            dismiss()
                        }
                    )

                },
                dismissButton = {}
            )
        }
    }
}