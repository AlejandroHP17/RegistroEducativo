package com.mx.liftechnology.registroeducativo.main.ui.components.cards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorPrincipalText

/**
 * A composable function for previewing the cards in this file.
 */
@Preview(showBackground = true)
@Composable
private fun SelectCardView() {
    Column {
        DialogGroupItem(
            text = "nombre",
            isSelected = true,
            onSelected = {}
        )
    }
}

/**
 * A dialog item component with a radio button.
 *
 * @param text The text to display.
 * @param isSelected Whether the item is selected.
 * @param onSelected A lambda to be invoked when the item is selected.
 */
@Composable
fun DialogGroupItem(
    text: String,
    isSelected: Boolean,
    onSelected: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelected() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onSelected,
            colors = RadioButtonDefaults.colors(selectedColor = colorPrincipalText)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = text,
            fontSize = 16.sp
        )
    }
}