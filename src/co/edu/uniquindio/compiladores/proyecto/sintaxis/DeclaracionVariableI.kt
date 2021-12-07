package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import javafx.scene.control.TreeItem

class DeclaracionVariableI(var tipoDato: String, var identificador: String, var valor: Token) {
    override fun toString(): String {
        return "DeclaracionVariableI(tipoDato='$tipoDato', identificador='$identificador', valor=$valor)"
    }

    fun getArbolVisual(): TreeItem<String> {

        var raiz = TreeItem("Declaración Variable Inmutable")

        raiz.children.add(TreeItem("$tipoDato"))
        raiz.children.add(TreeItem("$identificador"))
        raiz.children.add(TreeItem("$valor"))

        return raiz
    }
}