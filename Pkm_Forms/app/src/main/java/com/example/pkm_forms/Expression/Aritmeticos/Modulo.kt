package com.example.pkm_forms.Expression.Aritmeticos

import com.example.pkm_forms.Models.SintaxError
import com.example.pkm_forms.Models.TableSymbol
import com.example.pkm_forms.Models.Tree
import com.example.pkm_forms.Models.Type
import com.example.pkm_forms.Models.TypeData
import com.example.pkm_forms.Patron.Instruction

class Modulo (
    private val left : Instruction,
    private val right : Instruction,
    linea : Int,
    columna : Int
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

        when (leftType){
            TypeData.INTEGER -> {
                when (rightType){
                    TypeData.INTEGER -> {
                        this.typeValue = Type(TypeData.INTEGER)
                        return (leftValue as Int) % (rightValue as Int)
                    }
                    TypeData.DECIMAL ->{
                        this.typeValue = Type(TypeData.DECIMAL)
                        if (leftValue is Number && rightValue is Number){
                            return  (leftValue as Int).toDouble() % (rightValue as Number).toDouble()
                        } else {
                            return SintaxError("SINTACTICO","No se puede sacar modulo de valores no numericos", this.line, this.column)
                        }
                    }
                    else ->{
                        val error : SintaxError =
                            SintaxError("SINTACTICO", "Modulo Invalido", this.line, this.column)
                        return  error
                    }
                }
            }

            TypeData.DECIMAL ->{
                when (rightType){
                    TypeData.INTEGER ->{
                        this.typeValue = Type(TypeData.DECIMAL)
                        return (leftValue as Double) % (rightValue as Int).toDouble()
                    }
                    TypeData.DECIMAL ->{
                        this.typeValue = Type(TypeData.DECIMAL)
                        return  leftValue as Double % rightValue as Double
                    }
                    else -> {
                        val error : SintaxError =
                            SintaxError("SINTACTICO", "Modulo Invalido", this.line, this.column)
                        return  error
                    }
                }
            }
            else -> {
                val error : SintaxError =
                    SintaxError("SINTACTICO", "Modulo Invalido", this.line, this.column)
                return  error
            }
        }
    }
}