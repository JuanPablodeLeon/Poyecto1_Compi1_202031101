package com.example.pkm_forms.Instruction.Elementos

import com.example.pkm_forms.Models.Styles.StylesGeneral
import com.example.pkm_forms.Patron.Instruction

class TextoAtributos{

    @JvmField
    var width: Instruction? = null
    @JvmField
    var height: Instruction? = null
    @JvmField
    var content: Instruction? = null
    @JvmField
    var styles: StylesGeneral? = null


    @JvmField
    var tieneWidth = false
    @JvmField
    var tieneHeight = false
    @JvmField
    var tieneContent = false
    @JvmField
    var tieneStyles = false
}