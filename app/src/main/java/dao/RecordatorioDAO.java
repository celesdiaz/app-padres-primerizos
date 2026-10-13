package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.time.LocalDate;
import java.util.ArrayList;

import OpenHelper.OpenHelper;
import frgp.utn.edu.app_padresprimerizos.EstadoRecordatorio;
import frgp.utn.edu.app_padresprimerizos.Recordatorio;
import frgp.utn.edu.app_padresprimerizos.TipoRecordatorio;

public class RecordatorioDAO {

    private static final String TABLA = "Recordatorios";

    private static final String COL_ID_BEBE = "id_bebe";
    private static final String COL_ID_VACUNA = "id_vacuna";
    private static final String COL_TIPO = "tipo";
    private static final String COL_FECHA = "fecha";
    private static final String COL_DESCRIPCION = "descripcion";
    private static final String COL_ESTADO = "estado";

    private OpenHelper helper;

    public RecordatorioDAO(Context context)
    {
        helper = new OpenHelper(context);
    }

    public long insertarRecordatorio(Recordatorio r) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(COL_ID_BEBE, r.getIdBebe());
        if(r.getIdVacuna() != null)
        {
            valores.put(COL_ID_VACUNA, r.getIdVacuna());
        }
        else
        {
            valores.putNull(COL_ID_VACUNA);
        }
        valores.put(COL_TIPO, r.getTipo().name());
        valores.put(COL_FECHA, r.getFecha().toString());
        valores.put(COL_DESCRIPCION, r.getDescripcion());
        valores.put(COL_ESTADO, r.getEstado().name());

        return db.insert(TABLA, null, valores);
    }


    public ArrayList<Recordatorio> obtenerRecordatoriosPorBebe(int idBebe)
    {
        SQLiteDatabase db = helper.getReadableDatabase();

        String query = "SELECT * FROM Recordatorios " +
                "WHERE id_bebe = " + idBebe +
                " " + "ORDER BY fecha ASC";

        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Recordatorio> listaRecordatorios = new ArrayList<>();

        while(cursor.moveToNext())
        {
            Recordatorio recordatorio = new Recordatorio();

            recordatorio.setIdRecordatorio(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id_recordatorio"))
            );

            recordatorio.setIdBebe(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id_bebe"))
            );

            int indiceVacuna = cursor.getColumnIndexOrThrow("id_vacuna");

            if(cursor.isNull(indiceVacuna))
            {
                recordatorio.setIdVacuna(null);
            }
            else
            {
                recordatorio.setIdVacuna(cursor.getInt(indiceVacuna));
            }

            recordatorio.setTipo(
                    TipoRecordatorio.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("tipo"))
                    )
            );

            recordatorio.setFecha(
                    LocalDate.parse(cursor.getString(cursor.getColumnIndexOrThrow("fecha"))
                    )
            );

            recordatorio.setDescripcion(
                    cursor.getString(cursor.getColumnIndexOrThrow("descripcion")
                    )
            );

            recordatorio.setEstado(
                    EstadoRecordatorio.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("estado"))
                    )
            );


            listaRecordatorios.add(recordatorio);
        }

        cursor.close();

        return listaRecordatorios;
    }


    public int marcarComoCumplido(Recordatorio recordatorio)
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(COL_ESTADO, EstadoRecordatorio.CUMPLIDO.name());

        return db.update(TABLA, valores, "id_recordatorio = ?", new String[]{String.valueOf(recordatorio.getIdRecordatorio())});
    }

}