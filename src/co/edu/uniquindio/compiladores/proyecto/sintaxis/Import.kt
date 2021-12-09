package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import javafx.scene.control.TreeItem

class Import(var identificador: Token)
{
    override fun toString(): String {
        return "Import(identificador='$identificador')"
    }

    fun getArbolVisual(): TreeItem<String> {

        var raiz = TreeItem("Import")

        raiz.children.add(TreeItem("Import: " + "$identificador"))

        return raiz

    }
}