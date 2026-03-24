package com.example.pkm_forms.Models.Styles

data class BorderStyles(
    val grosor: Number,
    val tipo: String,
    val color: String
){
    override fun toString(): String {
        return "($grosor, $tipo, $color)"
    }
}
