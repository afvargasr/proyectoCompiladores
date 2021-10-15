package co.edu.uniquindio.compiladores.proyecto


/**
 * Clase que representa un token o una categoria lexica del lenguaje
 * @author Laura Daniela Florez Polania
 *  @author Alex Ferney Vargas
 *   @author Andres Felipe Betancourt
 *
 */
class Token (var palabra:String, var categoria:Categoria, var fila:Int, var columna:Int ) {


    override fun toString(): String {
        return "Token(palabra='$palabra', categoria=$categoria, fila=$fila, columna=$columna)"
    }


}

