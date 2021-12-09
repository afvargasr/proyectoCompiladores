package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Token

class Logico(var identificador1: Token, var operador: String, var identificador2: Token) {
    override fun toString(): String {
        return "Logico(identificador1='$identificador1', operador='$operador', identificador2='$identificador2')"
    }
}