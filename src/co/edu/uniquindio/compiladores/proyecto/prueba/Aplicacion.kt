package co.edu.uniquindio.compiladores.proyecto

import co.edu.uniquindio.compiladores.proyecto.lexico.AnalizadorLexico

fun main() {
    //
    var codigoFuente = "12 1.2 .5 3. !hola1 hola2 + - / * % < > <= >= != == & | !! = += -= ++ -- ( ) [ ] { } #hola3# ; 'd' , . **hola mundo* @int : _hola mundo 2\n"
    val lexico = AnalizadorLexico(codigoFuente)
    lexico.analizar()
    println(lexico.listaTokens)
}