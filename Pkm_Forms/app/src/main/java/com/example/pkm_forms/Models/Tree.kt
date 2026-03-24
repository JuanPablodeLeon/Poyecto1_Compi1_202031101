package com.example.pkm_forms.Models

import com.example.pkm_forms.Patron.Instruction
import java_cup.symbol
import java.util.LinkedList

class Tree(
    val instructions: LinkedList<Instruction>
){
    var console: String = ""
    var errors: LinkedList<SintaxError> = LinkedList()
    private var symbol: LinkedList<Symbol> = LinkedList()

    fun nuevoSimbolo(newSymbol: LinkedList<Symbol>){
        this.symbol.addAll(newSymbol)
    }

    fun print(value: String){
        console += "$value\n"
    }
}