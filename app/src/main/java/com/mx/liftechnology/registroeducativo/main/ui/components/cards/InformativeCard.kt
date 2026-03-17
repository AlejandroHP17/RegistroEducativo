package com.mx.liftechnology.registroeducativo.main.ui.components.cards

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.share.CustomCard
import com.mx.liftechnology.registroeducativo.main.model.share.ModelComplexCard
import com.mx.liftechnology.registroeducativo.main.model.share.ModelSubComplexCard
import com.mx.liftechnology.registroeducativo.main.model.ui.SpinnerUiCallbacks
import com.mx.liftechnology.registroeducativo.main.model.workType.WotyUiCallbacks
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorAzulLink
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorFail
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorPrincipalText
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorWarning
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorWhite

/**
 * A composable function for previewing the cards in this file.
 */
@Preview(showBackground = true)
@Composable
private fun InformativeCardView() {
    Column {
        CustomCard(
            item = CustomCard(
                id = 1,
                numberList = "1",
                nameCard = "Curp",
            ),
            callbacks = SpinnerUiCallbacks(
                onItemClick = {},
                onEdit = {},
                onDelete = {}
            )
        )

        CustomSpace(dimensionResource(R.dimen.margin_divided))

        ComplexCard(
            item = ModelComplexCard(
                idTitle = 1,
                nameTitle = "1",
                isShowTitle = true,
                isExpandedTitle = true,
                list = null
            ),
            complexCallbacks = WotyUiCallbacks(
                onExpandedTitle = {},
                onExpandedSubTitle = { _, _ -> },
            )
        )
    }
}

/**
 * A custom card component with a title, a number, and a dropdown schoolCycle.
 *
 * @param item The data for the card.
 * @param callbacks The callbacks for the card.
 */
@Composable
fun CustomCard(
    item: CustomCard,
    callbacks: SpinnerUiCallbacks
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(
            bottomStart = 8.dp,
            topEnd = 8.dp,
            bottomEnd = 8.dp,
            topStart = 8.dp
        ),
        colors = CardDefaults.cardColors(containerColor = colorAzulLink)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = (item.numberList ?: "0"),
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
                colors = CardDefaults.cardColors(containerColor = colorWhite),
                onClick = { callbacks.onItemClick(item) }
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

                    if (item.isVisibleMenu) {
                        DropMenu(
                            onEdit = {callbacks.onEdit(item)},
                            onDelete = {callbacks.onDelete(item)},
                        )
                    }
                }
            }
        }
    }
}
@Composable
private fun DropMenu(
    onEdit:() ->Unit,
    onDelete:() ->Unit,
){
    var expanded by remember { mutableStateOf(false) }
    Box {
        Icon(
            painter = painterResource(id = R.drawable.ic_more_vert),
            contentDescription = stringResource(R.string.tools_custom_card_more),
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.touch_google))
                .padding(dimensionResource(id = R.dimen.margin_12dp))
                .clickable { expanded = true }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Editar") },
                onClick = {
                    expanded = false
                    onEdit()
                }
            )
            DropdownMenuItem(
                text = { Text("Eliminar") },
                onClick = {
                    expanded = false
                    onDelete()
                }
            )
        }
    }
}

/**
 * A complex card component with an expandable title and a list of subtitles.
 *
 * @param item The data for the card.
 * @param complexCallbacks The callbacks for the card.
 */
@Composable
fun ComplexCard(
    item: ModelComplexCard?,
    complexCallbacks: WotyUiCallbacks,
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(
            bottomStart = 8.dp,
            topEnd = 8.dp,
            bottomEnd = 8.dp,
            topStart = 8.dp
        ),
        colors = CardDefaults.cardColors(containerColor = colorAzulLink)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.margin_8dp)))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(bottom = dimensionResource(id = R.dimen.margin_divided)),
                shape = RoundedCornerShape(
                    bottomStart = 8.dp,
                    topEnd = 8.dp

                ),
                colors = CardDefaults.cardColors(containerColor = colorWhite),
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .clickable { complexCallbacks.onExpandedTitle(item!!) }
                            .fillMaxWidth()
                            .padding(
                                start = dimensionResource(id = R.dimen.margin_16dp)
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = item?.nameTitle ?: "",
                            fontSize = 16.sp,
                            modifier = Modifier
                                .weight(1f)
                                .padding(top = dimensionResource(id = R.dimen.margin_12dp))
                        )

                        Icon(
                            painter =
                                if (item?.isExpandedTitle == true) painterResource(id = R.drawable.ic_principal_drop_up)
                                else painterResource(id = R.drawable.ic_principal_drop_down),
                            contentDescription = stringResource(R.string.tools_custom_card_more),

                            modifier = Modifier
                                .size(dimensionResource(id = R.dimen.touch_google))
                                .padding(dimensionResource(id = R.dimen.margin_12dp))
                        )
                    }

                    FirstLevelAnimated(
                        item = item,
                        onClick = {
                            complexCallbacks.onExpandedSubTitle(
                                it,
                                item!!
                            )
                        }
                    )
                }
            }
        }
    }
}
@Composable()
private fun FirstLevelAnimated(
    item: ModelComplexCard?,
    onClick:(ModelSubComplexCard) -> Unit
){
    AnimatedVisibility(visible = item?.isExpandedTitle ?: false) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        ) {
            item?.list?.forEach { subItem ->
                Row(
                    modifier = Modifier
                        .clickable {
                            onClick(subItem!!)
                        }
                        .fillMaxWidth()
                        .padding(start = dimensionResource(id = R.dimen.margin_16dp)),
                    horizontalArrangement = Arrangement.SpaceBetween,

                    ) {
                    Text(
                        text = subItem?.nameSubTitle ?: "",
                        modifier = Modifier.padding(8.dp)
                    )
                    Icon(
                        painter =
                            if (subItem?.isExpandedSubTitle == true) painterResource(
                                id = R.drawable.ic_drop_up
                            )
                            else painterResource(id = R.drawable.ic_drop_down),
                        contentDescription = stringResource(R.string.tools_custom_card_more),

                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.touch_google))
                            .padding(dimensionResource(id = R.dimen.margin_16dp))
                    )
                }
                SecondLevelAnimated(subItem)
            }
        }
    }
}
@Composable()
private fun SecondLevelAnimated(subItem: ModelSubComplexCard?) {
    AnimatedVisibility(visible = subItem?.isExpandedSubTitle ?: false) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        ) {
            subItem?.list?.forEach { subSubItem ->
                Row(
                    modifier = Modifier
                        .clickable { }
                        .fillMaxWidth()
                        .padding(start = dimensionResource(id = R.dimen.margin_16dp)),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CompositionLocalProvider(
                        when {
                            subSubItem?.grade == null -> {
                                LocalContentColor provides colorWarning
                            }

                            subSubItem.grade > 6.0 -> {
                                LocalContentColor provides colorPrincipalText
                            }

                            else -> {
                                LocalContentColor provides colorFail
                            }
                        }
                    )
                    {
                        Text(
                            text = subSubItem?.nameDescription ?: "",
                            modifier = Modifier.padding(8.dp)
                        )
                        Text(
                            text = (subSubItem?.grade
                                ?: " — ").toString(),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}
