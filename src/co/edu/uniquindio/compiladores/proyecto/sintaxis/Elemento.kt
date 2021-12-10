package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Elemento(var listaImports: ArrayList<Import>, var listaDeclaracionVariable: ArrayList<DeclaracionVariable>, var listaDeclaracionVariableI: ArrayList<DeclaracionVariableI>, var listaFuncion: ArrayList<Funcion>)
{
    override fun toString(): String {
        return "Elemento(listaImports=$listaImports, listaDeclaracionVariable=$listaDeclaracionVariable, listaDeclaracionVariableI=$listaDeclaracionVariableI, listaFuncion=$listaFuncion)"
    }

    fun getArbolVisual(): TreeItem<String> {

        var raiz = TreeItem("Lista Elementos")

        for (f in listaImports){
            raiz.children.add(f.getArbolVisual())
        }

        for (f in listaDeclaracionVariable){
            raiz.children.add(f.getArbolVisual())
        }

        for (f in listaDeclaracionVariableI){
            raiz.children.add(f.getArbolVisual())
        }

        for (f in listaFuncion){
            raiz.children.add(f.getArbolVisual())
        }

        return raiz
    }

    fun llenarTablaSimbolos(tablaSimbolos:TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String){
        for (f in listaFuncion){
            f.llenarTablaSimbolos(tablaSimbolos, listaErrores, ambito)
        }

        for (i in listaImports){
            i.llenarTablaSimbolos(tablaSimbolos, listaErrores, ambito)
        }

        for (v in listaDeclaracionVariable){
            v.llenarTablaSimbolos(tablaSimbolos, listaErrores, ambito)
        }

        for (vi in listaDeclaracionVariableI){
            vi.llenarTablaSimbolos(tablaSimbolos, listaErrores, ambito)
        }
    }

    fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>){
        for (f in listaFuncion){
            f.analizarSemantica(tablaSimbolos, listaErrores)
        }

        for (i in listaImports){
            i.analizarSemantica(tablaSimbolos, listaErrores)
        }

        for (v in listaDeclaracionVariable){
            v.analizarSemantica(tablaSimbolos, listaErrores)
        }

        for (vi in listaDeclaracionVariableI){
            vi.analizarSemantica(tablaSimbolos, listaErrores)
        }
    }



}