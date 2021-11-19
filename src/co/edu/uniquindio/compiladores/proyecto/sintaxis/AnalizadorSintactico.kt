package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Categoria
import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import co.edu.uniquindio.compiladores.proyecto.lexico.Error


class AnalizadorSintactico(var listaToken:ArrayList<Token>) {

    var posicionActual = 0
    var tokenActual = listaToken[posicionActual]
    var listaErrores = ArrayList<Error>()

    fun obtenerSiguienteToken() {
        posicionActual++

        if (posicionActual < listaToken.size) {
            tokenActual = listaToken[posicionActual]
        }
    }

    /**
     * Reporte de errores
     */
    fun reportarError(mensaje: String){

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

        var declaracionVariable = esDeclaracionVariable()

        if(declaracionVariable  != null)
        {
            obtenerSiguienteToken()

            var listaFuncion = esListaFunciones()

            if(listaFuncion != null)
            {
                obtenerSiguienteToken()
               // el elemento esta bien escrito
                return Elemento(listaImports, declaracionVariable, listaFuncion)
            }
            else
            {
                reportarError("Falta las funciones en el elemento")
            }

        }
        else
        {
            reportarError("Falta la declaración de variable en el elemento")
        }

        return null
    }

    /**
     * <ListaImports> ::= <Import>[<ListaImports>]
     */
    fun esListaImports(): ArrayList<Import>{

        var listaImports = ArrayList<Import>()
        var import = esImport()

        while (import!=null){
            listaImports.add(import)
        }
        return listaImports

    }

    /**
     * <Import> ::= import identificador”;”
     */
    fun esImport(): Import?
    {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.palabra == "import") {
            obtenerSiguienteToken()

            if(tokenActual.categoria == Categoria.IDENTIFICADOR)
            {
                var identificador = tokenActual.palabra

                obtenerSiguienteToken()

                if(tokenActual.categoria == Categoria.FIN_SENTENCIA)
                {
                    obtenerSiguienteToken()
                    //El import esta bien escrito
                    return Import(identificador)
                }


            }

        }

