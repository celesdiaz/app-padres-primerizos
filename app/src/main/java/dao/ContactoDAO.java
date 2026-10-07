package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import OpenHelper.OpenHelper;
import frgp.utn.edu.app_padresprimerizos.Contacto;

public class ContactoDAO {
    private static final String TABLA = "Contactos";
    private static final String COL_ID = "id_contacto";
    private static final String COL_ID_USUARIO = "id_usuario";
    private static final String COL_NOMBRE = "nombre";
    private static final String COL_TELEFONO = "telefono";
    // La columna de la BD sigue llamándose "relacion" (no hace falta tocar OpenHelper)
    private static final String COL_TIPO_CONTACTO = "relacion";
    private static final String COL_PRINCIPAL = "principal";
    private OpenHelper helper;

    public ContactoDAO(Context context) {
        helper = new OpenHelper(context);
    }

    public long insertarContacto(Contacto c, int idUsuario) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(COL_ID_USUARIO, idUsuario);
        valores.put(COL_NOMBRE, c.getNombre());
        valores.put(COL_TELEFONO, c.getTelefono());
        valores.put(COL_TIPO_CONTACTO, c.getTipoContacto());
        valores.put(COL_PRINCIPAL, c.isPrincipal() ? 1 : 0);

        return db.insert(TABLA, null, valores);
    }

    public ArrayList<Contacto> obtenerContactosPorUsuario(int idUsuario) {
        SQLiteDatabase db = helper.getReadableDatabase();

        ArrayList<Contacto> listaContactos = new ArrayList<>();
        Cursor cursor = db.query(TABLA, null, COL_ID_USUARIO + " = ?",
                new String[]{String.valueOf(idUsuario)}, null, null, COL_NOMBRE + " ASC");

        while (cursor.moveToNext()) {
            Contacto c = new Contacto();
            c.setIdContacto(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
            c.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(COL_NOMBRE)));
            c.setTelefono(cursor.getString(cursor.getColumnIndexOrThrow(COL_TELEFONO)));
            c.setTipoContacto(cursor.getString(cursor.getColumnIndexOrThrow(COL_TIPO_CONTACTO)));
            c.setPrincipal(cursor.getInt(cursor.getColumnIndexOrThrow(COL_PRINCIPAL)) == 1);
            listaContactos.add(c);
        }
        cursor.close();
        return listaContactos;
    }

    public Contacto obtenerPorId(int idContacto) {
        SQLiteDatabase db = helper.getReadableDatabase();

        Contacto contacto = null;
        Cursor cursor = db.query(TABLA, null, COL_ID + " = ?",
                new String[]{String.valueOf(idContacto)}, null, null, null);

        if (cursor.moveToFirst()) {
            contacto = new Contacto();
            contacto.setIdContacto(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
            contacto.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(COL_NOMBRE)));
            contacto.setTelefono(cursor.getString(cursor.getColumnIndexOrThrow(COL_TELEFONO)));
            contacto.setTipoContacto(cursor.getString(cursor.getColumnIndexOrThrow(COL_TIPO_CONTACTO)));
            contacto.setPrincipal(cursor.getInt(cursor.getColumnIndexOrThrow(COL_PRINCIPAL)) == 1);
        }
        cursor.close();
        return contacto;
    }

    public int modificarContacto(Contacto c) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(COL_NOMBRE, c.getNombre());
        valores.put(COL_TELEFONO, c.getTelefono());
        valores.put(COL_TIPO_CONTACTO, c.getTipoContacto());
        valores.put(COL_PRINCIPAL, c.isPrincipal() ? 1 : 0);

        return db.update(TABLA, valores, COL_ID + " = ?",
                new String[]{String.valueOf(c.getIdContacto())});
    }

    public int eliminarContacto(Contacto c) {
        SQLiteDatabase db = helper.getWritableDatabase();

        return db.delete(TABLA, COL_ID + " = ?",
                new String[]{String.valueOf(c.getIdContacto())});
    }
}