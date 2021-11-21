package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Token

class Parametro(var tipoDato: Token, var identificador: Token) {

    override fun toString(): String {
        return "Parametro(tipoDato='$tipoDato', identificador='$identificador')"
    }
}