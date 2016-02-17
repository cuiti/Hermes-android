package hermescuitinorodriguez.laboratorio.hermescomunicador;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
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

    ListView listaAlumnos;
    Button nuevoAlumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comunicador_grilla);
        Database db = new Database(this);
        ArrayList<Alumno> lista = db.listaAlumnos();
        listaAlumnos = (ListView) findViewById(R.id.list);
        ListAdapter adapter = new ArrayAdapter<Alumno>(this, android.R.layout.simple_list_item_1, lista);
        listaAlumnos.setAdapter(adapter);
        listaAlumnos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(ComunicadorGrillaActivity.this, AlumnoActivity.class);
                Alumno alumno = (Alumno) adapterView.getAdapter().getItem(position);
                intent.putExtra("alumno", alumno);
                intent.putExtra("modoEdicion", false);
                startActivity(intent);
            }
        });
        setTitle("Lista de Alumnos");
        nuevoAlumno = (Button) findViewById(R.id.nuevoAlumno);
        nuevoAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComunicadorGrillaActivity.this, AjustesActivity.class);
                Alumno alumno = null; // Para evitar un null point exception
                intent.putExtra("alumno", alumno);
                startActivity(intent);
                finish();
            }
        });
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_alumnos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

}
