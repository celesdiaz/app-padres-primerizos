package frgp.utn.edu.app_padresprimerizos;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class RegistroActivity extends AppCompatActivity {

    private TextInputEditText edtNombre;
    private TextInputEditText edtApellido;
    private TextInputEditText edtEmail;
    private TextInputEditText edtContrasena;
    private TextInputEditText edtConfirmar;
    private MaterialButton btnRegistrar;

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
        btnRegistrar = findViewById(R.id.btn_registrar);
    }
}