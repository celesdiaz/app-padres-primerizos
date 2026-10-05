package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import OpenHelper.OpenHelper;
import frgp.utn.edu.app_padresprimerizos.Usuario;

public class UsuarioDAO {

    private static final String TABLA = "Usuarios";
    private static final String COL_ID = "id_usuario";
    private static final String COL_NOMBRE = "nombre";
    private static final String COL_APELLIDO = "apellido";
    private static final String COL_EMAIL = "email";
    private static final String COL_CONTRASENA = "contrasena";
    private static final String COL_ROL = "rol";
    private OpenHelper helper;

    public UsuarioDAO(Context context) {
        helper = new OpenHelper(context);
    }

    public long insertarUsuario(Usuario u){
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(COL_NOMBRE, u.getNombre());
        valores.put(COL_APELLIDO, u.getApellido());
        valores.put(COL_EMAIL, u.getEmail());
        valores.put(COL_CONTRASENA, u.getContrasena());
        valores.put(COL_ROL, u.getRol().name());

        return db.insert(TABLA, null, valores);
    }


}
