package com.mx.liftechnology.registroeducativo.main.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.mx.liftechnology.core.network.callapi.ResponseGetListAssessmentType
import com.mx.liftechnology.data.model.ModelPrincipalMenuData
import com.mx.liftechnology.domain.model.ModelDatePeriodDomain
import com.mx.liftechnology.domain.model.menu.ModelDialogStudentGroupDomain
import com.mx.liftechnology.domain.model.subject.ModelFormatSubjectDomain
import com.mx.liftechnology.domain.model.subject.ModelSpinnersWorkMethods
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.share.ModelCustomCardStudent
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_transparent
import java.time.LocalDate

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
        ),
        ModelDialogStudentGroupDomain(
            selected = true,
            item = null,
            nameItem = "Hola mundo",
        ),
    )

    val items3 = listOf(ModelFormatSubjectDomain(1,"hola","mundo",1))

    Column {
        MyGridScreen(items, 410.dp) {}
        DialogGroupList(items2) {}
        //EvaluationPercentList(null, items3,{},{})
    }

}

@Composable
fun MyGridScreen(
    items: List<ModelPrincipalMenuData>,
    height: Dp,
    onItemClick: (ModelPrincipalMenuData) -> Unit,
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // Grid de 2 columnas
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(color = color_transparent),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        userScrollEnabled = false
    ) {
        items(items.size) { index ->  // Pasamos el índice
            GridItem(items[index]) { selectedItem ->
                onItemClick(selectedItem)
            } // Pasamos el recurso individualmente
        }
    }
}


@Composable
fun DialogGroupList(
    items: List<ModelDialogStudentGroupDomain>,
    onItemSelected: (ModelDialogStudentGroupDomain) -> Unit,
) {
    var selectedIndex by remember { mutableIntStateOf(-1) }

    LazyColumn(
        modifier = Modifier.padding(8.dp)
    ) {
        itemsIndexed(items) { index, item ->
            DialogGroupItem(
                item = item,
                isSelected = index == selectedIndex,
                onSelected = {
                    selectedIndex = index
                    onItemSelected(item.copy(selected = true))
                }
            )
        }
    }
}


@Composable
fun EvaluationPercentList(
    listWorkMethods :List<ResponseGetListAssessmentType?>,
    items: List<ModelSpinnersWorkMethods>,
    onNameChange:(Pair<ResponseGetListAssessmentType?, Int>) -> Unit,
    onPercentChange:(Pair<String, Int>) -> Unit,
) {
    LazyColumn {
        itemsIndexed(items) { index, item ->
            EvaluationPercentItem(
                listWorkMethods = listWorkMethods,
                name = item.name?: "vacio",
                percent = item.percent ?: "0",
                isErrorName = item.isErrorName,
                isErrorPercent = item.isErrorPercent,
                onNameChange = {onNameChange(Pair(it, index)) },
                onPercentChange = {onPercentChange(Pair(it, index))}
            )
        }
    }
}

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
                nameStudent = item.studentName ?: "Desconocido",
                score = item.score,
                isErrorScore = item.isErrorScore,
                onScoreChange =  {onScoreChange(Pair(item.id, it))},
            )
        }
    }
}

@Composable
fun RegisterPartialList(
    items: List<ModelDatePeriodDomain>,
    onDateChange:(Pair<Pair<LocalDate?, LocalDate?>, Int>) -> Unit
) {
    LazyColumn (){
        itemsIndexed(items) { index, item ->
            RegisterPartialListItem(
                index = index,
                date = item,
                onDateChange = {onDateChange(Pair(it, index)) },
            )
        }
    }
}