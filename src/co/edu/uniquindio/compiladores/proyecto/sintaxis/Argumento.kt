package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Token

class Argumento(var identificador: Token) {
    override fun toString(): String {
        return "Argumento(identificador=$identificador)"
    }
}