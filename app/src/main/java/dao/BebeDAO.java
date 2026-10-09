package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.time.LocalDate;
import java.util.ArrayList;

import OpenHelper.OpenHelper;
import frgp.utn.edu.app_padresprimerizos.Bebe;

public class BebeDAO {

    private static final String TABLA = "Bebes";
    private static final String COL_ID = "id_bebe";
    private static final String COL_ID_USUARIO = "id_usuario";
    private static final String COL_NOMBRE = "nombre";
    private static final String COL_FECHA_NACIMIENTO = "fecha_nacimiento";

    private OpenHelper helper;

    public BebeDAO(Context context) {
        helper = new OpenHelper(context);
    }

    public long insertarBebe(Bebe b) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(COL_ID_USUARIO, b.getIdUsuario());
        valores.put(COL_NOMBRE, b.getNombre());
        valores.put(COL_FECHA_NACIMIENTO, b.getFechaNacimiento().toString());

        return db.insert(TABLA, null, valores);
    }


    public ArrayList<Bebe> obtenerBebesPorUsuario(int idUsuario)
    {
        SQLiteDatabase db = helper.getReadableDatabase();

        String query = "SELECT * FROM Bebes " +
                "WHERE id_usuario = " + idUsuario;

        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Bebe> listaBebes = new ArrayList<>();

        while(cursor.moveToNext())
        {
            Bebe bebe = new Bebe();

            bebe.setIdBebe(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID))
            );

            bebe.setIdUsuario(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID_USUARIO))
            );

            bebe.setNombre(
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_NOMBRE))
            );

            bebe.setFechaNacimiento(
                    LocalDate.parse(
                            cursor.getString(cursor.getColumnIndexOrThrow(COL_FECHA_NACIMIENTO))
                    )
            );

            listaBebes.add(bebe);
        }

        cursor.close();

        return listaBebes;
    }
}