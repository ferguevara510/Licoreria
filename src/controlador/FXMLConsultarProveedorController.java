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
import javafx.scene.input.MouseEvent;
import modelo.Proveedor;
import modelo.util.BusquedaProveedor;

import java.io.IOException;
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

        this.llenarTabla(listaProveedores);

        String tipo = System.getProperties().getProperty("Empleado");
        if(tipo.equals("Trabajador")){
            registroProveedorBtn.setDisable(true);
            editarProveedorBtn.setDisable(true);
            eliminarProveedorBtn.setDisable(true);
        }
    }

    @FXML
    private void consultar(MouseEvent mouseEvent) {
        this.table.getItems().clear();
        Proveedor proveedor = new Proveedor();
        String cadenaBusqueda = buscarProveedorTf.getText().trim();
        List<Proveedor> proveedores = proveedor.obtenerProvedores(cadenaBusqueda, BusquedaProveedor.BUSCAR_CONTENGA_NOMBRE);
        ObservableList<Proveedor> listaProveedores = FXCollections.observableArrayList(proveedores);

        this.llenarTabla(listaProveedores);
    }


    private void llenarTabla(ObservableList<Proveedor> listaProveedores){
        col_direccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        col_telefono.setCellValueFactory(new PropertyValueFactory<>("numTelefono"));
        col_empresa.setCellValueFactory(new PropertyValueFactory<>("empresa"));
        col_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        table.setItems(listaProveedores);
    }

    @FXML
    private void agregar(MouseEvent mouseEvent) throws IOException {
        VistaController.cargarVista(mouseEvent,"Registrar Proveedor","vista/RegistrarPorveedor.fxml");
    }

    @FXML
    private void regresar(MouseEvent mouseEvent) throws IOException {
        VistaController.cargarVista(mouseEvent,"Principal","vista/Principal.fxml");
    }

    @FXML
    private void editar(MouseEvent mouseEvent) throws IOException {
        if(table.getSelectionModel().getSelectedItem() != null){
            FXMLConsultarProveedorController.proveedorSeleccionado = table.getSelectionModel().getSelectedItem();
            VistaController.cargarVista(mouseEvent,"Editar Proveedor","vista/EditarProveedor.fxml");
        }else {
            MensajeController.mensajeAdvertencia("Selecciona el proveedor a editar");
        }
    }

    @FXML
    private void eliminar(MouseEvent mouseEvent) {
        Proveedor proveedorSeleccionado = this.table.getSelectionModel().getSelectedItem();

        if(proveedorSeleccionado != null){
            if(MensajeController.mensajeDesicion("¿Estas seguro que deseas eliminar este proveedor?")){
                if(Proveedor.borrarProvedor(proveedorSeleccionado.getIdProveedor())){
                    this.table.getItems().remove(proveedorSeleccionado);
                    MensajeController.mensajeInformacion("Se eliminos el proveedor");
                }else {
                    MensajeController.mensajeAdvertencia("No se pudo eliminar el porveedor");
                }
            }
        }else{
            MensajeController.mensajeAdvertencia("Selecciona el proveedor que que desea eliminar");
        }
    }
}
