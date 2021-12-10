package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import co.edu.uniquindio.compiladores.proyecto.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Import(var identificador: Token)
{
    override fun toString(): String {
        return "Import(identificador='$identificador')"
    }

    fun getArbolVisual(): TreeItem<String> {

        var raiz = TreeItem("Import")

        raiz.children.add(TreeItem("Import: " + "$identificador"))

        return raiz

    }

    fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String){
        tablaSimbolos.guardarSimboloValor(identificador.palabra, identificador.categoria.toString(), true, ambito, identificador.fila, identificador.columna)
    }

    fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>){
        analizarSemantica(tablaSimbolos, listaErrores)
    }

    fun getJavaCode(): String
    {
        return "import "+ identificador.getJavaCode() + ";"

    }
}