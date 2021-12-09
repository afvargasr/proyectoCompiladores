package co.edu.uniquindio.compiladores.proyecto.semantica

import co.edu.uniquindio.compiladores.proyecto.lexico.Error

class TablaSimbolos (var listaErrores: ArrayList<Error>){

    var listaSimbolos: ArrayList<Simbolo> = ArrayList()

    /**
     * Permite almacenar un símbolo que representa un valor
     */
    fun guardarSimboloValor(nombre: String, tipo: String, modificable:Boolean, ambito: String, fila:Int, columna:Int){

        val s = buscarSimboloValor(nombre, ambito)

        if (s == null){
            listaSimbolos.add(Simbolo(nombre,tipo, modificable, ambito, fila, columna))
        }else{
            listaErrores.add(Error("El campo $nombre ya existe en el ámbito $ambito", fila, columna))
        }

    }

    /**
     * Permite almacenar un símbolo que representa un método
     */
    fun guardarSimboloMetodo(nombre: String, tipoRetorno: String, tipoParametros: ArrayList<String>, ambito: String, fila:Int, columna:Int){
        val s = buscarSimboloMetodo(nombre, tipoParametros)
        if (s==null){
            listaSimbolos.add(Simbolo(nombre, tipoRetorno, tipoParametros, ambito))
        }else{
            listaErrores.add(Error("El metodo $nombre ya existe en el ámbito $ambito", fila, columna))
        }
    }

     /**
     * Permite buscar un símbolo Valor para saber si ya existe
     */
    fun buscarSimboloValor(nombre: String, ambito: String): Simbolo? {
        for (s in listaSimbolos){
            if (s.tipoParametros == null) {
                if (s.nombre == nombre && s.ambito == ambito) {
                    return s
                }
            }
        }
        return null
    }

    /**
     * Permite buscar un símbolo método para saber si ya existe
     */
    fun buscarSimboloMetodo(nombre: String, tipoParametros: ArrayList<String>): Simbolo? {
        for (s in listaSimbolos){
            if (s.tipoParametros != null) {
                if (s.nombre == nombre && s.tipoParametros == tipoParametros) {
                    return s
                }
            }
        }
        return null
    }

    override fun toString(): String {
        return "TablaSimbolos(listaSimbolos=$listaSimbolos)"
    }


}