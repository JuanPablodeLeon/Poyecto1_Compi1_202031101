package com.example.pkm_forms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pkm_forms.Analizadores.Forms.FormsLexer
import com.example.pkm_forms.Analizadores.Forms.ParserForms
import com.example.pkm_forms.Models.Symbol
import com.example.pkm_forms.Models.TableSymbol
import com.example.pkm_forms.Models.Tree
import com.example.pkm_forms.Patron.Instruction
import com.example.pkm_forms.ui.theme.Pkm_FormsTheme
import java.io.StringReader
import java.util.LinkedList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Pkm_FormsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CompiLogicoApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CompiLogicoApp(modifier: Modifier = Modifier) {

    var inputText by remember { mutableStateOf("MOSTRAR \"12 > 22\" " +
            "\n number a = 12 + 8" +
            "\n number b = 2 + 5" +
            "\n MOSTRAR a  + b") }
    var consoleText by remember { mutableStateOf("Consola lista...\n") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // 🔹 titulo
        Text(
            text = "CompiLogico",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🟢 run
        Button(
            // aca se corre el codigo
            onClick = {
                consoleText = "===== EJECUCIÓN =====\n"

                try {
                    val lexer = FormsLexer(StringReader(inputText))
                    val parser = ParserForms(lexer)
                    val result: Symbol? = null
                    try {
                        val result = parser.parse()
                        val ast = Tree(result.value as LinkedList<Instruction>)
                        val table = TableSymbol();
                        consoleText = "TERMINAL\n"

                        if (result != null && lexer.listaErrorLexico.isEmpty() && parser.listErrorSintactico.isEmpty()){

                            consoleText += "--------- TEXTO SIN ERRORES -----------"
                            for(instruction in ast.instructions) {
                                instruction.interprete(ast, table);
                            }

                            consoleText += ast.console
                        }

                        if(lexer.listaErrorLexico.isNotEmpty()){ //en caso de existir errores lexicos se mostraran
                            consoleText += "\n---- ERROR LEXICO ----\n"
                            for(error in lexer.listaErrorLexico){
                                consoleText += "${error.mensaje} en :  línea ${error.linea} | columna ${error.columna} \n"
                            }
                        }
                        //verificar porque no muestra errores sintacticos
                        if(parser.listErrorSintactico.isNotEmpty()){ //en caso de existir errores sintacticos se mostraran
                            consoleText += "\n---- ERROR SINTACTICO ----\n"
                            for(error in parser.listErrorSintactico){
                                consoleText += "${error.mensaje} en :  línea ${error.linea} | columna ${error.columna} \n"
                            }
                        }
                    }catch (e: Exception){

                    }

                } catch (e: Exception) {
                    if (e.message != null && !e.message!!.contains("Couldn't repair")){
                        consoleText += "\n ---- ERROR ---- \n" + e.message
                    }
                }
            }
            ,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50)
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Run", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 📝 area de texto principal
        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text("Escribe tu código aquí...") },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 💻 consola
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.6f),
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFF1E1E1E),
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = consoleText,
                    color = Color(0xFF00FF00),
                    fontSize = 14.sp
                )
            }
        }
    }
}