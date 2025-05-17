package com.dapm.fitvision.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dapm.fitvision.R
import com.dapm.fitvision.navigation.AppScreens

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SelectSexScreen(navController: NavController){
    Scaffold {
        SelectSexBodyComponent(navController)
    }
}

@Composable
fun SelectSexBodyComponent(navController: NavController){
    var selectedSex by remember {mutableStateOf<String?>(null) }

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
            Row (Modifier.padding(top = 120.dp, bottom = 30.dp)){
                SexTextComponent()
            }

            SexoOptionButton(
                imagenId = R.drawable.hombre_img,
                texto = "HOMBRE",
                isSelected = selectedSex == "HOMBRE",
                onClick = { selectedSex = "HOMBRE" }
            )

            Spacer(modifier = Modifier.height(24.dp))

            SexoOptionButton(
                imagenId = R.drawable.mujer_img,
                texto = "MUJER",
                isSelected = selectedSex == "MUJER",
                onClick = { selectedSex = "MUJER" }
            )

            Spacer(modifier = Modifier.weight(1f))

            ContinueButtonComponent(navController)
        }
    }
}


@Composable
fun SexTextComponent(){
        Text(
            text = "Selecciona tu sexo",
            fontSize = 36.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 10.dp))
}

@Composable
fun SexoOptionButton(imagenId: Int, texto: String, isSelected: Boolean, onClick: () -> Unit
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
fun ContinueButtonComponent(navController: NavController) {
    Button(
        onClick = {navController.navigate(route = AppScreens.CaptureScreen.route)},
        modifier = Modifier
            .padding(bottom = 70.dp)
            .width(300.dp)
            .height(56.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF1E2E78), // Color azul oscuro del botón
            contentColor = Color.White // Texto blanco
        )
    ) {
        Text(
            text = "Continuar",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


@Preview
@Composable
fun SexTextPreview(){
    SexTextComponent()
}