package com.example.pkm_forms.Expression

import com.example.pkm_forms.Models.TableSymbol
import com.example.pkm_forms.Models.Tree
import com.example.pkm_forms.Models.Type
import com.example.pkm_forms.Patron.Instruction

//Para poder almacenar los nombres de los identificadores y valores numericos
class Native(
    val value: Any?,
    type: Type,
    linea: Int,
    columna: Int
) : Instruction(
    type,
    linea,
    columna
){
    override fun interprete(tree: Tree, table: TableSymbol): Any? {
        return value //unicamente devuelve el valor
    }
}