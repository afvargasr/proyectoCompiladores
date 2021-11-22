package co.edu.uniquindio.compiladores.proyecto.sintaxis

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
}
