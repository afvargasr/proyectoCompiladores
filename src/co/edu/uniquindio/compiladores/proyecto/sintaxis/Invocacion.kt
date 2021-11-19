package co.edu.uniquindio.compiladores.proyecto.sintaxis

class Invocacion(var identificador: String, var argumentos: ArrayList<Argumento>) {
    override fun toString(): String {
        return "Invocacion(identificador='$identificador', argumentos=$argumentos)"
    }
}