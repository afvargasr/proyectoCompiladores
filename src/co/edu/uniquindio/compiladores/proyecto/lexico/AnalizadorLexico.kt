package co.edu.uniquindio.compiladores.proyecto.lexico

import co.edu.uniquindio.compiladores.proyecto.excepciones.ReporteErrorException

/**
 * Analizador léxico del compilador
 * @author DANIELA FLOREZ, ANDRES BETANCOURT, ALEX VARGAS
 * @param codigoFuente para analizar
 */

class AnalizadorLexico ( var codigoFuente:String)
{
    var posicionActual = 0
    var filaActual = 0
    var columnaActual = 0
    var caracterActual = codigoFuente[0]
    var listaTokens =  ArrayList<Token>()
    var finCodigo = 0.toChar()
    var palabrasReservadas = listOf<String>("@int", "@void", "@text", "@get", "@set", "@final", "@for", "@while", "@int", "@if", "@boolean")

    fun almacenarToken(palabra:String, categoria: Categoria, fila:Int, columna:Int ) = listaTokens.add(Token(palabra, categoria, fila, columna))

    fun hacerBT(posicionInicial:Int, columna:Int, fila:Int) {

        posicionActual = posicionInicial
        filaActual = fila
        columnaActual = columna

        caracterActual = codigoFuente[posicionActual]
    }

	/**
	 * Metodo que permite analizar el codigo e ir agregando a la lista de token la
	 * palabra identificada con su categoria y ubicacion donde fue encontrada. Fila
	 * y columna donde inicia
	 */
    fun analizar() {
        while (caracterActual !== finCodigo) {
            if (caracterActual == ' ' || caracterActual == '\t' || caracterActual == '\n') {
                obtenerSgteCaracter()
                continue
            }

            if (esEntero()) continue
            if (esDecimal()) continue
            if (esIdentificador()) continue
            if (esReservada()) continue
            if (esOperadorLogico()) continue
            if (esAgrupador()) continue
            if (esOperadorRelacional()) continue
            if (esAsignacion()) continue
            if (esIncrementoODecremento()) continue
            if (esComentarioLinea()) continue
            if (esComentarioBloque()) continue
            if (esOperadorAritmetico()) continue
            if (esCaracter()) continue
            if (esCadenaCaracteres()) continue
            if (esFinSentencia()) continue
            if (esSeparador()) continue
            if (esPunto()) continue
            if (esDosPuntos()) continue

            /*
            * Si no se forma ningun token valido con la secuencia de caracteres el caracter se clasifica como desconocido
            */
            almacenarToken(caracterActual.toString(), Categoria.DESCONOCIDO, filaActual, columnaActual)
            obtenerSgteCaracter()
        }
    }
    
    /**
	 * Permite obtener el siguiente caracter del codigo fuente
	 * 
	 */
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
    /**
	 * Permite obtener el caracter en la posicion n del codigo fuente
	 * 
	 */
    fun obtenerCaracterN(posicionN:Int, columna:Int, fila:Int) {
        posicionActual = posicionN
        if (posicionActual < codigoFuente.length) {
            caracterActual = codigoFuente[posicionActual]
            columnaActual = columna
            filaActual = fila
        }
    }
    
