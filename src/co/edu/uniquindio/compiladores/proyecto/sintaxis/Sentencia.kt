package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

open class Sentencia(var sentencia: Any?) {

    constructor() : this(null)

    override fun toString(): String {
        return "Sentencia(sentencia=$sentencia)"
    }

    open fun getArbolVisual(): TreeItem<String>? {

        var raiz = TreeItem("Sentencia")

        raiz.children.add(TreeItem("Sentencia: " + "$sentencia"))

        return raiz
    }

    open fun llenarTablaSimbolos(tablaSimbolos:TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String){

    }

    open fun analizarSemantica(tablaSimbolos:TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String){

    }
}
