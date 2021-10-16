package co.edu.uniquindio.compiladores.proyecto.controladores

import co.edu.uniquindio.compiladores.proyecto.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextArea
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
    private lateinit var colLexema: TableColumn<Token, String>

    @FXML
    private lateinit var tblTokens: TableView<Token>

    @FXML
    private lateinit var txtData: TextArea

    @FXML
    fun analizar(actionEvent: javafx.event.ActionEvent) {
        if (txtData.text.length > 0){
            val lexico = AnalizadorLexico(txtData.text)
            lexico.analizar()
            tblTokens.items = FXCollections.observableArrayList(lexico.listaTokens)
        }
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {

        colLexema.cellValueFactory = PropertyValueFactory("palabra")
        colCategoria.cellValueFactory = PropertyValueFactory("categoria")
        colFila.cellValueFactory = PropertyValueFactory("fila")
        colColumna.cellValueFactory = PropertyValueFactory("columna")

    }

}