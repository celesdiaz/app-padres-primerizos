package frgp.utn.edu.app_padresprimerizos;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import dao.UsuarioDAO;

public class RegistroActivity extends AppCompatActivity {

    private TextInputEditText edtNombre;
    private TextInputEditText edtApellido;
    private TextInputEditText edtEmail;
    private TextInputEditText edtContrasena;
    private TextInputEditText edtConfirmar;

    private UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtNombre = findViewById(R.id.edt_nombre);
        edtApellido = findViewById(R.id.edt_apellido);
        edtEmail = findViewById(R.id.edt_email);
        edtContrasena = findViewById(R.id.edt_contrasena);
        edtConfirmar = findViewById(R.id.edt_confirmar);

        usuarioDAO = new UsuarioDAO(this);

    }

    public void eventoRegistrar(View view)
    {
        String nombre = edtNombre.getText().toString().trim();
        String apellido = edtApellido.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String contrasena = edtContrasena.getText().toString().trim();
        String confirmar = edtConfirmar.getText().toString().trim();

        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        usuario.setContrasena(contrasena);

        long resultado = usuarioDAO.insertarUsuario(usuario);

        if (resultado != -1)
        {
            Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
        }
    }
}