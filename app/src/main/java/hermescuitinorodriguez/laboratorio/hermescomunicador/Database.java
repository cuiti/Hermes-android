package hermescuitinorodriguez.laboratorio.hermescomunicador;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper{

    private static final String NOMBRE = "HERMES";

    private static final int VERSION = 1;

    private static final String ALUMNO = "CREATE TABLE alumno" +
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, apellido TEXT, sexo TEXT, tamañoPictogramas TEXT, solapas TEXT )";

    private static final String PICTOGRAMA = "CREATE TABLE pictograma"+
            "(ID TEXT, nombre TEXT, carpeta TEXT)";

    private static final String CONFIGURACION = "CREATE TABLE configuracion"+
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT, ip TEXT, puerto INT)";

    private  static final String PICTOGRAMA_ALUMNO = "CREATE TABLE pictograma_alumno"+
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT,  alumno_id INTEGER, pictograma_id TEXT," +
            "  FOREIGN KEY(alumno_id) REFERENCES alumno(id), FOREIGN KEY(pictograma_id) REFERENCES pictograma(id))";


    public Database(Context context) {
        super(context, NOMBRE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ALUMNO);
        db.execSQL(PICTOGRAMA);
        db.execSQL(CONFIGURACION);
        db.execSQL(PICTOGRAMA_ALUMNO);
        this.cargarPictogramas(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS");
        onCreate(db);
    }

    public void nuevoAlumno(String nombre, String apellido, String sexo, String tamañoPictograma, String solapas){
        SQLiteDatabase db = getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("apellido", apellido);
            values.put("sexo", sexo);
            values.put("tamañoPictogramas", tamañoPictograma);
            values.put("solapas", solapas);
            db.insert("alumno", null, values);
            db.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cargarPictogramaAlumno(int alumno, String pictograma){
        SQLiteDatabase db = getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put("alumno_id", alumno);
            values.put("pictograma_id", pictograma);
            db.insert("pictograma_alumno", null, values);
            db.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void agregarConfiguracion(String ip, Integer puerto){
        SQLiteDatabase db = getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put("ip", ip);
            values.put("puerto", puerto);
            db.insert("configuracion", null, values);
            db.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ArrayList<Alumno> listaAlumnos() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Alumno> listaAlumnos = new ArrayList<Alumno>();
        Cursor c = db.rawQuery(" SELECT * FROM alumno", null);
        if (c.moveToFirst()) {
            do {
                Alumno alumno = new Alumno(c.getInt(0), c.getString(1), c.getString(2), c.getString(3),
                c.getString(4), c.getString(5));
                listaAlumnos.add(alumno);
            } while (c.moveToNext());
            db.close();
            c.close();
            return listaAlumnos;
        }
        return listaAlumnos;
    }

    public Settings getConfiguracion(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(" SELECT ip, puerto FROM configuracion", null);
        if (c.moveToFirst()) {
            Settings config;
            do {
                config = new Settings(c.getString(0), c.getInt(1));
            } while (c.moveToNext());
            db.close();
            c.close();
            return config;
        }
        return null;
    }

    public void modificarConfiguracion(String ip, Integer puerto){
        SQLiteDatabase db = getWritableDatabase();
        if(db != null){
            ContentValues values = new ContentValues();
            values.put("ip", ip);
            values.put("puerto", puerto);
            db.update("configuracion", values, "id= 1", null);
            db.close();
        }
    }

    public Alumno modificarAlumno(int id, String nombre, String apellido, String sexo, String tamañoPictograma, String solapas){
        SQLiteDatabase db = getWritableDatabase();
        if(db != null){
            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("apellido", apellido);
            values.put("sexo", sexo);
            values.put("tamañoPictogramas", tamañoPictograma);
            values.put("solapas", solapas);
            db.update("alumno", values, "id="+id, null);
            db.close();
        }
        return getAlumno(id);
    }

    public Alumno getAlumno(int id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(" SELECT * FROM alumno WHERE id=" + id, null);
        if(c != null) {
            c.moveToFirst();
        }
        Alumno alumno = new Alumno(c.getInt(0), c.getString(1), c.getString(2), c.getString(3),
                c.getString(4), c.getString(5));
        db.close();
        c.close();
        return alumno;
    }

    private void cargarPictogramas( SQLiteDatabase db){

        try{
            ContentValues values = new ContentValues();
            values.put("ID", "aro");
            values.put("nombre", "aro");
            values.put("carpeta", "pista");
            db.insert("pictograma", null, values);

            values.put("ID", "asustada");
            values.put("nombre", "asustada");
            values.put("carpeta", "emociones");
            db.insert("pictograma", null, values);

            values.put("ID", "asustado");
            values.put("nombre", "asustado");
            values.put("carpeta", "emociones");
            db.insert("pictograma", null, values);

            values.put("ID", "bano");
            values.put("nombre", "bano");
            values.put("carpeta", "necesidades");
            db.insert("pictograma", null, values);

            values.put("ID", "sedf");
            values.put("nombre", "sed");
            values.put("carpeta", "necesidades");
            db.insert("pictograma", null, values);

            values.put("ID", "sedm");
            values.put("nombre", "sed");
            values.put("carpeta", "necesidades");
            db.insert("pictograma", null, values);

            values.put("ID", "broches");
            values.put("nombre", "broches");
            values.put("carpeta", "pista");
            db.insert("pictograma", null, values);

            values.put("ID", "burbujas");
            values.put("nombre", "burbujas");
            values.put("carpeta", "pista");
            db.insert("pictograma", null, values);

            values.put("ID", "caballo1");
            values.put("nombre", "caballo");
            values.put("carpeta", "establo");
            db.insert("pictograma", null, values);

            values.put("ID", "caballo2");
            values.put("nombre", "caballo");
            values.put("carpeta", "establo");
            db.insert("pictograma", null, values);

            values.put("ID", "caballo3");
            values.put("nombre", "caballo");
            values.put("carpeta", "establo");
            db.insert("pictograma", null, values);

            values.put("ID", "cansada");
            values.put("nombre", "cansada");
            values.put("carpeta", "emociones");
            db.insert("pictograma", null, values);

            values.put("ID", "cansado");
            values.put("nombre", "cansado");
            values.put("carpeta", "emociones");
            db.insert("pictograma", null, values);

            values.put("ID", "casco");
            values.put("nombre", "casco");
            values.put("carpeta", "pista");
            db.insert("pictograma", null, values);

            values.put("ID", "cepillo");
            values.put("nombre", "cepillo");
            values.put("carpeta", "establo");
            db.insert("pictograma", null, values);

            values.put("ID", "chapas");
            values.put("nombre", "chapas");
            values.put("carpeta", "pista");
            db.insert("pictograma", null, values);

            values.put("ID", "contento");
            values.put("nombre", "contento");
            values.put("carpeta", "emociones");
            db.insert("pictograma", null, values);

            values.put("ID", "contenta");
            values.put("nombre", "contenta");
            values.put("carpeta", "emociones");
            db.insert("pictograma", null, values);

            values.put("ID", "cubos");
            values.put("nombre", "cubos");
            values.put("carpeta", "pista");
            db.insert("pictograma", null, values);

            values.put("ID", "meduelef");
            values.put("nombre", "dolorida");
            values.put("carpeta", "emociones");
            db.insert("pictograma", null, values);

            values.put("ID", "meduelem");
            values.put("nombre", "dolorido");
            values.put("carpeta", "emociones");
            db.insert("pictograma", null, values);

            values.put("ID", "enojada");
            values.put("nombre", "enojada");
            values.put("carpeta", "emociones");
            db.insert("pictograma", null, values);

            values.put("ID", "enojado");
            values.put("nombre", "enojado");
            values.put("carpeta", "emociones");
            db.insert("pictograma", null, values);

            values.put("ID", "escarbavasos");
            values.put("nombre", "escarba vasos");
            values.put("carpeta", "establo");
            db.insert("pictograma", null, values);

            values.put("ID", "letras");
            values.put("nombre", "letras");
            values.put("carpeta", "pista");
            db.insert("pictograma", null, values);

            values.put("ID", "limpieza");
            values.put("nombre", "limpieza");
            values.put("carpeta", "establo");
            db.insert("pictograma", null, values);

            values.put("ID", "maracas");
            values.put("nombre", "maracas");
            values.put("carpeta", "pista");
            db.insert("pictograma", null, values);

            values.put("ID", "matra");
            values.put("nombre", "matra");
            values.put("carpeta", "establo");
            db.insert("pictograma", null, values);

            values.put("ID", "montura");
            values.put("nombre", "montura");
            values.put("carpeta", "establo");
            db.insert("pictograma", null, values);

            values.put("ID", "palos");
            values.put("nombre", "palos");
            values.put("carpeta", "pista");
            db.insert("pictograma", null, values);

            values.put("ID", "pasto");
            values.put("nombre", "pasto");
            values.put("carpeta", "establo");
            db.insert("pictograma", null, values);

            values.put("ID", "pato");
            values.put("nombre", "pato");
            values.put("carpeta", "pista");
            db.insert("pictograma", null, values);

            values.put("ID", "pelota");
            values.put("nombre", "pelota");
            values.put("carpeta", "pista");
            db.insert("pictograma", null, values);

            values.put("ID", "rasquetablanda");
            values.put("nombre", "rasqueta blanda");
            values.put("carpeta", "establo");
            db.insert("pictograma", null, values);

            values.put("ID", "rasquetadura");
            values.put("nombre", "rasqueta dura");
            values.put("carpeta", "establo");
            db.insert("pictograma", null, values);

            values.put("ID", "riendas");
            values.put("nombre", "riendas");
            values.put("carpeta", "pista");
            db.insert("pictograma", null, values);

            values.put("ID", "sorprendida");
            values.put("nombre", "sorprendida");
            values.put("carpeta", "emociones");
            db.insert("pictograma", null, values);

            values.put("ID", "sorprendido");
            values.put("nombre", "sorprendido");
            values.put("carpeta", "emociones");
            db.insert("pictograma", null, values);

            values.put("ID", "tarima");
            values.put("nombre", "tarima");
            values.put("carpeta", "pista");
            db.insert("pictograma", null, values);

            values.put("ID", "tristef");
            values.put("nombre", "triste");
            values.put("carpeta", "emociones");
            db.insert("pictograma", null, values);

            values.put("ID", "tristem");
            values.put("nombre", "triste");
            values.put("carpeta", "emociones");
            db.insert("pictograma", null, values);

            values.put("ID", "zanahoria");
            values.put("nombre", "zanahoria");
            values.put("carpeta", "establo");
            db.insert("pictograma", null, values);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void borrarAlumno(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("alumno", "id="+id, null);
        db.close();
    }

    public String getCategoria(String pictograma){
        String categoria = "";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(" SELECT carpeta FROM pictograma WHERE id='" + pictograma + "' ", null);
        if(c != null) {
            c.moveToFirst();
            categoria = c.getString(0);
        }
        db.close();
        c.close();
        return categoria;
    }
}
