package com.example.pkm_forms.Instruction.Colores

import com.example.pkm_forms.Models.SintaxError
import com.example.pkm_forms.Models.TableSymbol
import com.example.pkm_forms.Models.Tree
import com.example.pkm_forms.Models.Type
import com.example.pkm_forms.Models.TypeData
import com.example.pkm_forms.Patron.Instruction

class HSL (
    private val hue : Instruction,
    private val saturation : Instruction,
    private val lightness: Instruction,
    linea: Int,
    columna: Int
) : Instruction(
    Type(TypeData.VOID),
    linea,
    columna
){
    override fun interprete(tree: Tree, table: TableSymbol): Any? {
        val hueValue = hue.interprete(tree, table)
        if (hueValue is Error)
            return hueValue

        val saturationValue = saturation.interprete(tree, table)
        if (saturationValue is Error)
            return saturationValue

        val lightnessValue = lightness.interprete(tree, table)
        if (lightnessValue is Error)
            return lightnessValue

        var hueTpye = when(hue.typeValue.typeData){
            TypeData.INTEGER ->{
                (hueValue as Int).coerceIn(0,360)
            }

            TypeData.DECIMAL ->{
                this.typeValue = Type(TypeData.INTEGER)
                val conversion : Int = (hueValue as Double).toInt()
                conversion.coerceIn(0,360)
            }
            else -> {
                return SintaxError("SEMANTICO", "Tono no valido", this.line, this.column)
            }
        }

        var saturationTpye = when(saturation.typeValue.typeData){
            TypeData.INTEGER ->{
                (saturationValue as Int).coerceIn(0,100)
            }

            TypeData.DECIMAL ->{
                this.typeValue = Type(TypeData.INTEGER)
                val conversion : Int = (saturationValue as Double).toInt()
                conversion.coerceIn(0,100)
            }
            else -> {
                return SintaxError("SEMANTICO", "Saturacion no valida", this.line, this.column)
            }
        }

        var lightnessType = when(lightness.typeValue.typeData){
            TypeData.INTEGER ->{
                (lightnessValue as Int).coerceIn(0,100)
            }

            TypeData.DECIMAL ->{
                this.typeValue = Type(TypeData.INTEGER)
                val conversion : Int = (lightnessValue as Double).toInt()
                conversion.coerceIn(0,100)
            }
            else -> {
                return SintaxError("SEMANTICO", "Brillo no valido", this.line, this.column)
            }
        }

        this.typeValue = Type(TypeData.COLOR)
        return Triple(hueTpye, saturationTpye, lightnessType)
    }
}