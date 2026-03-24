package com.example.pkm_forms.Expression.Ciclos

import com.example.pkm_forms.Models.SintaxError
import com.example.pkm_forms.Models.TableSymbol
import com.example.pkm_forms.Models.Tree
import com.example.pkm_forms.Models.Type
import com.example.pkm_forms.Models.TypeData
import com.example.pkm_forms.Patron.Instruction
import java.util.LinkedList

class IfElse(
    val condicion: Instruction,
    val instruccionesIf: LinkedList<Instruction>?,
    val instruccionesELse: LinkedList<Instruction>?,
    linea: Int,
    columna: Int
) : Instruction(
    Type(TypeData.VOID),
    linea,
    columna
){
    override fun interprete(tree: Tree, table: TableSymbol): Any? {
        val condicionValue = condicion.interprete(tree,table)
        if (condicionValue is Error)
            return condicionValue

        if (condicion.typeValue.typeData != TypeData.BOOLEAN){
            return SintaxError("SEMANTICO", "Se necesita condicon logica", this.line, this.column)
        }

        val esVerdadero = when(condicionValue){
            is Boolean -> condicionValue
            is Int -> condicionValue >= 1
            is Double -> condicionValue >= 1.0
            else -> false
        }

        val nuevoEntornoIf = TableSymbol(table).apply { nombre= "IF" }
        val nuevoEntornoElse = TableSymbol(table).apply { nombre = "ELSE" }

        if (esVerdadero){
            instruccionesIf?.forEach { i ->
                val resultado = i.interprete(tree, nuevoEntornoIf)
                if (resultado is Error) return resultado
            }
            tree.nuevoSimbolo(nuevoEntornoIf.obtenerListaSimbolos())
        }else{
            instruccionesELse?.forEach { i ->
                val resultado = i.interprete(tree, nuevoEntornoElse)
                if (resultado is Error) return resultado
            }
            tree.nuevoSimbolo(nuevoEntornoElse.obtenerListaSimbolos())
        }
        return null
    }
}