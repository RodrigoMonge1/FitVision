package com.dapm.fitvision.screens

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import com.dapm.fitvision.R
import com.dapm.fitvision.navigation.AppScreens
import java.io.File

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CaptureScreen(navController: NavController){
    Scaffold {
        CaptureBodyComponent(navController)
    }
}

@Composable
fun CaptureBodyComponent(navController: NavController) {
    val context = LocalContext.current
    var capturedImage by remember { mutableStateOf<Bitmap?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            capturedImage = bitmap
            Log.d("CAMERA", "Imagen capturada correctamente")
        } else {
            Log.e("CAMERA", "Error al capturar imagen o cancelado")
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        Log.d("GALERIA", "Imagen seleccionada: $it")
        // Aquí puedes almacenar el URI si necesitas
    }

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
            TextCaptureComponent()
            Spacer(modifier = Modifier.height(20.dp))
            TextDescriptionComponent()
            Spacer(modifier = Modifier.height(40.dp))

            ImageSelectionButtons(
                onCameraClick = { cameraLauncher.launch()},
                onGalleryClick = { galleryLauncher.launch("image/*") }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Mostrar imagen capturada
            capturedImage?.let { bitmap ->
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Imagen tomada",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            CalculateButtonComponent(navController)
        }
    }
}


@Composable
fun TextCaptureComponent(){
    Text(
        text = "Aproximación del somatotipo",
        fontSize = 30.sp,
        fontWeight = FontWeight.ExtraBold,
        color = Color.White,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(top = 110.dp, bottom = 40.dp))
}

@Composable
fun TextDescriptionComponent(){
    Text(text = "Captura o carga una imagen para analizar tu tipo de cuerpo",
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        textAlign = TextAlign.Center)
}

@Composable
fun ImageSelectionButtons(
    onCameraClick: () -> Unit,
    onGalleryClick: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        IconTextButton(
            iconId = R.drawable.ic_camera, // Tu ícono
            text = "Capturar imagen",
            onClick = onCameraClick
        )

        IconTextButton(
            iconId = R.drawable.ic_gallery, // Tu ícono
            text = "Cargar imagen",
            onClick = onGalleryClick
        )
    }
}

@Composable
fun IconTextButton(iconId: Int, text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF7D87A6),
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = null,
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text)
        }
    }
}

@Composable
fun CalculateButtonComponent(navController: NavController) {
    Button(
        onClick = {navController.navigate(route = AppScreens.LoadingScreen.route)},
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
            text = "Calcular somatotipo",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
fun TextPreview(){
    TextCaptureComponent()
}