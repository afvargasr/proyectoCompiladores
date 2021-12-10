package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

open class Sentencia {

    open fun getArbolVisual(): TreeItem<String>? {
        return TreeItem("Sentencia")
    }

    open fun llenarTablaSimbolos(tablaSimbolos:TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String){

    }

    open fun analizarSemantica(tablaSimbolos:TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String){

    }

    fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>) {

    }
}
