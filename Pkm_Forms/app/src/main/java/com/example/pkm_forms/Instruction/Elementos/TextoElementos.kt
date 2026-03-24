package com.example.pkm_forms.Instruction.Elementos

import com.example.pkm_forms.Models.Styles.StylesGeneral

data class TextoElementos(
    val content : String,
    val width: Number? = null,
    val height : Number? = null,
    val styles: StylesGeneral? = null
){
    override fun toString(): String {
        val partes = mutableListOf<String>()
        width?.let { partes.add("widht: $it") }
        height?.let { partes.add("height: $it") }
        partes.add("content: \"$content\"")
        styles?.let { partes.add(it.toString()) }
        return "TEXT [ ${partes.joinToString (", ")}]"
    }
}
