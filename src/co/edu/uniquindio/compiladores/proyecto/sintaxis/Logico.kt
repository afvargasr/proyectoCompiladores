package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import co.edu.uniquindio.compiladores.proyecto.semantica.TablaSimbolos

class Logico(var identificador1: Token, var operador: String, var identificador2: Token): Expresion() {
    override fun toString(): String {
        return "Logico(identificador1='$identificador1', operador='$operador', identificador2='$identificador2')"
    }

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito: String): String {
        return "boolean"
    }
}