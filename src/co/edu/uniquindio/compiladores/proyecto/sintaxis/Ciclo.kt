package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.semantica.TablaSimbolos

class Ciclo(var expresion: Logico, var sentencias: ArrayList<Sentencia>): Sentencia() {
    override fun toString(): String {
        return "Ciclo(expresion=$expresion, sentencias=$sentencias)"
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String){
        for (s in sentencias){
            s.llenarTablaSimbolos(tablaSimbolos, listaErrores, ambito)
        }
    }

    override fun getJavaCode(): String {

        var codigo = "while ("+expresion.getJavaCode()+ "){"
        for(s in sentencias ){
            codigo+= s.getJavaCode()
        }
        codigo += "}"
        return codigo

    }
}