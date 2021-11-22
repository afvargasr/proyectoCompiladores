package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Token

class InicializacionArreglo(var tipoDato:Token, var identificador:Token, var valores:ArrayList<String>) {
    override fun toString(): String {
        return "InicializacionArreglo(tipoDato=$tipoDato, identificador=$identificador, valores=$valores)"
    }
}