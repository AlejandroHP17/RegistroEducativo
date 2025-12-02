package com.mx.liftechnology.registroeducativo.main.ui.components.feedback

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
import com.mx.liftechnology.domain.model.schoolCycle.DialogGroupPartialDomain
import com.mx.liftechnology.domain.model.schoolCycle.DialogStudentGroupDomain
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.MenuUiDialog
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorAction
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorError
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorSuccess
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.TextTitleDialog
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.TextBody
import com.mx.liftechnology.registroeducativo.main.ui.components.buttons.ButtonAction
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.DialogGroupList
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.DialogPartialList

/**
 * A composable function for previewing the alert dialogs in this file.
 */
@Preview(showBackground = true)
@Composable
fun AlertDialogPreview(){
    Column {
        AlertDialogMenu(MenuUiDialog(),{},{},false){}
        CustomSpace(16.dp)
        AlertDialogConfirm({},{})
    }

}

/**
 * A confirmation dialog.
 *
 * @param itemSelectedReturn A lambda to be invoked when an item is selected.
 * @param dismiss A lambda to be invoked when the dialog is dismissed.
 */
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

/**
 * A schoolCycle dialog.
 *
 * @param uiDialog The state of the dialog.
 * @param itemSelectedReturn A lambda to be invoked when a group item is selected.
 * @param itemSelectedPartialReturn A lambda to be invoked when a partial item is selected.
 * @param selectType Whether the dialog is for selecting a group or a partial.
 * @param dismiss A lambda to be invoked when the dialog is dismissed.
 */
@Composable
fun AlertDialogMenu(
    uiDialog: MenuUiDialog,
    itemSelectedReturn: (DialogStudentGroupDomain) -> Unit,
    itemSelectedPartialReturn: (DialogGroupPartialDomain?) -> Unit,
    selectType : Boolean,
    dismiss: () -> Unit
) {
    val openDialog = remember { mutableStateOf(true) }
    val itemSelected = remember { mutableStateOf<DialogStudentGroupDomain?>(null) }
    val itemPartialSelected = remember { mutableStateOf<DialogGroupPartialDomain?>(null) }

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