package es.solvam.ejemplo00.controlador_vistas;

import es.solvam.ejemplo00.modelos.Persona;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    //ATRIBUTOS----------
    @FXML private ImageView imInformacion;
    @FXML private ImageView imGuardar;
    @FXML private ImageView imSalir;
    @FXML private ImageView imAtras;
    @FXML private ImageView imDelante;
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
    private File imgFile;
    private ArrayList<Persona> personas;
    //Rellenamos el ComboBox (cbOcupacion) con una colección de Strings
    ObservableList<String> contenidoCbOcupacion = FXCollections.observableArrayList("Estudiante", "Empleado", "Autónomo", "Jubilado");
    private final String rutaCopiaSeguridad = "src/main/resources/es/solvam/ejemplo00/backup/backup.bck";
    //Mantenemos el índice del arrayList de la persona mostrada
    private int personaActual = -1;

    //MÉTODOS BOTONES----------
    public void btnGuardarClick(MouseEvent mouseEvent) {
        //Creo una persona
        Persona persona = new Persona();

        //Asigno valores
        persona.setDni(txtDni.getText());
        persona.setNombre(txtNombre.getText());
        persona.setTelefono(txtTelefono.getText());
        persona.setImagen(imgFile.getPath());
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

        //Incremento la persona actual y por tanto, muestro el botón de atrás
        personaActual++;
        imAtras.setVisible(true);
    }
    public void onExitButtonClick(MouseEvent mouseEvent) {
        //Copia de seguridad
        try {
            copiaSeguridad();
        } catch (IOException e) {
            e.printStackTrace();
            lanzaAlert("¡ERROR!","Error al escribir la copia de seguridad");
        }

        System.exit(0);
    }
    public void atrasClicked(MouseEvent mouseEvent) {
        personaActual--;
        rellenaDatos(personas.get(personaActual));
        imDelante.setVisible(true);
        if (personaActual == 0) {
            imAtras.setVisible(false);
        }
    }
    public void delanteClicked(MouseEvent mouseEvent) {
        personaActual++;
        rellenaDatos(personas.get(personaActual));
        imAtras.setVisible(true);
        if (personaActual == (personas.size() - 1)) {
            imDelante.setVisible(false);
        }
    }

    //MÉTODOS GENERALES----------
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbOcupacion.setItems(contenidoCbOcupacion);
        personas = new ArrayList<>();

        //Inicializamos los datos del fichero
        try {
            cargarDatos();
        } catch (FileNotFoundException e) {
            lanzaAlert("¡ERROR!","Error al leer el fichero");
            e.printStackTrace();
        } catch (IOException e) {
            lanzaAlert("¡ERROR!","Error al leer el fichero");
            throw new RuntimeException(e);
        }
    }
    private void copiaSeguridad() throws IOException {
        File copiaSeguridad = new File(rutaCopiaSeguridad);
        FileWriter fileWriter = new FileWriter(copiaSeguridad, false);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        for (Persona p: personas) {
            printWriter.println(p.modeloToFichero());
        }
        printWriter.close();
        fileWriter.close();
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

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
            File destino = new File("src/main/resources/es/solvam/ejemplo00/imagenes_user/" + sdf.format(new Date()) + "-" + imgFile.getName());
            Files.copy(imgFile.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
            imgFile = destino;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (imgFile != null) {
            Image imagen = new Image(imgFile.toURI().toString());
            imFoto.setImage(imagen);
        }
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
    private void cargarDatos() throws IOException {
        File copiaSeguridad = new File(rutaCopiaSeguridad);
        FileReader fileReader = new FileReader(copiaSeguridad);
        BufferedReader br = new BufferedReader(fileReader);
        String linea;
        while ((linea = br.readLine()) != null) {
            Persona p = new Persona(linea);
            personas.add(p);
        }
        br.close();
        fileReader.close();
        lanzaAlert("Datos Cargados","Se han cargado " + personas.size() + " personas");
        if (personas.size() > 0) {
            rellenaDatos(personas.get(0));
            imAtras.setVisible(false);
            personaActual = 0;
            //Si tengo un solo elemento en el arrayList no quiero poder desplazarme tampoco hacia delante
            if (personaActual == (personas.size() - 1)) {
                imDelante.setVisible(false);
            }
        }
        //Si no tengo personas en el arrayList no quiero poder desplazarme hacia delante ni hacia atrás
        if (personas.size() == 0) {
            imAtras.setVisible(false);
            imDelante.setVisible(false);
        }
    }
    private void rellenaDatos(Persona persona) {
        txtDni.setText(persona.getDni());
        txtNombre.setText(persona.getNombre());
        txtTelefono.setText(persona.getTelefono());
        File file = new File(persona.getImagen());
        imFoto.setImage(new Image(file.toURI().toString()));
        rbHombre.setSelected(persona.isSexo());
        rbMujer.setSelected(!persona.isSexo());
        dtFechaNacimiento.setValue(persona.getFechaNacimiento());
        cbOcupacion.setValue(persona.getOcupacion());
        chTecnologia.setSelected(persona.isTecnologia());
        chDisenyo.setSelected(persona.isDisenyo());
        chConsultoria.setSelected(persona.isConsultoria());
        chFormacion.setSelected(persona.isFormacion());
    }
}
