package co.edu.uniquindio.compiladores.proyecto.sintaxis

import javafx.scene.control.TreeItem

class Elemento(var listaImports: ArrayList<Import>, var declaracionVariable: DeclaracionVariable , var listaFuncion: ArrayList<Funcion>)
{
    override fun toString(): String {
        return "Elemento(listaImports=$listaImports, declaracionVariable=$declaracionVariable, listaFuncion=$listaFuncion)"
    }

    fun getArbolVisual(): TreeItem<String> {

        var raiz = TreeItem("Lista Elementos")

        for (f in listaImports){
            raiz.children.add(f.getArbolVisual())
        }

        raiz.children.add(TreeItem("Declaracion de Variable"))

        raiz.children.add(TreeItem("${declaracionVariable.tipoDato}"))
        raiz.children.add(TreeItem("${declaracionVariable.identificador}"))
        raiz.children.add(TreeItem("${declaracionVariable.expresion}"))

        for (f in listaFuncion){
            raiz.children.add(f.getArbolVisual())
        }

        return raiz
    }

}