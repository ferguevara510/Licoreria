package controlador;

import javafx.collections.ObservableList;
import javafx.scene.control.*;
import modelo.Empleado;
import modelo.CRUDEmpleado;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.util.Conexion;

public class FXMLConsultarController implements Initializable{
    public static Empleado aux= new Empleado();
    @FXML
    private TextField buscarEmpleadoTf;
    @FXML
    private TableView<Empleado> table;
    @FXML
    private TableColumn<Empleado, String> col_numTrabajador;
    @FXML
    private TableColumn<Empleado, String> col_contraseña;
    @FXML
    private TableColumn<Empleado, String> col_nombre;
    @FXML
    private TableColumn<Empleado, String> col_apellidos;
    @FXML
    private TableColumn<Empleado, String> col_tipo;
    ObservableList<Empleado> oblist=FXCollections.observableArrayList();

    public FXMLConsultarController() {
    }

    @FXML
    public void agregar(MouseEvent event) throws IOException{
        Parent root; 
        root = FXMLLoader.load(getClass().getClassLoader().getResource("vista/DatosEmpleado.fxml"));
        Stage stage = new Stage();
        stage.setTitle(" DatosEmpleado ");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
        stage.setResizable(false);
        stage.getIcons().add(new Image("/vista/imagenes/vintage.png"));
        // Hide this current window (if this is what you want)
        ((Node)(event.getSource())).getScene().getWindow().hide(); 
    }
    
    @FXML
    public void regresar(MouseEvent event) throws IOException{
        Parent root; 
        root = FXMLLoader.load(getClass().getClassLoader().getResource("vista/Principal.fxml"));
        Stage stage = new Stage();
        stage.setTitle(" Principal ");
        stage.setScene(new Scene(root, 650, 600));
        stage.show();
        stage.setResizable(false);
        stage.getIcons().add(new Image("/vista/imagenes/vintage.png"));
        ((Node)(event.getSource())).getScene().getWindow().hide(); 
    }
    
    @FXML
    public void consultar(MouseEvent event){
        Connection con = Conexion.getInstance();
        ResultSet rs;
        try {
            this.table.getItems().clear();
            rs = con.createStatement().executeQuery("select * from empleado where nombre like '%"+buscarEmpleadoTf.getText()+"%'");
            while(rs.next()){
                if(rs.getBoolean("tipo")){
                    oblist.add(new Empleado(rs.getString("numTrabajador"),rs.getString("nombre"),rs.getString("apellidos"),rs.getString("contraseña"),"Gerente"));
                }else{
                    oblist.add(new Empleado(rs.getString("numTrabajador"),rs.getString("nombre"),rs.getString("apellidos"),rs.getString("contraseña"),"Trabajador"));
                }
            }
            col_numTrabajador.setCellValueFactory(new PropertyValueFactory("numTrabajador"));
            col_nombre.setCellValueFactory(new PropertyValueFactory("nombre"));
            col_apellidos.setCellValueFactory(new PropertyValueFactory("apellidos"));
            col_tipo.setCellValueFactory(new PropertyValueFactory("tipo"));
            table.setItems(oblist);
        }catch (SQLException ex) {
            Logger.getLogger(controlador.FXMLConsultarController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                if(!con.isClosed()){
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(CRUDEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @FXML
    public void editar(MouseEvent event) throws IOException{
        Empleado sele = table.getSelectionModel().getSelectedItem();
        
        if(sele != null){
            aux.setNumTrabajador(sele.getNumTrabajador());
            aux.setNombre(sele.getNombre());
            aux.setApellidos(sele.getApellidos());
            aux.setContraseña(sele.getContraseña());
            aux.setTipo(sele.isTipo());
            Parent root;
            root = FXMLLoader.load(getClass().getClassLoader().getResource("vista/EditarEmpleado.fxml"));
            Stage stage = new Stage();
            stage.setTitle(" DatosEmpleado ");
            stage.setScene(new Scene(root, 600, 375));
            stage.show();
            stage.setResizable(false);
            stage.getIcons().add(new Image("/vista/imagenes/vintage.png"));
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }else{
            Alert aler = new Alert(Alert.AlertType.INFORMATION, "Selecciona un elemento a modificar", ButtonType.OK);
            aler.showAndWait();
        }
    }
    
    @FXML
    public void eliminar(MouseEvent event){
        Connection con = Conexion.getInstance();
        try {
            Empleado rowSelected = table.getSelectionModel().getSelectedItem();
            String selec = table.getSelectionModel().getSelectedItem().getNumTrabajador();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Está seguro de que lo quiere eliminar?", ButtonType.YES, ButtonType.NO);
            ButtonType resul = alert.showAndWait().orElse(ButtonType.YES);
            if(ButtonType.YES.equals(resul)) {
                PreparedStatement pstm;
                pstm = con.prepareStatement("delete from empleado where numTrabajador='"+selec+"'");
                pstm.execute();
                this.table.getItems().remove(rowSelected);
                pstm.close();
                Alert aler2 = new Alert(Alert.AlertType.INFORMATION, "¡Eliminación exitosa!", ButtonType.OK);
                aler2.showAndWait();
            }
        } catch (SQLException ex) {
            Logger.getLogger(controlador.FXMLConsultarController.class.getName()).log(Level.SEVERE, null, ex);
        }catch(Exception ex){
            Alert aler = new Alert(Alert.AlertType.INFORMATION, "Selecciona un elemento a eliminar", ButtonType.OK);
            // clicking X also means no
            aler.showAndWait();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Connection con = Conexion.getInstance();
        ResultSet rs;
        try {
            rs = con.createStatement().executeQuery("select * from empleado");
            while (rs.next()){
                if(rs.getBoolean("tipo")){
                    oblist.add(new Empleado(rs.getString("numTrabajador"),rs.getString("nombre"),rs.getString("apellidos"),rs.getString("contrasena"),"Gerente"));
                }else{
                    oblist.add(new Empleado(rs.getString("numTrabajador"),rs.getString("nombre"),rs.getString("apellidos"),rs.getString("contrasena"),"Trabajador"));
                }
            }
            col_numTrabajador.setCellValueFactory(new PropertyValueFactory("numTrabajador"));
            col_nombre.setCellValueFactory(new PropertyValueFactory("nombre"));
            col_apellidos.setCellValueFactory(new PropertyValueFactory("apellidos"));
            col_tipo.setCellValueFactory(new PropertyValueFactory("tipo"));
            table.setItems(oblist);
        } catch (SQLException ex) {
            Logger.getLogger(controlador.FXMLConsultarController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                if(!con.isClosed()){
                    con.close();
                } 
            } catch (SQLException ex) {
                Logger.getLogger(CRUDEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}