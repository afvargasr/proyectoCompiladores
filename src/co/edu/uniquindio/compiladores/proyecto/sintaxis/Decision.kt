package co.edu.uniquindio.compiladores.proyecto.sintaxis

class Decision(
    var expresiones: ArrayList<Expresion>,
    var sentenciasIf: ArrayList<Sentencia>,
    var setenciasElse: ArrayList<Sentencia>
) {
    override fun toString(): String {
        return "Decision(expresiones=$expresiones, sentenciasIf=$sentenciasIf, setenciasElse=$setenciasElse)"
    }
}