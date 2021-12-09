package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import co.edu.uniquindio.compiladores.proyecto.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Funcion(var identificador: Token, var listaParametros : ArrayList<Parametro>, var tipoRetorno: String, var listaSentencias: ArrayList<Sentencia>, var retorno: Retorno? )
{
    override fun toString(): String {
        return "Funcion(identificador='$identificador', listaParametros=$listaParametros, tipoRetorno='$tipoRetorno', listaSentencias=$listaSentencias, retorno=$retorno)"
    }

    fun getArbolVisual(): TreeItem<String> {

        var raiz = TreeItem("Función")

        raiz.children.add(TreeItem("Identificador: " + "$identificador"))

        if (listaParametros != null) {
            for (f in listaParametros) {
                raiz.children.add(f.getArbolVisual())
            }
        }

        raiz.children.add(TreeItem("Tipo Retorno: " + "$tipoRetorno"))

        for (g in listaSentencias){
            raiz.children.add(TreeItem("Sentencia: " + "$g"))
        }


        raiz.children.add(TreeItem("Retorno: " + "${retorno?.identificador}"))

        return raiz
    }

    fun obtenerTiposParametros():ArrayList<String> {
        var lista= ArrayList<String>( )
        for(p in listaParametros){
            lista.add(p.tipoDato.palabra)
        }
        return lista
    }

    fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String){
        tablaSimbolos.guardarSimboloMetodo(identificador.palabra, tipoRetorno, obtenerTiposParametros(), ambito, identificador.fila, identificador.columna)

        for (p in listaParametros){
            tablaSimbolos.guardarSimboloValor(p.identificador.palabra, p.tipoDato.palabra, true, ambito, p.identificador.fila, p.identificador.columna)
        }

        for (s in listaSentencias){
            s.llenarTablaSimbolos(tablaSimbolos, listaErrores, ambito)
        }
    }

    fun analizarSemantica(tablaSimbolos:TablaSimbolos, listaErrores: ArrayList<Error>){
        for(s in listaSentencias){
            s.analizarSemantica(tablaSimbolos, listaErrores, identificador.palabra)
        }
    }
}