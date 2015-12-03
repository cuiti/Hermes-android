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

        ListView lv = (ListView) findViewById(R.id.listaAlumnos);
        ListAdapter adapter = new ArrayAdapter<Alumno>(this, android.R.layout.simple_list_item_1, alumnos);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(MainActivity.this, GrillaAlumnoActivity.class);
                intent.putExtra("alumno", adapterView.getAdapter().getItem(position).toString());
                startActivity(intent);
            }
        });
    }

    public void inicializarListaAumnos(){
        alumnos.add(new Alumno(1,"Juan","Fernández"));
        alumnos.add(new Alumno(2,"Aléxándér","Ródriguéz"));
        alumnos.add(new Alumno(3,"Verena","Olsowy"));
    }
}
