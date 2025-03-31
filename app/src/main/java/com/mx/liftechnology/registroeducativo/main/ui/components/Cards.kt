package com.mx.liftechnology.registroeducativo.main.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.share.ModelCustomCard
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_azul_link
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_white

@Preview(showBackground = true)
@Composable
fun CustomCardView() {
    CustomCard(
        item = ModelCustomCard(
            id = "1",
            numberId = "1",
            nameCard = "Curp",
            ),
        onItemMore = {},
        onItemClick = {}
    )
}

@Composable
fun CustomCard(
    item: ModelCustomCard,
    onItemClick: (ModelCustomCard) -> Unit,
    onItemMore: (ModelCustomCard) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(20),
        colors = CardDefaults.cardColors(containerColor = color_azul_link)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Text(
                text = (item.numberId ?: "0").toString(),
                fontSize = dimensionResource(id = R.dimen.text_size_form).value.sp,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.margin_8dp))
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.dimen_card))
                    .padding(bottom = dimensionResource(id = R.dimen.margin_divided)),
                shape = RoundedCornerShape(
                    bottomStart = 8.dp,
                    topEnd = 8.dp

                ),
                colors = CardDefaults.cardColors(containerColor = color_white),
                onClick = { onItemClick(item) }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = dimensionResource(id = R.dimen.margin_16dp)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = item.nameCard ?: "",
                        fontSize = 16.sp,
                        modifier = Modifier.weight(1f)
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.ic_more_vert),
                        contentDescription = "More Options",
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.touch_google))
                            .padding(dimensionResource(id = R.dimen.margin_12dp))
                            .clickable { onItemMore(item) }
                    )
                }
            }
        }
    }
}

