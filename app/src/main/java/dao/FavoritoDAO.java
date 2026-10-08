package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import OpenHelper.OpenHelper;
import frgp.utn.edu.app_padresprimerizos.Servicio;
import frgp.utn.edu.app_padresprimerizos.TipoServicio;

public class FavoritoDAO {

    private static final String TABLA = "Favoritos";
    private static final String COL_USUARIO = "id_usuario";
    private static final String COL_SERVICIO = "id_servicio";

    private OpenHelper helper;

    public FavoritoDAO(Context context) {
        helper = new OpenHelper(context);
    }

    // devuelve -1 si el servicio ya estaba en fav
    public long agregar(int idUsuario, int idServicio) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(COL_USUARIO, idUsuario);
        valores.put(COL_SERVICIO, idServicio);

        return db.insert(TABLA, null, valores);
    }

    public int quitar(int idUsuario, int idServicio) {
        SQLiteDatabase db = helper.getWritableDatabase();

        return db.delete(TABLA,
                COL_USUARIO + " = ? AND " + COL_SERVICIO + " = ?",
                new String[]{String.valueOf(idUsuario), String.valueOf(idServicio)});
    }

    public boolean esFavorito(int idUsuario, int idServicio) {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(TABLA, null,
                COL_USUARIO + " = ? AND " + COL_SERVICIO + " = ?",
                new String[]{String.valueOf(idUsuario), String.valueOf(idServicio)},
                null, null, null);

        boolean existe;
        if (cursor.getCount() > 0) {
            existe = true;
        } else
        {
            existe = false;
        }

        cursor.close();

        return existe;
    }

    public ArrayList<Servicio> listarServicios(int idUsuario) {
        SQLiteDatabase db = helper.getReadableDatabase();
        ArrayList<Servicio> listaServicios = new ArrayList<>();

        String sql = "SELECT s.id_servicio, s.tipo, s.nombre, s.ubicacion, s.telefono, s.horario "
                + "FROM Servicios s "
                + "INNER JOIN Favoritos f ON f.id_servicio = s.id_servicio "
                + "WHERE f.id_usuario = ? "
                + "ORDER BY s.nombre ASC";

        Cursor scursor = db.rawQuery(sql, new String[]{String.valueOf(idUsuario)});

        if (scursor.moveToFirst()) {
            do {
                Servicio s = new Servicio();
                s.setIdServicio(scursor.getInt(0));
                s.setTipo(TipoServicio.valueOf(scursor.getString(1)));
                s.setNombre(scursor.getString(2));
                s.setUbicacion(scursor.getString(3));
                s.setTelefono(scursor.getString(4));
                s.setHorario(scursor.getString(5));
                listaServicios.add(s);
            } while (scursor.moveToNext());
        }

        scursor.close();
        return listaServicios;
    }
}
