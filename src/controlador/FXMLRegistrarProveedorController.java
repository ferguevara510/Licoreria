package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import modelo.Proveedor;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLRegistrarProveedorController implements Initializable {
    @FXML
    private TextArea direccionProveedorTf;
    @FXML
    private TextField nombreProveedorTf;
    @FXML
    private Button cancelarBtn;
    @FXML
    private Button aceptarBtn;
    @FXML
    private TextField empresaProveedorTf;
    @FXML
    private TextField telefonoProveedorTf;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void guardarProveedor(ActionEvent actionEvent) {
        if (this.validarCamposVacios()){
            MensajeController.mensajeAdvertencia("Hay campos vacios");
        }else{
            Proveedor proveedor = new Proveedor();
            proveedor.setNombre(nombreProveedorTf.getText().trim());
            proveedor.setDireccion(direccionProveedorTf.getText().trim());
            proveedor.setEmpresa(empresaProveedorTf.getText().trim());
            proveedor.setNumTelefono(telefonoProveedorTf.getText().trim());

            if (proveedor.registrarProvedor()) {
                MensajeController.mensajeInformacion("Se registro el proveedor");
                this.limpiarCampos();
            }else {
                MensajeController.mensajeAdvertencia("No se pudo registrar el proveedor");
            }
        }
    }

    private void limpiarCampos(){
        nombreProveedorTf.setText("");
        direccionProveedorTf.setText("");
        empresaProveedorTf.setText("");
        telefonoProveedorTf.setText("");
    }

    private boolean validarCamposVacios(){
        return direccionProveedorTf.getText().trim().isEmpty() || nombreProveedorTf.getText().trim().isEmpty() || empresaProveedorTf.getText().trim().isEmpty() || telefonoProveedorTf.getText().trim().isEmpty();
    }

    @FXML
    private void regresar(MouseEvent mouseEvent) throws IOException {
        VistaController.cargarVista(mouseEvent,"Editar Provedor","vista/ConsultarProveedores.fxml");
    }
}
