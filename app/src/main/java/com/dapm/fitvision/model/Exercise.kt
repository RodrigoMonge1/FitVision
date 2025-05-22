package com.dapm.fitvision.model

data class Exercise(
    val nombre: String,
    val descripcion: String,
    val tipo: String,
    val seriesPorTipo: Map<String, String>
)