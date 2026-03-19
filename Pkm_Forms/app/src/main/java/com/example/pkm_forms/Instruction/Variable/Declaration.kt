package com.example.pkm_forms.Instruction.Variable

import com.example.pkm_forms.Models.SintaxError
import com.example.pkm_forms.Models.Symbol
import com.example.pkm_forms.Models.TableSymbol
import com.example.pkm_forms.Models.Tree
import com.example.pkm_forms.Models.Type
import com.example.pkm_forms.Models.TypeData
import com.example.pkm_forms.Patron.Instruction

class Declaration (
    val name: String,
    val value: Instruction?,
    linea : Int,
    columna: Int
) : Instruction(
    Type(TypeData.VOID),
    linea,
    columna
){
    override fun interprete(tree: Tree, table: TableSymbol): Any? {
        if (value == null){
            val type = Type(TypeData.VOID)
            val newVal : Any? = null //Le asigna el valor null de inicio
            //Prueba establecer la variable en la tabla de simbolos
            if (table.setVariable(Symbol(this.name, newVal, type))){
                return null //lo regresa a null para que cuando llegue a el esto mas bajo pueda ser modificado
            }
        }

        val newValue = value?.interprete(tree, table)
        if(newValue is Error){
            return newValue
        }

        val data = value?.typeValue
        if (table.setVariable(Symbol(this.name, newValue, data))){
            return null
        }

        return SintaxError("Semantico", "Variable ya existente", line, column)
    }
}