package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import co.edu.uniquindio.compiladores.proyecto.semantica.TablaSimbolos

class Asignacion(var identificador: Token, var operador: Token, var valor: Token): Sentencia() {
    override fun toString(): String {
        return "Asignacion(identificador=$identificador, operador=$operador, valor=$valor)"
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {

        var s = tablaSimbolos.buscarSimboloValor(identificador.palabra, ambito)

        if(s == null){
            listaErrores.add(Error("El campo ${identificador.palabra} no existe en el ambito $ambito", identificador.fila, identificador.columna))
        }else{
            var tipo = identificador.categoria
            var tipoValor = valor.categoria
            println(tipo)
            println(tipoValor)
            if (tipo != tipoValor){
                listaErrores.add(Error("El campo $tipoValor no concuerda con el tipo $tipoValor", identificador.fila, identificador.columna))
            }
        }
    }


}