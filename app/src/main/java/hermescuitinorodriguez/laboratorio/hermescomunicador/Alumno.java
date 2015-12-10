package hermescuitinorodriguez.laboratorio.hermescomunicador;
/**
 * Created by Diego on 01/12/15.
 */
public class Alumno {
    private String nombre;
    private String apellido;

    public Alumno(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @Override
    public String toString() {
        return this.getApellido()+", "+this.getNombre();
    }
}
