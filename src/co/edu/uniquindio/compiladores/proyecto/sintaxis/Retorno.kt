package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Token

class Retorno(var identificador: Token) {

    override fun toString(): String {
        return "Retorno(identificador='$identificador')"
    }
}