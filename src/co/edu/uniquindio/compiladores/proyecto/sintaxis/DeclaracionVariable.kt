package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import co.edu.uniquindio.compiladores.proyecto.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class DeclaracionVariable(var tipoDato: String, var identificador: Token, var valor: Token):Sentencia() {

    override fun toString(): String {
        return "DeclaracionVariable(tipoDato='$tipoDato', identificador='$identificador', valor=$valor)"
    }

    override fun getArbolVisual(): TreeItem<String> {

        var raiz = TreeItem("Declaración Variable")

        raiz.children.add(TreeItem("$tipoDato"))
        raiz.children.add(TreeItem("$identificador"))
        raiz.children.add(TreeItem("$valor"))

        return raiz
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String){
        tablaSimbolos.guardarSimboloValor(identificador.palabra, tipoDato, true, ambito, identificador.fila, identificador.columna)
    }

    override fun analizarSemantica(tablaSimbolos:TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String){
        analizarSemantica(tablaSimbolos, listaErrores, identificador.palabra)
    }
}