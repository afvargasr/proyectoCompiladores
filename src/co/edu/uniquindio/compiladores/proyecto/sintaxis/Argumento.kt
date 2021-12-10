package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import javafx.scene.control.TreeItem

class Argumento(var identificador: Token) {
    override fun toString(): String {
        return "Argumento(identificador=$identificador)"
    }

    fun getArbolVisual(): TreeItem<String>? {

        var raiz = TreeItem("Argumento")

        raiz.children.add(TreeItem("Valor: " + "$identificador"))

        return raiz
    }


    fun getJavaCode(): String {

        return identificador.getJavaCode()

    }

}