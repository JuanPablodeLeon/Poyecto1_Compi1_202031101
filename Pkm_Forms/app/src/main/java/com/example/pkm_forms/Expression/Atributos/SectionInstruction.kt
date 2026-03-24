package com.example.pkm_forms.Expression.Atributos

import com.example.pkm_forms.Instruction.Elementos.SectionAtributos
import com.example.pkm_forms.Instruction.Elementos.SectionElementos
import com.example.pkm_forms.Models.SintaxError
import com.example.pkm_forms.Models.TableSymbol
import com.example.pkm_forms.Models.Tree
import com.example.pkm_forms.Models.Type
import com.example.pkm_forms.Models.TypeData
import com.example.pkm_forms.Patron.Instruction

class SectionInstruction (
    private val atributos: SectionAtributos,
    linea: Int,
    columna: Int
) : Instruction(
    Type(TypeData.VOID),
    linea,
    columna
){
    override fun interprete(tree: Tree, table: TableSymbol): Any? {
        if(!atributos.tieneWith || atributos.with == null)
            return SintaxError("SEMANTICO", "SECTION requiere elemento \"width\" ", this.line, this.column)

        if(!atributos.tieneHeight || atributos.height == null)
            return SintaxError("SEMANTICO", "SECTION requiere elemento \"heigth\" ", this.line, this.column)

        if(!atributos.tienePointX || atributos.pointX == null)
            return SintaxError("SEMANTICO", "SECTION requiere elemento \"pointX\" ", this.line, this.column)

        if(!atributos.tienePointY || atributos.pointY == null)
            return SintaxError("SEMANTICO", "SECTION requiere elemento \"poinY\" ", this.line, this.column)

        val widthValue = atributos.with!!.interprete(tree, table)
        if (widthValue is Error) return widthValue
        widthValue as? Number

        val heightValue = atributos.height!!.interprete(tree, table)
        if (heightValue is Error) return heightValue
        heightValue as? Number

        val pointXValue = atributos.pointX!!.interprete(tree, table)
        if (pointXValue is Error) return pointXValue
        pointXValue as? Number

        val pointYValue = atributos.pointY!!.interprete(tree, table)
        if (pointYValue is Error) return pointYValue
        pointYValue as? Number

        val orientation = if(atributos.tieneOrientation && atributos.orientation != null){
            atributos.orientation!!.interprete(tree, table).toString()
        } else "VERTICAL"

        val elementsValue = mutableListOf<Any>()
        atributos.elements?.forEach { instruction ->
            val resultado = instruction.interprete(tree, table)
            if (resultado is Error) return resultado
            resultado?.let { elementsValue.add(it) }
        }

        return SectionElementos(
            with = widthValue as Number,
            height = heightValue as Number,
            pointX = pointXValue as Number,
            pointY = pointYValue as Number,
            orientation = orientation,
            elements = elementsValue,
            styles = atributos.styles
        )
    }
}