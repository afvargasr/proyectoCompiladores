package co.edu.uniquindio.compiladores.proyecto

/**
 * Definicion de las categorias lexicas del lenguaje 
 * @author Laura Daniela Florez Polania
 *  @author Alex Ferney Vargas
 *   @author Andres Felipe Betancourt
 *
 */
enum class Categoria
{
    IDENTIFICADOR,
    PALABRA_RESERVADA,
    ENTERO,
    DECIMAL,
    OPERADOR_ARTIMETICO,
    OPERADOR_LOGICO,
    OPERADOR_RELACIONAL,
    OPERADOR_ASIGNACION,
    INCREMENTO_DECREMENTO,
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
