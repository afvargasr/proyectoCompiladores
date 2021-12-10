package co.edu.uniquindio.compiladores.proyecto.lexico


/**
 * Clase que representa un token o una categoria lexica del lenguaje
 * @author Laura Daniela Florez Polania
 *  @author Alex Ferney Vargas
 *   @author Andres Felipe Betancourt
 *
 */
class Token(var palabra: String, var categoria: Categoria, var fila: Int, var columna: Int) {


    override fun toString(): String {
        return "Token(palabra='$palabra', categoria=$categoria, fila=$fila, columna=$columna)"
    }

    fun getJavaCode(): String {

        if (categoria == Categoria.PALABRA_RESERVADA) {
            if (palabra == "string") {
                return "String"
            }
        }else if (categoria == Categoria.OPERADOR_LOGICO) {
                    if (palabra == "|") {
                        return "||"
                    }else if (palabra == "&") {
                        return "&&"
                    }else if (palabra == "!!") {
                        return "!="
            }

        } else if (categoria == Categoria.CADENA_CARACTERES) {
            return palabra.replace("#", "\"")

        }
        return palabra


        }
    }

