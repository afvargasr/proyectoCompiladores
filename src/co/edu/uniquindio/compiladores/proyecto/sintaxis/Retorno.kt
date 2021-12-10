package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Categoria
import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import co.edu.uniquindio.compiladores.proyecto.semantica.TablaSimbolos

class Retorno(var identificador: Token) {

    override fun toString(): String {
        return "Retorno(identificador='$identificador')"
    }

    fun obtenerTipoRetorno():String{
        if (identificador.categoria == Categoria.DECIMAL){
            return "float"
        }else if (identificador.categoria == Categoria.ENTERO){
            return "int"
        }else if (identificador.categoria == Categoria.CADENA_CARACTERES){
            return "string"
        }else if (identificador.categoria == Categoria.IDENTIFICADOR){
            ""
        }
        return ""
    }

    fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String){
        analizarSemantica(tablaSimbolos, listaErrores, identificador.palabra)
    }


    fun getJavaCode(): String {

        return "return " + identificador.getJavaCode() + ";"
    }


}