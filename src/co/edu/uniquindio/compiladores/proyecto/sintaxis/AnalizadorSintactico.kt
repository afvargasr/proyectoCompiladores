package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Categoria
import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.lexico.Token

class AnalizadorSintactico(var listaToken: ArrayList<Token>) {

    var posicionActual = 0
    var tokenActual = listaToken[posicionActual]
    var listaErrores = ArrayList<Error>()

    fun obtenerSiguienteToken() {
        posicionActual++

        if (posicionActual < listaToken.size) {
            tokenActual = listaToken[posicionActual]
        }
    }

    fun hacerBT(posicionInicial: Int) {
        posicionActual = posicionInicial
        tokenActual = listaToken[posicionActual]
    }

    /**
     * Reporte de errores
     */
    fun reportarError(mensaje: String) {
        listaErrores.add(Error(mensaje, tokenActual.fila, tokenActual.columna))
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

        var listaImports = esListaImports()

        var listaDeclaracionVariable = esListaDeclaracionVariable()

        var listaDeclaracionVariableI = esListaDeclaracionVariableI()

        var listaFuncion = esListaFunciones()

        if (listaFuncion.size > 0) {
            // el elemento esta bien escrito
            return Elemento(listaImports, listaDeclaracionVariable, listaDeclaracionVariableI, listaFuncion)
        } else {
            reportarError("Falta las funciones en el elemento")
        }

        return null
    }

    /**
     * <ListaImports> ::= <Import>[<ListaImports>]
     */
    fun esListaImports(): ArrayList<Import> {

        var listaImports = ArrayList<Import>()
        var import = esImport()

        while (import != null) {
            listaImports.add(import)
            import = esImport()
        }
        return listaImports
    }

