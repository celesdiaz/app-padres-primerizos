package frgp.utn.edu.app_padresprimerizos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dao.ContactoDAO;

public class ContactosFragment extends Fragment implements ContactoAdapter.OnContactoListener {
    private RecyclerView recycler;
    private Button btnAgregar;
    private ContactoAdapter adapter;
    private ArrayList<Contacto> contactos;
    private ContactoDAO contactoDAO;
    private int idUsuarioActual;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contactos, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recycler = view.findViewById(R.id.recycler_contactos);
        btnAgregar = view.findViewById(R.id.btn_agregar_contacto);

        // Obtener ID del usuario actual desde SharedPreferences
        SharedPreferences prefs = requireContext().getSharedPreferences("sesion", Context.MODE_PRIVATE);
        idUsuarioActual = prefs.getInt("id_usuario", -1);

        // Inicializar DAO
        contactoDAO = new ContactoDAO(requireContext());

        // Cargar contactos
        cargarContactos();

        // Botón agregar
        btnAgregar.setOnClickListener(v -> {
            FormularioContactoFragment dialogo = new FormularioContactoFragment(null, idUsuarioActual, this::cargarContactos);
            dialogo.show(getChildFragmentManager(), "formulario_contacto");
        });
    }

    private void cargarContactos() {
        contactos = contactoDAO.obtenerContactosPorUsuario(idUsuarioActual);
        adapter = new ContactoAdapter(contactos, getContext(), this);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(adapter);
    }

    @Override
    public void onEliminar(Contacto contacto) {
        new AlertDialog.Builder(getContext())
                .setTitle("Eliminar contacto")
                .setMessage("¿Estás seguro que querés eliminar a " + contacto.getNombre() + "?")
                .setPositiveButton("Eliminar", (dialog, which) -> {
                    contactoDAO.eliminarContacto(contacto);
                    cargarContactos();
                    Toast.makeText(getContext(), "Contacto eliminado", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    @Override
    public void onEditar(Contacto contacto) {
        FormularioContactoFragment dialogo = new FormularioContactoFragment(contacto, idUsuarioActual, this::cargarContactos);
        dialogo.show(getChildFragmentManager(), "formulario_contacto");
    }
}