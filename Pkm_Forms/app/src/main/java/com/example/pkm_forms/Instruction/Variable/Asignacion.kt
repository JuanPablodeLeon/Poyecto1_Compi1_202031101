package com.example.pkm_forms.Instruction.Variable

import com.example.pkm_forms.Analizadores.Forms.sym
import com.example.pkm_forms.Models.SintaxError
import com.example.pkm_forms.Models.TableSymbol
import com.example.pkm_forms.Models.Tree
import com.example.pkm_forms.Models.Type
import com.example.pkm_forms.Models.TypeData
import com.example.pkm_forms.Patron.Instruction

class Asignacion(
    private val nombre: String,
    private val valor: Instruction,
    linea: Int,
    columna: Int
) : Instruction(
    Type(TypeData.VOID),
    linea,
    columna
){
    override fun interprete(tree: Tree, table: TableSymbol): Any? {

        val symbol = table.getVariableCompleto(nombre)?:
        return SintaxError("SEMANTICO", "Variable no declarada"+nombre, this.line, this.column)

        val nuevoValor = valor.interprete(tree, table)
        if (nuevoValor is Error)
            return nuevoValor

        val tipoActual = symbol.typeData?.typeData
        when(tipoActual){
            TypeData.INTEGER ->{
                when (nuevoValor){
                    is Int ->{
                        table.updateVariable(nombre, nuevoValor)
                    }
                    is Double ->{
                        symbol.typeData = Type(TypeData.DECIMAL)
                        table.updateVariable(nombre, nuevoValor)
                    }
                    else -> {
                        return SintaxError("SEMANTICO", "No se puede cambiar el tipo a la variable", this.line, this.column)
                    }
                }
            }

            TypeData.DECIMAL->{
                when (nuevoValor){
                    is Double ->{
                        table.updateVariable(nombre, nuevoValor)
                    }
                    is Int ->{
                        symbol.typeData = Type(TypeData.INTEGER)
                        table.updateVariable(nombre, nuevoValor.toDouble())
                    }
                    else -> {
                        return SintaxError("SEMANTICO", "No se puede cambiar el tipo a la variable (number)", this.line, this.column)
                    }
                }
            }
            TypeData.STRING -> {
                when(nuevoValor){
                    is String ->{
                        table.updateVariable(nombre, nuevoValor)
                    }
                    else -> {
                        return SintaxError("SEMANTICO", "No se puede cambiar de tipo (string)", this.line, this.column)
                    }
                }
            }
            null ->{
                return SintaxError("SEMANTICO", "La variable no tiene tipo", this.line, this.column)
            }
            else -> {
                return SintaxError("SEMANTICO", "Tipo no soportado", this.line, this.column)
            }
        }
        return null
    }
}