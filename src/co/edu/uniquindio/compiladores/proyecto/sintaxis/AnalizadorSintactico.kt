package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Token

class AnalizadorSintactico(var listaToken:ArrayList<Token>) {

    var posicionActual = 0
    var tokenActual = listaToken[0]

    fun obtenerSiguienteToken() {
        posicionActual++

        if (posicionActual < listaToken.size) {
            tokenActual = listaToken[posicionActual]
        }
    }

    /**
     * <UnidadDeCompilacion> ::= <ListaElementos>
     */
    fun esUnidadDeCompilacion(): UnidadDeCompilacion? {
        val listaElementos: ArrayList<Elemento> = esListaElementos()
        return if (listaElementos.size > 0) {
            UnidadDeCompilacion(listaElementos)
        } else null
    }

    /**
     * <ListaElementos> ::= <Elemento>[<ListaElementos>]
     */
    fun esListaElementos(): ArrayList<Elemento> {
        var listaElementos = ArrayList<Elemento>()
        var elemento = esElemento()
        while (elemento != null) {
            listaElementos.add(elemento)
            elemento = esElemento()
        }
        return listaElementos
    }

    /**
     * <Elemento> ::= [<ListaImports>] | <DeclaracionVariable> | <ListaFunciones>
     */
    fun esElemento(): Elemento? {
        return null
    }
}