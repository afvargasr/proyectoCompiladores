package co.edu.uniquindio.compiladores.proyecto.sintaxis

class Aritmetico(var identificador1: String, var operador: String, var identificador2: String)
{
    override fun toString(): String {
        return "Aritmetico(identificador1='$identificador1', operador='$operador', identificador2='$identificador2')"
    }
}