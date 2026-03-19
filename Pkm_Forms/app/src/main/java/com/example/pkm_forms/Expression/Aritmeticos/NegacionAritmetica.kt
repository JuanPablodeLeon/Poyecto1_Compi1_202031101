package com.example.pkm_forms.Expression.Aritmeticos

import com.example.pkm_forms.Models.SintaxError
import com.example.pkm_forms.Models.TableSymbol
import com.example.pkm_forms.Models.Tree
import com.example.pkm_forms.Models.Type
import com.example.pkm_forms.Models.TypeData
import com.example.pkm_forms.Patron.Instruction

class NegacionAritmetica (
    private val operador : Instruction,
    linea: Int,
    columna: Int
): Instruction(
    Type(TypeData.VOID),
    linea,
    columna
){
    override fun interprete(tree: Tree, table: TableSymbol): Any? {
        val operatorValue = operador.interprete(tree, table)
        if (operatorValue is Error)
            return  operatorValue

        var operatorType = operador.typeValue.typeData

        when(operatorType){
            TypeData.INTEGER -> {
                this.typeValue = Type(TypeData.INTEGER)
                return (operatorValue as Int)* -1
            }
            TypeData.DECIMAL -> {
                this.typeValue = Type(TypeData.DECIMAL)
                return (operatorValue as Double)* -1
            }
            else -> {
                val error : SintaxError =
                    SintaxError("SINTACTICO", "Numero no valido", this.line, this.column)
                return  error
            }
        }
    }
}