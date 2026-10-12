package frgp.utn.edu.app_padresprimerizos;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import adapter.AdminServicioAdapter;
import dao.ServicioDAO;

public class AdminServicioFragment extends Fragment {

    private ListView listView;
    private ServicioDAO dao;

    public AdminServicioFragment() {
        // Required empty public constructor
    }

    public static AdminServicioFragment newInstance(String param1, String param2) {
        AdminServicioFragment fragment = new AdminServicioFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_admin_servicio, container, false);
        listView = vista.findViewById(R.id.listViewAdminServicios);
        TextView tvSinServicios = vista.findViewById(R.id.tvSinServiciosAdmin);
        Button btnAgregar = vista.findViewById(R.id.btnAgregarServicio);

        dao = new ServicioDAO(requireContext());
        listView.setEmptyView(tvSinServicios);

        btnAgregar.setOnClickListener(v -> abrirFormulario(null));

        actualizarLista();

        return vista;

    }

    private void actualizarLista() {
        ArrayList<Servicio> lista = dao.getListaDeServicios();

        AdminServicioAdapter adapter = new AdminServicioAdapter(requireContext(), lista,
                new AdminServicioAdapter.AdminServicioListener() {
                    @Override
                    public void onEditarClick(Servicio servicio) {
                        abrirFormulario(servicio);
                    }

                    @Override
                    public void onEliminarClick(Servicio servicio) {
                        confirmarEliminar(servicio);
                    }
                });

        listView.setAdapter(adapter);
    }

    private void abrirFormulario(Servicio servicio) {
        new FormularioServicioFragment(servicio, () -> actualizarLista())
                .show(getParentFragmentManager(), "formServicio");
    }

    private void confirmarEliminar(Servicio servicio) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Eliminar servicio")
                .setMessage("¿Eliminar " + servicio.getNombre() + "?")
                .setPositiveButton("Eliminar", (dialog, which) -> {
                    dao.eliminarServicio(servicio);
                    actualizarLista();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}