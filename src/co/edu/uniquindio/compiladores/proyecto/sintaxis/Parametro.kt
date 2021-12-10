package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import co.edu.uniquindio.compiladores.proyecto.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Parametro(var tipoDato: Token, var identificador: Token) {

    override fun toString(): String {
        return "Parametro(tipoDato='$tipoDato', identificador='$identificador')"
    }

    fun getArbolVisual(): TreeItem<String> {

        var raiz = TreeItem("Parámetro")

        raiz.children.add(TreeItem("Tipo Dato: " + "${tipoDato.palabra}"))

        raiz.children.add(TreeItem("Identificador: " + "${identificador.palabra}"))

        return raiz
    }

    open fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String){

    }

    open fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String){

    }

    fun getJavaCode(): String
    {
        return tipoDato.getJavaCode() + " " + identificador.getJavaCode()
    }
}