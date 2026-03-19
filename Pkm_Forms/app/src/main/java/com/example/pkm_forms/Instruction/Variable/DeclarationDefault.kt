package com.example.pkm_forms.Instruction.Variable

import com.example.pkm_forms.Models.SintaxError
import com.example.pkm_forms.Models.Symbol
import com.example.pkm_forms.Models.TableSymbol
import com.example.pkm_forms.Models.Tree
import com.example.pkm_forms.Models.Type
import com.example.pkm_forms.Models.TypeData
import com.example.pkm_forms.Patron.Instruction

class DeclarationDefault(
    val tipoDato : String,
    val nombreVariable : String,
    linea: Int,
    columna : Int
) : Instruction(
    Type(TypeData.VOID),
    linea,
    columna
){
    override fun interprete(tree: Tree, table: TableSymbol): Any? {
        val existente = table.getVariableCompleto(nombreVariable)
        if (existente != null)
            return SintaxError("SINTACTICO", "Variable ya delcarada:"+nombreVariable, this.line, this.column)

        when(tipoDato){
            "number" ->{
                val symbol = Symbol(nombreVariable, 0, Type(TypeData.INTEGER))
                val agregar = table.setVariable(symbol)
                if (!agregar){
                    return SintaxError("SINTACTICO", "No se pudo declarar la variable:"+nombreVariable, this.line, this.column)
                }
                return null
            }
            "string" -> {
                val symbol = Symbol(nombreVariable, "TWICE", Type(TypeData.STRING))
                val agregar = table.setVariable(symbol)
                if (!agregar){
                    return SintaxError("SINTACTICO", "No se pudo declarar la variable:"+nombreVariable, this.line, this.column)
                }
                return null
            }
            else -> {
                val error : SintaxError =
                    SintaxError("SINTACTICO", "Dato desconocido", this.line, this.column)
                return  error
            }
        }
    }
}