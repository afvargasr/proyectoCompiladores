package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.semantica.TablaSimbolos

class ExpresionCadena(var cadena: String, var expresion: Expresion?): Expresion() {
    override fun toString(): String {
        return "ExpresionCadena(cadena='$cadena', expresion=$expresion)"
    }

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito: String): String {
        return "string"
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {
        if (expresion!=null){
            expresion!!.analizarSemantica(tablaSimbolos,listaErrores,ambito)
        }
    }
}