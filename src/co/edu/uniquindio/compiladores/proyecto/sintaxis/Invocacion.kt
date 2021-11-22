package co.edu.uniquindio.compiladores.proyecto.sintaxis

import javafx.scene.control.TreeItem

class Invocacion(var identificador: String, var argumentos: ArrayList<Argumento>) : Sentencia() {
    override fun toString(): String {
        return "Invocacion(identificador='$identificador', argumentos=$argumentos)"
    }

    override fun getArbolVisual(): TreeItem<String>? {

        var raiz = TreeItem("Invocacion")

        raiz.children.add(TreeItem("Identificador: " + "$identificador"))

        for (f in argumentos){
            raiz.children.add(f.getArbolVisual())
        }
        return raiz
    }
}