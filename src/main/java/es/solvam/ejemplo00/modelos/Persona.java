package es.solvam.ejemplo00.modelos;

import java.time.LocalDate;

public class Persona {
    //ATRIBUTOS----------
    private String dni;
    private String nombre;
    private String telefono;
    private String imagen;
    private boolean sexo;
    private LocalDate fechaNacimiento;
    private String ocupacion;
    private boolean tecnologia;
    private boolean disenyo;
    private boolean consultoria;
    private boolean formacion;

    //CONSTRUCTORES---------
    public Persona() {
    }

    public Persona(String linea) {
        String[] elementos = linea.split(";");
        this.dni = elementos[0];
        this.nombre = elementos[1];
        this.telefono = elementos[2];
        this.imagen = elementos[3];
        this.sexo = elementos[4].equals("true");
        this.fechaNacimiento = LocalDate.parse(elementos[5]);
        this.ocupacion = elementos[6];
        this.tecnologia = elementos[7].equals("true");
        this.disenyo = elementos[8].equals("true");
        this.consultoria = elementos[9].equals("true");
        this.formacion = elementos[10].equals("true");
    }

    //MÉTODOS----------
    public String modeloToFichero() {
        return dni + ";" + nombre + ";" + telefono + ";" + imagen + ";" + sexo + ";" + fechaNacimiento.toString() + ";" + ocupacion + ";" + tecnologia + ";" + disenyo + ";" + consultoria + ";" + formacion;
    }

    //GETTERS&SETTERS----------
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public boolean isSexo() {
        return sexo;
    }

    public void setSexo(boolean sexo) {
        this.sexo = sexo;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public boolean isTecnologia() {
        return tecnologia;
    }

    public void setTecnologia(boolean tecnologia) {
        this.tecnologia = tecnologia;
    }

    public boolean isDisenyo() {
        return disenyo;
    }

    public void setDisenyo(boolean disenyo) {
        this.disenyo = disenyo;
    }

    public boolean isConsultoria() {
        return consultoria;
    }

    public void setConsultoria(boolean consultoria) {
        this.consultoria = consultoria;
    }

    public boolean isFormacion() {
        return formacion;
    }

    public void setFormacion(boolean formacion) {
        this.formacion = formacion;
    }
}
