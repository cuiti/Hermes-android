package hermescuitinorodriguez.laboratorio.hermescomunicador;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AlumnoActivity extends AppCompatActivity {

    private GridViewImageAdapter adapter;
    private GridView gridView;
    private int anchoColumna;
    private List<Integer> imagenes = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno);
        gridView = (GridView) this.findViewById(R.id.grid_view);

        inicializarGrilla(Constantes.CANTIDAD_COLUMNAS, Constantes.PADDING_GRILLA);
        TextView tv = (TextView) this.findViewById(R.id.alumno);
        Bundle b = getIntent().getExtras();
        tv.setText(b.getString("alumno"));

        imagenes.add(R.drawable.casco);
        imagenes.add(R.drawable.chapas);
        imagenes.add(R.drawable.pelota);
        imagenes.add(R.drawable.chapas);
        imagenes.add(R.drawable.palos);
        imagenes.add(R.drawable.pelota);
        imagenes.add(R.drawable.palos);
        imagenes.add(R.drawable.pelota);
        imagenes.add(R.drawable.pelota);

        adapter = new GridViewImageAdapter(this, imagenes,anchoColumna);
        gridView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu_activity_alumno; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_alumno, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle app bar item clicks here. The app bar
        // automatically handles clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(item.getItemId()) {
            case R.id.ajustes:
                Intent intent = new Intent(this, AjustesActivity.class);
                TextView textView = (TextView) findViewById(R.id.alumno);
                String alumno = textView.getText().toString();
                intent.putExtra("alumno", alumno);
                startActivity(intent);
                return true;
            case R.id.modo_edicion:
                // modo edición
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void inicializarGrilla(int cantidadColumnas, int paddingGrilla) {
        anchoColumna = (int) ((this.getScreenWidth() - ((cantidadColumnas + 1) * paddingGrilla)) / cantidadColumnas);
        gridView.setNumColumns(cantidadColumnas);
        gridView.setColumnWidth(anchoColumna);
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setPadding(paddingGrilla, paddingGrilla, paddingGrilla, paddingGrilla);
        gridView.setHorizontalSpacing(paddingGrilla);
        gridView.setVerticalSpacing(paddingGrilla);
    }

    public int getScreenWidth() {

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        return width;
    }

}
