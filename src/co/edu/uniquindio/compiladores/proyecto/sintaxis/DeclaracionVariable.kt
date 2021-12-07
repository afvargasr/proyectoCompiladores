package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import javafx.scene.control.TreeItem

class DeclaracionVariable(var tipoDato: String, var identificador: String, var valor: Token) {

    override fun toString(): String {
        return "DeclaracionVariable(tipoDato='$tipoDato', identificador='$identificador', valor=$valor)"
    }

    fun getArbolVisual(): TreeItem<String> {

        var raiz = TreeItem("Declaración Variable")

        raiz.children.add(TreeItem("$tipoDato"))
        raiz.children.add(TreeItem("$identificador"))
        raiz.children.add(TreeItem("$valor"))

        return raiz
    }
}