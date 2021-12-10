package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Categoria
import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import co.edu.uniquindio.compiladores.proyecto.semantica.TablaSimbolos

class Aritmetico(var identificador1: Token, var operador: Token, var identificador2: Token): Expresion() {
    override fun toString(): String {
        return "Aritmetico(identificador1='$identificador1', operador='$operador', identificador2='$identificador2')"
    }

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito: String): String {
        if (identificador1 != null && identificador2!= null){
            if (identificador1.categoria == Categoria.DECIMAL || identificador2.categoria == Categoria.DECIMAL){
                return "float"
            }else{
                return "int"
            }
        }
        return ""
    }

    override fun getJavaCode(): String
    {
        if(identificador1!=null && identificador2!=null)
        {
            return identificador1.getJavaCode()+ operador.getJavaCode()+identificador2.getJavaCode()
        }

        return " "
    }
}