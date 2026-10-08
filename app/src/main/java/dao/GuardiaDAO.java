package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.time.LocalDate;
import java.util.ArrayList;

import OpenHelper.OpenHelper;
import frgp.utn.edu.app_padresprimerizos.Guardia;
import frgp.utn.edu.app_padresprimerizos.TipoGuardia;

public class GuardiaDAO {

    private static final String TABLA = "Guardias";
    private static final String COL_ID = "id_guardia";
    private static final String COL_TIPO = "tipo";
    private static final String COL_NOMBRE = "nombre";
    private static final String COL_UBICACION = "ubicacion";
    private static final String COL_TELEFONO = "telefono";
    private static final String COL_HORARIO = "horario";
    private static final String COL_FECHA = "fecha_guardia";

    private static final String[] COLUMNAS = {
            COL_ID, COL_TIPO, COL_NOMBRE, COL_UBICACION, COL_TELEFONO, COL_HORARIO, COL_FECHA
    };

    private OpenHelper helper;

    public GuardiaDAO(Context context) {
        helper = new OpenHelper(context);
    }

    public long insertar(Guardia g)
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues valores = new ContentValues();

        valores.put(COL_TIPO, g.getTipo().name());
        valores.put(COL_NOMBRE, g.getNombre());
        valores.put(COL_UBICACION, g.getUbicacion());
        valores.put(COL_TELEFONO, g.getTelefono());
        valores.put(COL_HORARIO, g.getHorario());
        valores.put(COL_FECHA, g.getFechaGuardia().toString());

        return db.insert(TABLA, null, valores);
    }

    public int modificar(Guardia g)
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues valores = new ContentValues();

        valores.put(COL_TIPO, g.getTipo().name());
        valores.put(COL_NOMBRE, g.getNombre());
        valores.put(COL_UBICACION, g.getUbicacion());
        valores.put(COL_TELEFONO, g.getTelefono());
        valores.put(COL_HORARIO, g.getHorario());
        valores.put(COL_FECHA, g.getFechaGuardia().toString());

        String id = String.valueOf(g.getIdGuardia());
        return db.update(TABLA, valores, COL_ID + " = ?", new String[]{id});
    }

    public int eliminar(Guardia g) {
        SQLiteDatabase db = helper.getWritableDatabase();

        String id = String.valueOf(g.getIdGuardia());
        return db.delete(TABLA, COL_ID + " = ?", new String[]{id});
    }

    // Guardias de hoy, tipo null = "Todas"
    public ArrayList<Guardia> obtenerActivas(TipoGuardia tipo) {
        SQLiteDatabase db = helper.getReadableDatabase();
        ArrayList<Guardia> listaGuardias = new ArrayList<>();

        ArrayList<String> condiciones = new ArrayList<>();
        ArrayList<String> valores = new ArrayList<>();

        condiciones.add(COL_FECHA + " = ?");
        valores.add(LocalDate.now().toString());

        if (tipo != null) {
            condiciones.add(COL_TIPO + " = ?");
            valores.add(tipo.name());
        }

        String filtroSql = TextUtils.join(" AND ", condiciones);
        String[] valoresFiltro = valores.toArray(new String[0]);

        Cursor gcursor = db.query(TABLA, COLUMNAS, filtroSql, valoresFiltro,
                null, null, COL_NOMBRE + " ASC");

        if (gcursor.moveToFirst()) {
            do {
                listaGuardias.add(armarGuardia(gcursor));
            } while (gcursor.moveToNext());
        }

        gcursor.close();
        return listaGuardias;
    }

    public Guardia obtenerPorId(int idGuardia) {
        SQLiteDatabase db = helper.getReadableDatabase();

        Guardia guardia = null;
        Cursor cursor = db.query(TABLA, COLUMNAS, COL_ID + " = ?",
                new String[]{String.valueOf(idGuardia)}, null, null, null);

        if (cursor.moveToFirst()) {
            guardia = armarGuardia(cursor);
        }

        cursor.close();
        return guardia;
    }

    private Guardia armarGuardia(Cursor c) {
        Guardia g = new Guardia();
        g.setIdGuardia(c.getInt(0));
        g.setTipo(TipoGuardia.valueOf(c.getString(1)));
        g.setNombre(c.getString(2));
        g.setUbicacion(c.getString(3));
        g.setTelefono(c.getString(4));
        g.setHorario(c.getString(5));
        g.setFechaGuardia(LocalDate.parse(c.getString(6)));
        return g;
    }
}
