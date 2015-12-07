package hermescuitinorodriguez.laboratorio.hermescomunicador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.*;

public class MainActivity extends AppCompatActivity {

    List<Alumno> alumnos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarListaAumnos();

        ListView listView = (ListView) findViewById(R.id.listaAlumnos);
        ListAdapter adapter = new ArrayAdapter<Alumno>(this, android.R.layout.simple_list_item_1, alumnos);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, AlumnoActivity.class);
                intent.putExtra("alumno", adapterView.getAdapter().getItem(position).toString());
                startActivity(intent);
            }
        });
    }

    public void inicializarListaAumnos(){
        alumnos.add(new Alumno(1,"Juan","Fernández"));
        alumnos.add(new Alumno(2,"Alexander","Rodríguz"));
        alumnos.add(new Alumno(3,"Verena","Olsowy"));
        alumnos.add(new Alumno(1,"Edgar","Tordó"));
        alumnos.add(new Alumno(2,"Mariano","Ber"));
        alumnos.add(new Alumno(3,"Lucia","Moyano"));
    }
}
