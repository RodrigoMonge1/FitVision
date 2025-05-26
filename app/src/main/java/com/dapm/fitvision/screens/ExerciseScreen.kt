package com.dapm.fitvision.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dapm.fitvision.R
import com.dapm.fitvision.model.Exercise

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ExerciseScreen(somatotipo: String) {
    Scaffold {
        ExerciseBodyComponent(somatotipo)
    }
}

@Composable
fun ExerciseBodyComponent(somatotipo: String) {
    val ejercicios = listOf(
        Exercise(
            nombre = "Flexiones de pecho",
            descripcion = "Ejercicio para desarrollar el pectoral y tríceps.",
            tipo = "Tren superior",
            seriesPorTipo = mapOf(
                "Ectomorfo" to "3 series de 12 repeticiones",
                "Mesomorfo" to "4 series de 15 repeticiones",
                "Endomorfo" to "4 series de 20 repeticiones"
            )
        ),
        Exercise(
            nombre = "Sentadillas",
            descripcion = "Ejercicio para piernas y glúteos.",
            tipo = "Tren inferior",
            seriesPorTipo = mapOf(
                "Ectomorfo" to "3 series de 10 repeticiones",
                "Mesomorfo" to "4 series de 12 repeticiones",
                "Endomorfo" to "5 series de 15 repeticiones"
            )
        ),
        Exercise(
            nombre = "Plancha abdominal",
            descripcion = "Ejercicio isométrico para trabajar el core.",
            tipo = "Core",
            seriesPorTipo = mapOf(
                "Ectomorfo" to "3 series de 30 segundos",
                "Mesomorfo" to "3 series de 45 segundos",
                "Endomorfo" to "4 series de 1 minuto"
            )
        ),
        Exercise(
            nombre = "Elevaciones laterales",
            descripcion = "Ejercicio para deltoides.",
            tipo = "Tren superior",
            seriesPorTipo = mapOf(
                "Ectomorfo" to "3 series de 12 repeticiones",
                "Mesomorfo" to "4 series de 10 repeticiones",
                "Endomorfo" to "3 series de 15 repeticiones"
            )
        ),
        Exercise(
            nombre = "Zancadas",
            descripcion = "Fortalece piernas y glúteos.",
            tipo = "Tren inferior",
            seriesPorTipo = mapOf(
                "Ectomorfo" to "3 series de 10 repeticiones por pierna",
                "Mesomorfo" to "4 series de 12 repeticiones",
                "Endomorfo" to "4 series de 15 repeticiones"
            )
        )
    )

    val tipos = listOf("Todos", "Tren superior", "Tren inferior", "Core")
    var tipoSeleccionado by remember { mutableStateOf("Todos") }

    val ejerciciosFiltrados = ejercicios.filter {
        tipoSeleccionado == "Todos" || it.tipo == tipoSeleccionado
    }

    val scrollFiltros = rememberScrollState()


    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.process_img), // reemplaza con tu imagen de fondo real
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 40.dp, vertical = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Ejercicios para $somatotipo",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // Filtro scroll horizontal
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(scrollFiltros)
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                tipos.forEach { filtro ->
                    val isSelected = tipoSeleccionado == filtro
                    Button(
                        onClick = { tipoSeleccionado = filtro },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelected) Color(0xFF1E2E78) else Color.Gray,
                            contentColor = Color.White
                        )
                    ) {
                        Text(filtro)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Lista con scroll vertical
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                items(ejerciciosFiltrados) { ejercicio ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E2E78))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = ejercicio.nombre,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = ejercicio.descripcion,
                                fontSize = 16.sp,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = ejercicio.seriesPorTipo[somatotipo] ?: "No definido",
                                fontSize = 14.sp,
                                color = Color.LightGray
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ExerciseScreenPreview() {
    ExerciseScreen("Mesomorfo")
}