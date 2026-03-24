package com.example.pkm_forms.Expression.Ciclos

import com.example.pkm_forms.Models.SintaxError
import com.example.pkm_forms.Models.TableSymbol
import com.example.pkm_forms.Models.Tree
import com.example.pkm_forms.Models.Type
import com.example.pkm_forms.Models.TypeData
import com.example.pkm_forms.Patron.Instruction
import java.util.LinkedList

class If(
    private val condicion: Instruction,
    private val instrucciones: LinkedList<Instruction>?,
    linea: Int,
    columna: Int
) : Instruction(
    Type(TypeData.VOID),
    linea,
    columna
){
    override fun interprete(tree: Tree, table: TableSymbol): Any? {
        val condicionValue = condicion.interprete(tree, table)
        if (condicionValue is Error)
            return condicionValue

        if (condicion.typeValue.typeData != TypeData.BOOLEAN){
            return SintaxError("SEMANTICO", "Se necesita condicional logica", this.line, this.column)
        }

        val esVerdadero = when(condicionValue){
            is Boolean -> condicionValue
            is Int -> condicionValue >= 1
            is Double -> condicionValue >= 1.0
            else -> false
        }

        if (!esVerdadero || instrucciones == null)
            return null

        val newEntorno = TableSymbol(table)
        newEntorno.nombre = "IF"

        for(i in instrucciones){
            val resultado = i.interprete(tree, newEntorno)

            if (resultado is Error)
                return resultado

        }
        tree.nuevoSimbolo(newEntorno.obtenerListaSimbolos())

        return null
    }
}