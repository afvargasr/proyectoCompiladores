package co.edu.uniquindio.compiladores.proyecto.sintaxis

import javafx.scene.control.TreeItem

class Import(var identificador: String)
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