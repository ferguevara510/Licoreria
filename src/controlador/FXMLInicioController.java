package controlador;

import modelo.util.Conexion;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.Empleado;

public class FXMLInicioController implements Initializable{
    Connection con = Conexion.getInstance();
    ResultSet rs;
    @FXML
    private TextField usuarioTf;
    @FXML
    private PasswordField contraseñaPf;

    @FXML
    private void iniciarSesion(MouseEvent event) throws SQLException, IOException{
       
       if (camposVacios()) {
            // allow user to decide between yes and no
            Alert alert = new Alert(Alert.AlertType.WARNING, "Hay campos vacios", ButtonType.OK);
            // clicking X also means no
            ButtonType result = alert.showAndWait().orElse(ButtonType.OK);
            if (ButtonType.OK.equals(result)) {}
        } else {
            if(verificarUsuario()==1){
                if(verificarContraseña()==1){
                    Empleado empleado = new Empleado();
                    empleado = empleado.obtenerEmpleado(usuarioTf.getText());
                    System.getProperties().put("Empleado", empleado.isTipo());
                    System.getProperties().put("Id_empleado", empleado.getNumTrabajador());
                    Parent root;
                    root = FXMLLoader.load(getClass().getClassLoader().getResource("vista/Principal.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle(" Principal ");
                    stage.setScene(new Scene(root, 650, 600));
                    stage.show();
                    stage.setResizable(false);
                    stage.getIcons().add(new Image("/vista/imagenes/vintage.png"));
                    ((Node)(event.getSource())).getScene().getWindow().hide(); 
                }else{
                    Alert alert = new Alert(Alert.AlertType.WARNING, " Contraseña incorrecta ", ButtonType.OK);
                    ButtonType result = alert.showAndWait().orElse(ButtonType.OK);
                    if (ButtonType.OK.equals(result)) {}
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING, " Usuario incorrecto ", ButtonType.OK);
                ButtonType result = alert.showAndWait().orElse(ButtonType.OK);
                if (ButtonType.OK.equals(result)) {}
            }
        }  
    }
    
    private int verificarUsuario() throws SQLException{
        int v=0;
        rs = con.createStatement().executeQuery("select * from empleado where numTrabajador='"+usuarioTf.getText()+"'");
        if(rs.next()){
            v=1;
        }else{
            v=0;
        }
        return v;
    }
    
    private int verificarContraseña() throws SQLException{
        int v=0;
        rs = con.createStatement().executeQuery("select * from empleado where numTrabajador='"+usuarioTf.getText()+"' and contrasena='"+contraseñaPf.getText()+"'");
        if(rs.next()){
            v=1;
        }else{
            v=0;
        }
        return v;
    }
    
    private boolean camposVacios() {
        return (usuarioTf.getText().isEmpty() || contraseñaPf.getText().isEmpty());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
}