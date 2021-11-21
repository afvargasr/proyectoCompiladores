package co.edu.uniquindio.compiladores.proyecto.sintaxis

import javafx.scene.control.TreeItem

class UnidadDeCompilacion(var listaElementos: ArrayList<Elemento>) {
    override fun toString(): String {
        return "UnidadDeCompilacion(listaElementos=$listaElementos)"
    }

    fun getArbolVisual():TreeItem<String>{

        var raiz = TreeItem("Unidad de Compilación")

        for (f in listaElementos){
            raiz.children.add(f.getArbolVisual())
        }
        return raiz
    }
}