package hermescuitinorodriguez.laboratorio.hermescomunicador;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alfonso on 10/2/2016.
 * Envía una lista de NotificacionDTO al monitor
 */
public class SendNotificationTask extends AsyncTask{

    @Override
    protected Object doInBackground(Object[] params) {
        ArrayList<NotificacionDTO> lista = (ArrayList<NotificacionDTO>)params[0];

        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
        String listaNotiJson = gson.toJson(lista);

        System.out.println(listaNotiJson);

        String url = "http://192.168.43.70:"+"8000"+"/hermes";       //192.168.0.27 ToDo direccion y puerto tienen que venir de config

        OutputStream os = null;
        HttpURLConnection conn=null;
        try {
            URL urlObject = new URL(url);
            conn = (HttpURLConnection) urlObject.openConnection();
            conn.setReadTimeout(100000);
            conn.setDoOutput(true);
            os = conn.getOutputStream();
        } catch (Exception e1) {
            System.out.println("Error de IO al conectarse a la URL");
            e1.printStackTrace();
        }

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));

        try {
            writer.write(listaNotiJson);
            writer.close();
            os.close();
            int code = conn.getResponseCode();
            System.out.println("code:"+code);
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }
            in.close();
            System.out.println(conn.getResponseMessage());

        } catch (IOException e) {
            System.out.println("Error de al enviar datos ");
            e.printStackTrace();
        }



        return null;
    }
}
