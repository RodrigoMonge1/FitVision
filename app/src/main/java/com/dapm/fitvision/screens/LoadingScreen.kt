package com.dapm.fitvision.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoadingScreen(){
    Scaffold {
        LoadingBodyComponent()
    }
}

@Composable
fun LoadingBodyComponent(){
    Surface(modifier = Modifier.fillMaxSize(),
        color = Color.Black){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 40.dp, vertical = 24.dp)
                .background(color = Color.White), //BORRAR
            horizontalAlignment = Alignment.CenterHorizontally
        ){

        }
    }
}


@Preview
@Composable
fun LoadingPreview(){
    LoadingBodyComponent()
}