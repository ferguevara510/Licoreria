package controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import modelo.CRUDProducto;
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
import modelo.Producto;
import modelo.Proveedor;
import modelo.util.BusquedaProveedor;

public class FXMLDatosProductoController implements Initializable{
    @FXML
    private ComboBox<Proveedor> cbProvedor;
    @FXML
    private TextField nombreProductoTf;
    @FXML
    private TextField cantidadProductoTf;
    @FXML
    private TextField precioProductoTf;
    @FXML
    private TextField marcaProductoTf;
    
    @FXML
    public void agregar(MouseEvent event) throws IOException{
        if(camposVacios()){
            MensajeController.mensajeAdvertencia("Hay campos vacios");
        }else{    
            String nombre = nombreProductoTf.getText();
            String marca = marcaProductoTf.getText();
            float precio = Float.parseFloat(precioProductoTf.getText());
            int cantidad = Integer.parseInt(cantidadProductoTf.getText());
            Proveedor proveedor = this.cbProvedor.getValue();
            if(this.cbProvedor.getItems().isEmpty()){
                MensajeController.mensajeAdvertencia("Tiene que registrar un porveedor para poder registrar productos");
            }else{
                if (proveedor == null){
                    MensajeController.mensajeAdvertencia("Selecciona un proveedor");
                }else {
                    Producto producto = new Producto();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Realmente desea guardar?", ButtonType.YES, ButtonType.NO);
                    ButtonType result = alert.showAndWait().orElse(ButtonType.NO);
                    if (producto.obtenerProducto(nombre) == null){
                        CRUDProducto agre =new CRUDProducto();
                        Alert aler = new Alert(Alert.AlertType.INFORMATION, "¡Registro exitoso!", ButtonType.OK);
                        agre.nuevoProducto(nombre, marca, precio, cantidad,cbProvedor.getValue().getIdProveedor());
                        ButtonType resul = aler.showAndWait().orElse(ButtonType.OK);
                        if (ButtonType.OK.equals(resul)) {
                            limpiar();
                        }
                    } else{
                        MensajeController.mensajeAdvertencia("El Producto ya existe");
                    }
                }
            }
        }
    }
    
    @FXML
    public void cancelar(MouseEvent event) throws IOException{
        VistaController.cargarVista(event,"Consultar Productos","vista/ConsultarProductos.fxml");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Proveedor proveedor = new Proveedor();
        List<Proveedor> proveedores = proveedor.obtenerProvedores("", BusquedaProveedor.BUSCAR_PROVEEDORES_NO_BORRADOS);
        ObservableList<Proveedor> listaProveedores = FXCollections.observableArrayList(proveedores);
        if(listaProveedores.isEmpty()){
            MensajeController.mensajeAdvertencia("Tiene que registrar un porveedor para poder registrar productos");
            this.cbProvedor.setItems(listaProveedores);
        }else{
            this.cbProvedor.setItems(listaProveedores);
        }
    }
    
    public void limpiar(){
        nombreProductoTf.setText("");
        cantidadProductoTf.setText("");
        precioProductoTf.setText("");
        marcaProductoTf.setText("");
        this.cbProvedor.setValue(null);
    }
    
    public boolean camposVacios(){
        return (cbProvedor.getValue() == null || nombreProductoTf.getText().isEmpty() || cantidadProductoTf.getText().isEmpty() || precioProductoTf.getText().isEmpty() || marcaProductoTf.getText().isEmpty());
    }
    
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