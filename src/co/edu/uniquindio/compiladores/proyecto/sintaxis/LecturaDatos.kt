package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Token

class LecturaDatos(var identificador: Token, var valor: Token) {
    override fun toString(): String {
        return "LecturaDatos(identificador=$identificador, valor=$valor)"
    }
}