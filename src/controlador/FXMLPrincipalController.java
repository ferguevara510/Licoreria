package controlador;

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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.Empleado;
import modelo.ReporteVenta;

public class FXMLPrincipalController implements Initializable{
    //ReporteProducto gpdf = new ReporteProducto();
    private Empleado empleado;

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
        if(this.empleado != null && this.empleado.equals("Trabajador")){
            empleadosBtn.setDisable(true);
            reporteVentasBtn.setDisable(true);
        }
    }

    /*@FXML
    public void crearReporteInven(MouseEvent event){
        gpdf.crear();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Desea abrir el reporte?", ButtonType.YES, ButtonType.NO);
        ButtonType result = alert.showAndWait().orElse(ButtonType.NO);
        if (ButtonType.YES.equals(result)) {
            gpdf.abrirPDF();
        }
    }*/
    
    @FXML
    public void irProducto(MouseEvent event) throws IOException{
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
    
    @FXML
    public void irEmpleado(MouseEvent event) throws IOException{
        Parent root; 
        root = FXMLLoader.load(getClass().getClassLoader().getResource("vista/ConsultarEmpleados.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Consultar empleados");
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
        stage.setResizable(false);
        stage.getIcons().add(new Image("/vista/imagenes/vintage.png"));
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    
    @FXML
    public void cerrarSesion(MouseEvent event) throws IOException{
        Parent root; 
        root = FXMLLoader.load(getClass().getClassLoader().getResource("vista/IniciarSesion.fxml"));
        Stage stage = new Stage();
        stage.setTitle(" IniciarSesion ");
        stage.setScene(new Scene(root, 500, 500));
        stage.show();
        stage.setResizable(false);
        stage.getIcons().add(new Image("/vista/imagenes/vintage.png"));
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    
    @FXML
    public void genReporte(MouseEvent event) throws IOException{
        ReporteVenta gpdf = new ReporteVenta();
        //System.out.println("He sido pulsado");
        gpdf.crear();
        // allow user to decide between yes and no
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Desea abrir el reporte?", ButtonType.YES, ButtonType.NO);
        // clicking X also means no
        ButtonType result = alert.showAndWait().orElse(ButtonType.NO);
        if (ButtonType.YES.equals(result)) {
            gpdf.abrirPDF();
        }
    }
    
    @FXML
    private Button reporteVentasBtn; 
    @FXML
    private Button empleadosBtn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String tipo = System.getProperties().getProperty("Empleado");
        if(tipo.equals("Trabajador")){
            empleadosBtn.setDisable(true);
            reporteVentasBtn.setDisable(true);
        }
        //Moverlos
        
    }

    @FXML
    private void irProveedor(MouseEvent mouseEvent) throws IOException {
        VistaController.cargarVista(mouseEvent,"Consultar Provedor","vista/ConsultarProveedores.fxml");
    }
}