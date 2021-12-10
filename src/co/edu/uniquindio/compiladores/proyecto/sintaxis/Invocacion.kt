package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Categoria
import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import co.edu.uniquindio.compiladores.proyecto.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Invocacion(var identificador: Token, var argumentos: ArrayList<Argumento>) : Sentencia() {
    override fun toString(): String {
        return "Invocacion(identificador='$identificador', argumentos=$argumentos)"
    }

    override fun getArbolVisual(): TreeItem<String>? {

        var raiz = TreeItem("Invocacion")

        raiz.children.add(TreeItem("Identificador: " + "$identificador"))

        for (f in argumentos){
            raiz.children.add(f.getArbolVisual())
        }
        return raiz
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {
        analizarSemantica(tablaSimbolos, listaErrores, identificador.palabra)
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String){
        var listaArgumentos = obtenerTiposArgumentos()
        var s = tablaSimbolos.buscarSimboloMetodo(identificador.palabra, listaArgumentos)
        if (s == null) {
            listaErrores.add(Error("El método ${identificador.palabra} no se encuentra en el ambito $ambito", identificador.fila, identificador.columna))
        }
    }

    fun obtenerTiposArgumentos():ArrayList<String> {
        var lista= ArrayList<String>( )
        for(a in argumentos){
            if (a.identificador.categoria == Categoria.DECIMAL){
                lista.add("float")
            } else if (a.identificador.categoria == Categoria.CADENA_CARACTERES){
                lista.add("string")
            } else if (a.identificador.categoria == Categoria.ENTERO){
                lista.add("int")
            }
        }
        return lista
    }

    override fun getJavaCode(): String
    {
        var codigo = identificador.getJavaCode() + "("
        for(a in argumentos)
        {
            codigo+= a.getJavaCode() + ","
        }
        codigo+= codigo.substring(0,codigo.length-1)
        codigo+= ")"

        return codigo

    }

}