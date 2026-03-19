package com.example.pkm_forms.Instruction.Variable

import com.example.pkm_forms.Models.SintaxError
import com.example.pkm_forms.Models.TableSymbol
import com.example.pkm_forms.Models.Tree
import com.example.pkm_forms.Models.Type
import com.example.pkm_forms.Models.TypeData
import com.example.pkm_forms.Patron.Instruction

class AccesVariable (
    val name: String,
    linea: Int,
    columna: Int
): Instruction(
    Type(TypeData.VOID),
    linea,
    columna
) {
    override fun interprete(tree: Tree, table: TableSymbol): Any? {
        val value = table.getVariable(name)// obtiene el nombre del identificador
        if (value == null){//si es null significa que aun no fue declarada
            return SintaxError("Semantico", "Variable no encontrada", line, column)
        }
        this.typeValue = value.typeData!!//le remueve el puntero null
        return value.value
    }
}