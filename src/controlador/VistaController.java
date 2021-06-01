package controlador;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class VistaController {

    public static void cargarVista(MouseEvent event, String titulo, String fxml) throws IOException {
        Parent root;
        root = FXMLLoader.load(Objects.requireNonNull(VistaController.class.getClassLoader().getResource(fxml)));
        Stage stage = new Stage();
        stage.setTitle(titulo);
        stage.setScene(new Scene(root));
        stage.show();
        stage.setResizable(false);
        stage.getIcons().add(new Image("/vista/imagenes/vintage.png"));
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}
