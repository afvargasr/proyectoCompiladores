package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Token

class Decremento(var identificador: Token): Sentencia() {
    override fun toString(): String {
        return "Decremento(identificador=$identificador)"
    }
}