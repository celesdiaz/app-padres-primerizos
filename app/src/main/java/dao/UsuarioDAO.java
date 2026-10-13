package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import OpenHelper.OpenHelper;
import frgp.utn.edu.app_padresprimerizos.Rol;
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


    // true si ya hay un usuario registrado con ese email
    public boolean existeEmail(String email) {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(TABLA, new String[]{COL_ID}, COL_EMAIL + " = ?",
                new String[]{email}, null, null, null);

        boolean existe = cursor.getCount() > 0;
        cursor.close();

        return existe;
    }

    public int iniciarSesion(Usuario usuario)
    {
        SQLiteDatabase db = helper.getReadableDatabase();

        String emailLogin = usuario.getEmail();
        String contrasenaLogin = usuario.getContrasena();
        String query = "SELECT id_usuario, nombre, rol FROM Usuarios WHERE email = ? AND contrasena = ?";

        Cursor cursor = db.rawQuery(query, new String[]{emailLogin, contrasenaLogin});

        int idUsuario = -1;

        if(cursor.moveToFirst())
        {
            idUsuario = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
            usuario.setIdUsuario(idUsuario);

            usuario.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(COL_NOMBRE)));

            usuario.setRol(Rol.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(COL_ROL))));
        }
        cursor.close();

        return idUsuario;
    }

}
