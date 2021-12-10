package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Token

class LecturaDatos(var identificador: Token): Sentencia() {
    override fun toString(): String {
        return "LecturaDatos(identificador=$identificador)"
    }

    override fun getJavaCode(): String {
        return identificador.getJavaCode() + " = System.console().readLine();"
    }
}