    /**
	 * Metodo que permite verificar si un numero es entero
	 * 
	 * @return true si es entero false si no
	 */
    
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
                obtenerSgteCaracter()
                return true

            }else {
                obtenerCaracterN(posicionInicial, columna, fila)
                return false
            }

        }
    //Rechazo Inmediato
    return false
    }
    
    /**
	 * Metodo que permite verificar si un numero Decimal
	 * 
	 * @return true si es real false si no
	 */
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
            obtenerSgteCaracter()
            return true

        }
        //Rechazo inmediato
        return false
    }
    
    /**
	 * Permite ver si una cadena es un identificador. Un identificador inicia con ! y concatenacion
	 * de letras y digitos
	 * 
	 * 
	 *@return true si es identificador false si no
	 */
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
    
    /**
	 * Permite ver si es fin de sentencia
	 * 
	 * 
	 *@return true si es fin de sentencia false si no
	 */
    fun esFinSentencia(): Boolean
    {
        if(caracterActual == ';')
        {
            var palabra = ""
            val fila = filaActual
            val columna = columnaActual

            palabra += caracterActual

            almacenarToken(palabra, Categoria.FIN_SENTENCIA, fila, columna)
            obtenerSgteCaracter()
            return true
        }
        return false
    }
    
    /**
	 * Metodo que permite verificar si un operador es aritmetico
	 * 
	 * @return true si es operador aritmetico false si no
	 */
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
                else
                {
                    palabra += caracterActual
                    obtenerSgteCaracter()
                }
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
                else
                {
                    palabra += caracterActual
                    obtenerSgteCaracter()
                }
            }
            if(caracterActual =='/' || caracterActual == '*' || caracterActual == '%')
            {
                palabra += caracterActual
                obtenerSgteCaracter()
            }
            almacenarToken(palabra, Categoria.OPERADOR_ARTIMETICO, fila, columna)
            return true
        }
        return false
    }

    /**
	 * Metodo que permite verificar si un operador es relacional
	 * 
	 * @return true si es operador relacional false si no
	 */
    fun esOperadorRelacional(): Boolean
    {
        if (caracterActual == '<' || caracterActual == '>' || caracterActual == '!' || caracterActual == '=') {
            var palabra = ""
            val fila = filaActual
            val columna = columnaActual
            val caracterInicial = caracterActual
            val posicionInicial = posicionActual

            //Transición
            palabra += caracterActual
            obtenerSgteCaracter()
            if (caracterInicial == '<' || caracterInicial == '>') {
                if (caracterActual == '=') {
                    //Transición
                    palabra += caracterActual
                    almacenarToken(palabra, Categoria.OPERADOR_RELACIONAL, fila, columna)
                    obtenerSgteCaracter()
                    return true
                } else {
                    //Transición
                    almacenarToken(palabra, Categoria.OPERADOR_RELACIONAL, fila, columna)
                    obtenerSgteCaracter()
                    return true
                }
            }
            if(caracterInicial=='!')
            {
                if(caracterActual== '!' || caracterActual.isDigit() || caracterActual.isLetter())
                {
                    hacerBT(posicionInicial, columna, fila)
                    return false
                }
                if(caracterActual== '=')
                {
                    palabra += caracterActual
                    almacenarToken(palabra, Categoria.OPERADOR_RELACIONAL, fila, columna)
                    obtenerSgteCaracter()
                    return true
                }
            }
            if (caracterInicial == '=') {
                if (caracterActual == '=') {
                    palabra += caracterActual
                    almacenarToken(palabra, Categoria.OPERADOR_RELACIONAL, fila, columna)
                    obtenerSgteCaracter()
                    return true
                } else {
                    hacerBT(posicionInicial, columna, fila)
                    return false
                }
            }
        }

        return false
    }
    
    //Excepcion propia para reporte error
    @Throws(ReporteErrorException::class)
    
    /**
	 * Permite ver si es un caracter
	 * 
	 * 
	 *@return true si es un caracter false si no
	 */
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
                    obtenerSgteCaracter()
                    return true
                } else {
                    //RE
                    throw ReporteErrorException("No se encontró el cierre del carácter")
                }
            }
        }

        //RI
        return false
    }

    @Throws(ReporteErrorException::class)
    
    /**
	 * Permite ver si es una cadena de caracteres 
	 * 
	 * 
	 *@return true si es una cadena de caracteres false si no
	 */
    fun esCadenaCaracteres():Boolean {
        if (caracterActual == '#') {
            var palabra = ""
            val fila = filaActual
            val columna = columnaActual

            //Transición
            palabra += caracterActual
            obtenerSgteCaracter()
            while (caracterActual >= Char(32) && caracterActual <= Char(255)) {
                if (caracterActual == '#') {
                    palabra += caracterActual
                    almacenarToken(palabra, Categoria.CADENA_CARACTERES, fila, columna)
                    obtenerSgteCaracter()
                    return true
                }
                palabra += caracterActual
                obtenerSgteCaracter()
            }
            //RE
            throw ReporteErrorException("No se encontró el cierre de la cadena de carácteres")
        }

        //RI
        return false
    }

    /**
	 * Permite ver si es un comentario de linea
	 * 
	 * 
	 *@return true si es una comentario de linea false si no
	 */
    fun esComentarioLinea(): Boolean {
        if (caracterActual == '_') {
            var palabra = ""
            val fila = filaActual
            val columna = columnaActual

            //Transición
            palabra += caracterActual
            obtenerSgteCaracter()
            while (caracterActual != '\n') {
                palabra += caracterActual
                obtenerSgteCaracter()
            }

            almacenarToken(palabra, Categoria.COMENTARIO_LINEA, fila, columna)
            return true
        }

        //RI
        return false
    }

    @Throws(ReporteErrorException::class)
    
    /**
	 * Permite ver si es un comentario de bloque
	 * 
	 * 
	 *@return true si es comentario de bloque false si no
	 */
    fun esComentarioBloque(): Boolean {
        if(caracterActual == '*') {
            var palabra = ""
            val fila = filaActual
            val columna = columnaActual
            val posicionInicial = posicionActual

            //Transición
            palabra += caracterActual
            obtenerSgteCaracter()
            if (caracterActual == '*') {
                palabra += caracterActual
                obtenerSgteCaracter()
                while (caracterActual >= Char(32) && caracterActual <= Char(255)) {
                    if (caracterActual == '*') {
                        palabra += caracterActual
                        almacenarToken(palabra, Categoria.COMENTARIO_BLOQUE, fila, columna)
                        obtenerSgteCaracter()
                        return true
                    }
                    palabra += caracterActual
                    obtenerSgteCaracter()
                }
                //RE
                throw ReporteErrorException("No se encontró el cierre del comentario de bloque")
            } else {
                hacerBT(posicionInicial,columna,fila)
                return false
            }
        }

        //RI
        return false
    }
    
    /**
	 * Permite ver si es un operador de asignacion
	 * 
	 * 
	 *@return true si es asignacion false si no
	 */
    fun esAsignacion(): Boolean {
        if (caracterActual == '=' || caracterActual == '+' || caracterActual == '-') {
            var palabra = ""
            val fila = filaActual
            val columna = columnaActual
            val caracterInicial = caracterActual
            val posicionInicial = posicionActual

            //Transición
            palabra += caracterActual
            obtenerSgteCaracter()
            if (caracterInicial == '=') {
                if (caracterActual == '=') {
                    hacerBT(posicionInicial, columna, fila)
                    return false
                } else {
                    almacenarToken(palabra, Categoria.OPERADOR_ASIGNACION, fila, columna)
                    return true
                }
            } else {
                if (caracterActual == '=') {
                    palabra += caracterActual
                    almacenarToken(palabra, Categoria.OPERADOR_ASIGNACION, fila, columna)
                    obtenerSgteCaracter()
                    return true
                } else {
                    hacerBT(posicionInicial, columna, fila)
                    return false
                }
            }
        }

        //RI
        return false
    }

    /**
	 * Permite ver si es un agrupador
	 * 
	 * 
	 *@return true si es un agrupador false si no
	 */
    fun esAgrupador(): Boolean {
        if (caracterActual == '(' || caracterActual == ')' || caracterActual == '[' || caracterActual == ']' || caracterActual == '{' || caracterActual == '}') {
            var palabra = caracterActual.toString()
            val fila = filaActual
            val columna = columnaActual
            val posicionInicial = posicionActual

            almacenarToken(palabra, Categoria.AGRUPADOR, fila, columna)
            obtenerSgteCaracter()
            return true
        }
        //RI
        return false
    }

    /**
	 * Metodo que permite verificar si un operador es logico
	 * 
	 * @return true si es operador logico false si no
	 */
    fun esOperadorLogico(): Boolean {

        if (caracterActual=='&' || caracterActual=='|' || caracterActual=='!') {

            var palabra = ""
            val fila = filaActual
            val columna = columnaActual
            val posicionInicial = posicionActual
            val caracterInicial = caracterActual

            //Transición
            palabra += caracterActual
            obtenerSgteCaracter()

            if(caracterInicial=='!') {
                if (caracterActual == '=' || caracterActual.isDigit() || caracterActual.isLetter())
                {
                    hacerBT(posicionInicial, columna, fila)
                    return false;
                }
                if(caracterActual == '!') {
                    palabra += caracterActual
                    almacenarToken(palabra, Categoria.OPERADOR_LOGICO, fila, columna)
                    obtenerSgteCaracter()
                    return true
                } else {
                    obtenerCaracterN(posicionInicial, columna, fila)
                    return false
                }
            }
            if(caracterInicial=='&' || caracterInicial=='|')
            {
                almacenarToken(palabra, Categoria.OPERADOR_LOGICO, fila, columna)
                obtenerSgteCaracter()
                return true
            }
        }
        //RI
        return false
    }
    
    /**
	 * Permite ver si es incremento o decremento
	 * 
	 * 
	 *@return true si es incremento o decremento false si no
	 */
    fun esIncrementoODecremento(): Boolean
    {
        if (caracterActual=='+' || caracterActual=='-') {
            var palabra = ""
            val fila = filaActual
            val columna = columnaActual
            val posicionInicial = posicionActual
            val caracterInicial = caracterActual

            //Transición
            palabra += caracterActual
            obtenerSgteCaracter()

            if ((caracterInicial=='+' && caracterActual=='+') || (caracterInicial=='-' && caracterActual=='-')) {
                palabra += caracterActual;
                almacenarToken(palabra, Categoria.INCREMENTO_DECREMENTO, fila,columna)
                obtenerSgteCaracter()
                return true
            } else {
                hacerBT(posicionInicial, columna, fila)
                return false
            }
        }
        //RI
        return false;
    }

    /**
	 * Permite ver si una palabra es reservada 
	 * 
	 * 
	 *@return true si es una palabra reservada false si no
	 */
    fun esReservada(): Boolean {
        if (caracterActual == '@') {
            var palabra = ""
            val fila = filaActual
            val columna = columnaActual
            val posicionInicial = posicionActual

            //Transición
            palabra += caracterActual
            obtenerSgteCaracter()
            while (caracterActual.isLetter()) {
                palabra += caracterActual
                obtenerSgteCaracter()
            }
            if (palabrasReservadas.contains(palabra)) {
                almacenarToken(palabra, Categoria.PALABRA_RESERVADA, fila, columna)
                return true
            } else {
                obtenerCaracterN(posicionInicial, columna, fila)
                return false
            }
        }

        //RI
        return false
    }

    /**
     * Permite ver si es un separador
     *
     *
     *@return true si es un separador false si no
     */
    fun esSeparador(): Boolean
    {
        if(caracterActual == ',')
        {
            var palabra = ""
            val fila = filaActual
            val columna = columnaActual

            palabra += caracterActual

            almacenarToken(palabra, Categoria.SEPARADOR, fila, columna)
            obtenerSgteCaracter()
            return true
        }
        return false
    }

    /**
     * Permite ver si es un punto
     *
     *
     *@return true si es un punto false si no
     */
    fun esPunto(): Boolean
    {
        if(caracterActual == '.')
        {
            var palabra = ""
            val fila = filaActual
            val columna = columnaActual

            palabra += caracterActual

            almacenarToken(palabra, Categoria.PUNTO, fila, columna)
            obtenerSgteCaracter()
            return true
        }
        return false
    }

    /**
     * Permite ver si son dos puntos
     *
     *
     *@return true si son dos puntos false si no
     */
    fun esDosPuntos(): Boolean
    {
        if(caracterActual == ':')
        {
            var palabra = ""
            val fila = filaActual
            val columna = columnaActual

            palabra += caracterActual

            almacenarToken(palabra, Categoria.DOS_PUNTOS, fila, columna)
            obtenerSgteCaracter()
            return true
        }
        return false
    }
}
