package co.edu.uniquindio.compiladores.proyecto.sintaxis

class Logico(var identificador1: String, var operador: String, var identificador2: String)
{
    override fun toString(): String {
        return "Logico(identificador1='$identificador1', operador='$operador', identificador2='$identificador2')"
    }
}