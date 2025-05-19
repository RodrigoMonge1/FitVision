package com.dapm.fitvision.screens

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
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
import androidx.compose.ui.platform.LocalContext
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
fun CaptureScreen(navController: NavController) {
    Scaffold {
        CaptureBodyComponent(navController)
    }
}

@Composable
fun CaptureBodyComponent(navController: NavController) {
    val context = LocalContext.current
    var capturedImage by remember { mutableStateOf<Bitmap?>(null) }
    var showError by remember { mutableStateOf(false) }

    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            capturedImage = bitmap
            showError = false
            Log.d("CAMERA", "Imagen capturada correctamente")
        } else {
            Log.e("CAMERA", "Error al capturar imagen o cancelado")
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            val bitmap = uriToBitmap(context, uri)
            capturedImage = bitmap
            showError = false
            Log.d("GALERIA", "Imagen seleccionada correctamente")
        } else {
            Log.e("GALERIA", "Error al seleccionar imagen o cancelado")
        }
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
                onCameraClick = { cameraLauncher.launch() },
                onGalleryClick = { galleryLauncher.launch("image/*") }
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (capturedImage != null) {
                Text(
                    text = "Imagen cargada con éxito",
                    color = Color.Green,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Mostrar error si no hay imagen al hacer clic
            if (showError) {
                Text(
                    text = "Debe subir una imagen de cuerpo completo antes de continuar",
                    color = Color.Red,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            CalculateButtonComponent(
                capturedImage = capturedImage,
                showError = showError,
                setShowError = { showError = it },
                navController = navController
            )
        }
    }
}

@Composable
fun TextCaptureComponent() {
    Text(
        text = "Aproximación del somatotipo",
        fontSize = 30.sp,
        fontWeight = FontWeight.ExtraBold,
        color = Color.White,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(top = 110.dp, bottom = 40.dp)
    )
}

@Composable
fun TextDescriptionComponent() {
    Text(
        text = "Captura o carga una imagen para analizar tu tipo de cuerpo",
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        textAlign = TextAlign.Center
    )
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
fun CalculateButtonComponent(
    capturedImage: Bitmap?,
    showError: Boolean,
    setShowError: (Boolean) -> Unit,
    navController: NavController
) {
    Button(
        onClick = {
            if (capturedImage != null) {
                navController.navigate(route = AppScreens.LoadingScreen.route)
            } else {
                setShowError(true)
            }
        },
        modifier = Modifier
            .padding(bottom = 70.dp)
            .width(300.dp)
            .height(56.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF1E2E78),
            contentColor = Color.White
        )
    ) {
        Text(
            text = "Calcular somatotipo",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

fun uriToBitmap(context: android.content.Context, uri: android.net.Uri): Bitmap? {
    return try {
        val stream = context.contentResolver.openInputStream(uri)
        android.graphics.BitmapFactory.decodeStream(stream)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

@Preview
@Composable
fun TextPreview() {
    TextCaptureComponent()
}