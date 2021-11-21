package co.edu.uniquindio.compiladores.proyecto.sintaxis

class ExpresionCadena(var cadena: String, var expresion: Expresion?) {
    override fun toString(): String {
        return "ExpresionCadena(cadena='$cadena', expresion=$expresion)"
    }
}