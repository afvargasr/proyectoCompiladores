package co.edu.uniquindio.compiladores.proyecto.sintaxis

class Funcion(var identificador: String, var listaParametros : ArrayList<Parametro>, var tipoRetorno: String, var listaSentencias: ArrayList<Sentencia> )
{
    override fun toString(): String {
        return "Funcion(identificador='$identificador', listaParametros=$listaParametros, tipoRetorno='$tipoRetorno', listaSentencias=$listaSentencias)"
    }
}