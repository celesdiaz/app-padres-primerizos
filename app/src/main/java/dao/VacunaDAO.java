package dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import OpenHelper.OpenHelper;
import frgp.utn.edu.app_padresprimerizos.Vacuna;

public class VacunaDAO {

    private static final String COL_ID = "id_vacuna";
    private static final String COL_NOMBRE = "nombre";
    private static final String COL_EDAD_APLICACION = "edad_aplicacion";

    private OpenHelper helper;

    public VacunaDAO(Context context)
    {
        helper = new OpenHelper(context);
    }


    public ArrayList<Vacuna> obtenerVacunas()
    {
        SQLiteDatabase db = helper.getReadableDatabase();

        String query = "SELECT * FROM Vacunas";

        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Vacuna> listaVacunas = new ArrayList<>();

        while(cursor.moveToNext())
        {
            Vacuna vacuna = new Vacuna();

            vacuna.setIdVacuna(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID))
            );

            vacuna.setNombre(
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_NOMBRE))
            );

            vacuna.setEdadAplicacion(
                    cursor.getInt(
                            cursor.getColumnIndexOrThrow(COL_EDAD_APLICACION)
                    )
            );

            listaVacunas.add(vacuna);
        }

        cursor.close();

        return listaVacunas;
    }

}