package co.edu.uniquindio.compiladores.proyecto.sintaxis

import javafx.scene.control.TreeItem

class DeclaracionVariableI(var tipoDato: String, var identificador: String, var expresion: Expresion) {
    override fun toString(): String {
        return "DeclaracionVariableI(tipoDato='$tipoDato', identificador='$identificador', expresion=$expresion)"
    }

    fun getArbolVisual(): TreeItem<String> {

        var raiz = TreeItem("Declaración Variable Inmutable")

        raiz.children.add(TreeItem("$tipoDato"))
        raiz.children.add(TreeItem("$identificador"))
        raiz.children.add(TreeItem("$expresion"))

        return raiz
    }
}