package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

open class Expresion {

    open fun getArbolVisual():TreeItem<String>{
        return TreeItem("Expresión")
    }

    open fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String){}

    open fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito: String): String{
        return ""
    }
}