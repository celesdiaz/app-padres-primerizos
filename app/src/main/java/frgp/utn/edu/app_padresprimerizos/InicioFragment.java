package frgp.utn.edu.app_padresprimerizos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import dao.ContactoDAO;

public class InicioFragment extends Fragment {

    private static final String PREFS = "sesion";
    private static final String KEY_ID_USUARIO = "id_usuario";
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_ROL = "rol";
    private static final String ROL_ADMIN = "ADMINISTRADOR";

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

        // SOS
        view.findViewById(R.id.btn_sos).setOnClickListener(v -> confirmarSOS());

        // Accesos rápidos
        view.findViewById(R.id.btn_calendario).setOnClickListener(v -> irA(R.id.nav_mibebe));
        view.findViewById(R.id.btn_directorio).setOnClickListener(v -> irA(R.id.nav_servicios));
        view.findViewById(R.id.btn_guardias).setOnClickListener(v -> irA(R.id.nav_guardias));
        view.findViewById(R.id.btn_favoritos).setOnClickListener(v ->
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.contenedor_fragments, new FavoritosFragment())
                        .addToBackStack(null)
                        .commit());
        view.findViewById(R.id.btn_contactos).setOnClickListener(v -> irA(R.id.nav_contactos));

        // Sección administrador
        if (ROL_ADMIN.equals(rol)) {
            view.findViewById(R.id.layout_admin).setVisibility(View.VISIBLE);

            view.findViewById(R.id.btn_admin_guardias)
                    .setOnClickListener(v ->
                            requireActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .setReorderingAllowed(true)
                                    .replace(R.id.contenedor_fragments, new AdminGuardiaFragment())
                                    .addToBackStack(null)
                                    .commit());

            view.findViewById(R.id.btn_admin_directorio)
                    .setOnClickListener(v ->
                            requireActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .setReorderingAllowed(true)
                                    .replace(R.id.contenedor_fragments, new AdminServicioFragment())
                                    .addToBackStack(null)
                                    .commit());

            view.findViewById(R.id.btn_admin_vacunas)
                    .setOnClickListener(v -> mostrarPendiente("Administrar vacunas"));
        }
    }

    private void irA(int itemId) {
        ((Principal) requireActivity()).navegarA(itemId);
    }

    private void confirmarSOS() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Confirmar SOS")
                .setMessage("Se llamará al contacto principal configurado. ¿Deseás continuar?")
                .setPositiveButton("Sí", (dialog, which) -> activarSOS())
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void activarSOS() {
        SharedPreferences prefs = requireContext()
                .getSharedPreferences(PREFS, Context.MODE_PRIVATE);

        int idUsuario = prefs.getInt(KEY_ID_USUARIO, -1);

        if (idUsuario == -1) {
            Toast.makeText(requireContext(), "No se encontró la sesión del usuario", Toast.LENGTH_SHORT).show();
            return;
        }

        ContactoDAO contactoDAO = new ContactoDAO(requireContext());
        Contacto contactoPrincipal = contactoDAO.obtenerContactoPrincipal(idUsuario);

        if (contactoPrincipal == null) {
            Toast.makeText(requireContext(), "No hay un contacto principal configurado", Toast.LENGTH_SHORT).show();
            return;
        }

        String telefono = contactoPrincipal.getTelefono();

        if (telefono == null || telefono.trim().isEmpty()) {
            Toast.makeText(requireContext(), "El contacto principal no tiene teléfono cargado", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + telefono));
        startActivity(intent);
    }

    private void mostrarPendiente(String nombre) {
        Toast.makeText(requireContext(), nombre + ": pendiente de implementar",
                Toast.LENGTH_SHORT).show();
    }
}