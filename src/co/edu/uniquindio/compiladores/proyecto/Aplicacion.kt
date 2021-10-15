package co.edu.uniquindio.compiladores.proyecto

fun main() {
    //12 1.2 .5 3. !hola1 hola2 + - / * % < > <= >= != == & | !! = += -= ++ -- ( ) [ ] { } #hola3# ; 'd' , . **hola mundo* @int :
    var codigoFuente = "_hola mundo 2"
    val lexico = AnalizadorLexico(codigoFuente)
    lexico.analizar()
    println(lexico.listaTokens)
}