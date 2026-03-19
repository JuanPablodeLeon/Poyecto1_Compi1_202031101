package com.example.pkm_forms.Instruction

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
        tree.print(value.toString()) //agrega como String el objeto ingresado
        return null
    }
}