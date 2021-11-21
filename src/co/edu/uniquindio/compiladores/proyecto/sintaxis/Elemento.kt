package co.edu.uniquindio.compiladores.proyecto.sintaxis

class Elemento(
    var listaImports: ArrayList<Import>,
    var declaracionVariable: DeclaracionVariable,
    var listaFuncion: ArrayList<Funcion>
) {
    override fun toString(): String {
        return "Elemento(listaImports=$listaImports, declaracionVariable=$declaracionVariable, listaFuncion=$listaFuncion)"
    }
}