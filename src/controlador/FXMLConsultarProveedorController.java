package controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Proveedor;
import modelo.util.BusquedaProveedor;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLConsultarProveedorController implements Initializable {
    public static Proveedor proveedorSeleccionado;

    @FXML
    private TextField buscarProveedorTf;
    @FXML
    private Button buscarProveedorBtn;
    @FXML
    private Button cerrarBtn;
    @FXML
    private Button registroProveedorBtn;
    @FXML
    private Button editarProveedorBtn;
    @FXML
    private Button eliminarProveedorBtn;
    @FXML
    private TableView<Proveedor> table;
    @FXML
    private TableColumn<Proveedor,String> col_empresa;
    @FXML
    private TableColumn<Proveedor,String> col_nombre;
    @FXML
    private TableColumn<Proveedor,String> col_direccion;
    @FXML
    private TableColumn<Proveedor,String> col_telefono;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Proveedor proveedor = new Proveedor();
        List<Proveedor> proveedores = proveedor.obtenerProvedores("", BusquedaProveedor.BUSCAR_PROVEEDORES_NO_BORRADOS);
        ObservableList<Proveedor> listaProveedores = FXCollections.observableArrayList(proveedores);

        col_direccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        col_telefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        col_empresa.setCellValueFactory(new PropertyValueFactory<>("empresa"));
        col_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        table.setItems(listaProveedores);

        String tipo = System.getProperties().getProperty("Empleado");
        if(tipo.equals("Trabajador")){
            registroProveedorBtn.setDisable(true);
            editarProveedorBtn.setDisable(true);
            eliminarProveedorBtn.setDisable(true);
        }
    }
}
