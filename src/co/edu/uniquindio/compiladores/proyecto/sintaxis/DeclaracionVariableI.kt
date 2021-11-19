package co.edu.uniquindio.compiladores.proyecto.sintaxis

class DeclaracionVariableI(var tipoDato: String, var identificador: String, var expresion: Expresion )
{
    override fun toString(): String {
        return "DeclaracionVariableI(tipoDato='$tipoDato', identificador='$identificador', expresion=$expresion)"
    }
}