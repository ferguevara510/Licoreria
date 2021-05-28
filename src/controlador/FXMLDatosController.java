package controlador;

import modelo.CRUDEmpleado;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.Empleado;

public class FXMLDatosController implements Initializable{
    @FXML
    private TextField nombreEmpleadoTf;
    @FXML
    private TextField contraseñaEmpleadoTf;
    @FXML
    private TextField apellidosEmpleadoTf;
    @FXML
    private TextField noTrabajadorTf;
    @FXML
    private CheckBox tipoEmpleadoCB;
    
    @FXML
    public void agregar(MouseEvent event) throws IOException{
        if(camposVacios()){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Hay campos vacios", ButtonType.OK);
            ButtonType result = alert.showAndWait().orElse(ButtonType.OK);
            if (ButtonType.OK.equals(result)) {}
        }else{    
            String nombre = nombreEmpleadoTf.getText();
            String apellidos = apellidosEmpleadoTf.getText();
            String contraseña = contraseñaEmpleadoTf.getText();
            String numTrabajador =noTrabajadorTf.getText();     
            Boolean tipo = tipoEmpleadoCB.isSelected();
            Empleado empleado = new Empleado();
            if(empleado.obtenerEmpleado(numTrabajador) == null){
                CRUDEmpleado agre = new CRUDEmpleado();
                Alert aler = new Alert(Alert.AlertType.INFORMATION, "¡Registro exitoso!", ButtonType.OK);
                agre.nuevoEmpleado(numTrabajador, nombre, apellidos, contraseña, tipo);
                ButtonType resul = aler.showAndWait().orElse(ButtonType.OK);
                if (ButtonType.OK.equals(resul)) {
                    limpiar();
                    Parent root; 
                    root = FXMLLoader.load(getClass().getClassLoader().getResource("vista/DatosEmpleado.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle(" DatosEmpleado ");
                    stage.setScene(new Scene(root, 600, 400));
                    stage.show();
                    stage.setResizable(false);
                    stage.getIcons().add(new Image("/vista/imagenes/vintage.png"));
                    ((Node)(event.getSource())).getScene().getWindow().hide();
                }
            }else{
                Alert aler = new Alert(Alert.AlertType.INFORMATION, "El numero de Empleado ya existe", ButtonType.OK);
                aler.show();
            }
        }
    }
    
    @FXML
    public void cancelar(MouseEvent event) throws IOException{
        Parent root; 
        root = FXMLLoader.load(getClass().getClassLoader().getResource("vista/ConsultarEmpleados.fxml"));
        Stage stage = new Stage();
        stage.setTitle(" ConsultarEmpleados ");
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
        stage.setResizable(false);
        stage.getIcons().add(new Image("/vista/imagenes/vintage.png"));
        ((Node)(event.getSource())).getScene().getWindow().hide(); 
    }
    
    public boolean camposVacios(){
        return (nombreEmpleadoTf.getText().isEmpty() || apellidosEmpleadoTf.getText().isEmpty() || noTrabajadorTf.getText().isEmpty());
    }
    
    public void limpiar(){
        nombreEmpleadoTf.setText("");
        contraseñaEmpleadoTf.setText("");
        apellidosEmpleadoTf.setText("");
        tipoEmpleadoCB.setText("");
        noTrabajadorTf.setText("");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }   
    
    @FXML
    private void filtrarNumeroTrabajador(KeyEvent evento) {
        char caracter = evento.getCharacter().charAt(0);
        if (Character.isDigit(caracter)) {
            if (noTrabajadorTf.getText().split("").length < 3 || Character.isDigit(caracter)) {
            } else {
                evento.consume();
            }
        } else {
            evento.consume();
        }
    }
    
    @FXML
    private void filtrarNombreApellido (KeyEvent evento) {
        char caracter = evento.getCharacter().charAt(0);
        if (Character.isAlphabetic(caracter)|| caracter == ' ') {
        } else {
            evento.consume();
        }
    }
    
}