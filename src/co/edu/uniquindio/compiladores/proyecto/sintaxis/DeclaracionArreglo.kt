package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import co.edu.uniquindio.compiladores.proyecto.semantica.TablaSimbolos

class DeclaracionArreglo(var tipoDato: Token, var identificador: Token): Sentencia() {
    override fun toString(): String {
        return "DeclaracionArreglo(tipoDato=$tipoDato, identificador=$identificador)"
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String){
        tablaSimbolos.guardarSimboloValor(identificador.palabra, tipoDato.palabra, true, ambito, identificador.fila, identificador.columna)
    }

    override fun getJavaCode(): String {
        return tipoDato.getJavaCode() + "[] " + identificador.getJavaCode() + ";"
    }
}