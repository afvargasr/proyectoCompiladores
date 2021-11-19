package co.edu.uniquindio.compiladores.proyecto.sintaxis

class Parametro(var tipoDato:Token, var identificador:Token) {

    override fun toString(): String {
        return "Parametro(tipoDato='$tipoDato', identificador='$identificador')"
    }
}