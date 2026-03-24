package com.example.pkm_forms.Instruction.Elementos

import com.example.pkm_forms.Models.Styles.StylesGeneral

data class TableElementos(
    val width: Number,
    val height: Number,
    val pointX: Number,
    val pointY: Number,
    val elements: String? = null,
    val styles: StylesGeneral? = null
)
{
    override fun toString(): String {
        val partes = mutableListOf<String>()
        partes.add("width: $width")
        partes.add("height: $height")
        partes.add("pointX: $pointX")
        partes.add("poinY: $pointY")
        elements?.let { partes.add("elements: $elements") }
        styles?.let { partes.add(it.toString()) }
        return "TABLE [ ${partes.joinToString (", ")} ]"
    }
}
