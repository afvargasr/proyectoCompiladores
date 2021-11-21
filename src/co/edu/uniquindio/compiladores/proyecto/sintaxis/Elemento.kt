package co.edu.uniquindio.compiladores.proyecto.sintaxis

import javafx.scene.control.TreeItem

class Elemento(var listaImports: ArrayList<Import>, var declaracionVariable: DeclaracionVariable , var listaFuncion: ArrayList<Funcion>)
{
    override fun toString(): String {
        return "Elemento(listaImports=$listaImports, declaracionVariable=$declaracionVariable, listaFuncion=$listaFuncion)"
    }

    fun getArbolVisual(): TreeItem<String> {

        var raiz = TreeItem("Función")

        return raiz
    }
}