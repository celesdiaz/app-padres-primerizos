package frgp.utn.edu.app_padresprimerizos;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import adapter.AdminGuardiaAdapter;
import dao.GuardiaDAO;

public class AdminGuardiaFragment extends Fragment {

    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("EEEE dd/MM/yyyy", new Locale("es", "AR"));

    private ListView listView;
    private TextView tvFecha;
    private GuardiaDAO dao;
    private LocalDate fechaSeleccionada = LocalDate.now();

    public AdminGuardiaFragment() {
        // Required empty public constructor
    }

    public static AdminGuardiaFragment newInstance(String param1, String param2) {
        AdminGuardiaFragment fragment = new AdminGuardiaFragment();
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
        View vista = inflater.inflate(R.layout.fragment_admin_guardia, container, false);

        listView = vista.findViewById(R.id.listViewAdminGuardias);
        tvFecha = vista.findViewById(R.id.tvFechaSeleccionada);
        TextView tvSinGuardias = vista.findViewById(R.id.tvSinGuardiasAdmin);
        Button btnAgregar = vista.findViewById(R.id.btnAgregarGuardia);
        ImageButton btnAnterior = vista.findViewById(R.id.btnDiaAnterior);
        ImageButton btnSiguiente = vista.findViewById(R.id.btnDiaSiguiente);

        dao = new GuardiaDAO(requireContext());
        listView.setEmptyView(tvSinGuardias);

        btnAgregar.setOnClickListener(v -> abrirFormulario(null));

        btnAnterior.setOnClickListener(v -> {
            fechaSeleccionada = fechaSeleccionada.minusDays(1);
            actualizarLista();
        });
        btnSiguiente.setOnClickListener(v -> {
            fechaSeleccionada = fechaSeleccionada.plusDays(1);
            actualizarLista();
        });
        tvFecha.setOnClickListener(v -> abrirCalendario());

        actualizarLista();
        return vista;
    }

    private void abrirCalendario() {
        // el mes de DatePickerDialog va de 0 a 11, el de LocalDate de 1 a 12
        new DatePickerDialog(requireContext(),
                (picker, anio, mes, dia) -> {
                    fechaSeleccionada = LocalDate.of(anio, mes + 1, dia);
                    actualizarLista();
                },
                fechaSeleccionada.getYear(),
                fechaSeleccionada.getMonthValue() - 1,
                fechaSeleccionada.getDayOfMonth()).show();
    }

    private void actualizarLista() {
        tvFecha.setText(fechaSeleccionada.format(FORMATO_FECHA));

        ArrayList<Guardia> lista = dao.obtenerPorFecha(fechaSeleccionada);

        AdminGuardiaAdapter adapter = new AdminGuardiaAdapter(requireContext(), lista,
                new AdminGuardiaAdapter.AdminGuardiaListener() {
                    @Override
                    public void onEditarClick(Guardia guardia) {
                        abrirFormulario(guardia);
                    }

                    @Override
                    public void onEliminarClick(Guardia guardia) {
                        confirmarEliminar(guardia);
                    }
                });

        listView.setAdapter(adapter);
    }

    private void abrirFormulario(Guardia guardia) {
        new FormularioGuardiaFragment(guardia, fechaSeleccionada, () -> actualizarLista())
                .show(getParentFragmentManager(), "formGuardia");
    }

    private void confirmarEliminar(Guardia guardia) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Eliminar guardia")
                .setMessage("¿Eliminar " + guardia.getNombre() + "?")
                .setPositiveButton("Eliminar", (dialog, which) -> {
                    dao.eliminar(guardia);
                    actualizarLista();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}