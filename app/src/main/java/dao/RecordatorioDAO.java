package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import OpenHelper.OpenHelper;
import frgp.utn.edu.app_padresprimerizos.Recordatorio;

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

}