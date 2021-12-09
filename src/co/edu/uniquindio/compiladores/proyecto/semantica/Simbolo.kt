package co.edu.uniquindio.compiladores.proyecto.semantica

class Simbolo {

    var nombre: String = ""
    var tipo: String = ""
    var modificable:Boolean = false
    var fila = 0
    var columna = 0
    var ambito: String = ""
    var tipoParametros: ArrayList<String>? = null

    /**
     * Constructor para valor
     */
    constructor(nombre: String, tipo: String, modificable:Boolean, ambito: String, fila:Int, columna:Int){
        this.nombre = nombre
        this.tipo = tipo
        this.modificable = modificable
        this.ambito = ambito
        this.fila = fila
        this.columna = columna
    }

    /**
     * Constructor para método
     */
    constructor(nombre: String, tipoRetorno: String, tipoParametros: ArrayList<String>, ambito: String){
        this.nombre = nombre
        this.tipo = tipoRetorno
        this.tipoParametros = tipoParametros
        this.ambito = ambito
    }

    override fun toString(): String {

        return if (tipoParametros == null){
            "Simbolo(nombre='$nombre', tipo='$tipo', modificable=$modificable, fila=$fila, columna=$columna, ambito='$ambito')"
        }else{
            "Simbolo(nombre='$nombre', tipo='$tipo', ambito='$ambito', tipoParametros=$tipoParametros)"
        }

    }


}