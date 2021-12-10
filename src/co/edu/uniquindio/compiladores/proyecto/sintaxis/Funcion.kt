package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import co.edu.uniquindio.compiladores.proyecto.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Funcion(var identificador: Token, var listaParametros : ArrayList<Parametro>, var tipoRetorno: Token, var listaSentencias: ArrayList<Sentencia>, var retorno: Retorno? )
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
        tablaSimbolos.guardarSimboloMetodo(identificador.palabra, tipoRetorno.palabra, obtenerTiposParametros(), ambito, identificador.fila, identificador.columna)

        for (p in listaParametros){
            tablaSimbolos.guardarSimboloValor(p.identificador.palabra, p.tipoDato.palabra, true, identificador.palabra, p.identificador.fila, p.identificador.columna)
        }

        for (s in listaSentencias){
            s.llenarTablaSimbolos(tablaSimbolos, listaErrores,  identificador.palabra)
        }
    }

    fun analizarSemantica(tablaSimbolos:TablaSimbolos, listaErrores: ArrayList<Error>){
        for(s in listaSentencias){
            s.analizarSemantica(tablaSimbolos, listaErrores, identificador.palabra)
        }

        for(p in listaParametros){
            p.analizarSemantica(tablaSimbolos, listaErrores, identificador.palabra)
        }

        retorno?.analizarSemantica(tablaSimbolos, listaErrores, "Funcion")
        if (tipoRetorno != null){
            var retornoFinal = retorno?.obtenerTipoRetorno()

            if (tipoRetorno.palabra != retornoFinal){
                listaErrores.add(Error("El tipo de retorno $retorno no coincide con el retorno $retornoFinal", identificador.fila, identificador.columna))
            }else{
                println("Probando")
            }
        }

    }

    fun getJavaCode(): String
    {

        var codigo = ""

        var tipo = "void"
        if (tipoRetorno != null) {
            tipo = tipoRetorno.getJavaCode()
        }

        //Main de Java
        if(identificador.getJavaCode() == "principal" || identificador.getJavaCode() == "main")
        {
            codigo= "public static void main(String [] args){"
        }else
        {
            codigo= "static " + tipo +" "+ identificador.getJavaCode() + "("
            if(listaParametros.isNotEmpty()) {
                for(p in listaParametros)
                {
                    codigo+= p.getJavaCode()+","
                }
                codigo = codigo.substring(0,codigo.length-1)
            }
            codigo+= "){"
        }
        for (s in listaSentencias)
        {
            codigo+=s.getJavaCode()

        }
        if (retorno != null) {
            codigo += retorno!!.getJavaCode()
        }
        codigo+= "}"

        return codigo

    }

}