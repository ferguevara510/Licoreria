package controlador;

import modelo.CRUDProducto;
import modelo.Producto;
import java.io.IOException;
import java.net.URL;
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

public class FXMLEditarProductoController implements Initializable{
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
        System.out.println("Editar");
        if(camposVacios()){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Hay campos vacios", ButtonType.OK);
            ButtonType result = alert.showAndWait().orElse(ButtonType.OK);
            if (ButtonType.OK.equals(result)) {}
        }else{
            Producto producto= new Producto(nombreProductoTf.getText(), marcaProductoTf.getText(), Float.parseFloat(precioProductoTf.getText()), Integer.valueOf(cantidadProductoTf.getText()));
            System.out.println(producto);
            CRUDProducto edit = new CRUDProducto();
            Alert aler = new Alert(Alert.AlertType.INFORMATION, "¡Edición exitosa!", ButtonType.OK);
                edit.editarProducto(llave, producto);
                ButtonType resul = aler.showAndWait().orElse(ButtonType.OK);
                if (ButtonType.OK.equals(resul)) {
                    Parent root; 
                    root = FXMLLoader.load(getClass().getClassLoader().getResource("vista/ConsultarProductos.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle(" ConsultarProductos ");
                    stage.setScene(new Scene(root, 900, 600));
                    stage.show();
                    stage.setResizable(false);
                    stage.getIcons().add(new Image("/vista/imagenes/vintage.png"));
                    ((Node)(event.getSource())).getScene().getWindow().hide(); 
                }
        }
    }
    
    @FXML
    public void cancelar(MouseEvent event) throws IOException{
        Parent root; 
        root = FXMLLoader.load(getClass().getClassLoader().getResource("vista/ConsultarProductos.fxml"));
        Stage stage = new Stage();
        stage.setTitle(" ConsultarProductos");
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
        stage.setResizable(false);
        stage.getIcons().add(new Image("/vista/imagenes/vintage.png"));
        ((Node)(event.getSource())).getScene().getWindow().hide(); 
    }
    
    public boolean camposVacios(){
        return (nombreProductoTf.getText().isEmpty() || cantidadProductoTf.getText().isEmpty() || precioProductoTf.getText().isEmpty() || marcaProductoTf.getText().isEmpty());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nombreProductoTf.setText(FXMLConsultarProductoController.aux.getNombre());
        marcaProductoTf.setText(FXMLConsultarProductoController.aux.getMarca());
        precioProductoTf.setText(String.valueOf(FXMLConsultarProductoController.aux.getPrecio()));
        cantidadProductoTf.setText(String.valueOf(FXMLConsultarProductoController.aux.getCantidad()));
        llave= FXMLConsultarProductoController.aux.getNombre();
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