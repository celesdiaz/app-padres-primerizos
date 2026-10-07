package frgp.utn.edu.app_padresprimerizos;

import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText edtEmailLogin;
    private TextInputEditText edtContrasenaLogin;

    private UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtEmailLogin = findViewById(R.id.edt_email_login);
        edtContrasenaLogin = findViewById(R.id.edt_contra_login);

        usuarioDAO = new UsuarioDAO(this);
    }



    public boolean validaciones()
    {
        boolean estado = true;

        edtEmailLogin.setError(null);
        edtContrasenaLogin.setError(null);

        if(edtEmailLogin.getText().toString().trim().isEmpty())
        {
            edtEmailLogin.setError("Completar email");
            estado = false;
        }
        else
        {
            if(!Patterns.EMAIL_ADDRESS.matcher(edtEmailLogin.getText().toString().trim()).matches())
            {
                edtEmailLogin.setError("Correo invalido");
                estado = false;
            }
        }

        if(edtContrasenaLogin.getText().toString().trim().isEmpty())
        {
            edtContrasenaLogin.setError("Ingresar contraseña");
            estado = false;
        }

        return estado;
    }


    public void iniciarSesion(View view)
    {
        if(!validaciones())
        {
            return;
        }

        String emailLogin = edtEmailLogin.getText().toString().trim();
        String contrasenaLogin = edtContrasenaLogin.getText().toString().trim();

        Usuario usuario = new Usuario();

        usuario.setEmail(emailLogin);
        usuario.setContrasena(contrasenaLogin);

        int idUsuario = usuarioDAO.iniciarSesion(usuario);

        if (idUsuario != -1)
        {
            Intent intent = new Intent(LoginActivity.this, Principal.class);
            intent.putExtra("idUsuario", idUsuario);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
        }

    }


    public void irRegistro(View view)
    {
        Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
        startActivity(intent);
    }

}