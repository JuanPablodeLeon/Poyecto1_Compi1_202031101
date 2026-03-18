package com.example.pkm_forms.Patron

import com.example.pkm_forms.Models.TableSymbol
import com.example.pkm_forms.Models.Tree
import com.example.pkm_forms.Models.Type

//clase abstracta para poder ingresar los elementos al arbol ast
abstract class Instruction (
    var typeValue : Type,
    val line : Int,
    val column: Int
){
    abstract fun interprete(tree: Tree, table: TableSymbol): Any?
}