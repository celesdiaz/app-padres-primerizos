package frgp.utn.edu.app_padresprimerizos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import adapter.BebeAdapter;
import dao.BebeDAO;


public class MiBebeFragment extends Fragment {

    private MaterialButton btnAgregarBebe;
    private RecyclerView recyclerView;
    private BebeAdapter bebeAdapter;
    private ArrayList<Bebe> bebes;

    public MiBebeFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_mi_bebe, container, false);

        btnAgregarBebe = view.findViewById(R.id.btn_agregar_bebe);
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

        recyclerView.setAdapter(bebeAdapter);
    }
}