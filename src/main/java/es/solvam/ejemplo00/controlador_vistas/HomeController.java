package es.solvam.ejemplo00.controlador_vistas;

import es.solvam.ejemplo00.modelos.Persona;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML private ImageView imInformacion;
    @FXML private ImageView imGuardar;
    @FXML private ImageView imSalir;

    @FXML private TextField txtDni;
    @FXML private TextField txtNombre;
    @FXML private TextField txtTelefono;
    @FXML private RadioButton rbHombre;
    @FXML private RadioButton rbMujer;
    @FXML private DatePicker dtFechaNacimiento;
    @FXML private ComboBox<String> cbOcupacion;
    @FXML private CheckBox chTecnologia;
    @FXML private CheckBox chDisenyo;
    @FXML private CheckBox chConsultoria;
    @FXML private CheckBox chFormacion;
    @FXML private ImageView imFoto;


    public void onExitButtonClick(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void onImFotoClick(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona una imagen");

        //Agregamos filtros para los ficheros
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );

        imgFile = fileChooser.showOpenDialog(null);

        if (imgFile != null) {
            Image imagen = new Image(imgFile.toURI().toString());
            imFoto.setImage(imagen);
        }
    }

    public void btnGuardarClick(MouseEvent mouseEvent) {
        //Creo una persona
        Persona persona = new Persona();

        //Asigno valores
        persona.setDni(txtDni.getText());
        persona.setNombre(txtNombre.getText());
        persona.setTelefono(txtTelefono.getText());
        persona.setImagen(imgFile.toURI().toString());
        persona.setSexo(rbHombre.isSelected());
        persona.setFechaNacimiento(dtFechaNacimiento.getValue());
        persona.setOcupacion(cbOcupacion.getValue());
        persona.setTecnologia(chTecnologia.isSelected());
        persona.setDisenyo(chDisenyo.isSelected());
        persona.setConsultoria(chConsultoria.isSelected());
        persona.setFormacion(chFormacion.isSelected());

        //Agrego a la lista
        personas.add(persona);
        lanzaAlert("Persona Insertada", "¡Se ha insertado la persona. A continuación se limpiará el formulario!");

        //Limpio el formulario
        limpiarForm();
    }

    private void lanzaAlert(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    private void limpiarForm() {
        txtDni.clear();
        txtNombre.clear();
        txtTelefono.clear();

        File img = new File("src/main/resources/es/solvam/ejemplo00/imagenes/user.png");
        imFoto.setImage(new Image(img.toURI().toString()));

        rbHombre.setSelected(false);
        rbMujer.setSelected(false);
        dtFechaNacimiento.setValue(null);
        cbOcupacion.setValue(null);
        chTecnologia.setSelected(false);
        chDisenyo.setSelected(false);
        chConsultoria.setSelected(false);
        chFormacion.setSelected(false);
    }
    private File imgFile;
    private ArrayList<Persona> personas;

    //Rellenamos el ComboBox (cbOcupacion) con una colección de Strings
    ObservableList<String> contenidoCbOcupacion = FXCollections.observableArrayList("Estudiante", "Empleado", "Autónomo", "Jubilado");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbOcupacion.setItems(contenidoCbOcupacion);
        personas = new ArrayList<>();
    }

}
