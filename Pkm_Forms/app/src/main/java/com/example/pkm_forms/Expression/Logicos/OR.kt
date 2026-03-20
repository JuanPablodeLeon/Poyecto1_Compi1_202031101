package com.example.pkm_forms.Expression.Logicos

import com.example.pkm_forms.Models.SintaxError
import com.example.pkm_forms.Models.TableSymbol
import com.example.pkm_forms.Models.Tree
import com.example.pkm_forms.Models.Type
import com.example.pkm_forms.Models.TypeData
import com.example.pkm_forms.Patron.Instruction

class OR (
    private val left: Instruction,
    private val right: Instruction,
    linea: Int,
    columna: Int
) : Instruction(
    Type(TypeData.VOID),
    linea,
    columna
){
    override fun interprete(tree: Tree, table: TableSymbol): Any? {
        val leftValue = left.interprete(tree, table)
        if (leftValue is Error)
            return leftValue

        val rightValue = right.interprete(tree, table)
        if (rightValue is Error)
            return rightValue

        var leftType = left.typeValue.typeData
        var rightType = right.typeValue.typeData

        when(leftType){
            TypeData.BOOLEAN -> {
                when(rightType){
                    TypeData.BOOLEAN -> {
                        this.typeValue = Type(TypeData.BOOLEAN)
                        if ((leftValue as Boolean) || (rightValue as Boolean)){
                            return true
                        } else{
                            return false
                        }
                    }
                    else -> {
                        return SintaxError("SINTACTICO", "Comparacion AND invalida", this.line, this.column)
                    }
                }
            }
            else -> {
                return SintaxError("SINTACTICO", "Comparacion AND invalida", this.line, this.column)
            }
        }
    }
}