package com.example.pkm_forms.Expression.Ciclos

import com.example.pkm_forms.Models.SintaxError
import com.example.pkm_forms.Models.TableSymbol
import com.example.pkm_forms.Models.Tree
import com.example.pkm_forms.Models.Type
import com.example.pkm_forms.Models.TypeData
import com.example.pkm_forms.Patron.Instruction
import java.util.LinkedList

class IfElseIF(
    val condifionIf: Instruction,
    val condicionElseIf: LinkedList<Instruction>?,
    val instrucciones: Instruction,
    linea: Int,
    columna: Int
) : Instruction(
    Type(TypeData.VOID),
    linea,
    columna
){
    override fun interprete(tree: Tree, table: TableSymbol): Any? {
        val condicionIfValue = condifionIf.interprete(tree,table)
        if (condicionIfValue is Error)
            return condicionIfValue

        if (condifionIf.typeValue.typeData != TypeData.BOOLEAN)
            return SintaxError("SEMANTICO", "Necesita condicion logica", this.line, this.column)

        var nuevoEntornoIf = TableSymbol(table)
        nuevoEntornoIf.nombre = "IF"

        val esVerdadero = when(condicionIfValue){
            is Boolean -> condicionIfValue
            is Int -> condicionIfValue >= 1
            is Double -> condicionIfValue >= 1.0
            else -> false
        }
        return if (esVerdadero){
            condicionElseIf?.forEach { i ->
                val resultado = i.interprete(tree, nuevoEntornoIf)
                if (resultado is Error) return resultado
            }
            tree.nuevoSimbolo(nuevoEntornoIf.obtenerListaSimbolos())
            null
        } else{
            instrucciones.interprete(tree, table)
        }

    }
}