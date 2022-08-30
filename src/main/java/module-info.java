module es.solvam.ejemplo00 {
    requires javafx.controls;
    requires javafx.fxml;


    opens es.solvam.ejemplo00 to javafx.fxml;
    exports es.solvam.ejemplo00;
    exports es.solvam.ejemplo00.controlador_vistas;
    opens es.solvam.ejemplo00.controlador_vistas to javafx.fxml;
}