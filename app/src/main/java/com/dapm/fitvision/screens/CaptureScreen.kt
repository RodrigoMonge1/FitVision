package com.dapm.fitvision.screens

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dapm.fitvision.R
import com.dapm.fitvision.navigation.AppScreens
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CaptureScreen(navController: NavController, sex: String) {
    Scaffold {
        CaptureBodyComponent(navController, sex)
    }
}

@Composable
fun CaptureBodyComponent(navController: NavController, sex: String) {
    val context = LocalContext.current
    var capturedImage by remember { mutableStateOf<Bitmap?>(null) }
    var showError by remember { mutableStateOf(false) }
    var cameraPermissionGranted by remember { mutableStateOf(false) }

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

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        cameraPermissionGranted = isGranted
        if (isGranted) {
            cameraLauncher.launch()
        } else {
            Toast.makeText(context, "Se requiere permiso de cámara", Toast.LENGTH_SHORT).show()
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

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.capture_img),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 50.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextCaptureComponent()
            Spacer(modifier = Modifier.height(20.dp))
            TextDescriptionComponent()
            Spacer(modifier = Modifier.weight(1f))

            ImageSelectionButtons(
                onCameraClick = {
                    if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED
                    ) {
                        cameraLauncher.launch()
                    } else {
                        permissionLauncher.launch(android.Manifest.permission.CAMERA)
                    }
                },
                onGalleryClick = { galleryLauncher.launch("image/*") }
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (capturedImage != null) {
                Text(
                    text = "Imagen cargada con éxito",
                    color = Color.Green,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(60.dp))

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
                navController = navController,
                sex = sex
            )
        }
    }
}

@Composable
fun TextCaptureComponent() {
    Text(
        text = "Aproximación del somatotipo",
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(top = 70.dp, bottom = 30.dp)
    )
}

@Composable
fun TextDescriptionComponent() {
    Text(
        text = "Captura o carga una imagen para analizar tu tipo de cuerpo",
        fontSize = 24.sp,
        fontWeight = FontWeight.Medium,
        color = Color.White,
        textAlign = TextAlign.Start
    )
}

@Composable
fun ImageSelectionButtons(
    onCameraClick: () -> Unit,
    onGalleryClick: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        IconTextButton(
            iconId = R.drawable.ic_camera,
            text = "Capturar imagen",
            onClick = onCameraClick
        )

        IconTextButton(
            iconId = R.drawable.ic_gallery,
            text = "Cargar imagen",
            onClick = onGalleryClick,
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
    navController: NavController,
    sex: String
) {
    Button(
        onClick = {
            if (capturedImage != null) {
                val base64 = bitmapToBase64(capturedImage)
                enviarImagenAlBackend(
                    base64Image = base64,
                    sex = sex,
                    onResult = { tipo ->
                        navController.navigate(AppScreens.LoadingScreen.createRoute(tipo))
                    },
                    onError = {
                        setShowError(true)
                        Log.e("ERROR", "Fallo la petición: ${it.localizedMessage}")
                    }
                )
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
            fontSize = 20.sp,
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

fun bitmapToBase64(bitmap: Bitmap): String {
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream)
    val byteArray = stream.toByteArray()
    return android.util.Base64.encodeToString(byteArray, android.util.Base64.NO_WRAP)
}

fun enviarImagenAlBackend(
    base64Image: String,
    sex: String,
    onResult: (String) -> Unit,
    onError: (Exception) -> Unit
) {
    val jsonObject = JSONObject()
    jsonObject.put("image", base64Image)
    jsonObject.put("sex", sex)
    val body = RequestBody.create(
        "application/json".toMediaType(),
        jsonObject.toString()
    )

    val client = OkHttpClient()
    val request = Request.Builder()
        .url("https://fitvision-backend-production.up.railway.app/predict")
        .post(body)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            onError(e)
        }

        override fun onResponse(call: Call, response: Response) {
            response.body?.string()?.let { body ->
                Log.d("RESPUESTA_BACKEND", body)
                try {
                    val jsonObject = JSONObject(body)
                    val somatotipo = jsonObject.getString("somatotipo")
                    Handler(Looper.getMainLooper()).post {
                        onResult(somatotipo)
                    }
                } catch (e: Exception) {
                    Handler(Looper.getMainLooper()).post {
                        onError(e)
                    }
                }
            }
        }
    })
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CaptureScreenPreview() {
    val navController = rememberNavController()
    CaptureBodyComponent(navController = navController, sex = "Masculino")
}
