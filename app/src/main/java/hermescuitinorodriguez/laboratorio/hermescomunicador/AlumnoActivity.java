package hermescuitinorodriguez.laboratorio.hermescomunicador;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
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
        gridView = (GridView) this.findViewById(R.id.gridView);

        inicializarGrilla(Constantes.CANTIDAD_COLUMNAS, Constantes.PADDING_GRILLA);
        TextView tv = (TextView) this.findViewById(R.id.textView2);
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
    private void inicializarGrilla(int cantidadColumnas, int paddingGrilla) {
        anchoColumna = (int) ((this.getScreenWidth() - ((cantidadColumnas + 1) * paddingGrilla)) /cantidadColumnas);
        gridView.setNumColumns(cantidadColumnas);
        gridView.setColumnWidth(anchoColumna);
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setPadding( paddingGrilla,  paddingGrilla,  paddingGrilla, paddingGrilla);
        gridView.setHorizontalSpacing(paddingGrilla);
        gridView.setVerticalSpacing(paddingGrilla);
    }

    public int getScreenWidth() {

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;

        return width;
    }

}