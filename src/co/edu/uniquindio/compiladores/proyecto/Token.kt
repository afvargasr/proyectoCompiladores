package co.edu.uniquindio.compiladores.proyecto

class Token (var palabra:String, var categoria:Categoria, var fila:Int, var columna:Int ) {


    override fun toString(): String {
        return "Token(palabra='$palabra', categoria=$categoria, fila=$fila, columna=$columna)"
    }


}

