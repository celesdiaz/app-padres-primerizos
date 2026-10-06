package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import OpenHelper.OpenHelper;
import frgp.utn.edu.app_padresprimerizos.Servicio;
import frgp.utn.edu.app_padresprimerizos.TipoServicio;

public class ServicioDAO {
    private static final String TABLA = "Servicios";
    private static final String COL_ID = "id_servicio";
    private static final String COL_TIPO = "tipo";
    private static final String COL_NOMBRE = "nombre";
    private static final String COL_UBICACION = "ubicacion";
    private static final String COL_TELEFONO = "telefono";
    private static final String COL_HORARIO = "horario";
    private OpenHelper helper;

    public ServicioDAO(Context context) {
        helper = new OpenHelper(context);
    }

    public long insertarServicios(Servicio s){
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(COL_TIPO, s.getTipo().name());
        valores.put(COL_NOMBRE, s.getNombre());
        valores.put(COL_UBICACION, s.getUbicacion());
        valores.put(COL_TELEFONO, s.getTelefono());
        valores.put(COL_HORARIO, s.getHorario());

        return db.insert(TABLA, null, valores);
    }

    public ArrayList<Servicio> getListaDeServicios() {
        SQLiteDatabase db = helper.getReadableDatabase();

        ArrayList<Servicio> listaServicios = new ArrayList<Servicio>();
        Cursor scursor = db.query(TABLA, null, null, null, null, null, COL_NOMBRE + " ASC");

        while (scursor.moveToNext()) {
            Servicio s = new Servicio();
            s.setIdServicio(scursor.getInt(scursor.getColumnIndexOrThrow(COL_ID)));
            s.setTipo(TipoServicio.valueOf(scursor.getString(scursor.getColumnIndexOrThrow(COL_TIPO))));
            s.setNombre(scursor.getString(scursor.getColumnIndexOrThrow(COL_NOMBRE)));
            s.setUbicacion(scursor.getString(scursor.getColumnIndexOrThrow(COL_UBICACION)));
            s.setTelefono(scursor.getString(scursor.getColumnIndexOrThrow(COL_TELEFONO)));
            s.setHorario(scursor.getString(scursor.getColumnIndexOrThrow(COL_HORARIO)));
            listaServicios.add(s);
        }
        scursor.close();
        return listaServicios;

    }

    public int eliminarServicio(Servicio s){
        SQLiteDatabase db = helper.getWritableDatabase();

        String id = String.valueOf(s.getIdServicio());

        return db.delete(TABLA, COL_ID + " = ?", new String[]{id});
    }

    public int modificarServicio(Servicio s) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(COL_TIPO, s.getTipo().name());
        valores.put(COL_NOMBRE, s.getNombre());
        valores.put(COL_UBICACION, s.getUbicacion());
        valores.put(COL_TELEFONO, s.getTelefono());
        valores.put(COL_HORARIO, s.getHorario());

        String id = String.valueOf(s.getIdServicio());
        return db.update(TABLA, valores, COL_ID + " = ?", new String[]{id});
    }

    public Servicio obtenerPorId(int idServicio){
        SQLiteDatabase db = helper.getReadableDatabase();

        Servicio servicio = null;
        Cursor cursor = db.query(TABLA, null, COL_ID + " = ?",
                new String[]{String.valueOf(idServicio)}, null, null, null);

        if (cursor.moveToFirst()) {
            servicio = new Servicio();
            servicio.setIdServicio(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
            servicio.setTipo(TipoServicio.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(COL_TIPO))));
            servicio.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(COL_NOMBRE)));
            servicio.setUbicacion(cursor.getString(cursor.getColumnIndexOrThrow(COL_UBICACION)));
            servicio.setTelefono(cursor.getString(cursor.getColumnIndexOrThrow(COL_TELEFONO)));
            servicio.setHorario(cursor.getString(cursor.getColumnIndexOrThrow(COL_HORARIO)));
        }
        cursor.close();
        return servicio;
    }

    public ArrayList<Servicio> buscar(TipoServicio tipo) {
        SQLiteDatabase db = helper.getReadableDatabase();
        ArrayList<Servicio> lista = new ArrayList<>();

        Cursor cursor;
        if (tipo == null) {
            cursor = db.query(TABLA, null, null, null, null, null, COL_NOMBRE + " ASC");
        } else {
            cursor = db.query(TABLA, null, COL_TIPO + " = ?",
                    new String[]{tipo.name()}, null, null, COL_NOMBRE + " ASC");
        }

        while (cursor.moveToNext()) {
            Servicio s = new Servicio();
            s.setIdServicio(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
            s.setTipo(TipoServicio.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(COL_TIPO))));
            s.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(COL_NOMBRE)));
            s.setUbicacion(cursor.getString(cursor.getColumnIndexOrThrow(COL_UBICACION)));
            s.setTelefono(cursor.getString(cursor.getColumnIndexOrThrow(COL_TELEFONO)));
            s.setHorario(cursor.getString(cursor.getColumnIndexOrThrow(COL_HORARIO)));
            lista.add(s);
        }

        cursor.close();
        return lista;
    }

}
