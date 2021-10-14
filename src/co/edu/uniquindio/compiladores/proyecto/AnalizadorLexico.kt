package co.edu.uniquindio.compiladores.proyecto

class AnalizadorLexico ( var codigoFuente:String)
{
    var posicionActual = 0
    var filaActual = 0
    var columnaActual = 0
    var caracterActual= codigoFuente[0]
    var listaTokens =  ArrayList<Token>()
    var finCodigo = 0.toChar()

    fun almacenarToken(palabra:String ,categoria:Categoria,fila:Int, columna:Int ) = listaTokens.add(Token(palabra, categoria,fila, columna))

    fun hacerBT(posicionInicial:Int, columna:Int, fila:Int) {

        posicionActual = posicionInicial
        filaActual = fila
        columnaActual = columna

        caracterActual = codigoFuente[posicionActual]
    }

    fun analizar() {
        while (caracterActual !== finCodigo) {
            if (caracterActual == ' ' || caracterActual == '\t' || caracterActual == '\n') {
                obtenerSgteCaracter()
                continue
            }

            if (esEntero()) continue
            if (esDecimal()) continue
            if (esIdentificador()) continue
         //   if (esReservadas()) continue
            // if (esOperadorLogico()) continue
        //    if (esAgrupador()) continue
            if (esOperadorRelacional()) continue
        //    if (esAsignacion()) continue
        //    if (esIncrementoODecremento()) continue
        //    if (esComentario()) continue
            if (esOperadorAritmetico()) continue
            if (esCaracter()) continue
            if (esCadenaCaracteres()) continue
            if (esFinSentencia()) continue



        }
    }

    fun obtenerSgteCaracter() {

        posicionActual++
        if(posicionActual<codigoFuente.length) {

            if(caracterActual=='\n') {
                filaActual++
                columnaActual=0
            }else {
                columnaActual++
            }

            caracterActual = codigoFuente[posicionActual]
        }else {
            caracterActual = finCodigo
        }


    }
    fun obtenerCaracterN(posicionN:Int, columna:Int, fila:Int) {
        posicionActual = posicionN;
        if (posicionActual < codigoFuente.length) {
            caracterActual = codigoFuente[posicionActual];
            columnaActual = columna;
            filaActual = fila;
        }
    }

    fun esEntero(): Boolean {
        if( caracterActual.isDigit() ) {

            var palabra = ""
            var fila = filaActual
            var columna = columnaActual
            var posicionInicial = posicionActual

            //Transición
            palabra+=caracterActual
            obtenerSgteCaracter()


            if (caracterActual != '.') {
                while( caracterActual.isDigit() ) {
                    //Transición
                    palabra += caracterActual
                    obtenerSgteCaracter()
                }
                if (caracterActual == '.') {
                    hacerBT(posicionInicial, columna, fila)
                    return false
                }

                almacenarToken(palabra, Categoria.ENTERO, fila, columna)
                return true

            }else {
                obtenerCaracterN(posicionInicial, columna, fila)
                return false
            }

        }

    return false
    }


    fun esDecimal(): Boolean {
        if (caracterActual.isDigit() || caracterActual == '.') {

            var palabra = ""
            val fila = filaActual
            val columna = columnaActual
            val posicionInicial = posicionActual


            if (caracterActual == '.') {
                palabra += caracterActual
                obtenerSgteCaracter()

                if (caracterActual.isDigit()) {
                    palabra += caracterActual
                    obtenerSgteCaracter()
                } else {
                    hacerBT(posicionInicial, columna, fila)
                    return false
                }
            }
             else
            {

                palabra += caracterActual
                obtenerSgteCaracter()

                if(!caracterActual.isDigit() && caracterActual != '.')
                {
                    hacerBT(posicionInicial, columna, fila)
                    return false
                }

                while (caracterActual.isDigit()) {
                    palabra += caracterActual
                    obtenerSgteCaracter()
                }
                if (caracterActual == '.') {
                    palabra += caracterActual
                    obtenerSgteCaracter()
                }
            }
            // Cerradura estrella
            while (caracterActual.isDigit()) {
                palabra += caracterActual
                obtenerSgteCaracter()
            }

            almacenarToken(palabra, Categoria.DECIMAL, fila, columna)
            return true


        }
        //RI
        return false
    }


