package hermescuitinorodriguez.laboratorio.hermescomunicador;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GrillaAlumnoActivity extends AppCompatActivity {

    TextView nombreAlumno;
    ArrayList<ImageView> imagenes = new ArrayList<ImageView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grilla_alumno);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nombreAlumno = (TextView) findViewById(R.id.nombreAlumno);
        Bundle parametros =  getIntent().getExtras();
        nombreAlumno.setText(parametros.getString("alumno"));

        cargarImagenes();

    }

    public void cargarImagenes(){
        imagenes.add(new ImageView(R.drawable.aro));
        imagenes.add(new ImageView(R.drawable.asustada));
        imagenes.add(new ImageView(R.drawable.asustado));
        imagenes.add(new ImageView(R.drawable.bano));
        imagenes.add(new ImageView(R.drawable.broches));
        imagenes.add(new ImageView(R.drawable.caballo1));
    }
}
