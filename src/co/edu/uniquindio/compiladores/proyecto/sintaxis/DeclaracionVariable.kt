package co.edu.uniquindio.compiladores.proyecto.sintaxis

class DeclaracionVariable(var tipoDato: String, var identificador: String, var expresion: Expresion) {

    override fun toString(): String {
        return "DeclaracionVariable(tipoDato='$tipoDato', identificador='$identificador', expresion=$expresion)"
    }
}