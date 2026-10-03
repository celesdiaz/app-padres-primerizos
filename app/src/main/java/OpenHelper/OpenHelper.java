package OpenHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class OpenHelper extends SQLiteOpenHelper {

    private static final int VERSION_DB = 1;
    private static final String NOMBRE_DB = "PadresPrimerizos.db";

    private static final String CREAR_TABLA_USUARIOS = "CREATE TABLE Usuarios ( " +
            "id_usuario INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nombre TEXT NOT NULL, " +
            "apellido TEXT NOT NULL, " +
            "email TEXT NOT NULL UNIQUE, " +
            "contrasena TEXT NOT NULL, " +
            "rol TEXT NOT NULL" +
            ")";

    private static final String CREAR_TABLA_BEBES = "CREATE TABLE Bebes (" +
            "id_bebe INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "id_usuario INTEGER NOT NULL, " +
            "nombre TEXT NOT NULL, " +
            "fecha_nacimiento TEXT NOT NULL, " +
            "FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario)" +
            ")";

    private static final String CREAR_TABLA_VACUNAS = "CREATE TABLE Vacunas (" +
            "id_vacuna INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nombre TEXT NOT NULL, " +
            "edad_aplicacion INTEGER NOT NULL" +
            ")";

    private static final String CREAR_TABLA_RECORDATORIOS = "CREATE TABLE Recordatorios (" +
            "id_recordatorio INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "id_bebe INTEGER NOT NULL, " +
            "id_vacuna INTEGER, " +
            "tipo TEXT NOT NULL, " +
            "fecha TEXT NOT NULL, " +
            "descripcion TEXT, " +
            "estado TEXT NOT NULL, " +
            "FOREIGN KEY (id_bebe) REFERENCES Bebes(id_bebe), " +
            "FOREIGN KEY (id_vacuna) REFERENCES Vacunas(id_vacuna)" +
            ")";

    private static final String CREAR_TABLA_CONTACTOS = "CREATE TABLE Contactos (" +
            "id_contacto INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "id_usuario INTEGER NOT NULL, " +
            "nombre TEXT NOT NULL, " +
            "telefono TEXT NOT NULL, " +
            "relacion TEXT NOT NULL, " +
            "principal INTEGER NOT NULL DEFAULT 0, " +
            "FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario) " +
            ")";

    private static final String CREAR_TABLA_SERVICIOS = "CREATE TABLE Servicios (" +
            "id_servicio INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "tipo TEXT NOT NULL, " +
            "nombre TEXT NOT NULL, " +
            "ubicacion TEXT NOT NULL, " +
            "telefono TEXT NOT NULL, " +
            "horario TEXT NOT NULL " +
            ")";

    private static final String CREAR_TABLA_FAVORITOS = "CREATE TABLE Favoritos (" +
            "id_favorito INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "id_usuario INTEGER NOT NULL, " +
            "id_servicio INTEGER NOT NULL, " +
            "FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario), " +
            "FOREIGN KEY (id_servicio) REFERENCES Servicios(id_servicio), " +
            "UNIQUE (id_usuario, id_servicio)" +
            ")";

    private static final String CREAR_TABLA_GUARDIAS = "CREATE TABLE Guardias (" +
            "id_guardia INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "tipo TEXT NOT NULL, " +
            "nombre TEXT NOT NULL, " +
            "ubicacion TEXT NOT NULL, " +
            "telefono TEXT NOT NULL, " +
            "horario TEXT NOT NULL, " +
            "fecha_guardia TEXT NOT NULL " +
            ")";

    public OpenHelper(@Nullable Context context) {
        super(context, NOMBRE_DB, null, VERSION_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREAR_TABLA_USUARIOS);
        db.execSQL(CREAR_TABLA_BEBES);
        db.execSQL(CREAR_TABLA_VACUNAS);
        db.execSQL(CREAR_TABLA_RECORDATORIOS);
        db.execSQL(CREAR_TABLA_CONTACTOS);
        db.execSQL(CREAR_TABLA_SERVICIOS);
        db.execSQL(CREAR_TABLA_FAVORITOS);
        db.execSQL(CREAR_TABLA_GUARDIAS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS Favoritos");
        db.execSQL("DROP TABLE IF EXISTS Recordatorios");
        db.execSQL("DROP TABLE IF EXISTS Contactos");
        db.execSQL("DROP TABLE IF EXISTS Bebes");

        db.execSQL("DROP TABLE IF EXISTS Guardias");
        db.execSQL("DROP TABLE IF EXISTS Vacunas");
        db.execSQL("DROP TABLE IF EXISTS Servicios");
        db.execSQL("DROP TABLE IF EXISTS Usuarios");

        onCreate(db);

    }
}
