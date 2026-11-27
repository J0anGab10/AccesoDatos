module es.ies.incidencias {
    requires javafx.controls;
    requires javafx.fxml;


    opens es.ies.incidencias to javafx.fxml;
    exports es.ies.incidencias;
}