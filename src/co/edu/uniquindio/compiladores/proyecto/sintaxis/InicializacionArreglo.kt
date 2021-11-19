package co.edu.uniquindio.compiladores.proyecto.sintaxis

class InicializacionArreglo(var tipoDato:Token, var identificador:Token, var valores:ArrayList<String>) {
    override fun toString(): String {
        return "InicializacionArreglo(tipoDato=$tipoDato, identificador=$identificador, valores=$valores)"
    }
}