package es.ies.incidencias;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalTime;

public class IncidenciasController {

    // Atributos inyectados desde el archivo FXML
    @FXML
    private ToggleGroup grupoUrgencia;

    @FXML
    private Label lblContador;

    @FXML
    private TextField txtEquipo;

    @FXML
    private ComboBox<String> cmbCategoria;

    @FXML
    private RadioButton rbBaja;

    @FXML
    private RadioButton rbMedia;

    @FXML
    private RadioButton rbAlta;

    @FXML
    private TextArea txtDescripcion;

    @FXML
    private VBox vboxIncidencias;

    // Metodo inicial que deja marcada la primera categoria y la urgencia baja
    @FXML
    public void initialize() {
        cmbCategoria.getSelectionModel().selectFirst();
        rbBaja.setSelected(true);
    }

    // Valida los campos y crea una tarjeta nueva en la lista inferior
    @FXML
    private void registrarIncidencia() {
        String equipo = txtEquipo.getText().trim();
        String descripcion = txtDescripcion.getText().trim();

        // Comprueba que el equipo y la descripcion no esten vacios
        if (equipo.isEmpty() || descripcion.isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Aviso");
            alerta.setHeaderText("Campos vacíos");
            alerta.setContentText("Rellena el equipo y la descripción antes de registrar.");
            alerta.showAndWait();
            return;
        }

        // Recoge los datos seleccionados y la hora actual
        String categoria = cmbCategoria.getValue();
        RadioButton seleccionado = (RadioButton) grupoUrgencia.getSelectedToggle();
        String urgencia = seleccionado.getText();
        String hora = LocalTime.now().toString().substring(0, 5);

        // Crea el contenedor horizontal de la tarjeta
        HBox tarjeta = new HBox(15.0);
        tarjeta.setPadding(new Insets(10.0));

        // Cambia el color de fondo y borde segun la urgencia elegida
        if (urgencia.equals("Alta")) {
            tarjeta.setStyle("-fx-background-color: #f8d7da; -fx-border-color: #dc3545;");
        } else if (urgencia.equals("Media")) {
            tarjeta.setStyle("-fx-background-color: #fff3cd; -fx-border-color: #ffc107;");
        } else {
            tarjeta.setStyle("-fx-background-color: #d4edda; -fx-border-color: #28a745;");
        }

        // Crea la etiqueta con el texto de la incidencia
        Label lblDatos = new Label("[" + hora + "] " + equipo + " | " + categoria + " | Urgencia: " + urgencia + "\n" + descripcion);
        lblDatos.setPrefWidth(620.0);
        lblDatos.setWrapText(true);

        // Crea el boton para borrar esta tarjeta al pulsar sobre el
        Button btnResolver = new Button("Resolver");
        btnResolver.setOnAction(e -> {
            vboxIncidencias.getChildren().remove(tarjeta);
            actualizarContador();
        });

        // Añade los elementos a la tarjeta y la inserta en la lista
        tarjeta.getChildren().addAll(lblDatos, btnResolver);
        vboxIncidencias.getChildren().add(tarjeta);

        actualizarContador();
        limpiarFormulario();
    }

    // Borra los textos y vuelve a poner los valores iniciales
    @FXML
    private void limpiarFormulario() {
        txtEquipo.clear();
        txtDescripcion.clear();
        cmbCategoria.getSelectionModel().selectFirst();
        rbBaja.setSelected(true);
    }

    // Pide confirmacion y vacia toda la lista de incidencias
    @FXML
    private void resolverTodas() {
        if (vboxIncidencias.getChildren().isEmpty()) {
            return;
        }

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmar");
        alerta.setHeaderText("Resolver todas las incidencias");
        alerta.setContentText("¿Seguro que quieres eliminar todas las incidencias abiertas?");

        if (alerta.showAndWait().get() == ButtonType.OK) {
            vboxIncidencias.getChildren().clear();
            actualizarContador();
        }
    }

    // Actualiza el texto del contador segun las tarjetas que haya abiertas
    private void actualizarContador() {
        lblContador.setText("Incidencias abiertas: " + vboxIncidencias.getChildren().size());
    }
}