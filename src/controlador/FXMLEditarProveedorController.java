package controlador;

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

public class FXMLEditarProveedorController implements Initializable {
    @FXML
    private Button cancelarBtn;
    @FXML
    private TextField nombreProveedorTf;
    @FXML
    private TextField empresaProveedorTf;
    @FXML
    private TextField telefonoProveedorTf;
    @FXML
    private Button aceptarBtn;
    @FXML
    private TextArea direccionProveedorTf;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Proveedor proveedor = FXMLConsultarProveedorController.proveedorSeleccionado;

        nombreProveedorTf.setText(proveedor.getNombre());
        empresaProveedorTf.setText(proveedor.getEmpresa());
        telefonoProveedorTf.setText(proveedor.getNumTelefono());
        direccionProveedorTf.setText(proveedor.getDireccion());
    }

    @FXML
    private void editar(MouseEvent mouseEvent) {
        if(this.validarCampos()){
            MensajeController.mensajeAdvertencia("Hay campos vacios");
        }else {
            Proveedor proveedor = FXMLConsultarProveedorController.proveedorSeleccionado;

            proveedor.setDireccion(direccionProveedorTf.getText().trim());
            proveedor.setEmpresa(empresaProveedorTf.getText().trim());
            proveedor.setNombre(nombreProveedorTf.getText().trim());
            proveedor.setNumTelefono(telefonoProveedorTf.getText().trim());

            if (proveedor.editarProvedor()){
                MensajeController.mensajeInformacion("Los datos fueron guardados");
            }else{
                MensajeController.mensajeAdvertencia("No se pudieron guardar los cambios");
            }
        }
    }

    @FXML
    private void regresar(MouseEvent mouseEvent) throws IOException {
        VistaController.cargarVista(mouseEvent,"Principal","vista/ConsultarProveedores.fxml");
    }

    private boolean validarCampos(){
        return nombreProveedorTf.getText().trim().isEmpty() || empresaProveedorTf.getText().trim().isEmpty() || telefonoProveedorTf.getText().trim().isEmpty() || direccionProveedorTf.getText().trim().isEmpty();
    }
}
