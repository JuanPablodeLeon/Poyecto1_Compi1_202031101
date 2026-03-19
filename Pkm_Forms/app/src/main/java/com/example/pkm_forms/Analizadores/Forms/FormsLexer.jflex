package com.example.pkm_forms.Analizadores.Forms;

import java_cup.runtime.Symbol;
import java.util.*;
import com.example.pkm_forms.Models.SintaxError;
import com.example.pkm_forms.Models.Token;

%%
%cup
%class FormsLexer
%public
%char
%line
%column
%full
%state CADENA

%{
    public LinkedList<SintaxError> listaErrorLexico = new LinkedList<>();

    private StringBuilder stringBuffer = new StringBuilder();

    public static LinkedList<Token> listaTokens = new LinkedList<>();
    private void agregarToken(String lexema, String tipo){
        listaTokens.add(new Token(lexema, tipo, yyline, yycolumn));
    }
%}

%init{
    yyline = 1;
    yycolumn = 1;
    listaErrorLexico = new LinkedList<>();
    listaTokens = new LinkedList<>();

%init}

/* ----- DEFINICIONES ----- */
COMILLA = "\""
ENTEROS = [0-9]+
LETRAS = [a-zA-Z]+
IDENTIFICADOR = ({LETRAS}|"_")+({LETRAS}|{ENTEROS}|"_")*
//DEFINICION_ID = {IDENTIFICADOR}
DECIMAL = {ENTEROS}"."{ENTEROS}
HEXADECIMAL = "#"[0-9A-F]{6}
WHO_IS_THAT_POKEMON = "who_is_that_pokemon"
COLOR = {COMILLA}"color"{COMILLA}
BACKGROUND_COLOR = {COMILLA}"background color"{COMILLA}
FONT_FAMILY = {COMILLA}"font family"{COMILLA}
TEXT_SIZE = {COMILLA}"text size"{COMILLA}
BORDER = {COMILLA}"border"{COMILLA}
RANGO = ".."

/* ---- EMOJIS ---- */
EMO_SMILE = "@[:"(")")+"]"
SMILE = "@[:smile:]"
EMO_SAD = "@[:"("(")+"]"
SAD = "@[:sad:]"
EMO_SERIOUS = "@[:"("|")+"]"
SERIOUS = "@[:serious:]"
EMO_HEART = "@["("<")+("3")+"]"
HEART = "@[:heart:]"
STAR = "@[:star:]"
MULTI_STAR_UNO = "@[:star:"{ENTEROS}"]"
MULTI_STAR_DOS = "@[:star-"{ENTEROS}"]"
EMO_CAT = "@[:^^:]"
CAT = "@[:cat:]"
/* ---- TOKENS ----*/

//Operadores Aritmeticos
SUMA = "+"
RESTA = "-"
DIVISION = "/"
MULTIPLICACION = "*"
POTENCIACION = "^"
MODULO = "%"
LPAREN = "("
RPAREN = ")"

//Operadores de Comparacion
IGUALDAD = "=="
DIFERENTE = "!!"
MAYOR_QUE = ">"
MENOR_QUE = "<"
MAYOR_IGUAL_QUE = ">="
MENOR_IGUAL_QUE = "<="

// Operadores Logicos
AND = "&&"
OR = "||"
NOT = "~"

//Resto de Simbolos
PUNTO = "."
COMA = ","
IGUAL = "="
LLLAVE = "["
RLLAVE = "]"
LCORCHETE = "{"
RCORCHETE = "}"
QUESTION_MARK = "?"
DOS_PUNTOS = ":"

