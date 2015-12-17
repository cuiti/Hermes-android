package hermescuitinorodriguez.laboratorio.hermescomunicador;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by mbrown on 09/12/15.
 */
public class Datos {
    public static Hashtable<Integer, ArrayList<Integer>> img = new Hashtable<Integer, ArrayList<Integer>>();
    static {
        ArrayList<Integer> pista = new ArrayList<>();
        pista.add(R.drawable.casco);
        pista.add(R.drawable.chapas);
        pista.add(R.drawable.letras);
        pista.add(R.drawable.cubos);
        pista.add(R.drawable.maracas);
        pista.add(R.drawable.palos);
        pista.add(R.drawable.pato);
        pista.add(R.drawable.pelota);
        pista.add(R.drawable.riendas);
        pista.add(R.drawable.burbujas);
        pista.add(R.drawable.broches);
        pista.add(R.drawable.aro);
        pista.add(R.drawable.tarima);
        img.put(1, pista);

        ArrayList<Integer> establo = new ArrayList<Integer>();
        establo.add(R.drawable.cepillo);
        establo.add(R.drawable.limpieza);
        establo.add(R.drawable.escarbavasos);
        establo.add(R.drawable.montura);
        establo.add(R.drawable.matra);
        establo.add(R.drawable.raquetadura);
        establo.add(R.drawable.raquetablanda);
        establo.add(R.drawable.pasto);
        establo.add(R.drawable.zanahoria);
        establo.add(R.drawable.caballo1);
        establo.add(R.drawable.caballo2);
        establo.add(R.drawable.caballo3);
        img.put(2, establo);

        ArrayList<Integer> necesidades = new ArrayList<Integer>();
        necesidades.add(R.drawable.bano);
        necesidades.add(R.drawable.sedf);
        necesidades.add(R.drawable.sedm);
        img.put(3, necesidades);

        ArrayList<Integer> emociones = new ArrayList<Integer>();
        emociones.add(R.drawable.dolorida);
        emociones.add(R.drawable.dolorido);
        emociones.add(R.drawable.cansada);
        emociones.add(R.drawable.cansado);
        emociones.add(R.drawable.sorprendida);
        emociones.add(R.drawable.sorprendido);
        emociones.add(R.drawable.asustada);
        emociones.add(R.drawable.asustado);
        emociones.add(R.drawable.contento);
        emociones.add(R.drawable.contents);
        emociones.add(R.drawable.enojada);
        emociones.add(R.drawable.enojado);
        img.put(4, emociones);

        ArrayList<Integer> alumno = new ArrayList<Integer>();
        img.put(5, alumno);
    }
}
