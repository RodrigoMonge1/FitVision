package com.dapm.fitvision.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ResultScreen(somatotipo: String) {
    // Características según el somatotipo
    val caracteristicas = when (somatotipo) {
        "Ectomorfo" -> listOf(
            "Metabolismo muy rápido",
            "Delgado y lineal",
            "Poca grasa corporal"
        )
        "Mesomorfo" -> listOf(
            "Musculatura desarrollada",
            "Fácil ganancia de masa",
            "Cintura delgada"
        )
        "Endomorfo" -> listOf(
            "Tendencia a acumular grasa",
            "Cuerpo ancho",
            "Dificultad para definir músculos"
        )
        else -> listOf(
            "No se pudo identificar el somatotipo",
            "Intenta nuevamente"
        )
    }

    Scaffold {
        ResultBodyComponent(
            somatotipo = somatotipo,
            caracteristicas = caracteristicas
        )
    }
}

@Composable
fun ResultBodyComponent(
    somatotipo: String,
    caracteristicas: List<String>
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 40.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Tu somatotipo es",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 100.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = somatotipo,
                color = Color(0xFF1E2E78),
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(36.dp))

            Text(
                text = "Características",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            caracteristicas.forEach {
                Text(
                    text = "• $it",
                    color = Color.White,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { /* Acción futura */ },
                modifier = Modifier
                    .padding(bottom = 30.dp)
                    .width(260.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1E2E78),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Ver ejercicios",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResultPreview() {
    ResultBodyComponent(
        somatotipo = "Mesomorfo",
        caracteristicas = listOf(
            "Musculatura desarrollada",
            "Fácil ganancia de masa",
            "Cintura delgada"
        )
    )
}
