package frgp.utn.edu.app_padresprimerizos;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Principal extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_principal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnItemSelectedListener(item -> {
            mostrarFragment(crearFragment(item.getItemId()));
            return true;
        });

        if (savedInstanceState == null) {
            bottomNav.setSelectedItemId(R.id.nav_inicio);
        }
    }

    // Cada integrante reemplaza su placeholder por su Fragment real
    private Fragment crearFragment(int itemId) {
        if (itemId == R.id.nav_inicio) {
            return new InicioFragment();
        } else if (itemId == R.id.nav_guardias) {
            return PlaceholderFragment.nuevo("Guardias (Integrante B)");
        } else if (itemId == R.id.nav_servicios) {
            return new DirectorioFragment();
        } else if (itemId == R.id.nav_contactos) {
            return new ContactosFragment();
        } else {
            return PlaceholderFragment.nuevo("Mi bebé (Integrante A)");
        }
    }

    private void mostrarFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedor_fragments, fragment)
                .commit();
    }

    // Lo usan los accesos rápidos del Inicio
    public void navegarA(int itemId) {
        bottomNav.setSelectedItemId(itemId);
    }
}