package com.dapm.fitvision.screens

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dapm.fitvision.navigation.AppScreens
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoadingScreen(navController: NavController) {
    Scaffold {
        LoadingBodyComponent(navController)
    }
}

@Composable
fun LoadingBodyComponent(navController: NavController) {
    var progress by remember { mutableStateOf(0) }
    var completed by remember { mutableStateOf(false) }

    // Simulación del progreso
    LaunchedEffect(Unit) {
        while (progress < 100) {
            delay(50)
            progress += 2
        }
        completed = true
        delay(800) // Pequeña pausa tras el 100%
        navController.navigate(AppScreens.ResultScreen.route)
    }

    val circleColor by animateColorAsState(
        targetValue = if (completed) Color(0xFF1E2E78) else Color.White,
        label = "circleColor"
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 40.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (!completed) "Procesando datos..." else "Proceso Completado",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = if (!completed) "Calculando somatotipo" else "Somatotipo calculado",
                color = Color.White,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(160.dp)
                    .background(color = Color.Transparent)
            ) {
                androidx.compose.foundation.Canvas(
                    modifier = Modifier.size(160.dp)
                ) {
                    drawCircle(
                        color = circleColor,
                        radius = size.minDimension / 2,
                        style = androidx.compose.ui.graphics.drawscope.Stroke(width = 6.dp.toPx())
                    )
                }

                Text(
                    text = "$progress%",
                    color = circleColor,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}