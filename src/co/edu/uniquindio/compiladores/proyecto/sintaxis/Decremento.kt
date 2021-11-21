package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Token

class Decremento(var identificador: Token) {
    override fun toString(): String {
        return "Decremento(identificador=$identificador)"
    }
}