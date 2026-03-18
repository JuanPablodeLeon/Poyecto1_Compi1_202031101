package com.example.pkm_forms.Models

import com.example.pkm_forms.Patron.Instruction
import java.util.LinkedList

class Tree(
    val instructions: LinkedList<Instruction>
){
    var console: String = ""
    var errors: LinkedList<SintaxError> = LinkedList()

    fun print(value: String){
        console += "$value\n"
    }
}