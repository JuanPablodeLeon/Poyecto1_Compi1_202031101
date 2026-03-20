package com.example.pkm_forms.Expression.Comparacion

import com.example.pkm_forms.Models.SintaxError
import com.example.pkm_forms.Models.TableSymbol
import com.example.pkm_forms.Models.Tree
import com.example.pkm_forms.Models.Type
import com.example.pkm_forms.Models.TypeData
import com.example.pkm_forms.Patron.Instruction

class Mayor_Que (
    private val left: Instruction,
    private val right: Instruction,
    linea: Int,
    columna: Int
): Instruction(
    Type(TypeData.VOID),
    linea,
    columna
){
    override fun interprete(tree: Tree, table: TableSymbol): Any? {
        val leftValue = left.interprete(tree,table)
        if (leftValue is Error)
            return leftValue

        val rightValue = right.interprete(tree,table)
        if (rightValue is Error)
            return rightValue

        var leftType = left.typeValue.typeData
        var rightType = right.typeValue.typeData

        when(leftType){
            TypeData.INTEGER -> {
                when(rightType){
                    TypeData.INTEGER -> {
                        this.typeValue = Type(TypeData.BOOLEAN)
                        return (leftValue as Int) > (rightValue as Int)
                    }
                    TypeData.DECIMAL -> {
                        this.typeValue = Type(TypeData.BOOLEAN)
                        return (leftValue as Int) > (rightValue as Double)
                    }
                    else -> {
                        return SintaxError("SEMANTICO", "Comparacion Invalida", this.line, this.column)
                    }
                }
            }
            TypeData.DECIMAL -> {
                when(rightType){
                    TypeData.INTEGER -> {
                        this.typeValue = Type(TypeData.BOOLEAN)
                        return (leftValue as Double) > (rightValue as Int)
                    }
                    TypeData.DECIMAL -> {
                        this.typeValue = Type(TypeData.BOOLEAN)
                        return (leftValue as Double) > (rightValue as Double)
                    }
                    else -> {
                        return SintaxError("SEMANTICO", "Comparacion Invalida", this.line, this.column)
                    }
                }
            }
            else -> {
                return SintaxError("SEMANTICO", "No se puede realizar la Comparacion", this.line, this.column)
            }
        }
    }
}