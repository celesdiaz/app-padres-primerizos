package frgp.utn.edu.app_padresprimerizos;

import android.os.Bundle;
import android.util.Patterns;
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

    public boolean validaciones()
        {
            boolean estado = true;

            edtNombre.setError(null);
            edtApellido.setError(null);
            edtEmail.setError(null);
            edtContrasena.setError(null);
            edtConfirmar.setError(null);

            if(edtNombre.getText().toString().trim().isEmpty())
            {
                edtNombre.setError("Campo requerido");
                estado = false;
            }
            else
            {
                if(edtNombre.getText().toString().trim().length() < 3)
                {
                    edtNombre.setError("Minimo 3 caracteres");
                    estado = false;
                }
            }

            if(edtApellido.getText().toString().trim().isEmpty())
            {
                edtApellido.setError("Campo requerido");
                estado = false;
            }
            else
            {
                if(edtApellido.getText().toString().trim().length() < 3)
                {
                    edtApellido.setError("Minimo 3 caracteres");
                    estado = false;
                }
            }

            if(edtEmail.getText().toString().trim().isEmpty())
            {
                edtEmail.setError("Campo requerido");
                estado = false;
            }
            else
            {
                if(!Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString().trim()).matches())
                {
                    edtEmail.setError("Correo invalido");
                    estado = false;
                }
            }


            if(edtContrasena.getText().toString().trim().isEmpty())
            {
                edtContrasena.setError("Campo requerido");
                estado = false;
            }
            else
            {
                if(edtContrasena.getText().toString().trim().length() < 6)
                {
                    edtContrasena.setError("Minimo 6 caracteres");
                    estado = false;
                }
                else
                {
                    if(edtContrasena.getText().toString().contains(" "))
                    {
                        edtContrasena.setError("La contraseña no puede contener espacios");
                        estado = false;
                    }
                }
            }

            if(edtConfirmar.getText().toString().trim().isEmpty())
            {
                edtConfirmar.setError("Campo requerido");
                estado = false;
            }
            else
            {
                if(!edtContrasena.getText().toString().equals(edtConfirmar.getText().toString()))
                {
                    edtConfirmar.setError("Las contraseñas no coinciden");
                    estado = false;
                }
            }

            return estado;

        }

    public void eventoRegistrar(View view)
    {

        if(!validaciones())
        {
            return;
        }

        String nombre = edtNombre.getText().toString().trim();
        String apellido = edtApellido.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String contrasena = edtContrasena.getText().toString().trim();

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