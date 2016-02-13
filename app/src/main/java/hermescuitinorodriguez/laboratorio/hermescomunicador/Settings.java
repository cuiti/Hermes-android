package hermescuitinorodriguez.laboratorio.hermescomunicador;

public class Settings {
    private String direccionIP;
    private Integer puerto;

    public Settings(String ip, Integer puerto){
        this.direccionIP = ip;
        this.puerto = puerto;
    }

    public String getDireccionIP() {
        return direccionIP;
    }

    public void setDireccionIP(String direccionIP) {
        this.direccionIP = direccionIP;
    }

    public Integer getPuerto() {
        return puerto;
    }

    public void setPuerto(Integer puerto) {
        this.puerto = puerto;
    }

}
