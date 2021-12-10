package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Token

class Incremento(var identificador:Token): Sentencia() {
    override fun toString(): String {
        return "Incremento(identificador=$identificador)"
    }
}