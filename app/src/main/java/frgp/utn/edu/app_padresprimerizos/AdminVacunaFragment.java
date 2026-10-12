package frgp.utn.edu.app_padresprimerizos;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import dao.VacunaDAO;

public class AdminVacunaFragment extends Fragment implements VacunaAdapter.OnVacunaListener {

    private RecyclerView recycler;
    private MaterialButton btnAgregar;
    private VacunaAdapter adapter;
    private ArrayList<Vacuna> vacunas;
    private VacunaDAO vacunaDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_vacunas, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recycler = view.findViewById(R.id.recycler_vacunas);
        btnAgregar = view.findViewById(R.id.btn_agregar_vacuna);

        vacunaDAO = new VacunaDAO(requireContext());

        cargarVacunas();

        btnAgregar.setOnClickListener(v -> {
            FormularioVacunaFragment dialogo =
                    new FormularioVacunaFragment(null, this::cargarVacunas);
            dialogo.show(getChildFragmentManager(), "formulario_vacuna");
        });
    }

    private void cargarVacunas() {
        vacunas = vacunaDAO.obtenerVacunas();
        adapter = new VacunaAdapter(vacunas, getContext(), this);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(adapter);
    }

    @Override
    public void onEliminar(Vacuna vacuna) {
        new AlertDialog.Builder(getContext())
                .setTitle("Eliminar vacuna")
                .setMessage("¿Estás seguro que querés eliminar " + vacuna.getNombre() + "?")
                .setPositiveButton("Eliminar", (dialog, which) -> {
                    vacunaDAO.eliminarVacuna(vacuna);
                    cargarVacunas();
                    Toast.makeText(getContext(), "Vacuna eliminada", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    @Override
    public void onEditar(Vacuna vacuna) {
        FormularioVacunaFragment dialogo =
                new FormularioVacunaFragment(vacuna, this::cargarVacunas);
        dialogo.show(getChildFragmentManager(), "formulario_vacuna");
    }
}