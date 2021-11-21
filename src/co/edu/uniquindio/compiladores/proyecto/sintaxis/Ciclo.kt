package co.edu.uniquindio.compiladores.proyecto.sintaxis

class Ciclo(var expresion: Logico, var sentencias:ArrayList<Sentencia>) {
    override fun toString(): String {
        return "Ciclo(expresion=$expresion, sentencias=$sentencias)"
    }
}