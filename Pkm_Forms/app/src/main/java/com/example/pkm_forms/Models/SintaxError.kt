package com.example.pkm_forms.Models

//Clase para poder mostrar errores personalizados
data class SintaxError(
    val tipo: String,
    val mensaje: String,
    val linea: Int,
    val columna: Int
)
