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
        if (estaVacia()) {
            cargarCalendarioInicial();
        }
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

    // true si la tabla no tiene ninguna vacuna (para la carga inicial)
    public boolean estaVacia() {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLA, null);
        cursor.moveToFirst();
        int cantidad = cursor.getInt(0);
        cursor.close();

        return cantidad == 0;
    }

    // Calendario Nacional de Vacunación 2026 de 0 a 15 meses
    private void cargarCalendarioInicial() {
        // Recién nacido
        agregar("BCG (dosis única)", 0);
        agregar("Hepatitis B (dosis neonatal)", 0);

        // 2 meses
        agregar("Neumococo conjugada (1ª dosis)", 2);
        agregar("Quíntuple o pentavalente (1ª dosis)", 2);
        agregar("IPV - Polio (1ª dosis)", 2);
        agregar("Rotavirus (1ª dosis)", 2);

        // 3 meses
        agregar("Meningococo ACWY (1ª dosis)", 3);

        // 4 meses
        agregar("Neumococo conjugada (2ª dosis)", 4);
        agregar("Quíntuple o pentavalente (2ª dosis)", 4);
        agregar("IPV - Polio (2ª dosis)", 4);
        agregar("Rotavirus (2ª dosis)", 4);

        // 5 meses
        agregar("Meningococo ACWY (2ª dosis)", 5);

        // 6 meses
        agregar("Quíntuple o pentavalente (3ª dosis)", 6);
        agregar("IPV - Polio (3ª dosis)", 6);
        agregar("Antigripal (dosis anual)", 6);

        // 12 meses
        agregar("Neumococo conjugada (refuerzo)", 12);
        agregar("Hepatitis A (dosis única)", 12);
        agregar("Triple viral (1ª dosis)", 12);

        // 15 meses
        agregar("Meningococo ACWY (refuerzo)", 15);
        agregar("Varicela (1ª dosis)", 15);
        agregar("Triple viral (2ª dosis)", 15);
        agregar("Quíntuple o pentavalente (1º refuerzo)", 15);
    }

    private void agregar(String nombre, int edadMeses) {
        Vacuna v = new Vacuna();
        v.setNombre(nombre);
        v.setEdadAplicacion(edadMeses);
        insertarVacuna(v);
    }
}
