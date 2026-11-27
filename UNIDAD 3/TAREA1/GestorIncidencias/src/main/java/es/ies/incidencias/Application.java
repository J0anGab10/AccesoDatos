package es.ies.incidencias;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    // Carga el archivo FXML y muestra la ventana principal
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("vista_incidencias.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 550);
        stage.setTitle("Gestor de incidencias ");
        stage.setScene(scene);
        stage.show();
    }

    // Metodo principal que arranca la aplicacion
    public static void main(String[] args) {
        launch();
    }
}