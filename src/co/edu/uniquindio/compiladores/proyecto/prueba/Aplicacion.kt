package co.edu.uniquindio.compiladores.proyecto

import co.edu.uniquindio.compiladores.proyecto.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.proyecto.sintaxis.AnalizadorSintactico

fun main() {
    //var codigoFuente = "12 1.2 .5 3. !hola1 hola2 + - / * % < > <= >= != == & | !! = += -= ++ -- ( ) [ ] { } #hola3hola4# ; 'd' , . **hola mundo* int : _hola mundo 2\n"
    var codigoFuente = "import casa; var int n = 0+1; cons int N = 1+1; fun funcion ():void { N = 2; }"
    val lexico = AnalizadorLexico(codigoFuente)
    lexico.analizar()
    println(lexico.listaTokens)
    val sintaxis = AnalizadorSintactico(lexico.listaTokens)
    println(sintaxis.esUnidadDeCompilacion())
    println(sintaxis.listaErrores)
}