package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import co.edu.uniquindio.compiladores.proyecto.semantica.TablaSimbolos

class InicializacionArreglo(var tipoDato:Token, var identificador:Token, var valores:ArrayList<String>): Sentencia() {
    override fun toString(): String {
        return "InicializacionArreglo(tipoDato=$tipoDato, identificador=$identificador, valores=$valores)"
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String){
        tablaSimbolos.guardarSimboloValor(identificador.palabra, tipoDato.palabra, true, ambito, identificador.fila, identificador.columna)
    }

    override fun getJavaCode(): String {
        var codigo = tipoDato.getJavaCode() + "[] " + identificador.getJavaCode() + "="
        if (valores != null) {
            codigo += "{"
            for (v in valores) {
                codigo += v + ","
            }
            codigo = codigo.substring(0, codigo.length-1)
            codigo += "}"
        }
        codigo += ";"
        return codigo
    }
}