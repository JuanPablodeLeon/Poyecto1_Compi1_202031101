package com.example.pkm_forms.Instruction.Elementos

import com.example.pkm_forms.Models.Styles.StylesGeneral

data class SectionElementos(
    val with: Number,
    val height: Number,
    val pointX: Number,
    val pointY: Number,
    val orientation: String = "VERTICAL",
    val elements: List<Any> = emptyList(),
    val styles: StylesGeneral? = null
){
    override fun toString(): String {
        val partes = mutableListOf<String>()
        partes.add("width: $with")
        partes.add("height: $height")
        partes.add("pointX: $pointX")
        partes.add("pointY: $pointY")
        orientation?.let { partes.add("orientation: $it") }
        if (elements.isNotEmpty()){
            val elementosStr = elements.joinToString(", "){it.toString()}
            partes.add("elements: { $elementosStr }")
        }
        styles?.let{ partes.add(it.toString())}
        return "SECTION [ ${partes.joinToString (", ")} ]"
    }
}

