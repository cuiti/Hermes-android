package hermescuitinorodriguez.laboratorio.hermescomunicador;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Alumno implements Serializable {
    private int id;
    private String nombre;
    private String apellido;
    private String sexo;
    private String tamañoPictogramas;
    private String pestañas;

    public Alumno(int id, String nombre, String apellido, String sexo, String tamañoPictogramas, String pestañas){
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo = sexo;
        this.tamañoPictogramas = tamañoPictogramas;
        this.pestañas = pestañas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getApellido() {
        return apellido;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getPestañas() {
        return pestañas;
    }

    public void setPestañas(String pestañas) {
        this.pestañas = pestañas;
    }

    public String getTamañoPictogramas() {
        return tamañoPictogramas;
    }

    public void setTamañoPictogramas(String tamañoPictogramas) {
        this.tamañoPictogramas = tamañoPictogramas;
    }

    @Override
    public String toString() {
        return nombre+ " " +apellido;
    }
}

