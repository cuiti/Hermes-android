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
    CheckBox pista;
    CheckBox establo;
    CheckBox necesidades;
    CheckBox emociones;


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

        direccionIP = (TextView) findViewById(R.id.direccionIP);
        puerto = (TextView) findViewById(R.id.puerto);
        if (configuracion != null) {
            direccionIP.setText(configuracion.getDireccionIP());
            puerto.setText(configuracion.getPuerto().toString());
        }

        nombreAlumno = (TextView) findViewById(R.id.nombreAlumno);
        apellidoAlumno = (TextView) findViewById(R.id.apellidoAlumno);
        sexoAlumno = (TextView) findViewById(R.id.sexoAlumno);
        alumno = (Alumno)getIntent().getExtras().getSerializable("alumno");

        pista = (CheckBox) findViewById(R.id.checkbox_pista);
        establo = (CheckBox) findViewById(R.id.checkbox_establo);
        necesidades = (CheckBox) findViewById(R.id.checkbox_necesidades);
        emociones = (CheckBox) findViewById(R.id.checkbox_emociones);

        if (alumno != null) {
            nombreAlumno.setText(alumno.getNombre());
            apellidoAlumno.setText(alumno.getApellido());
            sexoAlumno.setText(alumno.getSexo());
            pestañas = alumno.getPestañas();
            String[] solapas = alumno.getPestañas().split(","); //los nombres de las solapas están separadas por comas
            for(String solapa: solapas) {
                switch (solapa) {
                    case "pista":
                        pista.setChecked(true);
                        break;
                    case "establo":
                        establo.setChecked(true);
                        break;
                    case "necesidades":
                        necesidades.setChecked(true);
                        break;
                    case "emociones":
                        emociones.setChecked(true);
                        break;
                }
            }
        }else{
            Button botonEliminar = (Button) findViewById(R.id.eliminarAlumno);
            botonEliminar.setEnabled(false);
        }
    }

    public void guardarAlumno(){
        setearSolapas();
        Boolean guardado;
        if (alumno != null){
            guardado = modificarAlumno();
        }else{
            guardado = nuevoAlumno();
        }
        String ip = direccionIP.getText().toString();
        Integer puertoC = puerto.getText() != null ? Integer.parseInt(puerto.getText().toString()): null;

        if (configuracion == null && (ip != null && ip.length() != 0 && puertoC != null)){
            db.agregarConfiguracion(ip, puertoC);
        }else{
            if (configuracion != null && ((configuracion.getDireccionIP() != ip && ip != null && ip.length() != 0) || (configuracion.getPuerto() != puertoC && puertoC != null))){
                db.modificarConfiguracion(ip, puertoC);
            }
        }
        if(guardado) {
            if (alumno != null) {
                Intent intent = new Intent(this, AlumnoActivity.class);
                intent.putExtra("alumno", alumno);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(this, ComunicadorGrillaActivity.class);
                startActivity(intent);
                finish();
            }
        }else{
            AlertDialog.Builder dialogGuardar = new AlertDialog.Builder(this);

            dialogGuardar.setIcon(android.R.drawable.ic_dialog_alert);
            dialogGuardar.setTitle(getResources().getString(R.string.alumno_guardar_titulo));
            dialogGuardar.setMessage(getResources().getString(R.string.alumno_guardar_mensaje));
            dialogGuardar.setCancelable(false);

            dialogGuardar.setPositiveButton(getResources().getString(R.string.salir), new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int boton) {
                    Intent intent;
                    if (alumno == null){
                        intent = new Intent(AjustesActivity.this, ComunicadorGrillaActivity.class);
                    }else{
                        intent = new Intent(AjustesActivity.this, AlumnoActivity.class);
                        intent.putExtra("modoEdicion", false);
                        intent.putExtra("alumno", alumno);
                    }
                    startActivity(intent);
                    finish();
                }
            });

            dialogGuardar.setNegativeButton(R.string.seguir, null);
            dialogGuardar.show();
        }
    }

    private boolean modificarAlumno(){
        String nombre = nombreAlumno.getText().toString();
        String apellido = apellidoAlumno.getText().toString();
        String tamañoPictograma = "";
        if ((nombre != null && nombre.length() != 0) && (apellido != null && apellido.length() != 0)) {
            alumno = db.modificarAlumno(alumno.getId(), nombre, apellido, "Femenino", tamañoPictograma, pestañas);
            Toast.makeText(AjustesActivity.this, R.string.alumno_guardar_confirmacion, Toast.LENGTH_SHORT).show();
            return true;
        }else{
            return false;
        }
    }

    public boolean nuevoAlumno() {
        String nombre = nombreAlumno.getText().toString();
        String apellido = apellidoAlumno.getText().toString();
        String sexo = sexoAlumno.getText().toString();
        String tamañoPictograma = "";

        if ((nombre != null && nombre.length() != 0) && (apellido != null && apellido.length() != 0)) {
            Database database = new Database(getApplicationContext());
            database.getWritableDatabase();
            database.nuevoAlumno(nombre, apellido, sexo, tamañoPictograma, pestañas);
            Toast.makeText(AjustesActivity.this, R.string.alumno_crear_confirmacion, Toast.LENGTH_SHORT).show();
            return true;
        }else{
            return false;
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
        guardarAlumno();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ajustes) {
            return true;
        }
        if (id == android.R.id.home){
            this.guardarAlumno();
        }

        return super.onOptionsItemSelected(item);
    }


    public void setearSolapas() {
        String solapas = "";

        if (pista.isChecked()) {
            solapas += "pista,";
        }
        if (establo.isChecked()) {
            solapas += "establo,";
        }
        if (necesidades.isChecked()) {
            solapas += "necesidades,";
        }
        if (emociones.isChecked()) {
            solapas += "emociones,";
        }

        if (solapas.length() > 1) {
            solapas = solapas.substring(0, solapas.length() - 1);
        }
        pestañas = solapas;
    }
}
