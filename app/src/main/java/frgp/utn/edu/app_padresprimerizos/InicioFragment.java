package frgp.utn.edu.app_padresprimerizos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class InicioFragment extends Fragment {

    // Acordar con A: el login debe guardar estos datos
    private static final String PREFS = "sesion";
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_ROL = "rol";
    private static final String ROL_ADMIN = "ADMIN";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences prefs = requireContext()
                .getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        String nombre = prefs.getString(KEY_NOMBRE, "");
        String rol = prefs.getString(KEY_ROL, "USUARIO");

        TextView tvSaludo = view.findViewById(R.id.tv_saludo);
        if (!nombre.isEmpty()) {
            tvSaludo.setText("¡Hola, " + nombre + "!");
        }

        // SOS: la lógica (confirmación + llamada) se implementa en el RF 8
        view.findViewById(R.id.btn_sos).setOnClickListener(v -> mostrarPendiente("SOS"));

        // Accesos rápidos
        view.findViewById(R.id.btn_calendario).setOnClickListener(v -> irA(R.id.nav_mibebe));
        view.findViewById(R.id.btn_directorio).setOnClickListener(v -> irA(R.id.nav_servicios));
        view.findViewById(R.id.btn_guardias).setOnClickListener(v -> irA(R.id.nav_guardias));
        view.findViewById(R.id.btn_favoritos).setOnClickListener(v -> irA(R.id.nav_servicios));
        view.findViewById(R.id.btn_contactos).setOnClickListener(v -> irA(R.id.nav_mibebe));

        // Sección de administrador
        if (ROL_ADMIN.equals(rol)) {
            view.findViewById(R.id.layout_admin).setVisibility(View.VISIBLE);
            view.findViewById(R.id.btn_admin_guardias)
                    .setOnClickListener(v -> mostrarPendiente("Administrar guardias"));
            view.findViewById(R.id.btn_admin_directorio)
                    .setOnClickListener(v -> mostrarPendiente("Administrar directorio"));
            view.findViewById(R.id.btn_admin_vacunas)
                    .setOnClickListener(v -> mostrarPendiente("Administrar vacunas"));
        }
    }

    private void irA(int itemId) {
        ((Principal) requireActivity()).navegarA(itemId);
    }

    private void mostrarPendiente(String nombre) {
        Toast.makeText(requireContext(), nombre + ": pendiente de implementar",
                Toast.LENGTH_SHORT).show();
    }
}