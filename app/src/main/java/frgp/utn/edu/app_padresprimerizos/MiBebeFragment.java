package frgp.utn.edu.app_padresprimerizos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import adapter.BebeAdapter;
import dao.BebeDAO;


public class MiBebeFragment extends Fragment {

    private MaterialButton btnAgregarBebe;
    private MaterialButton btnCalendario;
    private RecyclerView recyclerView;
    private BebeAdapter bebeAdapter;
    private ArrayList<Bebe> bebes;
    private TextView txtSinBebes;

    public MiBebeFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_mi_bebe, container, false);

        btnAgregarBebe = view.findViewById(R.id.btn_agregar_bebe);
        btnCalendario = view.findViewById(R.id.btn_calendario);
        txtSinBebes = view.findViewById(R.id.txt_sin_bebes);
        recyclerView = view.findViewById(R.id.recycler_bebes);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        cargarBebes();

        btnAgregarBebe.setOnClickListener(v ->
        {
            FormularioBebeFragment formulario = new FormularioBebeFragment();

            formulario.setOnBebeGuardadoListener(
                    new FormularioBebeFragment.OnBebeGuardadoListener() {
                        @Override
                        public void onBebeGuardado() {
                            cargarBebes();
                        }
                    }
            );

            formulario.show(
                    getParentFragmentManager(),
                    "FormularioBebe"
            );
        });


        btnCalendario.setOnClickListener(v -> {getParentFragmentManager().beginTransaction().replace(R.id.contenedor_fragments, new CalendarioFragment()).commit();});

        return view;
    }


    private void cargarBebes()
    {
        SharedPreferences preferences = requireContext().getSharedPreferences("sesion", Context.MODE_PRIVATE);
        int idUsuario = preferences.getInt("id_usuario", -1);

        if(idUsuario == -1)
        {
            return;
        }

        BebeDAO bebeDAO = new BebeDAO(requireContext());

        bebes = bebeDAO.obtenerBebesPorUsuario(idUsuario);

        if (bebes.isEmpty()) {
            txtSinBebes.setVisibility(View.VISIBLE);
        } else {
            txtSinBebes.setVisibility(View.GONE);
        }

        bebeAdapter = new BebeAdapter(bebes);

        bebeAdapter.SetOnRegistrarControlListener(
                new BebeAdapter.OnRegistrarControlListener() {
                    @Override
                    public void onRegistrarControl(Bebe bebe) {
                        FormularioControlMedicoFragment formulario = new FormularioControlMedicoFragment();
                        formulario.setBebe(bebe);
                        formulario.show(getParentFragmentManager(), "FormularioControlMedico");
                    }
                }
        );


        bebeAdapter.setOnEliminarBebeListener(new BebeAdapter.OnEliminarBebeListener() {
            @Override
            public void onEliminarBebe(Bebe bebe) {
                confirmarEliminarBebe(bebe);
            }
        });

        recyclerView.setAdapter(bebeAdapter);
    }


    private void confirmarEliminarBebe(Bebe bebe)
    {
        new AlertDialog.Builder(getContext())
                .setTitle("Eliminar Bebé")
                .setMessage("¿Está seguro de que desea eliminar a " + bebe.getNombre() + "?")
                .setPositiveButton("Eliminar", (dialog, which) -> {
                    BebeDAO bebeDAO = new BebeDAO(requireContext());
                    bebeDAO.eliminarBebe(bebe);
                    cargarBebes();
                    Toast.makeText(requireContext(), "Bebé eliminado", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}