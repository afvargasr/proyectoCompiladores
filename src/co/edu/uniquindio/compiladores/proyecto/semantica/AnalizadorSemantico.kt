package co.edu.uniquindio.compiladores.proyecto.semantica

import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.sintaxis.UnidadDeCompilacion

class AnalizadorSemantico (var unidadDeCompilacion: UnidadDeCompilacion){

    val listaErrores : ArrayList<Error> = ArrayList()
    val tablaSimbolos : TablaSimbolos = TablaSimbolos(listaErrores)

    fun llenarTablaSimbolos(){
        unidadDeCompilacion.llenarTablaSimbolos(tablaSimbolos, listaErrores)
    }

    fun analizarSemantica(){
        unidadDeCompilacion.analizarSemantica(tablaSimbolos, listaErrores)
    }
}