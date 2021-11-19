package co.edu.uniquindio.compiladores.proyecto.sintaxis

class Asignacion(var identificador:Token, var operador:Token, var expresion:Expresion) {
    override fun toString(): String {
        return "Asignacion(identificador=$identificador, operador=$operador, expresion=$expresion)"
    }
}