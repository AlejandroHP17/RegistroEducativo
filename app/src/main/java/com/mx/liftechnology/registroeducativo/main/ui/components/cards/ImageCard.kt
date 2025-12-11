package com.mx.liftechnology.registroeducativo.main.ui.components.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mx.liftechnology.domain.model.schoolCycle.PrincipalMenuDomain
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.TextDescription
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorWhite

/**
 * A composable function for previewing the cards in this file.
 */
@Preview(showBackground = true)
@Composable
private fun ImageCardView() {
    Column {
        GridItem(
            item = PrincipalMenuDomain(
                id = "1",
                image = com.mx.liftechnology.data.R.drawable.ic_students,
                titleCard = "texto"
            ),
            onItemClick = {}
        )
    }
}


/**
 * A grid item component.
 *
 * @param item The data for the grid item.
 * @param onItemClick A lambda to be invoked when the grid item is clicked.
 */
@Composable
fun GridItem(
    item: PrincipalMenuDomain,
    onItemClick: (PrincipalMenuDomain) -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onItemClick(item) },
        colors = CardDefaults.cardColors(
            containerColor = colorWhite,
            contentColor = colorWhite
        ),
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.margin_between)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = item.image!!),
                contentDescription = null,
                modifier = Modifier.size(150.dp)
            )

            CustomSpace(dimensionResource(id = R.dimen.margin_between))
            TextDescription(item.titleCard!!)
        }
    }
}
