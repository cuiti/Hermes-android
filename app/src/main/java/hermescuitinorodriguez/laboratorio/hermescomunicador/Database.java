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


    public Database(Context context) {
        super(context, NOMBRE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ALUMNO);
        db.execSQL(PICTOGRAMA);
        db.execSQL(CONFIGURACION);
        //this.cargarPictogramas();
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
            int id = (int)db.insert("alumno", null, values);
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

/*    public ArrayList<Alumno> listaAlumnos() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Alumno> listaAlumnos = new ArrayList<Alumno>();
        Cursor c = db.rawQuery(" SELECT nombre, apellido FROM alumno", null);
        if (c.moveToFirst()) {
            do {
                Alumno alumno = new Alumno(c.getString(0), c.getString(1));
                listaAlumnos.add(alumno);
            } while (c.moveToNext());
            db.close();
            c.close();
            return listaAlumnos;
        }
        return listaAlumnos;
    }*/

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

    private void cargarPictogramas(){
        SQLiteDatabase db = getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put("ID", "caballo1");
            values.put("nombre", "caballo");
            values.put("carpeta", "pista");
            db.insert("pictograma", null, values);
            values.put("ID", "caballo1");
            values.put("nombre", "caballo");
            values.put("carpeta", "pista");
            db.insert("pictograma", null, values);
            values.put("ID", "caballo1");
            values.put("nombre", "caballo");
            values.put("carpeta", "pista");
            db.insert("pictograma", null, values);
            values.put("ID", "caballo1");
            values.put("nombre", "caballo");
            values.put("carpeta", "pista");
            db.insert("pictograma", null, values);
            values.put("ID", "caballo1");
            values.put("nombre", "caballo");
            values.put("carpeta", "pista");
            db.insert("pictograma", null, values);
            values.put("ID", "caballo1");
            values.put("nombre", "caballo");
            values.put("carpeta", "pista");
            db.insert("pictograma", null, values);values.put("ID", "caballo1");
            values.put("nombre", "caballo");
            values.put("carpeta", "pista");
            db.insert("pictograma", null, values);
            values.put("ID", "caballo1");
            values.put("nombre", "caballo");
            values.put("carpeta", "pista");
            db.insert("pictograma", null, values);
            values.put("ID", "caballo1");
            values.put("nombre", "caballo");
            values.put("carpeta", "pista");
            db.insert("pictograma", null, values);
            values.put("ID", "caballo1");
            values.put("nombre", "caballo");
            values.put("carpeta", "pista");
            db.insert("pictograma", null, values);
            values.put("ID", "caballo1");
            values.put("nombre", "caballo");
            values.put("carpeta", "pista");
            db.insert("pictograma", null, values);
            values.put("ID", "caballo1");
            values.put("nombre", "caballo");
            values.put("carpeta", "pista");
            db.insert("pictograma", null, values);

            db.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void borrarAlumno(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("alumno", "id="+id, null);
        db.close();
    }
}
