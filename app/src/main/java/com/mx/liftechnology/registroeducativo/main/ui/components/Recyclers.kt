package com.mx.liftechnology.registroeducativo.main.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mx.liftechnology.data.model.ModelPrincipalMenuData
import com.mx.liftechnology.data.model.ModelWorkTypeData
import com.mx.liftechnology.domain.model.ModelDatePeriodDomain
import com.mx.liftechnology.domain.model.formativeFields.ModelFormatFormativeFieldsDomain
import com.mx.liftechnology.domain.model.formativeFields.ModelSpinnersWorkMethods
import com.mx.liftechnology.domain.model.menu.ModelDialogGroupPartialDomain
import com.mx.liftechnology.domain.model.menu.ModelDialogStudentGroupDomain
import com.mx.liftechnology.domain.util.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelCustomCardStudent
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorTransparent
import java.time.LocalDate

/**
 * A composable function for previewing the lists in this file.
 */
@Preview(showBackground = true)
@Composable
fun MyGridScreenPreview() {
    val items = listOf(
        ModelPrincipalMenuData(
            id = "hola",
            image = R.drawable.ic_launcher_foreground,
            titleCard = "text"
        ),
        ModelPrincipalMenuData(
            id = "hola",
            image = R.drawable.ic_launcher_foreground,
            titleCard = "text"
        ),
        ModelPrincipalMenuData(
            id = "hola",
            image = R.drawable.ic_launcher_foreground,
            titleCard = "text"
        ),
        ModelPrincipalMenuData(
            id = "hola",
            image = R.drawable.ic_launcher_foreground,
            titleCard = "text"
        ),
    )

    val items2 = listOf(
        ModelDialogStudentGroupDomain(
            selected = false,
            item = null,
            nameItem = "Hola mundo",
            listItemPartial = null,
            itemPartial = null,
            namePartial = null
        ),
        ModelDialogStudentGroupDomain(
            selected = true,
            item = null,
            nameItem = "Hola mundo",
            listItemPartial = null,
            itemPartial = null,
            namePartial = null
        ),
    )

    val items3 = listOf(ModelFormatFormativeFieldsDomain(1,"hola","mundo",1))

    Column {
        MyGridScreen(items, 410.dp) {}
        DialogGroupList(items2) {}
    }

}

/**
 * A screen that displays a grid of items.
 *
 * @param items The list of items to display.
 * @param height The height of the grid.
 * @param onItemClick A lambda to be invoked when an item is clicked.
 */
@Composable
fun MyGridScreen(
    items: List<ModelPrincipalMenuData>,
    height: Dp,
    onItemClick: (ModelPrincipalMenuData) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(color = colorTransparent),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        userScrollEnabled = false
    ) {
        items(items.size) { index ->
            GridItem(items[index]) { selectedItem ->
                onItemClick(selectedItem)
            }
        }
    }
}

/**
 * A list of group items for a dialog.
 *
 * @param items The list of group items to display.
 * @param onItemSelected A lambda to be invoked when an item is selected.
 */
@Composable
fun DialogGroupList(
    items: List<ModelDialogStudentGroupDomain>,
    onItemSelected: (ModelDialogStudentGroupDomain) -> Unit,
) {
    var selectedIndex by remember { mutableIntStateOf(-1) }

    LazyColumn {
        itemsIndexed(items) { index, item ->
            DialogGroupItem(
                text = "${item.item?.name.orEmpty()}",
                isSelected = index == selectedIndex,
                onSelected = {
                    selectedIndex = index
                    onItemSelected(item.copy(selected = true))
                }
            )
        }
    }
}

/**
 * A list of partial items for a dialog.
 *
 * @param items The list of partial items to display.
 * @param onItemSelected A lambda to be invoked when an item is selected.
 */
@Composable
fun DialogPartialList(
    items: List<ModelDialogGroupPartialDomain>?,
    onItemSelected: (ModelDialogGroupPartialDomain) -> Unit,
) {
    var selectedIndex by remember { mutableIntStateOf(-1) }

    LazyColumn {
        items?.let {
            itemsIndexed(items) { index, item ->

                DialogGroupItem(
                    text = "${item.name}",
                    isSelected = index == selectedIndex,
                    onSelected = {
                        selectedIndex = index
                        onItemSelected(item)
                    }
                )
            }
        }

    }
}

/**
 * A list of evaluation percentage items.
 *
 * @param listWorkMethods The list of work methods to choose from.
 * @param items The list of evaluation percentage items to display.
 * @param onNameChange A lambda to be invoked when the name of an item changes.
 * @param onPercentChange A lambda to be invoked when the percentage of an item changes.
 */
@Composable
fun EvaluationPercentList(
    listWorkMethods :List<ModelWorkTypeData?>,
    items: List<ModelSpinnersWorkMethods>,
    onNameChange:(Pair<ModelWorkTypeData?, Int>) -> Unit,
    onPercentChange:(Pair<String, Int>) -> Unit,
) {
    LazyColumn {
        itemsIndexed(items) { index, item ->
            EvaluationPercentItem(
                listWorkMethods = listWorkMethods,
                name = item.name,
                percent = item.percent,
                onNameChange = {onNameChange(Pair(it, index)) },
                onPercentChange = {onPercentChange(Pair(it, index))}
            )
        }
    }
}

/**
 * A list of student evaluation items.
 *
 * @param items The list of student evaluation items to display.
 * @param onScoreChange A lambda to be invoked when the score of an item changes.
 */
@Composable
fun EvaluationStudentList(
    items: List<ModelCustomCardStudent>,
    onScoreChange:(Pair<String, String>) -> Unit,
) {
    LazyColumn (
        Modifier.fillMaxHeight()
    ){
        itemsIndexed(items) { index, item ->
            EvaluationStudentItem(
                nameStudent = item.studentName.stringToModelStateOutFieldText(),
                score = item.score,
                onScoreChange =  {onScoreChange(Pair(item.id, it))},
            )
        }
    }
}

/**
 * A list for registering partials.
 *
 * @param items The list of date periods to display.
 * @param onDateChange A lambda to be invoked when the date of an item changes.
 */
@Composable
fun RegisterPartialList(
    items: List<ModelDatePeriodDomain>,
    isActive: Boolean,
    onDateChange:(Pair<Pair<LocalDate?, LocalDate?>, Int>) -> Unit
) {
    LazyColumn (){
        itemsIndexed(items) { index, item ->
            RegisterPartialListItem(
                index = index,
                date = item,
                isActive = isActive,
                onDateChange = {onDateChange(Pair(it, index)) },
            )
        }
    }
}