comentario_bloque = "/*""*"*([^*] | \*+ [^/*] )*"*"+"/"
espacios = [ \t\n\r\f]+

%%
/* ----- IGNORAR ESPACIOS EN BLANCO Y COMENTARIOS ----- */
<YYINITIAL> {espacios} { }
<YYINITIAL> "$".* { }
<YYINITIAL> {comentario_bloque} { }

//Incio de Cadena de texto
<YYINITIAL> {COMILLA} { stringBuffer.setLength(0); yybegin(CADENA);}


/* ---- PALABRAS RESERVADAS ---- */

<YYINITIAL> number { agregarToken(yytext(), "NUMBER"); return new Symbol(sym.NUMBER, yyline, yycolumn, yytext());}
<YYINITIAL> string { agregarToken(yytext(), "STRING"); return new Symbol(sym.STRING, yyline, yycolumn, yytext());}
<YYINITIAL> special { agregarToken(yytext(), "SPECIAL"); return new Symbol(sym.SPECIAL, yyline, yycolumn, yytext());}

<YYINITIAL> draw { agregarToken(yytext(), "DRAW"); return new Symbol(sym.DRAW, yyline, yycolumn, yytext());}

<YYINITIAL> RED { agregarToken(yytext(), "RED"); return new Symbol(sym.RED, yyline, yycolumn, yytext());}
<YYINITIAL> BLUE { agregarToken(yytext(), "BLUE"); return new Symbol(sym.BLUE, yyline, yycolumn, yytext());}
<YYINITIAL> GREEN { agregarToken(yytext(), "GREEN"); return new Symbol(sym.GREEN, yyline, yycolumn, yytext());}
<YYINITIAL> PURPLE { agregarToken(yytext(), "PURPLE"); return new Symbol(sym.PURPLE, yyline, yycolumn, yytext());}
<YYINITIAL> SKY { agregarToken(yytext(), "SKY"); return new Symbol(sym.SKY, yyline, yycolumn, yytext());}
<YYINITIAL> YELLOW { agregarToken(yytext(), "YELLOW"); return new Symbol(sym.YELLOW, yyline, yycolumn, yytext());}
<YYINITIAL> BLACK { agregarToken(yytext(), "BLACK"); return new Symbol(sym.BLACK, yyline, yycolumn, yytext());}
<YYINITIAL> WHITE { agregarToken(yytext(), "WHITE"); return new Symbol(sym.WHITE, yyline, yycolumn, yytext());}

<YYINITIAL> width { agregarToken(yytext(), "WIDTH"); return new Symbol(sym.WIDTH, yyline, yycolumn, yytext());}
<YYINITIAL> height { agregarToken(yytext(), "HEIGHT"); return new Symbol(sym.HEIGHT, yyline, yycolumn, yytext());}
<YYINITIAL> label { agregarToken(yytext(), "LABEL"); return new Symbol(sym.LABEL, yyline, yycolumn, yytext());}

<YYINITIAL> SECTION { agregarToken(yytext(), "SECTION"); return new Symbol(sym.SECTION, yyline, yycolumn, yytext());}

<YYINITIAL> pointX { agregarToken(yytext(), "POINTX"); return new Symbol(sym.POINTX, yyline, yycolumn, yytext());}
<YYINITIAL> pointY { agregarToken(yytext(), "POINTY"); return new Symbol(sym.POINTY, yyline, yycolumn, yytext());}

<YYINITIAL> orientation { agregarToken(yytext(), "ORIENTATION"); return new Symbol(sym.ORIENTATION, yyline, yycolumn, yytext());}
<YYINITIAL> VERTICAL { agregarToken(yytext(), "VERTICAL"); return new Symbol(sym.VERTICAL, yyline, yycolumn, yytext());}
<YYINITIAL> HORIZONTAL { agregarToken(yytext(), "HORIZONTAL"); return new Symbol(sym.HORIZONTAL, yyline, yycolumn, yytext());}

<YYINITIAL> elements { agregarToken(yytext(), "ELEMENTS"); return new Symbol(sym.ELEMENTS, yyline, yycolumn, yytext());}

<YYINITIAL> styles { agregarToken(yytext(), "STYLES"); return new Symbol(sym.STYLES, yyline, yycolumn, yytext());}
<YYINITIAL> {COLOR} { agregarToken(yytext(), "COLOR"); return new Symbol(sym.COLOR, yyline, yycolumn, yytext());}
<YYINITIAL> {BACKGROUND_COLOR} { agregarToken(yytext(), "BACKGROUND_COLOR"); return new Symbol(sym.BACKGROUND_COLOR, yyline, yycolumn, yytext());}

<YYINITIAL> {FONT_FAMILY} { agregarToken(yytext(), "FONT_FAMILY"); return new Symbol(sym.FONT_FAMILY, yyline, yycolumn, yytext());}
<YYINITIAL> MONO { agregarToken(yytext(), "MONO"); return new Symbol(sym.MONO, yyline, yycolumn, yytext());}
<YYINITIAL> SANS_SERIF { agregarToken(yytext(), "SANS_SERIF"); return new Symbol(sym.SANS_SERIF, yyline, yycolumn, yytext());}
<YYINITIAL> CURSIVE { agregarToken(yytext(), "CURSIVE"); return new Symbol(sym.CURSIVE, yyline, yycolumn, yytext());}

<YYINITIAL> {TEXT_SIZE} { agregarToken(yytext(), "TEXT_SIZE"); return new Symbol(sym.TEXT_SIZE, yyline, yycolumn, yytext());}

<YYINITIAL> {BORDER} { agregarToken(yytext(), "BORDER"); return new Symbol(sym.BORDER, yyline, yycolumn, yytext());}
<YYINITIAL> LINE { agregarToken(yytext(), "LINE"); return new Symbol(sym.LINE, yyline, yycolumn, yytext());}
<YYINITIAL> DOTTED { agregarToken(yytext(), "DOTTED"); return new Symbol(sym.DOTTED, yyline, yycolumn, yytext());}
<YYINITIAL> DOUBLE { agregarToken(yytext(), "DOUBLE"); return new Symbol(sym.DOUBLE, yyline, yycolumn, yytext());}

<YYINITIAL> TABLE { agregarToken(yytext(), "TABLE"); return new Symbol(sym.TABLE, yyline, yycolumn, yytext());}
<YYINITIAL> TEXT { agregarToken(yytext(), "TEXT"); return new Symbol(sym.TEXT, yyline, yycolumn, yytext());}
<YYINITIAL> content { agregarToken(yytext(), "CONTENT"); return new Symbol(sym.CONTENT, yyline, yycolumn, yytext());}

<YYINITIAL> OPEN_QUESTION { agregarToken(yytext(), "OPEN_QUESTION"); return new Symbol(sym.OPEN_QUESTION, yyline, yycolumn, yytext());}

<YYINITIAL> DROP_QUESTION { agregarToken(yytext(), "DROP_QUESTION"); return new Symbol(sym.DROP_QUESTION, yyline, yycolumn, yytext());}
<YYINITIAL> options { agregarToken(yytext(), "OPTIONS"); return new Symbol(sym.OPTIONS, yyline, yycolumn, yytext());}
<YYINITIAL> correct { agregarToken(yytext(), "CORRECT"); return new Symbol(sym.CORRECT, yyline, yycolumn, yytext());}
<YYINITIAL> {WHO_IS_THAT_POKEMON} { agregarToken(yytext(), "WHO_IS_THAT_POKEMON"); return new Symbol(sym.WHO_IS_THAT_POKEMON, yyline, yycolumn, yytext());}

<YYINITIAL> SELECT_QUESTION { agregarToken(yytext(), "SELECT_QUESTION"); return new Symbol(sym.SELECT_QUESTION, yyline, yycolumn, yytext());}
<YYINITIAL> MULTIPLE_QUESTION { agregarToken(yytext(), "MULTIPLE_QUESTION"); return new Symbol(sym.MULTIPLE_QUESTION, yyline, yycolumn, yytext());}

//Bloques de Codigo
<YYINITIAL> IF { agregarToken(yytext(), "IF"); return new Symbol(sym.IF, yyline, yycolumn, yytext());}
<YYINITIAL> ELSE { agregarToken(yytext(), "ELSE"); return new Symbol(sym.ELSE, yyline, yycolumn, yytext());}
<YYINITIAL> WHILE { agregarToken(yytext(), "WHILE"); return new Symbol(sym.WHILE, yyline, yycolumn, yytext());}
<YYINITIAL> DO { agregarToken(yytext(), "DO"); return new Symbol(sym.DO, yyline, yycolumn, yytext());}
<YYINITIAL> FOR { agregarToken(yytext(), "FOR"); return new Symbol(sym.FOR, yyline, yycolumn, yytext());}
<YYINITIAL> in { agregarToken(yytext(), "IN"); return new Symbol(sym.IN, yyline, yycolumn, yytext());}
<YYINITIAL> {RANGO} { agregarToken(yytext(), "RANGO"); return new Symbol(sym.RANGO, yyline, yycolumn, yytext());}

//Operadores Aritmeticos
<YYINITIAL> {SUMA} { agregarToken(yytext(), "SUMA"); return new Symbol(sym.SUMA, yyline, yycolumn, yytext());}
<YYINITIAL> {RESTA} { agregarToken(yytext(), "RESTA"); return new Symbol(sym.RESTA, yyline, yycolumn, yytext());}
<YYINITIAL> {DIVISION} { agregarToken(yytext(), "DIVISION"); return new Symbol(sym.DIVISION, yyline, yycolumn, yytext());}
<YYINITIAL> {MULTIPLICACION} { agregarToken(yytext(), "MULTIPLICACION"); return new Symbol(sym.MULTIPLICACION, yyline, yycolumn, yytext());}
<YYINITIAL> {POTENCIACION} { agregarToken(yytext(), "POTENCIACION"); return new Symbol(sym.POTENCIACION, yyline, yycolumn, yytext());}
<YYINITIAL> {MODULO} { agregarToken(yytext(), "MODULO"); return new Symbol(sym.MODULO, yyline, yycolumn, yytext());}
<YYINITIAL> {LPAREN} { agregarToken(yytext(), "LPAREN"); return new Symbol(sym.LPAREN, yyline, yycolumn, yytext());}
<YYINITIAL> {RPAREN} { agregarToken(yytext(), "RPAREN"); return new Symbol(sym.RPAREN, yyline, yycolumn, yytext());}

//Operadores de Comparacion
<YYINITIAL> {IGUALDAD} { agregarToken(yytext(), "IGUALDAD"); return new Symbol(sym.IGUALDAD, yyline, yycolumn, yytext());}
<YYINITIAL> {DIFERENTE} { agregarToken(yytext(), "DIFERENTE"); return new Symbol(sym.DIFERENTE, yyline, yycolumn, yytext());}
<YYINITIAL> {MAYOR_IGUAL_QUE} { agregarToken(yytext(), "MAYOR_IGUAL_QUE"); return new Symbol(sym.MAYOR_IGUAL_QUE, yyline, yycolumn, yytext());}
<YYINITIAL> {MENOR_IGUAL_QUE} { agregarToken(yytext(), "MENOR_IGUAL_QUE"); return new Symbol(sym.MENOR_IGUAL_QUE, yyline, yycolumn, yytext());}
<YYINITIAL> {MAYOR_QUE} { agregarToken(yytext(), "MAYOR_QUE"); return new Symbol(sym.MAYOR_QUE, yyline, yycolumn, yytext());}
<YYINITIAL> {MENOR_QUE} { agregarToken(yytext(), "MENOR_QUE"); return new Symbol(sym.MENOR_QUE, yyline, yycolumn, yytext());}

//Operadores Logicos
<YYINITIAL> {AND} { agregarToken(yytext(), "AND"); return new Symbol(sym.AND, yyline, yycolumn, yytext());}
<YYINITIAL> {OR} { agregarToken(yytext(), "OR"); return new Symbol(sym.OR, yyline, yycolumn, yytext());}
<YYINITIAL> {NOT} { agregarToken(yytext(), "NOT"); return new Symbol(sym.NOT, yyline, yycolumn, yytext());}

//Simbolos varios
<YYINITIAL> {PUNTO} { agregarToken(yytext(), "PUNTO"); return new Symbol(sym.PUNTO, yyline, yycolumn, yytext());}
<YYINITIAL> {COMA} { agregarToken(yytext(), "COMA"); return new Symbol(sym.COMA, yyline, yycolumn, yytext());}
<YYINITIAL> {IGUAL} { agregarToken(yytext(), "IGUAL"); return new Symbol(sym.IGUAL, yyline, yycolumn, yytext());}
<YYINITIAL> {LLLAVE} { agregarToken(yytext(), "LLLAVE"); return new Symbol(sym.LLLAVE, yyline, yycolumn, yytext());}
<YYINITIAL> {RLLAVE} { agregarToken(yytext(), "RLLAVE"); return new Symbol(sym.RLLAVE, yyline, yycolumn, yytext());}
<YYINITIAL> {LCORCHETE} { agregarToken(yytext(), "LCORCHETE"); return new Symbol(sym.LCORCHETE, yyline, yycolumn, yytext());}
<YYINITIAL> {RCORCHETE} { agregarToken(yytext(), "RCORCHETE"); return new Symbol(sym.RCORCHETE, yyline, yycolumn, yytext());}
<YYINITIAL> {QUESTION_MARK} { agregarToken(yytext(), "QUESTION_MARK"); return new Symbol(sym.QUESTION_MARK, yyline, yycolumn, yytext());}
<YYINITIAL> {DOS_PUNTOS} { agregarToken(yytext(), "DOS_PUNTOS"); return new Symbol(sym.DOS_PUNTOS, yyline, yycolumn, yytext());}
//<YYINITIAL> {COMILLA} { agregarToken(yytext(), "COMILLA"); return new Symbol(sym.COMILLA, yyline, yycolumn, yytext());}

//Forma Hexadecimal
<YYINITIAL> {HEXADECIMAL} { agregarToken(yytext(), "HEXADECIMAL"); return new Symbol(sym.HEXADECIMAL, yyline, yycolumn, yytext());}

//Numeros
<YYINITIAL> {ENTEROS} { agregarToken(yytext(), "ENTEROS"); return new Symbol(sym.ENTEROS, yyline, yycolumn, yytext());}
<YYINITIAL> {DECIMAL} { agregarToken(yytext(), "DECIMAL"); return new Symbol(sym.DECIMAL, yyline, yycolumn, yytext());}

//Letras
<YYINITIAL> {IDENTIFICADOR} { agregarToken(yytext(), "IDENTIFICADOR"); return new Symbol(sym.IDENTIFICADOR, yyline, yycolumn, yytext());}

//<YYINITIAL> {DEFINICION_ID} { agregarToken(yytext(), "DEFINICION_ID"); return new Symbol(sym.DEFINICION_ID, yyline, yycolumn, yytext());}


//Cierre de cadena de texto
<CADENA> {COMILLA} {String contenido = stringBuffer.toString();
                    if(!contenido.isEmpty()){agregarToken(contenido, "TEXTO"); yybegin(YYINITIAL); return new Symbol(sym.TEXTO, yyline, yycolumn, yytext());}
                    yybegin(YYINITIAL); return new Symbol(sym.TEXTO, yyline, yycolumn, "");}

//emojis
<CADENA> {MULTI_STAR_UNO} { agregarToken(yytext(), "MULTI_STAR_UNO"); return new Symbol(sym.MULTI_STAR_UNO, yyline, yycolumn, yytext());}
<CADENA> {MULTI_STAR_DOS} { agregarToken(yytext(), "MULTI_STAR_DOS"); return new Symbol(sym.MULTI_STAR_DOS, yyline, yycolumn, yytext());}
<CADENA> {STAR} { agregarToken(yytext(), "STAR"); return new Symbol(sym.STAR, yyline, yycolumn, yytext());}
<CADENA> {SMILE} { agregarToken(yytext(), "SMILE"); return new Symbol(sym.SMILE, yyline, yycolumn, yytext());}
<CADENA> {SAD} { agregarToken(yytext(), "SAD"); return new Symbol(sym.SAD, yyline, yycolumn, yytext());}
<CADENA> {SERIOUS} { agregarToken(yytext(), "SERIOUS"); return new Symbol(sym.SERIOUS, yyline, yycolumn, yytext());}
<CADENA> {HEART} { agregarToken(yytext(), "HEART"); return new Symbol(sym.HEART, yyline, yycolumn, yytext());}
<CADENA> {CAT} { agregarToken(yytext(), "CAT"); return new Symbol(sym.CAT, yyline, yycolumn, yytext());}
<CADENA> {EMO_SMILE} { agregarToken(yytext(), "EMO_SMILE"); return new Symbol(sym.EMO_SMILE, yyline, yycolumn, yytext());}
<CADENA> {EMO_SAD} { agregarToken(yytext(), "EMO_SAD"); return new Symbol(sym.EMO_SAD, yyline, yycolumn, yytext());}
<CADENA> {EMO_SERIOUS} { agregarToken(yytext(), "EMO_SERIOUS"); return new Symbol(sym.EMO_SERIOUS, yyline, yycolumn, yytext());}
<CADENA> {EMO_HEART} { agregarToken(yytext(), "EMO_HEART"); return new Symbol(sym.EMO_HEART, yyline, yycolumn, yytext());}
<CADENA> {EMO_CAT} { agregarToken(yytext(), "EMO_CAT"); return new Symbol(sym.EMO_CAT, yyline, yycolumn, yytext());}

<CADENA> [^\"\@]+ { stringBuffer.append(yytext());}

//Error Lexico
<YYINITIAL> . { agregarToken(yytext(), "ERROR"); listaErrorLexico.add(new SintaxError("LEXICO", "Simbolo: "+ yytext()+" no existente en el lenguaje", yyline, yycolumn));}



