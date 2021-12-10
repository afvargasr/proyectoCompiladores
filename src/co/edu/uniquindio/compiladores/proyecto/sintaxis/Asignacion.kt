package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Categoria
import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import co.edu.uniquindio.compiladores.proyecto.semantica.TablaSimbolos

class Asignacion(var identificador: Token, var operador: Token, var valor: Token): Sentencia() {
    override fun toString(): String {
        return "Asignacion(identificador=$identificador, operador=$operador, valor=$valor)"
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String){
        var s = tablaSimbolos.buscarSimboloValor(identificador.palabra, ambito)
        var tipo = ""
        if (valor.categoria == Categoria.ENTERO){
            tipo = "int"
        }else if (valor.categoria == Categoria.DECIMAL){
            tipo = "float"
        }else if (valor.categoria == Categoria.CADENA_CARACTERES){
            tipo = "string"
        } else{
            tipo="null"
        }

        if (s == null) {
            listaErrores.add(Error("El campo ${identificador.palabra} no se encuentra en el ambito $ambito", identificador.fila, identificador.columna))
        }else{
            if (s.tipo != tipo){
                listaErrores.add(Error("No se puede asignar un tipo de dato $tipo a un campo tipo ${s.tipo}", identificador.fila, identificador.columna))
            }
        }
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {
        analizarSemantica(tablaSimbolos, listaErrores, identificador.palabra)
    }

}