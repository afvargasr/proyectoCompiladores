package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.semantica.TablaSimbolos

class Decision(
    var expresiones: Expresion,
    var sentenciasIf: ArrayList<Sentencia>,
    var setenciasElse: ArrayList<Sentencia>
): Sentencia() {
    override fun toString(): String {
        return "Decision(expresiones=$expresiones, sentenciasIf=$sentenciasIf, setenciasElse=$setenciasElse)"
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String){
        for (s in sentenciasIf){
            s.llenarTablaSimbolos(tablaSimbolos, listaErrores, ambito)
        }
        if(setenciasElse!= null){
            for (s in setenciasElse!!){
                s.llenarTablaSimbolos(tablaSimbolos, listaErrores, ambito)
            }
        }

    }
}