package co.edu.uniquindio.compiladores.proyecto.sintaxis

class DeclaracionArreglo(var tipoDato:Token, var identificador:Token) {
    override fun toString(): String {
        return "DeclaracionArreglo(tipoDato=$tipoDato, identificador=$identificador)"
    }
}