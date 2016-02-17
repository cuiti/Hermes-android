package hermescuitinorodriguez.laboratorio.hermescomunicador;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Spinner;
import android.content.DialogInterface;
import android.widget.Toast;

public class AjustesActivity extends AppCompatActivity {

    TextView nombreAlumno;
    TextView apellidoAlumno;
    TextView sexoAlumno;
    TextView direccionIP;
    TextView puerto;
    Database db;
    Settings configuracion;
    Alumno alumno;
    String pestañas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        Spinner spinner = (Spinner) findViewById(R.id.sexo_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sexo_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
//        spinner.setAdapter(adapter);

        db = new Database(this);
        configuracion = db.getConfiguracion();

        nombreAlumno = (TextView) findViewById(R.id.nombreAlumno);
        apellidoAlumno = (TextView) findViewById(R.id.apellidoAlumno);
        sexoAlumno = (TextView) findViewById(R.id.sexoAlumno);
        direccionIP = (TextView) findViewById(R.id.direccionIP);
        puerto = (TextView) findViewById(R.id.puerto);

        alumno = (Alumno)getIntent().getExtras().getSerializable("alumno");
        if (alumno != null) {
            nombreAlumno.setText(alumno.getNombre());
            apellidoAlumno.setText(alumno.getApellido());
            sexoAlumno.setText(alumno.getSexo());
            pestañas = alumno.getPestañas();
        }else{
            Button botonEliminar = (Button) findViewById(R.id.eliminarAlumno);
            botonEliminar.setEnabled(false);
        }


        if (configuracion != null) {
            direccionIP.setText(configuracion.getDireccionIP());
            puerto.setText(configuracion.getPuerto().toString());
        }
    }

    public void guardar(View view){
        if (alumno != null){
            modificarAlumno();
        }else{
            nuevoAlumno();
        }
        String ip = direccionIP.getText().toString();
        Integer puertoC = Integer.parseInt(puerto.getText().toString());
        if (configuracion == null && (ip != null && ip != "" && puertoC != null)){
            db.agregarConfiguracion(ip, puertoC);
        }else{
            if((configuracion.getDireccionIP() != ip && ip != null && ip != "") || (configuracion.getPuerto() != puertoC && puertoC != null)){
                db.modificarConfiguracion(ip, puertoC);
            }
        }
        if (alumno != null){
            Intent intent = new Intent(this, AlumnoActivity.class);
            intent.putExtra("alumno", alumno);
            startActivity(intent);
            finish();
        }else {
            Intent intent = new Intent(this, ComunicadorGrillaActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void modificarAlumno(){
        String nombre = nombreAlumno.getText().toString();
        String apellido = apellidoAlumno.getText().toString();
        String sexo = sexoAlumno.getText().toString();
        String tamañoPictograma = "";
        alumno = db.modificarAlumno(alumno.getId(), nombre, apellido, sexo, tamañoPictograma, pestañas);
        Toast.makeText(AjustesActivity.this, R.string.alumno_guardar_confirmacion, Toast.LENGTH_SHORT).show();
    }

    public void nuevoAlumno() {
        String nombre = nombreAlumno.getText().toString();
        String apellido = apellidoAlumno.getText().toString();
        String sexo = sexoAlumno.getText().toString();
        String tamañoPictograma = "";

        if ((nombre != null && nombre != "") || (apellido != null && apellido != "") || (sexo != null && sexo != "")) {
            Database database = new Database(getApplicationContext());
            database.getWritableDatabase();
            database.nuevoAlumno(nombre, apellido, sexo, tamañoPictograma, pestañas);
            Toast.makeText(AjustesActivity.this, R.string.alumno_guardar_confirmacion, Toast.LENGTH_SHORT).show();

        }

    }

    public void borrar(View view)
    {
        /**
         * Borramos el registro con confirmación
         */
        AlertDialog.Builder dialogEliminar = new AlertDialog.Builder(this);

        dialogEliminar.setIcon(android.R.drawable.ic_dialog_alert);
        dialogEliminar.setTitle(getResources().getString(R.string.alumno_eliminar_titulo));
        dialogEliminar.setMessage(getResources().getString(R.string.alumno_eliminar_mensaje));
        dialogEliminar.setCancelable(false);

        dialogEliminar.setPositiveButton(getResources().getString(android.R.string.ok), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int boton) {
                //dbAdapter.delete(id);
                Database database = new Database(getApplicationContext());
                database.getWritableDatabase();
                database.borrarAlumno(alumno.getId());
                Toast.makeText(AjustesActivity.this, R.string.alumno_eliminar_confirmacion, Toast.LENGTH_SHORT).show();
                /**
                 * Devolvemos el control
                 */
                setResult(RESULT_OK);
                Intent intent = new Intent(AjustesActivity.this, ComunicadorGrillaActivity.class);
                startActivity(intent);
                finish();
            }
        });

        dialogEliminar.setNegativeButton(android.R.string.no, null);

        dialogEliminar.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_alumno, menu);
        return true;
    }

   @Override
    public void onBackPressed(){
       if (alumno != null){
           Intent intent = new Intent(this, AlumnoActivity.class);
           intent.putExtra("alumno", alumno);
           startActivity(intent);
           finish();
       }else {
           Intent intent = new Intent(this, ComunicadorGrillaActivity.class);
           startActivity(intent);
           finish();
       }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == android.R.id.home){
            this.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onCheckboxClicked(View view) {

        boolean checked = ((CheckBox) view).isChecked();

        String solapas = "";

        switch(view.getId()) {
            case R.id.checkbox_pista:
                if (checked)
                    solapas += "pista,";
                break;
            case R.id.checkbox_establo:
                if (checked)
                    solapas += "establo,";
                break;
            case R.id.checkbox_necesidades:
                if (checked)
                    solapas += "necesidades,";
                break;
            case R.id.checkbox_emociones:
                if (checked)
                    solapas += "emociones,";
                break;
        }

        pestañas = solapas;

    }
}