        return null
    }


    /**
     * <DeclaracionVariableMutable> ::= var <tipoDato> identificador " = " <Expresion>”;”
     */
    fun esDeclaracionVariable(): DeclaracionVariable? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.palabra == "var") {
            obtenerSiguienteToken()

            var tipoDato = esTipoDato()

            if(tipoDato!=null)
            {
            obtenerSiguienteToken()

            if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                var identificador = tokenActual.palabra
                obtenerSiguienteToken()

                if (tokenActual.categoria == Categoria.OPERADOR_ASIGNACION && tokenActual.palabra == "=") {
                    obtenerSiguienteToken()

                    var expresion = esExpresion()

                    if (expresion != null) {

                        obtenerSiguienteToken()

                        if(tokenActual.categoria == Categoria.FIN_SENTENCIA)
                            {
                                obtenerSiguienteToken()
                              //la declaracion de la variable esta bien escrita
                            return DeclaracionVariable(tipoDato.palabra,identificador,expresion)
                            }
                    }
                    else
                    {
                        reportarError("Falta la expresion en la declaración de la variable")
                    }

                }

                }

            }
            else
            {
                reportarError("Falta el tipo de dato en la declaración de la variable")
            }
        }

        return null
    }

    /**
     * <ListaExpresiones> ::= <Expresion> | <ListaExpresiones>
     */
    fun esListaExpresion(): ArrayList<Expresion>
    {
        var listaExpresion = ArrayList<Expresion>()
        var expresion = esExpresion()

        while (expresion!=null){
            listaExpresion.add(expresion)
        }
        return listaExpresion
    }

    /**
     * <Expresion> ::= [<Relacional>] | [<Logico>] | [<Aritmetico>] | [<ExpresionCadena>]
     */
    fun esExpresion(): Expresion?
    {

        var relacional = esRelacional()

        var logico =  esLogico()

        var aritmetico = esAritmetico()

        var expresionCadena = esExpresionCadena()


        if(relacional!=null)
        {
            return Expresion(relacional)
        }
        else
            if (logico!=null)
            {
                return Expresion(logico)
            }
        else
            if(aritmetico!=null)
            {
                return Expresion(aritmetico)
            }
        else
            if(expresionCadena!=null)
            {
                return Expresion(expresionCadena)
            }

        reportarError("No se detectó ninguna expresión")

        return null
    }

    /**
     *<Relacional>  ::= identificador OperadorRelacional  identificador
     */
    fun esRelacional(): Relacional? {
        if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
            var identificador1 = tokenActual.palabra
            obtenerSiguienteToken()

            if (tokenActual.categoria == Categoria.OPERADOR_RELACIONAL) {
                var operador = tokenActual.palabra
                obtenerSiguienteToken()

                if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                    var identificador2 = tokenActual.palabra
                    obtenerSiguienteToken()

                    //La expresión relacional esta bien escrita
                    return Relacional(identificador1, operador, identificador2)

                }
            }
        }
        return null
    }

    /**
     * <Logico> ::= valor OperadorLogico valor
     */
    fun esLogico(): Logico?
    {
        if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
            var identificador1 = tokenActual.palabra
            obtenerSiguienteToken()

            if (tokenActual.categoria == Categoria.OPERADOR_LOGICO) {
                var operador = tokenActual.palabra
                obtenerSiguienteToken()

                if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                    var identificador2 = tokenActual.palabra
                    obtenerSiguienteToken()

                    //La expresión relacional esta bien escrita
                    return Logico(identificador1, operador, identificador2)

                }
            }
        }
        return null
    }


    /**
     * <Aritmetico> ::= valor OperadorAritmetico valor
     */
    fun esAritmetico(): Aritmetico?
    {
        if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
            var identificador1 = tokenActual.palabra
            obtenerSiguienteToken()

            if (tokenActual.categoria == Categoria.OPERADOR_ARTIMETICO) {
                var operador = tokenActual.palabra
                obtenerSiguienteToken()

                if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                    var identificador2 = tokenActual.palabra
                    obtenerSiguienteToken()

                    //La expresión relacional esta bien escrita
                    return Aritmetico(identificador1, operador, identificador2)

                }
            }
        }
        return null
    }

    /**
     * <ExpresionCadena> ::= CadenaCaracteres [+ <Expresion>] | <Expresion> + CadenaCaracteres
     */
    fun esExpresionCadena(): ExpresionCadena?
    {
            if(tokenActual.categoria == Categoria.CADENA_CARACTERES)
            {
                var cadena1 = tokenActual.palabra

                obtenerSiguienteToken()

                if(tokenActual.categoria == Categoria.OPERADOR_ARTIMETICO && tokenActual.palabra == "+")
                {

                    var expresion = esExpresion()

                    obtenerSiguienteToken()
                    return ExpresionCadena(cadena1, expresion)

                }
            } else
            {
                var expresion2 = esExpresion()

                if(expresion2!=null)
                {
                    obtenerSiguienteToken()

                    if(tokenActual.categoria == Categoria.OPERADOR_ARTIMETICO && tokenActual.palabra == "+")
                    {
                        if(tokenActual.categoria == Categoria.CADENA_CARACTERES) {
                            var cadena2 = tokenActual.palabra

                            obtenerSiguienteToken()

                            return ExpresionCadena(cadena2, expresion2)
                        }

                    }
                }
            }
        return null
    }

    /**
     * <DeclaracionVariableInmutable> ::= cons <tipoDato> identificador " = " <Expresion>”;”
     */
    fun esDeclaracionVariableI(): DeclaracionVariableI? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.palabra == "cons") {
            obtenerSiguienteToken()

            var tipoDato = esTipoDato()

            if(tipoDato!=null)
            {
                obtenerSiguienteToken()

                if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                    var identificador = tokenActual.palabra
                    obtenerSiguienteToken()

                    if (tokenActual.categoria == Categoria.OPERADOR_ASIGNACION && tokenActual.palabra == "=") {
                        obtenerSiguienteToken()

                        var expresion = esExpresion()

                        if (expresion != null) {

                            obtenerSiguienteToken()

                            if(tokenActual.categoria == Categoria.FIN_SENTENCIA)
                            {
                                obtenerSiguienteToken()
                                //la declaracion de la variable esta bien escrita
                                return DeclaracionVariableI(tipoDato.palabra,identificador,expresion)
                            }
                        }
                        else
                        {
                            reportarError("Falta la expresion en la declaración de la variable inmutable")
                        }

                    }

                }

            }
            else
            {
                reportarError("Falta el tipo de dato en la declaración de la variable inmutable")
            }
        }

        return null
    }


    /**
     * <ListaFunciones>  ::=  <Funcion> [<ListaFunciones>]
     */
    fun esListaFunciones(): ArrayList<Funcion>{

        var listaFunciones = ArrayList<Funcion>()
        var funcion = esFuncion()

        while (funcion!=null){
            listaFunciones.add(funcion)
        }
        return listaFunciones
    }

    /**
     * <Funcion> ::= fun identificador “(“ [<ListaParametros>] ”)”
     * :<TipoRetorno> “{“ <ListaSentencias> “}”
     */
    fun esFuncion(): Funcion? {

        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.palabra == "fun") {
            obtenerSiguienteToken()

            if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                var identificador = tokenActual.palabra
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
                                obtenerSiguienteToken()

                                var listaSentencias = esListaSentencias()

                                if (listaSentencias != null) {

                                    obtenerSiguienteToken()
                                    //la función esta bien escrita
                                    return Funcion(identificador, listaParametros, tipoRetorno.palabra, listaSentencias)
                                }
                                else
                                {
                                    reportarError("Falta la lista de sentencias en la función")
                                }
                            } else {
                                reportarError("Falta el tipo de retorno en la función")
                            }
                        }
                        else
                        {
                            reportarError("Faltan los dos puntos antes del tipo de retorno en la función")
                        }
                    }
                    else
                    {
                        reportarError("La lista de parametros debe cerrar con parentesis derecho")
                    }
                }
                else
                {
                    reportarError("La lista de parametros debe iniciar con parentesis izquierdo")
                }

            }
            else
            {
                reportarError("La función debe llevar un identificador")
            }
        }else
        {
            reportarError("Debe iniciar con la palabra fun")
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
                    obtenerSiguienteToken()
                    return tokenActual
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
                || tokenActual.palabra == "boolean" || tokenActual.palabra == "string")
            {
                obtenerSiguienteToken()
                return tokenActual
            }

        }
        return null
    }


    /**
     * <ListaParametros> ::= <Parámetro> [“,”<ListaParametros>]
     */
    fun esListaParametros(): ArrayList<Parametro>? {
        var listaParametros = ArrayList<Parametro>()
        var parametro = esParametro()
        while (parametro != null) {
            listaParametros.add(parametro)
            if (tokenActual.categoria == Categoria.SEPARADOR) {
                obtenerSiguienteToken()
                parametro = esParametro()
            } else {
                if (tokenActual.categoria != Categoria.PARENTESIS_DERECHO) {
                    reportarError("Falta una coma en la lista de paramentros")
                }
                break
            }
        }
        return listaParametros
    }

    /**
     * <Parámetro> ::= <TipoDato> identificador
     */
    fun esParametro(): Parametro? {
        val tipoDato = esTipoDato()
        if (tipoDato != null) {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                obtenerSiguienteToken()
                return Parametro(tipoDato, tokenActual)
            } else {
                reportarError("El nombre del parametro esta incorrecto")
            }
        } else {
            reportarError("El tipo de dato es incorrecto")
        }
        return null
    }

    /**
     * <Retorno> ::= return identificador”;”
     */
    fun esRetorno(): Retorno? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.palabra == "return") {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                var identificador = tokenActual.palabra
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.FIN_SENTENCIA) {
                    return Retorno(identificador)
                } else {
                    reportarError("No se encuentra el fin de la sentencia")
                }
            } else {
                reportarError("El nombre del identificador esta incorrecto")
            }
        } else {
            reportarError("No se encuentra la palabra reservada 'return'")
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
     * <Sentencia> ::= <Decision> | <DeclaracionVariableMutable> | <DeclaracionVariableInmutable> | <Asignacion> | <ImpresionDatos> | <Ciclo> | <DeclaracionArreglos> | <InicializacionArreglos> | <Retorno> | <LecturaDatos> | <InvocacionFuncion> | <Incremento> | <Decremento>
     */
    fun esSentencia(): Sentencia? {
        val decision = esDecision()
        val declaracionVariableMutable = esDeclaracionVariable()
        val declaracionVariableInmutable = esDeclaracionVariableI()
        val asignacion = esAsignacion()
        val impresionDatos = esImpresionDatos()
        val ciclo = esCiclo()
        val declaracionArreglos = esDeclaracionArreglos()
        val inicializacionArreglos = esInicializacionArreglos()
        val retorno = esRetorno()
        val lecturaDatos = esLecturaDatos()
        val invocacionFuncion = esInvocacionFuncion()
        val incremento = esIncremento()
        val decremento = esDecremento()
        if (decision != null) {
            return Sentencia(decision)
        } else if (declaracionVariableMutable != null) {
            return Sentencia(declaracionVariableMutable)
        } else if (declaracionVariableInmutable != null) {
            return Sentencia(declaracionVariableInmutable)
        } else if (asignacion != null) {
            return Sentencia(asignacion)
        } else if (impresionDatos != null) {
            return Sentencia(impresionDatos)
        } else if (ciclo != null) {
            return Sentencia(ciclo)
        } else if (declaracionArreglos != null) {
            return Sentencia(declaracionArreglos)
        } else if (inicializacionArreglos != null) {
            return Sentencia(inicializacionArreglos)
        } else if (retorno != null) {
            return Sentencia(retorno)
        } else if (lecturaDatos != null) {
            return Sentencia(lecturaDatos)
        } else if (invocacionFuncion != null) {
            return Sentencia(invocacionFuncion)
        } else if (incremento != null) {
            return Sentencia(incremento)
        } else if (decremento != null) {
            return Sentencia(decremento)
        }
        reportarError("No se encuentra nunguna sentencia")
        return null
    }

    /**
     * <Decision> ::= if “(” <ListaExpresiones> “)” “{” <ListaSentencias> “}” [else “{” <ListaSentencias> “}”]
     */
    fun esDecision(): Decision? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.palabra == "if") {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.PARENTESIS_IZQUIERDO) {
                val expresiones = esListaExpresion()
                obtenerSiguienteToken()
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
        } else {
            reportarError("No se encuentra la palabra 'if'")
        }
        return null
    }

    /**
     * <Asignacion>  ::= identificador operadorAsignacion <Expresion>”;”
     */
    fun esAsignacion(): Asignacion? {
        if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
            val identificador = tokenActual
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.OPERADOR_ASIGNACION) {
                val operador = tokenActual
                obtenerSiguienteToken()
                val expresion = esExpresion()
                if (expresion != null) {
                    if (tokenActual.categoria == Categoria.FIN_SENTENCIA) {
                        obtenerSiguienteToken()
                        return Asignacion(identificador, operador, expresion)
                    } else {
                        reportarError("No se encuentra el fin de sentencia")
                    }
                } else {
                    reportarError("No se encuentra la expresion")
                }
            } else {
                reportarError("No se encuentra el operador de asignacion")
            }
        } else {
            reportarError("No se encuentra el identificador")
        }
        return null
    }

    /**
     * <ImpresionDatos> ::= print <CadenaCaracteres>”;”
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
        } else {
            reportarError("No se encuentra la palabra 'print'")
        }
        return null
    }

    /**
     * <Ciclo> ::= while “(” <ExpresionLogica> “)” “{” <ListaSentencias> “}”
     */
    fun esCiclo():Ciclo? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.palabra == "while") {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.PARENTESIS_IZQUIERDO) {
                val expresion = esLogico()
                if (expresion != null) {
                    if (tokenActual.categoria == Categoria.PARENTESIS_DERECHO) {
                        obtenerSiguienteToken()
                        if (tokenActual.categoria == Categoria.LLAVE_IZQUIERDA) {
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
        } else {
            reportarError("No se encuentra la palabra 'while'")
        }
        return null
    }

    /**
     * <DeclaracionArreglos> ::= <TipoDato> identificador ”;”
     */
    fun esDeclaracionArreglos(): DeclaracionArreglo? {
        val tipoDato = esTipoDato()
        if (tipoDato != null) {
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
        } else {
            reportarError("No se encuentra el tipo de dato")
        }
        return null
    }

    /**
     * <InicializacionArreglos> ::= <TipoDato> identificador “=” “{” <ListaValores> “}””;”
     */
    fun esInicializacionArreglos(): InicializacionArreglo? {
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
        } else {
            reportarError("No se encuentra el tipo de dato")
        }
        return null
    }

    /**
     * <ListaValores> ::= valor [“,” <ListaValores>]
     */
    fun esListaValores(): ArrayList<String> {
        var valores = ArrayList<String>()
        if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
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
     * <LecturaDatos> ::= identificador “=” valor”;”
     */
    fun esLecturaDatos(): LecturaDatos? {
        if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
            val identificador = tokenActual
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.OPERADOR_ASIGNACION && tokenActual.palabra == "=") {
                obtenerSiguienteToken()
                val valor = tokenActual
                if (valor != null) {
                    obtenerSiguienteToken()
                    if (tokenActual.categoria == Categoria.FIN_SENTENCIA) {
                        obtenerSiguienteToken()
                        return LecturaDatos(identificador, valor)
                    } else {
                        reportarError("No se encuentra el fin de sentencia")
                    }
                } else {
                    reportarError("No se encuentra el valor")
                }
            } else {
                reportarError("No se encuentra el operador '='")
            }
        } else {
            reportarError("No se encuentra el identificador")
        }
        return null
    }

    /**
     * <InvocacionFuncion> ::= identificador “(“ [<ListaArgumentos>] “)””;”
     */
    fun esInvocacionFuncion(): Invocacion? {
        if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
            val identificador = tokenActual.palabra
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
        } else {
            reportarError("No se encuentra el identificador")
        }
        return null
    }

    /**
     * <ListaArgumentos> ::= <Argumento> [“,” <ListaArgumentos>]
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
        if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
            val identificador = tokenActual
            obtenerSiguienteToken()
            return Argumento(identificador)
        }
        return null
    }

    /**
     * <Incremento> ::= identificador “++””;”
     */
    fun esIncremento(): Incremento? {
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
            } else {
                reportarError("No se encuentra el operador '++'")
            }
        } else {
            reportarError("No se encuentra el identificador")
        }
        return null
    }

    /**
     * <Decremento> ::= identificador “--””;”
     */
    fun esDecremento(): Decremento? {
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
            } else {
                reportarError("No se encuentra el operador '--'")
            }
        } else {
            reportarError("No se encuentra el identificador")
        }
        return null
    }
}
