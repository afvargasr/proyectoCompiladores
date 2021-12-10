package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.semantica.TablaSimbolos
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

    fun llenarTablaSimbolos(tablaSimbolos:TablaSimbolos, listaErrores: ArrayList<Error>){
        for (f in listaElementos){
            f.llenarTablaSimbolos(tablaSimbolos, listaErrores, "unidadCompilacion")
        }
    }

    fun analizarSemantica(tablaSimbolos:TablaSimbolos, listaErrores: ArrayList<Error>){
        for (f in listaElementos){
            f.analizarSemantica(tablaSimbolos, listaErrores)
        }
    }

    fun getJavaCode(): String{

        var codigo = ""
            for (e in listaElementos) {
                codigo += e.getJavaCode()
            }

        return codigo
    }
}