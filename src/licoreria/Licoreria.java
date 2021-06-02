package licoreria;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.util.Objects;

public class Licoreria extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/vista/IniciarSesion.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("IniciarSesion");
        stage.show();
        stage.setResizable(false);
        stage.getIcons().add(new Image("/vista/imagenes/vintage.png"));
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}