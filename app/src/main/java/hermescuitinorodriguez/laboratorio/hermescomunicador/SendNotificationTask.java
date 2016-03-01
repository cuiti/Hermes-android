package hermescuitinorodriguez.laboratorio.hermescomunicador;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

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
        ArrayList<NotificacionDTO> lista = (ArrayList<NotificacionDTO>) params[0];
        final Context contexto = (Context) params[1];
        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
        String listaNotiJson = gson.toJson(lista);

        Database db = new Database(contexto);
        Settings settings = db.getConfiguracion();
        if ((settings!=null)) {
            String url = "http://" + settings.getDireccionIP() + ":" + settings.getPuerto() + "/hermes";

            //-----Para chequear si hay conexion a internet
            ConnectivityManager cm = (ConnectivityManager) contexto.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo infoNet = cm.getActiveNetworkInfo();

            if (infoNet != null && infoNet.isConnectedOrConnecting()) {

                OutputStream os = null;
                HttpURLConnection conn = null;
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
                    System.out.print("code: " + code+" ");
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        System.out.print(inputLine+" ");
                    }
                    in.close();
                    System.out.println(conn.getResponseMessage());

                } catch (IOException e) {
                    System.out.println("Error de al enviar datos ");
                    e.printStackTrace();
                }
            } else {
                System.out.println("else del enviar notificacion, no hay red");
                //no hay red, acá avisa con un toast
                Handler handler = new Handler(contexto.getMainLooper());
                handler.post(new Runnable() {
                    public void run() {
                        Toast.makeText(contexto, R.string.alerta_enviar_notificacion, Toast.LENGTH_LONG).show();
                    }
                });
            }
        }else{
            System.out.println("else del getDireccionIP, no hay valores seteados");
            //no hay red, acá avisa con un toast
            Handler handler = new Handler(contexto.getMainLooper());
            handler.post(new Runnable() {
                public void run() {
                    Toast.makeText(contexto, R.string.alerta_conectar_monitor, Toast.LENGTH_LONG).show();
                }
            });
        }
        return null;

    }
}
