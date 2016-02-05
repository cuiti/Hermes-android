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
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, carpeta TEXT)";

    private static final String CONFIGURACION = "CREATE TABLE configuracion"+
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT, ip TEXT, puerto INT)";

    /*private static Database instance;

    public static synchronized Database getInstance(Context context) {
        if (instance == null) {
            instance = new Database(context.getApplicationContext());
        }
        return instance;
    }*/

    public Database(Context context) {
        super(context, NOMBRE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ALUMNO);
        db.execSQL(PICTOGRAMA);
        db.execSQL(CONFIGURACION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS");
        onCreate(db);
    }

    public void nuevoAlumno(String nombre, String apellido, String sexo, String tamañoPictograma, String solapas){
        SQLiteDatabase db = getWritableDatabase();
        /*if(db != null){
            System.out.println("La base de datos no existe");
        }*/
        try{
            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("apellido", apellido);
            values.put("sexo", sexo);
            values.put("tamañoPictogramas", tamañoPictograma);
            values.put("solapas", solapas);
            int id = (int)db.insert("alumno", null, values);
            db.close();
            System.out.println(id);
            //return id;
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Alumno> listaAlumnos() {
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
    }
}
