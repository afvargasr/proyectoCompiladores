package co.edu.uniquindio.compiladores.proyecto.lexico

/**
 * Definicion de las categorias lexicas del lenguaje
 * @author Laura Daniela Florez Polania
 *  @author Alex Ferney Vargas
 *   @author Andres Felipe Betancourt
 *
 */
enum class Categoria {
    IDENTIFICADOR,
    PALABRA_RESERVADA,
    ENTERO,
    DECIMAL,
    OPERADOR_ARTIMETICO,
    OPERADOR_LOGICO,
    OPERADOR_RELACIONAL,
    OPERADOR_ASIGNACION,
    INCREMENTO_DECREMENTO,
    PARENTESIS_IZQUIERDO,
    PARENTESIS_DERECHO,
    CORCHETE_IZQUIERDO,
    CORCHETE_DERECHO,
    LLAVE_IZQUIERDA,
    LLAVE_DERECHA,
    AGRUPADOR,
    FIN_SENTENCIA,
    PUNTO,
    DOS_PUNTOS,
    SEPARADOR,
    COMENTARIO_LINEA,
    COMENTARIO_BLOQUE,
    CADENA_CARACTERES,
    CARACTER,
    DESCONOCIDO
}
