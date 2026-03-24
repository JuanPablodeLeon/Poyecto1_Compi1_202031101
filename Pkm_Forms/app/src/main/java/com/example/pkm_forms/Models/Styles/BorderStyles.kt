package com.example.pkm_forms.Models.Styles

import com.example.pkm_forms.Patron.Instruction

data class BorderStyles(
    val grosor: Instruction,
    val tipo: Instruction,
    val color: Instruction
){
    override fun toString(): String {
        return "($grosor, $tipo, $color)"
    }
}
