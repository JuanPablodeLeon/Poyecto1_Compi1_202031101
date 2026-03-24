package com.example.pkm_forms.Instruction

import com.example.pkm_forms.Instruction.Elementos.TextoElementos
import com.example.pkm_forms.Models.TableSymbol
import com.example.pkm_forms.Models.Tree
import com.example.pkm_forms.Models.Type
import com.example.pkm_forms.Models.TypeData
import com.example.pkm_forms.Patron.Instruction

class Print (
    private  val expression: Instruction,
    linea: Int,
    columna: Int
) : Instruction(
    Type(TypeData.VOID),
    linea,
    columna
){
    override fun interprete(tree: Tree, table: TableSymbol): Any? {
        val value = expression.interprete(tree, table)
        if (value is Exception){
            return value
        }

        val output = when(value){
            is TextoElementos -> value.toString()
            else -> value.toString()
        }

        tree.print(output) //agrega como String el objeto ingresado
        return null
    }
}