package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.semantica.TablaSimbolos

class Decision(
    var expresion: Expresion,
    var sentenciasIf: ArrayList<Sentencia>,
    var setenciasElse: ArrayList<Sentencia>
): Sentencia() {
    override fun toString(): String {
        return "Decision(expresion=$expresion, sentenciasIf=$sentenciasIf, setenciasElse=$setenciasElse)"
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

    override fun getJavaCode(): String {
        var codigo = "if("+expresion.getJavaCode()+"){"
        for(s in sentenciasIf ) {
            codigo += s.getJavaCode()
        }
        codigo += "}"
        if(setenciasElse!=null)
        {
            codigo += "else{"
            for(s in setenciasElse!!)
            {
                codigo += s.getJavaCode()
            }
            codigo+="}"
        }
        return codigo
    }

}