package com.dapm.fitvision.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dapm.fitvision.R
import com.dapm.fitvision.navigation.AppScreens

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SelectSexScreen(navController: NavController) {
    Scaffold {
        SelectSexBodyComponent(navController)
    }
}

@Composable
fun SelectSexBodyComponent(navController: NavController) {
    var selectedSex by remember { mutableStateOf<String?>(null) }
    var showError by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 40.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(120.dp))
            SexTextComponent()

            Spacer(modifier = Modifier.height(30.dp))

            SexoOptionButton(
                imagenId = R.drawable.hombre_img,
                texto = "HOMBRE",
                isSelected = selectedSex == "HOMBRE",
                onClick = {
                    selectedSex = "HOMBRE"
                    showError = false
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            SexoOptionButton(
                imagenId = R.drawable.mujer_img,
                texto = "MUJER",
                isSelected = selectedSex == "MUJER",
                onClick = {
                    selectedSex = "MUJER"
                    showError = false
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            ContinueButtonComponent(
                selectedSex = selectedSex,
                showError = showError,
                onContinue = {
                    if (selectedSex != null) {
                        navController.navigate(route = AppScreens.CaptureScreen.route)
                    } else {
                        showError = true
                    }
                }
            )
        }
    }
}

@Composable
fun SexTextComponent() {
    Text(
        text = "Selecciona tu sexo",
        fontSize = 36.sp,
        fontWeight = FontWeight.Medium,
        color = Color.White,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(horizontal = 10.dp)
    )
}

@Composable
fun SexoOptionButton(
    imagenId: Int,
    texto: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) Color.Red else Color.Transparent

    Box(
        modifier = Modifier
            .width(280.dp)
            .height(170.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(2.dp, borderColor, shape = RoundedCornerShape(16.dp))
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(id = imagenId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        Text(
            text = texto,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp)
        )
    }
}

@Composable
fun ContinueButtonComponent(
    selectedSex: String?,
    showError: Boolean,
    onContinue: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (showError) {
            Text(
                text = "Selecciona tu sexo",
                color = Color.Red,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }

        Button(
            onClick = { onContinue() },
            enabled = selectedSex != null,
            modifier = Modifier
                .padding(bottom = 70.dp)
                .width(300.dp)
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedSex != null) Color(0xFF1E2E78) else Color.Gray,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Continuar",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}