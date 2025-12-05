package com.mx.liftechnology.registroeducativo.main.ui.control

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorPrincipalText
import org.koin.androidx.compose.koinViewModel

/**
 * Datos de los botones de servicios disponibles.
 */
data class ApiServiceButton(
    val title: String,
    val description: String,
    val parameterHint: String,
    val onClick: () -> Unit
)

/**
 * Pantalla de control de APIs.
 * Permite consumir servicios y visualizar el JSON de respuesta.
 *
 * @param navController El controlador de navegación.
 * @param viewModel El ViewModel para esta pantalla.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
@Composable
fun ControlScreen(
    navController: NavHostController,
    viewModel: ControlViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Título
        Text(
            text = "Control de APIs",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = colorPrincipalText,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Campo de texto para parámetros
        OutlinedTextField(
            value = uiState.parameterText,
            onValueChange = { viewModel.updateParameterText(it) },
            label = { Text("Parámetros (opcional)") },
            placeholder = { Text("Ej: 1 o 1,2,3,4,5") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = colorPrincipalText,
                unfocusedTextColor = colorPrincipalText
            )
        )

        ApiServiceButton(
            title = "Crear Codigo de registro",
            description = "Nuevo codigo para registro",
            parameterHint = "No requiere parámetros",
            onClick = { viewModel.callNewCode() }
        )

        // Lista de botones de servicios
        Text(
            text = "Servicios disponibles:",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = colorPrincipalText,
            modifier = Modifier.padding(top = 8.dp)
        )

        val services = listOf(
            ApiServiceButton(
                title = "Obtener Datos de Usuario",
                description = "getUserData()",
                parameterHint = "No requiere parámetros",
                onClick = { viewModel.callGetUserData() }
            ),
            ApiServiceButton(
                title = "Obtener Estudiantes",
                description = "getStudents(cycleSchoolId)",
                parameterHint = "ID del ciclo escolar (ej: 1)",
                onClick = { viewModel.callGetStudents() }
            ),
            ApiServiceButton(
                title = "Obtener Campos Formativos",
                description = "getFormativeFields(cycleSchoolId)",
                parameterHint = "ID del ciclo escolar (ej: 1)",
                onClick = { viewModel.callGetFormativeFields() }
            ),
            ApiServiceButton(
                title = "Obtener Parciales",
                description = "getPartials(schoolCycleId)",
                parameterHint = "ID del ciclo escolar (ej: 1)",
                onClick = { viewModel.callGetPartials() }
            ),
            ApiServiceButton(
                title = "Obtener CCT de Escuela",
                description = "getCct(cct)",
                parameterHint = "CCT de la escuela (ej: TEST123456)",
                onClick = { viewModel.callGetCct() }
            ),
            ApiServiceButton(
                title = "Obtener Ciclos Escolares",
                description = "getSchoolCycles(teacherId)",
                parameterHint = "ID del profesor (ej: 1)",
                onClick = { viewModel.callGetSchoolCycles() }
            ),
            ApiServiceButton(
                title = "Obtener Tipos de Trabajo",
                description = "getWorkTypes(teacherId)",
                parameterHint = "ID del profesor (ej: 1)",
                onClick = { viewModel.callGetWorkTypes() }
            ),
            ApiServiceButton(
                title = "Tipos de Trabajo por Campo",
                description = "getWorkTypesByFormativeField(formativeFieldId)",
                parameterHint = "ID del campo formativo (ej: 1)",
                onClick = { viewModel.callGetWorkTypesByFormativeField() }
            ),
            ApiServiceButton(
                title = "Obtener Evaluaciones",
                description = "getEvaluations(...)",
                parameterHint = "schoolCycleId,partialId,formativeFieldId,workTypeId,studentId",
                onClick = { viewModel.callGetEvaluations() }
            ),
            ApiServiceButton(
                title = "Obtener WotyFofi",
                description = "getWotyFofi(schoolCycleId)",
                parameterHint = "ID del ciclo escolar (ej: 1)",
                onClick = { viewModel.callGetWotyFofi() }
            )
        )

        LazyColumn(
            modifier = Modifier
                .weight(0.4f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(services) { service ->
                ServiceButton(
                    service = service,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // Área de respuesta JSON
        Column(
            modifier = Modifier
                .weight(0.6f)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Respuesta JSON:",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorPrincipalText
                )
                TextButton(onClick = { viewModel.clearResponse() }) {
                    Text("Limpiar")
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF1E1E1E)
                )
            ) {
                if (uiState.responseJson.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp)
                    ) {
                        item {
                            Text(
                                text = uiState.responseJson,
                                color = Color.White,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 12.sp,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "La respuesta aparecerá aquí...",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}


/**
 * Componente de botón para un servicio.
 */
@Composable
private fun ServiceButton(
    service: ApiServiceButton,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = service.onClick,
        modifier = modifier.height(60.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = service.title,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Text(
                text = service.description,
                fontSize = 11.sp,
                color = Color.White.copy(alpha = 0.8f)
            )
            Text(
                text = service.parameterHint,
                fontSize = 10.sp,
                color = Color.White.copy(alpha = 0.6f)
            )
        }
    }
}
