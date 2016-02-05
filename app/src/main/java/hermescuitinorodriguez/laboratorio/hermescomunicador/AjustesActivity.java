package hermescuitinorodriguez.laboratorio.hermescomunicador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class AjustesActivity extends AppCompatActivity {

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        Intent intent = getIntent();
        String alumno = intent.getStringExtra("alumno");
        TextView textView = (TextView) findViewById(R.id.datos_alumno);
        textView.setText(alumno);

    }*/

    TextView nombreAlumno;
    TextView apellidoAlumno;
    Button guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        nombreAlumno = (TextView) findViewById(R.id.nombreAlumno);
        apellidoAlumno = (TextView) findViewById(R.id.apellidoAlumno);
        guardar =   (Button) findViewById(R.id.guardar);
        guardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                nuevoAlumno();
            }
        });
    }

    protected void nuevoAlumno(){
        String nombre = nombreAlumno.getText().toString();
        String apellido = apellidoAlumno.getText().toString();
        Database database = new Database(getApplicationContext());
        database.getWritableDatabase();
        database.nuevoAlumno(nombre, apellido, "femenino", "mediano", "establo");
    }

    /*public void onCheckboxClicked(View view) {

        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.checkbox_pista:
                if (checked)
                // agregar pestaña pista
                break;
            case R.id.checkbox_establo:
                if (checked)
                // agregar pestaña establo
                break;
            case R.id.checkbox_necesidades:
                if (checked)
                // agregar pestaña necesidades
                break;
            case R.id.checkbox_emociones:
                if (checked)
                // agregar pestaña emociones
                break;
        }
    }*/
}
