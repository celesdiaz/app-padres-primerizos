package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import OpenHelper.OpenHelper;
import frgp.utn.edu.app_padresprimerizos.Vacuna;

public class VacunaDAO {

    private static final String TABLA = "Vacunas";
    private static final String COL_ID = "id_vacuna";
    private static final String COL_NOMBRE = "nombre";
    private static final String COL_EDAD_APLICACION = "edad_aplicacion";

    private OpenHelper helper;

    public VacunaDAO(Context context) {
        helper = new OpenHelper(context);
    }

    public ArrayList<Vacuna> obtenerVacunas() {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(
                TABLA,
                null,
                null,
                null,
                null,
                null,
                COL_EDAD_APLICACION + " ASC, " + COL_NOMBRE + " ASC"
        );

        ArrayList<Vacuna> listaVacunas = new ArrayList<>();

        while (cursor.moveToNext()) {
            Vacuna vacuna = new Vacuna();

            vacuna.setIdVacuna(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID))
            );

            vacuna.setNombre(
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_NOMBRE))
            );

            vacuna.setEdadAplicacion(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_EDAD_APLICACION))
            );

            listaVacunas.add(vacuna);
        }

        cursor.close();
        return listaVacunas;
    }

    public long insertarVacuna(Vacuna v) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(COL_NOMBRE, v.getNombre());
        valores.put(COL_EDAD_APLICACION, v.getEdadAplicacion());

        return db.insert(TABLA, null, valores);
    }

    public int modificarVacuna(Vacuna v) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(COL_NOMBRE, v.getNombre());
        valores.put(COL_EDAD_APLICACION, v.getEdadAplicacion());

        return db.update(
                TABLA,
                valores,
                COL_ID + " = ?",
                new String[]{String.valueOf(v.getIdVacuna())}
        );
    }

    public int eliminarVacuna(Vacuna v) {
        SQLiteDatabase db = helper.getWritableDatabase();

        db.delete(
                "Recordatorios",
                "id_vacuna = ?",
                new String[]{String.valueOf(v.getIdVacuna())}
        );

        return db.delete(
                TABLA,
                COL_ID + " = ?",
                new String[]{String.valueOf(v.getIdVacuna())}
        );
    }
}