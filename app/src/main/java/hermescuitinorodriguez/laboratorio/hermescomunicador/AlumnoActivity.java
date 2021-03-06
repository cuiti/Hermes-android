package hermescuitinorodriguez.laboratorio.hermescomunicador;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class AlumnoActivity extends AppCompatActivity {

    private GridViewImageAdapter adapter;
    private GridView gridView;
    private int anchoColumna;
    private List<Integer> imagenes = new ArrayList<Integer>();
    private Alumno alumno;
    private boolean modoEdicion;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contenta.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno);
        alumno = (Alumno)getIntent().getExtras().getSerializable("alumno");
        modoEdicion = (Boolean)getIntent().getExtras().getBoolean("modoEdicion");

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        setTitle(alumno.toString());

    }

    public void pictograma_si(View view){
        int soundId = this.getResources().getIdentifier("si", "raw", this.getPackageName());
        MediaPlayer player = MediaPlayer.create(this, soundId);
        player.start();
    }

    public void pictograma_no(View view){
        int soundId = this.getResources().getIdentifier("no", "raw", this.getPackageName());
        MediaPlayer player = MediaPlayer.create(this, soundId);
        player.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu_activity_alumno; this adds items to the action bar if it is present.
        if (modoEdicion) {
            getMenuInflater().inflate(R.menu.menu_activity_alumno_edicion, menu);
        }else{
            getMenuInflater().inflate(R.menu.menu_activity_alumno, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle app bar item clicks here. The app bar
        // automatically handles clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()) {
            case R.id.ajustes:
                Intent intent = new Intent(AlumnoActivity.this, AjustesActivity.class);
                intent.putExtra("alumno", alumno);
                startActivity(intent);
                finish();
                return true;
            case R.id.modo_edicion:
                Intent intentEdicion = new Intent(AlumnoActivity.this, AlumnoActivity.class);
                intentEdicion.putExtra("alumno", alumno);
                intentEdicion.putExtra("modoEdicion", true);
                startActivity(intentEdicion);
                finish();
                return true;
            case R.id.modo_alumno:
                Intent intentAlumno = new Intent(AlumnoActivity.this, AlumnoActivity.class);
                intentAlumno.putExtra("alumno", alumno);
                intentAlumno.putExtra("modoEdicion", false);
                startActivity(intentAlumno);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private GridView gridView;
        private List<Integer> imagenes = new ArrayList<Integer>();
        private int anchoColumna;
        private GridViewImageAdapter adapter;
        String nombre;
        String apellido;
        String nombreSolapa;
        Alumno alumno;
        boolean modoEdicion;
        int numeroFragment;


        public PlaceholderFragment() {
        }

        public PlaceholderFragment(int numeroFragment, Alumno alum, Boolean modo) {
            modoEdicion = modo;
            alumno = alum;
            nombreSolapa = getPageTitle(numeroFragment).toString();
            nombre = alum.getNombre();
            apellido = alum.getApellido();
            this.numeroFragment = numeroFragment;
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int pageTitle, Alumno alum, Boolean modo) {
            PlaceholderFragment fragment = new PlaceholderFragment(pageTitle, alum, modo);
            return fragment;
        }

        private CharSequence getPageTitle(int numeroFragmento) {
            if (modoEdicion) {
                switch (numeroFragmento) {
                    case 0:
                        return "Pista";
                    case 1:
                        return "Establo";
                    case 2:
                        return "Necesidades";
                    case 3:
                        return "Emociones";
                    case 4:
                        return alumno.toString();
                }
            }else {
                String[] solapas = alumno.getPestañas()!=null ? alumno.getPestañas().split(",") : null; //los nombres de las solapas están separadas por comas

                if (solapas != null && numeroFragmento < solapas.length) {
                    return solapas[numeroFragmento];
                } else if (solapas == null || numeroFragmento == solapas.length) {
                    return alumno.toString();
                } else {
                    return null;
                }
            }
            return null;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView;
            if(modoEdicion || !nombreSolapa.toLowerCase().equals(alumno.toString().toLowerCase())) {
                rootView = inflater.inflate(R.layout.fragment_activity_main, container, false);
                gridView = (GridView) rootView.findViewById(R.id.grid_view);
            }else{
                rootView = inflater.inflate(R.layout.fragment_activity_sino, container, false);
                gridView = (GridView) rootView.findViewById(R.id.imagenes_alumno);
            }

            int cant_columnas=1;
            switch (alumno.getTamañoPictogramas()) {
                case "Chico":
                    cant_columnas=(modoEdicion || !nombreSolapa.toLowerCase().equals(alumno.toString().toLowerCase()))?5:4;
                    break;
                case "Mediano":
                    cant_columnas=(modoEdicion || !nombreSolapa.toLowerCase().equals(alumno.toString().toLowerCase()))?4:3;
                    break;
                case "Grande":
                    cant_columnas=(modoEdicion || !nombreSolapa.toLowerCase().equals(alumno.toString().toLowerCase()))?3:2;
                    break;
            }

            this.inicializarGrilla(cant_columnas, Constantes.PADDING_GRILLA);

            Database db = new Database(this.getContext());
            List<Integer> listaIdImagenes = new Datos(alumno, getActivity()).getImages(db).get(nombreSolapa.toLowerCase()).getIds();
            List<String> listaNombreImagenes =  new Datos(alumno, getActivity()).getImages(db).get(nombreSolapa.toLowerCase()).getNombres();
            ArrayList<String> listaPictogramaAlumno = db.listaPictogramaAlumno(alumno.getId());

            adapter = new GridViewImageAdapter(getActivity(), listaIdImagenes, alumno, anchoColumna, listaNombreImagenes, modoEdicion, listaPictogramaAlumno, numeroFragment);

            gridView.setAdapter(adapter);

            return rootView;
        }

        private void inicializarGrilla(int cantidadColumnas, int paddingGrilla) {
            if(modoEdicion || !nombreSolapa.toLowerCase().equals(alumno.toString().toLowerCase())) {
                anchoColumna = (int) ((this.getScreenWidth() - ((cantidadColumnas + 1) * paddingGrilla)) / cantidadColumnas);
            }else {
                int sino = (this.getScreenWidth() * 20) /100;
                anchoColumna = (int) (((this.getScreenWidth() - sino) - ((cantidadColumnas + 1) * paddingGrilla)) / cantidadColumnas);
            }            gridView.setNumColumns(cantidadColumnas);
            gridView.setColumnWidth(anchoColumna);
            gridView.setStretchMode(GridView.NO_STRETCH);
            gridView.setPadding(paddingGrilla, paddingGrilla, paddingGrilla, paddingGrilla);
            gridView.setHorizontalSpacing(paddingGrilla);
            gridView.setVerticalSpacing(paddingGrilla);
        }

        public int getScreenWidth() {

            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            int width = dm.widthPixels;

            return width;
        }
    }

    public void actualizarFragmento(int pos){
        Fragment frag = mSectionsPagerAdapter.getFragment(pos);
        if(frag != null) {
            System.out.println("actualizando fragmento " + pos);
            getSupportFragmentManager()
                    .beginTransaction()
                    .detach(frag)
                    .attach(frag)
                    .commit();
        }
    }
    public void loadBitmap(int resId, ImageView imageView) {
        BitmapWorkerTask task = new BitmapWorkerTask(imageView, getResources());
        task.execute(resId);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        private Hashtable<Integer, Fragment> fragments = new Hashtable<Integer, Fragment>();
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int numeroFragmento) {
            Fragment fragmento = PlaceholderFragment.newInstance(numeroFragmento, alumno, modoEdicion);
            this.fragments.put(numeroFragmento, fragmento);
            return fragmento;
        }

        public Fragment getFragment(int pos){
            return this.fragments.get(pos);
        }

        @Override
        public int getCount() {
            if(modoEdicion) {
                return 5;
            }else{
                String [] solapas = alumno.getPestañas() != null ? alumno.getPestañas().split(","): null;
                return solapas != null ? solapas.length + 1 : 1;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (modoEdicion) {
                switch (position) {
                    case 0:
                        return "Pista";
                    case 1:
                        return "Establo";
                    case 2:
                        return "Necesidades";
                    case 3:
                        return "Emociones";
                    case 4:
                        return alumno.toString();
                }
            }else {
                String [] solapas = alumno.getPestañas() != null ? alumno.getPestañas().split(","): null; //los nombres de las solapas están separadas por comas

                if (solapas != null && position < solapas.length) {
                    return solapas[position];
                } else if (solapas == null || position == solapas.length) {
                    return alumno.toString();
                } else {
                    return null;
                }
            }
            return null;
        }
    }


}
