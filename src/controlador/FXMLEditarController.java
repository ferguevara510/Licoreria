package controlador;

import modelo.Empleado;
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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

public class FXMLEditarController implements Initializable{
    @FXML
    private TextField nombreEmpleadoTf;
    @FXML
    private TextField noTrabajadorTf;
    @FXML
    private TextField apellidosEmpleadoTf;
    @FXML
    private TextField contrasenaEmpleadoTf;
    @FXML
    private CheckBox tipoEmpleadoCB;
    
    @FXML
    public void editar(MouseEvent event) throws IOException{
        if(camposVacios()){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Hay campos vacios", ButtonType.OK);
            ButtonType result = alert.showAndWait().orElse(ButtonType.OK);
            if (ButtonType.OK.equals(result)) {}
        }else{
            Empleado empleado= new Empleado(noTrabajadorTf.getText(),nombreEmpleadoTf.getText(),apellidosEmpleadoTf.getText(),contrasenaEmpleadoTf.getText(),Empleado.obtenerTipoEmpleado(tipoEmpleadoCB.isSelected()));
            CRUDEmpleado edit = new CRUDEmpleado();
            edit.editarEmpleado(llave, empleado);
            Alert aler = new Alert(Alert.AlertType.INFORMATION, "¡Edición exitosa!", ButtonType.OK);
            ButtonType resul = aler.showAndWait().orElse(ButtonType.OK);
            if (ButtonType.OK.equals(resul)) {
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
        }
    }
    
    @FXML
    public void cancelar(MouseEvent event) throws IOException{
        Parent root; 
        root = FXMLLoader.load(getClass().getClassLoader().getResource("vista/ConsultarEmpleados.fxml"));
        Stage stage = new Stage();
        stage.setTitle(" ConsultarEmpleados");
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
        stage.setResizable(false);
        stage.getIcons().add(new Image("/vista/imagenes/vintage.png"));
        ((Node)(event.getSource())).getScene().getWindow().hide(); 
    }
    
    public boolean camposVacios(){
        return (nombreEmpleadoTf.getText().isEmpty() || apellidosEmpleadoTf.getText().isEmpty() || contrasenaEmpleadoTf.getText().isEmpty() || noTrabajadorTf.getText().isEmpty());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nombreEmpleadoTf.setText(FXMLConsultarController.aux.getNombre());
        apellidosEmpleadoTf.setText(FXMLConsultarController.aux.getApellidos());
        contrasenaEmpleadoTf.setText(String.valueOf(FXMLConsultarController.aux.getContraseña()));
        noTrabajadorTf.setText(String.valueOf(FXMLConsultarController.aux.getNumTrabajador()));
        tipoEmpleadoCB.setSelected(FXMLConsultarController.aux.isGerente());
        llave= FXMLConsultarController.aux.getNumTrabajador();
    }
    String llave= "";
    
    @FXML
    private void filtrarNumeroTrabajador(KeyEvent evento) {
        char caracter = evento.getCharacter().charAt(0);
        if (Character.isDigit(caracter)) {
        } else {
            evento.consume();
        }
    }
    
    @FXML
    private void filtrarNombreApellido (KeyEvent evento) {
        char caracter = evento.getCharacter().charAt(0);
        if (Character.isAlphabetic(caracter)) {
        } else {
            evento.consume();
        }
    }

}