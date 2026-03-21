package com.example.pkm_forms.Instruction.Colores

import com.example.pkm_forms.Models.SintaxError
import com.example.pkm_forms.Models.TableSymbol
import com.example.pkm_forms.Models.Tree
import com.example.pkm_forms.Models.Type
import com.example.pkm_forms.Models.TypeData
import com.example.pkm_forms.Patron.Instruction

class RGB(
    private val red : Instruction,
    private val green : Instruction,
    private val blue: Instruction,
    linea: Int,
    columna: Int
) : Instruction(
    Type(TypeData.VOID),
    linea,
    columna
){
    override fun interprete(tree: Tree, table: TableSymbol): Any? {
        val redValue = red.interprete(tree, table)
        if (redValue is Error)
            return redValue

        val greenValue = green.interprete(tree, table)
        if (greenValue is Error)
            return greenValue

        val blueValue = blue.interprete(tree, table)
        if (blueValue is Error)
            return blueValue

        var redTpye = when(red.typeValue.typeData){
            TypeData.INTEGER ->{
                (redValue as Int).coerceIn(0,255)
            }

            TypeData.DECIMAL ->{
                this.typeValue = Type(TypeData.INTEGER)
                val conversion : Int = (redValue as Double).toInt()
                return conversion.coerceIn(0,255)
            }
            else -> {
                return SintaxError("SEMANTICO", "Color rojo no valido", this.line, this.column)
            }
        }

        var greenTpye = when(green.typeValue.typeData){
            TypeData.INTEGER ->{
                (greenValue as Int).coerceIn(0,255)
            }

            TypeData.DECIMAL ->{
                this.typeValue = Type(TypeData.INTEGER)
                val conversion : Int = (greenValue as Double).toInt()
                return conversion.coerceIn(0,255)
            }
            else -> {
                return SintaxError("SEMANTICO", "Color verde no valido", this.line, this.column)
            }
        }

        var blueTpye = when(blue.typeValue.typeData){
            TypeData.INTEGER ->{
                (blueValue as Int).coerceIn(0,255)
            }

            TypeData.DECIMAL ->{
                this.typeValue = Type(TypeData.INTEGER)
                val conversion : Int = (blueValue as Double).toInt()
                return conversion.coerceIn(0,255)
            }
            else -> {
                return SintaxError("SEMANTICO", "Color azul no valido", this.line, this.column)
            }
        }

        this.typeValue = Type(TypeData.COLOR)
        return Triple(red, green, blue)
    }
}