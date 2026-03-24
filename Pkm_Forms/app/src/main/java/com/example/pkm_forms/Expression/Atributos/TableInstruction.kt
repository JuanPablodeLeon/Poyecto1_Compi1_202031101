package com.example.pkm_forms.Expression.Atributos

import com.example.pkm_forms.Instruction.Elementos.TableAtributos
import com.example.pkm_forms.Instruction.Elementos.TableElementos
import com.example.pkm_forms.Models.SintaxError
import com.example.pkm_forms.Models.TableSymbol
import com.example.pkm_forms.Models.Tree
import com.example.pkm_forms.Models.Type
import com.example.pkm_forms.Models.TypeData
import com.example.pkm_forms.Patron.Instruction

class TableInstruction(
    private val atributos: TableAtributos,
    linea: Int,
    columna: Int
) : Instruction(
    Type(TypeData.VOID),
    linea,
    columna
){
    override fun interprete(tree: Tree, table: TableSymbol): Any? {
        if (!atributos.tieneWidth || atributos.width == null)
            return SintaxError("SEMANTICO", "TABLE requiere elemento \"widht\"", this.line, this.column)

        if (!atributos.tieneHeight || atributos.height == null)
            return SintaxError("SEMANTICO", "TABLE requiere elemento \"height\"", this.line, this.column)

        if (!atributos.tienePointX || atributos.pointX == null)
            return SintaxError("SEMANTICO", "TABLE requiere elemento \"pointX\"", this.line, this.column)

        if (!atributos.tienePointY || atributos.pointY == null)
            return SintaxError("SEMANTICO", "TABLE requiere elemento \"pointY\"", this.line, this.column)

        val widthValue = atributos.width!!.interprete(tree,table)
        if (widthValue is Error) return widthValue
        widthValue as? Number

        val heightValue = atributos.height!!.interprete(tree,table)
        if (heightValue is Error) return heightValue
        heightValue as? Number

        val poinXValue = atributos.pointX!!.interprete(tree,table)
        if (poinXValue is Error) return poinXValue
        poinXValue as? Number

        val pointYValue = atributos.pointY!!.interprete(tree,table)
        if (pointYValue is Error) return pointYValue
        pointYValue as? Number

        val elements = if(atributos.tieneElements && atributos.elements != null){
            val elementsValue = atributos.elements!!.interprete(tree, table)
            if (elementsValue is Error) return elementsValue
            elementsValue as? String
        }else null

        return TableElementos(
            width = widthValue as Number,
            height = heightValue as Number,
            pointX = poinXValue as Number,
            pointY = pointYValue as Number,
            elements = elements.toString(),
            styles = atributos.styles
        )
    }
}