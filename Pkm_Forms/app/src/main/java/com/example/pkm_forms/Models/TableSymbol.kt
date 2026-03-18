package com.example.pkm_forms.Models

class TableSymbol(
    var tablaPrevia: TableSymbol ?= null
) {
    //uso de hash para facilidad al encontrar un identificador
    private val tablaActual: MutableMap<String, Symbol> = HashMap()
    var nombre: String = ""

    fun setVariable(symbol: Symbol): Boolean{
        val id = symbol.id.lowercase()

        if (!tablaActual.containsKey(id)){ //si el identificaro existe no se agregaba a la tabla de simbolos
            tablaActual[id] = symbol
            return true
        }
        return false
    }

    fun getVariable(id: String): Symbol?{
        return tablaActual[id.lowercase()]
    }
}