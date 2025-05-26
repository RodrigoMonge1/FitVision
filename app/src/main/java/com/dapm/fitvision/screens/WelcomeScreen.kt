package com.dapm.fitvision.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dapm.fitvision.R
import com.dapm.fitvision.navigation.AppScreens

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WelcomeScreen(navController: NavController) {
    Scaffold {
        WelcomeBodyComponent(navController)
    }
}

@Composable
fun WelcomeBodyComponent(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.inicio_img), // reemplaza con tu imagen de fondo real
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 60.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            LogoTitleComponent()
            Spacer(modifier = Modifier.weight(1f))
            TextComponent()
            WelcomeButtonComponent(navController)
        }
    }
}

@Composable
fun LogoTitleComponent() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = "Logo FitVision",
            modifier = Modifier.size(54.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Row {
            Text(
                text = "Fit",
                color = Color(0xFF2C3E94), // Azul
                fontSize = 44.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Vision",
                color = Color.White,
                fontSize = 44.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun TextComponent(){
    Text(
        text = "Descubre tu somatotipo\ny entrena de forma inteligente",
        color = Color.White,
        fontSize = 24.sp,
        lineHeight = 28.sp,
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(bottom = 40.dp))
}

@Composable
fun WelcomeButtonComponent(navController: NavController) {
    Button(
        onClick = {navController.navigate(route = AppScreens.SelectSexScreen.route)},
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
            text = "Comenzar análisis",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WelcomeScreenPreview() {
    val navController = rememberNavController()
    WelcomeBodyComponent(navController = navController)
}
