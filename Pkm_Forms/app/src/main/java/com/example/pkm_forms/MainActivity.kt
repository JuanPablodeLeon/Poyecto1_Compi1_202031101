package com.example.pkm_forms

import android.content.Intent
import android.os.Bundle
import android.provider.DocumentsContract
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.platform.LocalContext
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
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringReader
import java.util.LinkedList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Pkm_FormsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CompiLogico(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CompiLogico(modifier: Modifier = Modifier) {
    var inputText by remember { mutableStateOf(
        "MOSTRAR \"12 > 22\" \n number a = 12 + 8\n number b = 2 + 5\n MOSTRAR a  + b"
    ) }
    var consoleText by remember { mutableStateOf("Consola lista...\n") }

    val context = LocalContext.current

    fun getFileNameFromUri(uri: android.net.Uri): String? {
        var fileName: String? = null
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(DocumentsContract.Document.COLUMN_DISPLAY_NAME)
                if (nameIndex != -1) {
                    fileName = it.getString(nameIndex)
                }
            }
        }
        if (fileName.isNullOrEmpty()) {
            fileName = uri.lastPathSegment
        }
        return fileName
    }

    val openFileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            val uri = result.data?.data
            if (uri != null) {
                try {
                    val fileName = getFileNameFromUri(uri)
                    if (fileName == null || !fileName.endsWith(".forms", ignoreCase = true)) {
                        Toast.makeText(context, "Por favor selecciona un archivo con extensión .forms", Toast.LENGTH_LONG).show()
                        return@rememberLauncherForActivityResult
                    }
                    context.contentResolver.openInputStream(uri)?.use { inputStream ->
                        val reader = BufferedReader(InputStreamReader(inputStream))
                        val content = reader.readText()
                        inputText = content
                        Toast.makeText(context, "Archivo cargado", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, "Error al leer archivo", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    val saveFileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            val uri = result.data?.data
            if (uri != null) {
                try {
                    context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                        outputStream.write(inputText.toByteArray())
                        Toast.makeText(context, "Archivo guardado", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, "Error al guardar archivo", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Pkm Forms",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Botón Cargar
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                        addCategory(Intent.CATEGORY_OPENABLE)
                        type = "*/*"
                    }
                    openFileLauncher.launch(intent)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.weight(1f)
            ) {
                Text("Cargar", fontSize = 14.sp)
            }

            // Botón Ejecutar
            Button(
                onClick = {
                    consoleText = "===== EJECUCIÓN =====\n"

                    try {
                        val lexer = FormsLexer(StringReader(inputText))
                        val parser = ParserForms(lexer)
                        var result: java_cup.runtime.Symbol? = null
                        //para evitar el error generico y usar los sintacticos y lexicos
                        try {
                            result = parser.parse()
                        } catch (e : Exception){

                        }
                        //Verifica si el valor no es nulo y si ambas listas de errores se encuentran vacias
                        if (result != null && lexer.listaErrorLexico.isEmpty() && parser.listErrorSintactico.isEmpty()){
                            //crea arbol ast a base lo leido por parser
                            val ast = Tree(result.value as LinkedList<Instruction>)
                            val table = TableSymbol() //tabla de simbolos

                            //itera todos los elementos del arbol para poder interpretarlos
                            for (instruction in ast.instructions) {
                                instruction.interprete(ast, table)
                            }

                            consoleText += "TERMINAL\n"
                            consoleText += ast.console

                        }

                        if(lexer.listaErrorLexico.isNotEmpty()){ //en caso de existir errores lexicos se mostraran
                            consoleText += "\n---- ERROR LEXICO ----\n"
                            for(error in lexer.listaErrorLexico){
                                consoleText += "${error.mensaje} en :  línea ${error.linea} | columna ${error.columna} \n"
                            }
                        }

                        if(parser.listErrorSintactico.isNotEmpty()){ //en caso de existir errores sintacticos se mostraran
                            consoleText += "\n---- ERROR SINTACTICO ----\n"
                            for(error in parser.listErrorSintactico){
                                consoleText += "${error.mensaje} en :  línea ${error.linea} | columna ${error.columna} \n"
                            }
                        }

                    } catch (e: Exception) {
                        if (e.message != null && !e.message!!.contains("Couldn't repair")){
                            consoleText += "\n ---- ERROR ---- \n" + e.message
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.weight(1f)
            ) {
                Text("Ejecutar", fontSize = 12.sp)
            }

            // Botón Guardar
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                        addCategory(Intent.CATEGORY_OPENABLE)
                        type = "*/*"   // Para evitar que el sistema añada .txt automáticamente
                        putExtra(Intent.EXTRA_TITLE, "codigo.forms")   // Nombre sugerido con extensión .forms
                    }
                    saveFileLauncher.launch(intent)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.weight(1f)
            ) {
                Text("Guardar", fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text("Escribe tu código aquí...") },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = MaterialTheme.shapes.medium
        )

        Spacer(modifier = Modifier.height(14.dp))

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.6f),
            shape = MaterialTheme.shapes.medium,
            color = Color(0xFF1E1E1E),
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
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