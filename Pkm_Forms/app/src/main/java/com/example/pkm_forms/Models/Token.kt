package com.example.pkm_forms.Models

data class Token(
    val lexema: String,
    val tipo: String,
    val linea: Int,
    val columna: Int
)
