package controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import modelo.CRUDProducto;
import modelo.Producto;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import modelo.Proveedor;
import modelo.util.BusquedaProveedor;

public class FXMLEditarProductoController implements Initializable{
    @FXML
    private ComboBox<Proveedor> cbProveedor;
    @FXML
    private TextField nombreProductoTf;
    @FXML
    private TextField cantidadProductoTf;
    @FXML
    private TextField precioProductoTf;
    @FXML
    private TextField marcaProductoTf;
    
    @FXML
    public void editar(MouseEvent event) throws IOException{
        if(camposVacios()){
            MensajeController.mensajeAdvertencia("Hay campos vacios");
        }else{
            Producto producto= new Producto(nombreProductoTf.getText(), marcaProductoTf.getText(), Float.parseFloat(precioProductoTf.getText()), Integer.parseInt(cantidadProductoTf.getText()),cbProveedor.getValue().getIdProveedor());
            CRUDProducto edit = new CRUDProducto();
            Alert aler = new Alert(Alert.AlertType.INFORMATION, "¡Edición exitosa!", ButtonType.OK);
            edit.editarProducto(llave, producto);
            ButtonType resul = aler.showAndWait().orElse(ButtonType.OK);
            if (ButtonType.OK.equals(resul)) {
                VistaController.cargarVista(event,"Consultar Productos","vista/ConsultarProductos.fxml");
            }
        }
    }
    
    @FXML
    public void cancelar(MouseEvent event) throws IOException{
        VistaController.cargarVista(event,"Consultar Productos","vista/ConsultarProductos.fxml");
    }
    
    public boolean camposVacios(){
        return (cbProveedor.getValue() == null || nombreProductoTf.getText().isEmpty() || cantidadProductoTf.getText().isEmpty() || precioProductoTf.getText().isEmpty() || marcaProductoTf.getText().isEmpty());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nombreProductoTf.setText(FXMLConsultarProductoController.aux.getNombre());
        marcaProductoTf.setText(FXMLConsultarProductoController.aux.getMarca());
        precioProductoTf.setText(String.valueOf(FXMLConsultarProductoController.aux.getPrecio()));
        cantidadProductoTf.setText(String.valueOf(FXMLConsultarProductoController.aux.getCantidad()));
        llave= FXMLConsultarProductoController.aux.getNombre();

        Proveedor proveedor = new Proveedor();
        List<Proveedor> proveedores = proveedor.obtenerProvedores("", BusquedaProveedor.BUSCAR_PROVEEDORES_NO_BORRADOS);
        ObservableList<Proveedor> listaProveedores = FXCollections.observableArrayList(proveedores);
        this.cbProveedor.setItems(listaProveedores);
        this.cbProveedor.getItems().removeAll();

        for (Proveedor proveedorASeleccionar: listaProveedores) {
            if(proveedorASeleccionar.getIdProveedor() == FXMLConsultarProductoController.aux.getIdProveedor()){
                this.cbProveedor.getSelectionModel().select(proveedorASeleccionar);
                break;
            }
        }
    }
    String llave= "";
    
    @FXML
    private void filtrarPrecio(KeyEvent evento) {
        char caracter = evento.getCharacter().charAt(0);
        if (Character.isDigit(caracter) || caracter == '.') {
            if (precioProductoTf.getText().split("\\.").length < 2 || Character.isDigit(caracter)) {
            } else {
                evento.consume();
            }
        } else {
            evento.consume();
        }
    }
    
    @FXML
    private void filtrarCantidad(KeyEvent evento) {
        char caracter = evento.getCharacter().charAt(0);
        if (Character.isDigit(caracter) || caracter == '.') { 
        } else {
            evento.consume();
        }
    }
}