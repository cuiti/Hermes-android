package hermescuitinorodriguez.laboratorio.hermescomunicador;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComunicadorGrillaActivity extends AppCompatActivity {

    List<Map<String, Alumno>> alumnos = new ArrayList<Map<String,Alumno>>();
    List<Alumno> alumnosList = new ArrayList<Alumno>();
    Button nuevoAlumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         inicializarListaMaps();
         inicializarLista();
         setContentView(R.layout.activity_comunicador_grilla);
         ListView listView = (ListView) this.findViewById(R.id.list);
         // ListAdapter adapter= new SimpleAdapter(this,alumnos,android.R.layout.simple_list_item_1, new String[] {"alumno"}, new int[] {android.R.id.text1});
         ListAdapter adapter = new ArrayAdapter<Alumno>(this, android.R.layout.simple_list_item_1, alumnosList);

         listView.setAdapter(adapter);
         listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                 // Toast.makeText(ComunicadorGrillaActivity.this, adapterView.getAdapter().getItem(i).toString(), Toast.LENGTH_SHORT);
                 System.out.println(adapterView.getAdapter().getItem(position).toString());
                 Intent intent = new Intent(ComunicadorGrillaActivity.this, AlumnoActivity.class);

                 Alumno alum = (Alumno) adapterView.getAdapter().getItem(position);
//                 intent.putExtra("alumno", adapterView.getAdapter().getItem(position).toString());
                 String nombre = alum.getNombre();
                 String apellido = alum.getApellido();
                 intent.putExtra("nombre", nombre);
                 intent.putExtra("apellido", apellido);
                 startActivity(intent);
             }
         });

        nuevoAlumno = (Button) findViewById(R.id.nuevoAlumno);
        nuevoAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComunicadorGrillaActivity.this, AjustesActivity.class);
                startActivity(intent);
            }
        });
    }

    private void inicializarListaMaps() {
        alumnos.add(crearAlumno("alumno", new Alumno("Carlos", "Ferro")));
        alumnos.add(crearAlumno("alumno", new Alumno("Guillermo", "Coppola")));
        alumnos.add(crearAlumno("alumno", new Alumno("Diego", "Maradona")));
    }

    private void inicializarLista() {
        alumnosList.add(new Alumno("Carlos", "Ferro Viera"));
        alumnosList.add(new Alumno("Guillote", "Coppola"));
        alumnosList.add(new Alumno("Diego", "Maradona"));
    }

    private HashMap<String, Alumno> crearAlumno(String key, Alumno alumno) {
        HashMap<String, Alumno>  alumnoMap = new HashMap<String, Alumno>();
        alumnoMap.put(key, alumno);
        return alumnoMap;
    }

}
