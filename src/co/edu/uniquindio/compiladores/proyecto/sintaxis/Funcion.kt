package co.edu.uniquindio.compiladores.proyecto.sintaxis

import javafx.scene.control.TreeItem

class Funcion(var identificador: String, var listaParametros : ArrayList<Parametro>, var tipoRetorno: String, var listaSentencias: ArrayList<Sentencia>, var retorno: Retorno? )
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

}