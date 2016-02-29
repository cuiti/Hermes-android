package hermescuitinorodriguez.laboratorio.hermescomunicador;

import java.util.ArrayList;

public class Info {

    public ArrayList<Integer> ids;
    public ArrayList<String> nombres;

    public Info(ArrayList<Integer> ids , ArrayList<String>nombres) {
            this.ids = ids;
            this.nombres = nombres;
    }

    public ArrayList<String> getNombres() {
        return nombres;
    }

    public ArrayList<Integer> getIds() {
        return ids;
    }
}

