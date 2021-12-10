package co.edu.uniquindio.compiladores.proyecto.controladores

import co.edu.uniquindio.compiladores.proyecto.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import co.edu.uniquindio.compiladores.proyecto.semantica.AnalizadorSemantico
import co.edu.uniquindio.compiladores.proyecto.sintaxis.AnalizadorSintactico
import co.edu.uniquindio.compiladores.proyecto.sintaxis.UnidadDeCompilacion
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import java.net.URL
import java.util.*

class InicioController: Initializable {

    @FXML
    private lateinit var colCategoria: TableColumn<Token, String>

    @FXML
    private lateinit var colColumna: TableColumn<Token, Int>

    @FXML
    private lateinit var colFila: TableColumn<Token, Int>

    @FXML
    private lateinit var colError: TableColumn<Token, String>

    @FXML
    private lateinit var colCol: TableColumn<Token, Int>

    @FXML
    private lateinit var colFil: TableColumn<Token, Int>

    @FXML
    private lateinit var colLexema: TableColumn<Token, String>

    @FXML
    private lateinit var tblTokens: TableView<Token>

    @FXML
    private lateinit var tblError: TableView<Error>

    @FXML
    private lateinit var txtData: TextArea

    @FXML
    private lateinit var arbolVisual: TreeView<String>

    private var unidadCompilacion:UnidadDeCompilacion? = null

    @FXML
    fun analizar(actionEvent: javafx.event.ActionEvent) {
        if (txtData.text.length > 0){
            val lexico = AnalizadorLexico(txtData.text)
            lexico.analizar()
            tblTokens.items = FXCollections.observableArrayList(lexico.listaTokens)

            val sintaxis = AnalizadorSintactico(lexico.listaTokens)
            unidadCompilacion = sintaxis.esUnidadDeCompilacion()

            if (unidadCompilacion != null) {
                arbolVisual.root = unidadCompilacion!!.getArbolVisual()
                val semantica = AnalizadorSemantico(unidadCompilacion!!)
                semantica.llenarTablaSimbolos()
                tblError.items = FXCollections.observableArrayList(semantica.listaErrores)
                println(semantica.tablaSimbolos)
                println(semantica.listaErrores)
            } else {
                var alerta = Alert(Alert.AlertType.WARNING)
                alerta.headerText = "Mensaje de Error"
                alerta.contentText = "Hay errores léxicos"
            }
        }
    }

    @FXML
    fun traducir(actionEvent: javafx.event.ActionEvent) {
        if (unidadCompilacion != null) {
            println(unidadCompilacion!!.getJavaCode())
        }
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {

        colLexema.cellValueFactory = PropertyValueFactory("palabra")
        colCategoria.cellValueFactory = PropertyValueFactory("categoria")
        colFila.cellValueFactory = PropertyValueFactory("fila")
        colColumna.cellValueFactory = PropertyValueFactory("columna")

        colError.cellValueFactory = PropertyValueFactory("error")
        colFil.cellValueFactory = PropertyValueFactory("fila")
        colCol.cellValueFactory = PropertyValueFactory("columna")

    }

}