package co.edu.uniquindio.compiladores.proyecto

import co.edu.uniquindio.compiladores.proyecto.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.proyecto.sintaxis.AnalizadorSintactico

fun main() {
    //var codigoFuente = "12 1.2 .5 3. !hola1 hola2 + - / * % < > <= >= != == & | !! = += -= ++ -- ( ) [ ] { } #hola3hola4# ; 'd' , . **hola mundo* int : _hola mundo 2\n"
    var codigoFuente = "import casa; var int n = 0; cons int N = 1; fun funcion (int n, int N):void { N = 2; var int a = 2; cons int b = 3; if( 1!! 2) { int [] array;  } int array = {1,2}; hola = read; array(a,b); a++; b--; return a; }"
    val lexico = AnalizadorLexico(codigoFuente)
    lexico.analizar()
    println(lexico.listaTokens)
    val sintaxis = AnalizadorSintactico(lexico.listaTokens)
    println(sintaxis.esUnidadDeCompilacion())
    println(sintaxis.listaErrores)
}