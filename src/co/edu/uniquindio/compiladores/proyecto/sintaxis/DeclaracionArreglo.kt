package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Token

class DeclaracionArreglo(var tipoDato: Token, var identificador: Token) {
    override fun toString(): String {
        return "DeclaracionArreglo(tipoDato=$tipoDato, identificador=$identificador)"
    }
}