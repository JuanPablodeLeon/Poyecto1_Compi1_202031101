package com.example.pkm_forms.Expression.Logicos

import com.example.pkm_forms.Models.SintaxError
import com.example.pkm_forms.Models.TableSymbol
import com.example.pkm_forms.Models.Tree
import com.example.pkm_forms.Models.Type
import com.example.pkm_forms.Models.TypeData
import com.example.pkm_forms.Patron.Instruction

class Negacion(
    private val unique: Instruction,
    linea: Int,
    columna: Int
) : Instruction(
    Type(TypeData.VOID),
    linea,
    columna
){
    override fun interprete(tree: Tree, table: TableSymbol): Any? {
        val uniqueValue = unique.interprete(tree, table)
        if (uniqueValue is Error)
            return uniqueValue

        var uniqueType = unique.typeValue.typeData
        when(uniqueType){
            TypeData.BOOLEAN -> {
                this.typeValue = Type(TypeData.BOOLEAN)
                return !uniqueValue.toString().toBoolean()
            }
            else -> {
                return SintaxError("SEMANTICO", "Se esperaba valor bolleano", this.line, this.column)
            }
        }
    }
}