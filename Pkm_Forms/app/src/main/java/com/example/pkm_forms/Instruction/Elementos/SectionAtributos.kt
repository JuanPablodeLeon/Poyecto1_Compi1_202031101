package com.example.pkm_forms.Instruction.Elementos

import com.example.pkm_forms.Models.Styles.StylesGeneral
import com.example.pkm_forms.Patron.Instruction
import java.util.LinkedList

class SectionAtributos {

    @JvmField var with: Instruction? = null
    @JvmField var height: Instruction? = null
    @JvmField var pointX: Instruction? = null
    @JvmField var pointY: Instruction? = null
    @JvmField var orientation: Instruction? = null
    @JvmField var elements: LinkedList<Instruction>? = null
    @JvmField var styles: StylesGeneral?= null

    @JvmField var tieneWith = false
    @JvmField var tieneHeight = false
    @JvmField var tienePointX = false
    @JvmField var tienePointY = false
    @JvmField var tieneOrientation = false
    @JvmField var tieneElements = false
    @JvmField var tieneStyles = false


}