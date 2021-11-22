package co.edu.uniquindio.compiladores.proyecto.sintaxis

import javafx.scene.control.TreeItem

class ImpresionDatos(var cadenaCaracteres:ExpresionCadena): Sentencia() {
    override fun toString(): String {
        return "ImpresionDatos(cadenaCaracteres=$cadenaCaracteres)"
    }

    override fun getArbolVisual(): TreeItem<String>? {

        var raiz = TreeItem("Sentencia")

        raiz.children.add(TreeItem("Cadena Caracteres: " + "$cadenaCaracteres"))

        return raiz
    }
}