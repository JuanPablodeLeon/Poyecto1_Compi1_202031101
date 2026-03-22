package com.example.pkm_forms.Expression.Atributos

import com.example.pkm_forms.Instruction.Elementos.TextoAtributos
import com.example.pkm_forms.Models.SintaxError
import com.example.pkm_forms.Models.TableSymbol
import com.example.pkm_forms.Models.Tree
import com.example.pkm_forms.Models.Type
import com.example.pkm_forms.Models.TypeData
import com.example.pkm_forms.Patron.Instruction

class TextoInstruction(
    private val atributos: TextoAtributos,
    linea: Int,
    columna: Int
) : Instruction(
    Type(TypeData.VOID),
    linea,
    columna
){
    override fun interprete(tree: Tree, table: TableSymbol): Any? {
        if (!atributos.tieneContent || atributos.content == null)
            return SintaxError("SEMANTICO", "TEXT requiere elemento \"content\" ", this.line, this.column)

        val contenidoValue = atributos.content!!.interprete(tree,table)
        if (contenidoValue is Error)
            return contenidoValue

        val with = if (atributos.tieneWidth){
            val withValue = atributos.content!!.interprete(tree,table)
            if (withValue is Error) return withValue
            withValue
        } else null

        val height = if (atributos.tieneHeight){
            val heightValue = atributos.content!!.interprete(tree,table)
            if (heightValue is Error) return heightValue
            heightValue
        } else null


        return null
    }
}