package com.mx.liftechnology.registroeducativo.main.ui.components

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
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.control.ModelMenuControlState
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_action

@Preview(showBackground = true)
@Composable
fun AlertDialogPreview(){
    AlertDialogMenu(ModelMenuControlState(),{},{},false){}
}


@Composable
fun AlertDialogMenu(
    controlState: ModelMenuControlState,
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
                    DialogGroupList(controlState.studentGroupList) { selectedItem ->
                        itemSelected.value = selectedItem
                    }
                },
                confirmButton = {
                        ButtonAction(
                            containerColor = color_action,
                            text = stringResource(R.string.next)
                        ) {
                            itemSelected.value?.let { itemSelectedReturn(it) }
                        }

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
                    DialogPartialList(controlState.studentGroupItem.listItemPartial) { selectedItem ->
                        itemPartialSelected.value = selectedItem
                    }
                },
                confirmButton = {
                        ButtonAction(
                            containerColor = color_action,
                            text = stringResource(R.string.select)
                        ) {
                            itemPartialSelected.value?.let { itemSelectedPartialReturn(it) }
                            openDialog.value = false
                            dismiss()
                        }

                },
                dismissButton = {}
            )
        }



    }
}