    /**
     * <Import> ::= import identificador???;???
     */
    fun esImport(): Import? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.palabra == "import") {
            obtenerSiguienteToken()

            if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                var identificador = tokenActual

                obtenerSiguienteToken()

                if (tokenActual.categoria == Categoria.FIN_SENTENCIA) {
                    obtenerSiguienteToken()
                    //El import esta bien escrito
                    return Import(identificador)
                } else {
                    reportarError("No se encuentra el fin de sentencia")
                }
            } else {
                reportarError("No se encuentra el identificador")
            }
        }

        return null
    }

    /**
     * <ListaDeclaracionVariable> ::= <DeclaracionVariable>[<ListaDeclaracionVariable>]
     */
    fun esListaDeclaracionVariable(): ArrayList<DeclaracionVariable> {

        var listaDeclaracionVariable = ArrayList<DeclaracionVariable>()
        var declaracionVariable = esDeclaracionVariable()

        while (declaracionVariable != null) {
            listaDeclaracionVariable.add(declaracionVariable)
            declaracionVariable = esDeclaracionVariable()
        }
        return listaDeclaracionVariable
    }

    /**
     * <DeclaracionVariableMutable> ::= var <tipoDato> identificador " = " <Expresion>???;???
     */
    fun esDeclaracionVariable(): DeclaracionVariable? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.palabra == "var") {
            obtenerSiguienteToken()

            var tipoDato = esTipoDato()

            if (tipoDato != null) {
                if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                    var identificador = tokenActual
                    obtenerSiguienteToken()

                    if (tokenActual.categoria == Categoria.OPERADOR_ASIGNACION && tokenActual.palabra == "=") {
                        obtenerSiguienteToken()

                        if (tokenActual.categoria == Categoria.IDENTIFICADOR || tokenActual.categoria == Categoria.ENTERO || tokenActual.categoria == Categoria.DECIMAL || tokenActual.categoria == Categoria.CADENA_CARACTERES) {
                            var valor = tokenActual
                            obtenerSiguienteToken()

                            if (tokenActual.categoria == Categoria.FIN_SENTENCIA) {
                                obtenerSiguienteToken()
                                //la declaracion de la variable esta bien escrita
                                return DeclaracionVariable(tipoDato, identificador, valor)
                            } else {
                                reportarError("Falta el fin de sentencia")
                            }
                        } else {
                            reportarError("No se encuentra el valor")
                        }
                    } else {
                        reportarError("Falta el operador '='")
                    }
                } else {
                    reportarError("No se encuentra el identificador")
                }
            } else {
                reportarError("Falta el tipo de dato en la declaraci??n de la variable")
            }
        }
        return null
    }

    /**
     * <ListaExpresiones> ::= <Expresion> | <ListaExpresiones>
     */
    fun esListaExpresion(): ArrayList<Expresion> {
        var listaExpresion = ArrayList<Expresion>()
        var expresion = esExpresion()

        while (expresion != null) {
            listaExpresion.add(expresion)
            expresion = esExpresion()
        }
        return listaExpresion
    }

    /**
     * <Expresion> ::= [<Relacional>] | [<Logico>] | [<Aritmetico>] | [<ExpresionCadena>]
     */
    fun esExpresion(): Expresion? {
        var relacional = esRelacional()
        if (relacional != null) {
            return relacional
        }

        var logico = esLogico()
        if (logico != null) {
            return logico
        }

        var aritmetico = esAritmetico()
        if (aritmetico != null) {
            return aritmetico
        }

        var expresionCadena = esExpresionCadena()
        if (expresionCadena != null) {
            return expresionCadena
        }

        reportarError("No se detect?? ninguna expresi??n")
        return null
    }

    /**
     *<Relacional>  ::= identificador OperadorRelacional  identificador
     */
    fun esRelacional(): Relacional? {
        var posicionInicial = posicionActual
        if (tokenActual.categoria == Categoria.IDENTIFICADOR || tokenActual.categoria == Categoria.ENTERO || tokenActual.categoria == Categoria.DECIMAL) {
            var identificador1 = tokenActual
            obtenerSiguienteToken()

            if (tokenActual.categoria == Categoria.OPERADOR_RELACIONAL) {
                var operador = tokenActual
                obtenerSiguienteToken()

                if (tokenActual.categoria == Categoria.IDENTIFICADOR || tokenActual.categoria == Categoria.ENTERO || tokenActual.categoria == Categoria.DECIMAL) {
                    var identificador2 = tokenActual
                    obtenerSiguienteToken()

                    //La expresi??n relacional esta bien escrita
                    return Relacional(identificador1, operador, identificador2)
                } else {
                    reportarError("No se encuentra el segundo identificador")
                }
            }
        }
        hacerBT(posicionInicial)
        return null
    }

    /**
     * <Logico> ::= valor OperadorLogico valor
     */
    fun esLogico(): Logico? {
        var posicionInicial = posicionActual
        if (tokenActual.categoria == Categoria.IDENTIFICADOR || tokenActual.categoria == Categoria.ENTERO || tokenActual.categoria == Categoria.DECIMAL) {
            var identificador1 = tokenActual
            obtenerSiguienteToken()

            if (tokenActual.categoria == Categoria.OPERADOR_LOGICO) {
                var operador = tokenActual
                obtenerSiguienteToken()

                if (tokenActual.categoria == Categoria.IDENTIFICADOR || tokenActual.categoria == Categoria.ENTERO || tokenActual.categoria == Categoria.DECIMAL) {
                    var identificador2 = tokenActual
                    obtenerSiguienteToken()

                    //La expresi??n relacional esta bien escrita
                    return Logico(identificador1, operador, identificador2)
                } else {
                    reportarError("No se encuentra es el segundo identificador")
                }
            }
        }
        hacerBT(posicionInicial)
        return null
    }

    /**
     * <Aritmetico> ::= valor OperadorAritmetico valor
     */
    fun esAritmetico(): Aritmetico? {
        var posicionInicial = posicionActual
        if (tokenActual.categoria == Categoria.ENTERO || tokenActual.categoria == Categoria.DECIMAL) {
            var identificador1 = tokenActual
            obtenerSiguienteToken()

            if (tokenActual.categoria == Categoria.OPERADOR_ARTIMETICO) {
                var operador = tokenActual
                obtenerSiguienteToken()

                if (tokenActual.categoria == Categoria.ENTERO || tokenActual.categoria == Categoria.DECIMAL) {
                    var identificador2 = tokenActual
                    obtenerSiguienteToken()

                    //La expresi??n relacional esta bien escrita
                    return Aritmetico(identificador1, operador, identificador2)
                } else {
                    reportarError("No se encuentra el segundo valor")
                }
            }
        }
        hacerBT(posicionInicial)
        return null
    }

    /**
     * <ExpresionCadena> ::= CadenaCaracteres [+ <Expresion>]
     */
    fun esExpresionCadena(): ExpresionCadena? {
        if (tokenActual.categoria == Categoria.CADENA_CARACTERES) {
            var cadena1 = tokenActual

            obtenerSiguienteToken()

            if (tokenActual.categoria == Categoria.OPERADOR_ARTIMETICO && tokenActual.palabra == "+") {

                var expresion = esExpresion()

                return ExpresionCadena(cadena1, expresion)

            } else {
                return ExpresionCadena(cadena1, null)
            }
        }
        return null
    }

    /**
     * <ListaDeclaracionVariableI> ::= <DeclaracionVariableI>[<ListaDeclaracionVariableI>]
     */
    fun esListaDeclaracionVariableI(): ArrayList<DeclaracionVariableI> {

        var listaDeclaracionVariableI = ArrayList<DeclaracionVariableI>()
        var declaracionVariableI = esDeclaracionVariableI()

        while (declaracionVariableI != null) {
            listaDeclaracionVariableI.add(declaracionVariableI)
            declaracionVariableI = esDeclaracionVariableI()
        }
        return listaDeclaracionVariableI
    }

    /**
     * <DeclaracionVariableInmutable> ::= cons <tipoDato> identificador " = " <Expresion>???;???
     */
    fun esDeclaracionVariableI(): DeclaracionVariableI? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.palabra == "cons") {
            obtenerSiguienteToken()

            var tipoDato = esTipoDato()

            if (tipoDato != null) {

                if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                    var identificador = tokenActual
                    obtenerSiguienteToken()

                    if (tokenActual.categoria == Categoria.OPERADOR_ASIGNACION && tokenActual.palabra == "=") {
                        obtenerSiguienteToken()

                        if (tokenActual.categoria == Categoria.IDENTIFICADOR || tokenActual.categoria == Categoria.ENTERO || tokenActual.categoria == Categoria.DECIMAL || tokenActual.categoria == Categoria.CADENA_CARACTERES) {
                            var valor = tokenActual
                            obtenerSiguienteToken()

                            if (tokenActual.categoria == Categoria.FIN_SENTENCIA) {
                                obtenerSiguienteToken()
                                //la declaracion de la variable esta bien escrita
                                return DeclaracionVariableI(tipoDato, identificador, valor)
                            } else {
                                reportarError("Falta el fin de sentencia")
                            }
                        }
                    }
                } else {
                    reportarError("Falta el tipo de dato en la declaraci??n de la variable inmutable")
                }
            }
        }
        return null
    }

    /**
     * <ListaFunciones>  ::=  <Funcion> [<ListaFunciones>]
     */
    fun esListaFunciones(): ArrayList<Funcion> {

        var listaFunciones = ArrayList<Funcion>()
        var funcion = esFuncion()

        while (funcion != null) {
            listaFunciones.add(funcion)
            funcion = esFuncion()
        }
        return listaFunciones
    }

    /**
     * <Funcion> ::= fun identificador ???(??? [<ListaParametros>] ???)???
     * :<TipoRetorno> ???{??? <ListaSentencias> ???}???
     */
    fun esFuncion(): Funcion? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.palabra == "fun") {
            obtenerSiguienteToken()

            if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                var identificador = tokenActual
                obtenerSiguienteToken()

                if (tokenActual.categoria == Categoria.PARENTESIS_IZQUIERDO) {
                    obtenerSiguienteToken()

                    var listaParametros = esListaParametros()

                    if (tokenActual.categoria == Categoria.PARENTESIS_DERECHO) {
                        obtenerSiguienteToken()

                        if (tokenActual.categoria == Categoria.DOS_PUNTOS) {
                            obtenerSiguienteToken()

                            var tipoRetorno = esTipoRetorno()

                            if (tipoRetorno != null) {
                                if (tokenActual.categoria == Categoria.LLAVE_IZQUIERDA) {
                                    obtenerSiguienteToken()

                                    var listaSentencias = esListaSentencias()

                                    if (listaSentencias.size > 0) {

                                        var retorno = esRetorno()

                                        if (tokenActual.categoria == Categoria.LLAVE_DERECHA) {
                                            obtenerSiguienteToken()
                                            //la funci??n esta bien escrita
                                            return Funcion(
                                                identificador,
                                                listaParametros,
                                                tipoRetorno,
                                                listaSentencias,
                                                retorno
                                            )
                                        } else {
                                            reportarError("Falta la llave derecha")
                                        }
                                    } else {
                                        reportarError("Falta la lista de sentencias en la funci??n")
                                    }
                                } else {
                                    reportarError("Falta la llave izquierda")
                                }
                            } else {
                                reportarError("Falta el tipo de retorno en la funci??n")
                            }
                        } else {
                            reportarError("Faltan los dos puntos antes del tipo de retorno en la funci??n")
                        }
                    } else {
                        reportarError("La lista de parametros debe cerrar con parentesis derecho")
                    }
                } else {
                    reportarError("La lista de parametros debe iniciar con parentesis izquierdo")
                }

            } else {
                reportarError("La funci??n debe llevar un identificador")
            }
        }
        return null
    }

    /**
     * <TipoRetorno> ::= int | float | boolean | string | void
     */
    fun esTipoRetorno(): Token? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA) {
            if (tokenActual.palabra == "int" || tokenActual.palabra == "float"
                || tokenActual.palabra == "boolean" || tokenActual.palabra == "string" || tokenActual.palabra == "void"
            ) {
                val tipoRetorno = tokenActual
                obtenerSiguienteToken()
                return tipoRetorno
            }
        }
        return null
    }

    /**
     * <TipoDato> ::= int | boolean | string | float
     */
    fun esTipoDato(): Token? {

        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA) {

            if (tokenActual.palabra == "int" || tokenActual.palabra == "float"
                || tokenActual.palabra == "boolean" || tokenActual.palabra == "string"
            ) {
                val tipoDato = tokenActual
                obtenerSiguienteToken()
                return tipoDato
            }

        }
        return null
    }

    /**
     * <ListaParametros> ::= <Par??metro> [???,???<ListaParametros>]
     */
    fun esListaParametros(): ArrayList<Parametro> {
        var listaParametros = ArrayList<Parametro>()
        var parametro = esParametro()
        while (parametro != null) {
            listaParametros.add(parametro)
            if (tokenActual.categoria == Categoria.SEPARADOR) {
                obtenerSiguienteToken()
                parametro = esParametro()
            } else {
                if (tokenActual.categoria != Categoria.PARENTESIS_DERECHO) {
                    reportarError("Falta una coma en la lista de par??metros")
                }
                break
            }
        }
        return listaParametros
    }

    /**
     * <Par??metro> ::= <TipoDato> identificador
     */
    fun esParametro(): Parametro? {
        var posicionInicial = posicionActual
        val tipoDato = esTipoDato()
        if (tipoDato != null) {
            if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                var identificador = tokenActual
                obtenerSiguienteToken()
                return Parametro(tipoDato, identificador)
            } else {
                reportarError("El nombre del parametro esta incorrecto")
            }
        }
        hacerBT(posicionInicial)
        return null
    }

    /**
     * <Retorno> ::= return identificador???;???
     */
    fun esRetorno(): Retorno? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.palabra == "return") {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.IDENTIFICADOR || tokenActual.categoria == Categoria.ENTERO || tokenActual.categoria == Categoria.DECIMAL || tokenActual.categoria == Categoria.CADENA_CARACTERES) {
                var identificador = tokenActual
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.FIN_SENTENCIA) {
                    obtenerSiguienteToken()
                    return Retorno(identificador)
                } else {
                    reportarError("No se encuentra el fin de la sentencia")
                }
            } else {
                reportarError("El nombre del identificador esta incorrecto")
            }
        }

        return null
    }

    /**
     * <ListaSentencias> ::= <Sentencia> [<ListaSentencias>]
     */
    fun esListaSentencias(): ArrayList<Sentencia> {
        var listaSentencias = ArrayList<Sentencia>()
        var sentencia = esSentencia()
        while (sentencia != null) {
            listaSentencias.add(sentencia)
            sentencia = esSentencia()
        }
        return listaSentencias
    }

    /**
     * <Sentencia> ::= <Decision> | <DeclaracionVariableMutable> | <DeclaracionVariableInmutable> | <Asignacion> | <ImpresionDatos> | <Ciclo> | <DeclaracionArreglos> | <InicializacionArreglos> | <LecturaDatos> | <InvocacionFuncion> | <Incremento> | <Decremento>
     */
    fun esSentencia(): Sentencia? {
        var posicionInicial = posicionActual

        val decision = esDecision()
        if (decision != null) {
            return decision
        } else {
            hacerBT(posicionInicial)
        }

        val declaracionVariableMutable = esDeclaracionVariable()
        if (declaracionVariableMutable != null) {
            return declaracionVariableMutable
        } else {
            hacerBT(posicionInicial)
        }

        val declaracionVariableInmutable = esDeclaracionVariableI()
        if (declaracionVariableInmutable != null) {
            return declaracionVariableInmutable
        } else {
            hacerBT(posicionInicial)
        }

        val asignacion = esAsignacion()
        if (asignacion != null) {
            return asignacion
        } else {
            hacerBT(posicionInicial)
        }

        val impresionDatos = esImpresionDatos()
        if (impresionDatos != null) {
            return impresionDatos
        } else {
            hacerBT(posicionInicial)
        }

        val ciclo = esCiclo()
        if (ciclo != null) {
            return ciclo
        } else {
            hacerBT(posicionInicial)
        }

        val declaracionArreglos = esDeclaracionArreglos()
        if (declaracionArreglos != null) {
            return declaracionArreglos
        } else {
            hacerBT(posicionInicial)
        }

        val inicializacionArreglos = esInicializacionArreglos()
        if (inicializacionArreglos != null) {
            return inicializacionArreglos
        } else {
            hacerBT(posicionInicial)
        }

        val lecturaDatos = esLecturaDatos()
        if (lecturaDatos != null) {
            return lecturaDatos
        } else {
            hacerBT(posicionInicial)
        }

        val invocacionFuncion = esInvocacionFuncion()
        if (invocacionFuncion != null) {
            return invocacionFuncion
        } else {
            hacerBT(posicionInicial)
        }

        val incremento = esIncremento()
        if (incremento != null) {
            return incremento
        } else {
            hacerBT(posicionInicial)
        }

        val decremento = esDecremento()
        if (decremento != null) {
            return decremento
        } else {
            hacerBT(posicionInicial)
        }

        reportarError("No se encuentra nunguna sentencia")
        return null
    }

    /**
     * <Decision> ::= if ???(??? <ListaExpresiones> ???)??? ???{??? <ListaSentencias> ???}??? [else ???{??? <ListaSentencias> ???}???]
     */
    fun esDecision(): Decision? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.palabra == "if") {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.PARENTESIS_IZQUIERDO) {
                obtenerSiguienteToken()
                var expresiones = esExpresion()
                if (expresiones != null) {
                    if (tokenActual.categoria == Categoria.PARENTESIS_DERECHO) {
                        obtenerSiguienteToken()
                        if (tokenActual.categoria == Categoria.LLAVE_IZQUIERDA) {
                            obtenerSiguienteToken()
                            val sentenciasIf = esListaSentencias()
                            if (sentenciasIf != null) {
                                if (tokenActual.categoria == Categoria.LLAVE_DERECHA) {
                                    obtenerSiguienteToken()
                                    if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.palabra == "else") {
                                        obtenerSiguienteToken()
                                        if (tokenActual.categoria == Categoria.LLAVE_IZQUIERDA) {
                                            obtenerSiguienteToken()
                                            val sentenciasElse = esListaSentencias()
                                            if (sentenciasElse != null) {
                                                if (tokenActual.categoria == Categoria.LLAVE_DERECHA) {
                                                    obtenerSiguienteToken()
                                                    return Decision(expresiones, sentenciasIf, sentenciasElse)
                                                } else {
                                                    reportarError("No se encuentra la llave derecha")
                                                }
                                            } else {
                                                reportarError("No se encuentran las sentencias del else")
                                            }
                                        } else {
                                            reportarError("No se encuentra la llave izquierda")
                                        }
                                    } else {
                                        return Decision(expresiones, sentenciasIf, ArrayList())
                                    }
                                } else {
                                    reportarError("No se encuentra la llave derecha")
                                }
                            } else {
                                reportarError("No se encuentran las sentencias del if")
                            }
                        } else {
                            reportarError("No se encuentra la llave izquierda")
                        }
                    } else {
                        reportarError("No se encuentra el parentesis derecho")
                    }
                } else {
                    reportarError("No se encuentra la expresion")
                }
            } else {
                reportarError("No se encuentra el parentesis izquierdo")
            }
        }
        return null
    }

    /**
     * <Asignacion>  ::= identificador operadorAsignacion <Expresion>???;???
     */
    fun esAsignacion(): Asignacion? {
        var posicionInicial = posicionActual
        if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
            val identificador = tokenActual
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.OPERADOR_ASIGNACION) {
                val operador = tokenActual
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.IDENTIFICADOR || tokenActual.categoria == Categoria.ENTERO || tokenActual.categoria == Categoria.DECIMAL || tokenActual.categoria == Categoria.CADENA_CARACTERES) {
                    val valor = tokenActual
                    obtenerSiguienteToken()
                    if (tokenActual.categoria == Categoria.FIN_SENTENCIA) {
                        obtenerSiguienteToken()
                        return Asignacion(identificador, operador, valor)
                    } else {
                        reportarError("No se encuentra el fin de sentencia")
                    }
                } else {
                    reportarError("No se encuentra el valor")
                }
            } else {
                reportarError("No se encuentra el operador de asignacion")
            }
        }
        hacerBT(posicionInicial)
        return null
    }

    /**
     * <ImpresionDatos> ::= print <CadenaCaracteres>???;???
     */
    fun esImpresionDatos(): ImpresionDatos? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.palabra == "print") {
            obtenerSiguienteToken()
            val cadenaCaracteres = esExpresionCadena()
            if (cadenaCaracteres != null) {
                if (tokenActual.categoria == Categoria.FIN_SENTENCIA) {
                    obtenerSiguienteToken()
                    return ImpresionDatos(cadenaCaracteres)
                } else {
                    reportarError("No se encuentra el fin de sentencia")
                }
            } else {
                reportarError("No se encuentra la cadena de caracteres")
            }
        }
        return null
    }

    /**
     * <Ciclo> ::= while ???(??? <ExpresionRelacional> ???)??? ???{??? <ListaSentencias> ???}???
     */
    fun esCiclo(): Ciclo? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.palabra == "while") {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.PARENTESIS_IZQUIERDO) {
                obtenerSiguienteToken()
                var expresion = esRelacional()
                if (expresion != null) {
                    if (tokenActual.categoria == Categoria.PARENTESIS_DERECHO) {
                        obtenerSiguienteToken()
                        if (tokenActual.categoria == Categoria.LLAVE_IZQUIERDA) {
                            obtenerSiguienteToken()
                            val sentencias = esListaSentencias()
                            if (sentencias != null) {
                                if (tokenActual.categoria == Categoria.LLAVE_DERECHA) {
                                    obtenerSiguienteToken()
                                    return Ciclo(expresion, sentencias)
                                } else {
                                    reportarError("No se encuentra la llave derecha")
                                }
                            } else {
                                reportarError("No se encuentran las sentencias")
                            }
                        } else {
                            reportarError("No se encuentra la llave izquierda")
                        }
                    } else {
                        reportarError("No se encuentra el parentesis derecho")
                    }
                } else {
                    reportarError("No se encuentra la expresion")
                }
            } else {
                reportarError("No se encuentra el parentesis izquierdo")
            }
        }
        return null
    }

    /**
     * <DeclaracionArreglos> ::= <TipoDato> "[""]" identificador ???;???
     */
    fun esDeclaracionArreglos(): DeclaracionArreglo? {
        var posicionInicial = posicionActual
        val tipoDato = esTipoDato()
        if (tipoDato != null) {
            if(tokenActual.categoria == Categoria.CORCHETE_IZQUIERDO){
                obtenerSiguienteToken()
                if(tokenActual.categoria == Categoria.CORCHETE_DERECHO)
                {
                    obtenerSiguienteToken()
                    if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                        val identificador = tokenActual
                        obtenerSiguienteToken()
                        if (tokenActual.categoria == Categoria.FIN_SENTENCIA) {
                            obtenerSiguienteToken()
                            return DeclaracionArreglo(tipoDato, identificador)
                        } else {
                            reportarError("No se encuentra el fin de sentencia")
                        }
                    } else {
                        reportarError("No se encuentra el identificador")
                    }
                }
            }

        }
        hacerBT(posicionInicial)
        return null
    }

    /**
     * <InicializacionArreglos> ::= <TipoDato> identificador ???=??? ???{??? <ListaValores> ???}??????;???
     */
    fun esInicializacionArreglos(): InicializacionArreglo? {
        var posicionInicial = posicionActual
        val tipoDato = esTipoDato()
        if (tipoDato != null) {
            if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                val identificador = tokenActual
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.OPERADOR_ASIGNACION && tokenActual.palabra == "=") {
                    obtenerSiguienteToken()
                    if (tokenActual.categoria == Categoria.LLAVE_IZQUIERDA) {
                        obtenerSiguienteToken()
                        val valores = esListaValores()
                        if (valores != null) {
                            if (tokenActual.categoria == Categoria.LLAVE_DERECHA) {
                                obtenerSiguienteToken()
                                if (tokenActual.categoria == Categoria.FIN_SENTENCIA) {
                                    obtenerSiguienteToken()
                                    return InicializacionArreglo(tipoDato, identificador, valores)
                                } else {
                                    reportarError("No se encuentra el fin de sentencia")
                                }
                            } else {
                                reportarError("No se encuentra la llave derecha")
                            }
                        } else {
                            reportarError("No se encuentran los valores")
                        }
                    } else {
                        reportarError("No se encuentra la llave izquierda")
                    }
                } else {
                    reportarError("No se encuentra el operador '='")
                }
            } else {
                reportarError("No se encuentra el identificador")
            }
        }
        hacerBT(posicionInicial)
        return null
    }

    /**
     * <ListaValores> ::= valor [???,??? <ListaValores>]
     */
    fun esListaValores(): ArrayList<String> {
        var valores = ArrayList<String>()
        if (tokenActual.categoria == Categoria.IDENTIFICADOR || tokenActual.categoria == Categoria.ENTERO || tokenActual.categoria == Categoria.DECIMAL || tokenActual.categoria == Categoria.CADENA_CARACTERES) {
            var valor = tokenActual.palabra
            while (valor != null) {
                valores.add(valor)
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.SEPARADOR) {
                    obtenerSiguienteToken()
                    valor = tokenActual.palabra
                } else {
                    break
                }
            }
        } else {
            reportarError("No se encuentra el valor")
        }
        return valores
    }

    /**
     * <LecturaDatos> ::= identificador ???=" read???;???
     */
    fun esLecturaDatos(): LecturaDatos? {
        var posicionInicial = posicionActual
        if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
            val identificador = tokenActual
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.OPERADOR_ASIGNACION && tokenActual.palabra == "=") {
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.palabra == "read") {
                    obtenerSiguienteToken()
                    if (tokenActual.categoria == Categoria.FIN_SENTENCIA) {
                        obtenerSiguienteToken()
                        return LecturaDatos(identificador)
                    } else {
                        reportarError("No se encuentra el fin de sentencia")
                    }
                } else {
                    reportarError("No se encontr?? la palabra reservada read")
                }
            } else {
                reportarError("No se encuentra el operador '='")
            }
        }
        hacerBT(posicionInicial)
        return null
    }

    /**
     * <InvocacionFuncion> ::= identificador ???(??? [<ListaArgumentos>] ???)??????;???
     */
    fun esInvocacionFuncion(): Invocacion? {
        var posicionInicial = posicionActual
        if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
            val identificador = tokenActual
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.PARENTESIS_IZQUIERDO) {
                obtenerSiguienteToken()
                val argumentos: ArrayList<Argumento> = esListaArgumentos()
                if (tokenActual.categoria == Categoria.PARENTESIS_DERECHO) {
                    obtenerSiguienteToken()
                    if (tokenActual.categoria == Categoria.FIN_SENTENCIA) {
                        obtenerSiguienteToken()
                        return Invocacion(identificador, argumentos)
                    } else {
                        reportarError("No se encuentra el fin de sentencia")
                    }
                } else {
                    reportarError("No se encuentra el parentesis derecho")
                }
            } else {
                reportarError("No se encuentra el parentesis izquierdo")
            }
        }
        hacerBT(posicionInicial)
        return null
    }

    /**
     * <ListaArgumentos> ::= <Argumento> [???,??? <ListaArgumentos>]
     */
    fun esListaArgumentos(): ArrayList<Argumento> {
        var listaArgumentos = ArrayList<Argumento>()
        var argumento = esArgumento()
        while (argumento != null) {
            listaArgumentos.add(argumento)
            if (tokenActual.categoria == Categoria.SEPARADOR) {
                obtenerSiguienteToken()
                argumento = esArgumento()
            } else {
                if (tokenActual.categoria != Categoria.PARENTESIS_DERECHO) {
                    reportarError("Falta una coma en la lista de paramentros")
                }
                break
            }
        }
        return listaArgumentos
    }

    /**
     * <Argumento> ::= identificador
     */
    fun esArgumento(): Argumento? {
        if (tokenActual.categoria == Categoria.DECIMAL || tokenActual.categoria == Categoria.ENTERO || tokenActual.categoria == Categoria.CADENA_CARACTERES) {
            val identificador = tokenActual
            obtenerSiguienteToken()
            return Argumento(identificador)
        }
        return null
    }

    /**
     * <Incremento> ::= identificador ???++??????;???
     */
    fun esIncremento(): Incremento? {
        var posicionInicial = posicionActual
        if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
            val identificador = tokenActual
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.INCREMENTO_DECREMENTO && tokenActual.palabra == "++") {
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.FIN_SENTENCIA) {
                    obtenerSiguienteToken()
                    return Incremento(identificador)
                } else {
                    reportarError("No se encuentra el fin de sentencia")
                }
            }
        }
        hacerBT(posicionInicial)
        return null
    }

    /**
     * <Decremento> ::= identificador ???--??????;???
     */
    fun esDecremento(): Decremento? {
        var posicionInicial = posicionActual
        if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
            val identificador = tokenActual
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.INCREMENTO_DECREMENTO && tokenActual.palabra == "--") {
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.FIN_SENTENCIA) {
                    obtenerSiguienteToken()
                    return Decremento(identificador)
                } else {
                    reportarError("No se encuentra el fin de sentencia")
                }
            }
        }
        hacerBT(posicionInicial)
        return null
    }
}
