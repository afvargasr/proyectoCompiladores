package co.edu.uniquindio.compiladores.proyecto.sintaxis

import javafx.scene.control.TreeItem

class Elemento(var listaImports: ArrayList<Import>, var listaDeclaracionVariable: ArrayList<DeclaracionVariable>, var listaDeclaracionVariableI: ArrayList<DeclaracionVariableI>, var listaFuncion: ArrayList<Funcion>)
{
    override fun toString(): String {
        return "Elemento(listaImports=$listaImports, listaDeclaracionVariable=$listaDeclaracionVariable, listaDeclaracionVariableI=$listaDeclaracionVariableI, listaFuncion=$listaFuncion)"
    }

    fun getArbolVisual(): TreeItem<String> {

        var raiz = TreeItem("Lista Elementos")

        for (f in listaImports){
            raiz.children.add(f.getArbolVisual())
        }

        for (f in listaDeclaracionVariable){
            raiz.children.add(f.getArbolVisual())
        }

        for (f in listaDeclaracionVariableI){
            raiz.children.add(f.getArbolVisual())
        }

        for (f in listaFuncion){
            raiz.children.add(f.getArbolVisual())
        }

        return raiz
    }

}