    fun esIdentificador(): Boolean
    {
        if (caracterActual.isLetter() || caracterActual == '!')
        {
            var palabra = ""
            val fila = filaActual
            val columna = columnaActual
            val posicionInicial = posicionActual
            var contador = 0

            if(caracterActual=='!')
            {
                palabra += caracterActual
                obtenerSgteCaracter()

                if(caracterActual.isLetter() || caracterActual.isDigit() )
                {
                    palabra += caracterActual
                    obtenerSgteCaracter()
                }
                else
                {
                    if(caracterActual=='=' || caracterActual=='!')
                    {
                        hacerBT(posicionInicial,columna,fila)
                        return false
                    }
                }
            }
            else
            {
                palabra += caracterActual
                obtenerSgteCaracter()
            }


            while(caracterActual.isLetter() || caracterActual.isDigit() && contador  <= 8)
            {
                palabra += caracterActual
                obtenerSgteCaracter()
                contador++
            }

            almacenarToken(palabra, Categoria.IDENTIFICADOR, fila, columna)
            return true
        }
        return false
    }

    fun esFinSentencia(): Boolean
    {
        if(caracterActual == ';')
        {
            var palabra = ""
            val fila = filaActual
            val columna = columnaActual

            palabra += caracterActual

            almacenarToken(palabra, Categoria.FIN_SENTENCIA, fila, columna)
            return true
        }
        return false
    }

    fun esOperadorAritmetico(): Boolean {

        if (caracterActual == '+' || caracterActual == '-' || caracterActual == '/' || caracterActual == '*' || caracterActual == '%') {

            var palabra = ""
            val fila = filaActual
            val columna = columnaActual
            val posicionInicial = posicionActual

            if(caracterActual=='+')
            {
                palabra += caracterActual
                obtenerSgteCaracter()

                if(caracterActual== '+' || caracterActual== '=')
                {
                        hacerBT(posicionInicial, columna, fila)
                        return false
                }
            }else
            {
                palabra += caracterActual
                obtenerSgteCaracter()
            }

            if(caracterActual=='-')
            {
                palabra += caracterActual
                obtenerSgteCaracter()

                if(caracterActual== '-' || caracterActual== '=')
                {
                    hacerBT(posicionInicial, columna, fila)
                    return false
                }
            }
            else
            {
                palabra += caracterActual
                obtenerSgteCaracter()
            }

            if(caracterActual =='/')
            {
                palabra += caracterActual
                obtenerSgteCaracter()
            }



            almacenarToken(palabra, Categoria.OPERADOR_ARTIMETICO, fila, columna)
            return true
        }
        return false
    }


    fun esOperadorRelacional(): Boolean
    {
        if (caracterActual == '<' || caracterActual == '>' || caracterActual == '!' || caracterActual == '=') {

            var palabra = ""
            val fila = filaActual
            val columna = columnaActual
            val caracterInicial = caracterActual

            //Transición
            palabra += caracterActual
            obtenerSgteCaracter()
            if (caracterInicial == '<' || caracterInicial == '>') {
                if (caracterActual == '=') {
                    //Transición
                    palabra += caracterActual
                    obtenerSgteCaracter()
                } else {
                    //Transición
                    palabra += caracterActual
                    obtenerSgteCaracter()
                }
            }
        }

        return false
    }

    fun esCaracter():Boolean {
        if (caracterActual == Char(39)) {
            var palabra = ""
            val fila = filaActual
            val columna = columnaActual

            //Transición
            palabra += caracterActual
            obtenerSgteCaracter()
            if (caracterActual >= Char(32) && caracterActual <= Char(255)) {
                palabra += caracterActual
                obtenerSgteCaracter()

                if (caracterActual == Char(39)){
                    palabra += caracterActual
                    almacenarToken(palabra, Categoria.CARACTER, fila, columna)
                    return true
                } else {
                    //RE
                }
            }
        }

        //RI
        return false
    }

    fun esCadenaCaracteres():Boolean {
        if (caracterActual == '$') {
            var palabra = ""
            val fila = filaActual
            val columna = columnaActual

            //Transición
            palabra += caracterActual
            obtenerSgteCaracter()
            while (caracterActual >= Char(32) && caracterActual <= Char(255)) {
                if (caracterActual == '$') {
                    palabra += caracterActual
                    almacenarToken(palabra, Categoria.CADENA_CARACTERES, fila, columna)
                    return true
                }
                palabra += caracterActual
                obtenerSgteCaracter()
            }
        }

        //RI
        return false
    }
}