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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import adapter.RecordatorioAdapter;
import dao.BebeDAO;
import dao.RecordatorioDAO;


public class CalendarioFragment extends Fragment {
    private Spinner spinnerBebe;
    private RecyclerView recyclerRecordatorios;
    private MaterialButton btnPerfiles;
    private ArrayList<Bebe> listaBebes;
    private ArrayList<Recordatorio> listaRecordatorios;
    private RecordatorioAdapter recordatorioAdapter;
    private Bebe bebeSeleccionado;
    private TextView txtVacio;
    private TextView txtSeleccionarBebe;

    public CalendarioFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendario, container, false);

        spinnerBebe = view.findViewById(R.id.spinner_bebe);
        recyclerRecordatorios = view.findViewById(R.id.recycler_recordatorios);
        btnPerfiles = view.findViewById(R.id.btn_perfiles);
        txtVacio = view.findViewById(R.id.txt_calendario_vacio);
        txtSeleccionarBebe = view.findViewById(R.id.txt_seleccionar_bebe);

        btnPerfiles.setOnClickListener(v -> {getParentFragmentManager().beginTransaction().replace(R.id.contenedor_fragments, new MiBebeFragment()).commit();});

        recyclerRecordatorios.setLayoutManager(new LinearLayoutManager(requireContext()));

        cargarBebes();

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

        listaBebes = bebeDAO.obtenerBebesPorUsuario(idUsuario);

        if (listaBebes.isEmpty()) {
            spinnerBebe.setVisibility(View.GONE);
            txtSeleccionarBebe.setVisibility(View.GONE);
            mostrarMensaje("No tenés ningún bebé registrado. Agregá uno desde Perfiles.");
            return;
        }

        spinnerBebe.setVisibility(View.VISIBLE);

        ArrayList<String> nombresBebes = new ArrayList<>();

        for(Bebe bebe:listaBebes)
        {
            nombresBebes.add(bebe.getNombre());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, nombresBebes);

        spinnerBebe.setAdapter(arrayAdapter);

        spinnerBebe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bebeSeleccionado = listaBebes.get(position);
                cargarRecordatorios(bebeSeleccionado.getIdBebe());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void cargarRecordatorios(int idBebe)
    {
        RecordatorioDAO recordatorioDAO = new RecordatorioDAO(requireContext());
        listaRecordatorios = recordatorioDAO.obtenerRecordatoriosPorBebe(idBebe);

        if (listaRecordatorios.isEmpty()) {
            mostrarMensaje("El calendario está vacío: este bebé todavía no tiene recordatorios.");
        } else {
            ocultarMensaje();
        }

        recordatorioAdapter = new RecordatorioAdapter(listaRecordatorios);
        recordatorioAdapter.SetOnCumplirRecordatorioListener(new RecordatorioAdapter.OnCumplirRecordatorioListener() {
            @Override
            public void onCumplirRecordatorio(Recordatorio recordatorio) {
                int resultado = recordatorioDAO.marcarComoCumplido(recordatorio);

                if(resultado > 0)
                {
                    Toast.makeText(requireContext(), "Recordatorio marcado como cumplido", Toast.LENGTH_SHORT).show();
                    cargarRecordatorios(idBebe);
                }
                else
                {
                    Toast.makeText(requireContext(), "No se ha podido marcar como cumplido el recordatorio", Toast.LENGTH_SHORT).show();
                }
            }
        });

        recyclerRecordatorios.setAdapter(recordatorioAdapter);
    }
    private void mostrarMensaje(String mensaje) {
        txtVacio.setText(mensaje);
        txtVacio.setVisibility(View.VISIBLE);
    }

    private void ocultarMensaje() {
        txtVacio.setVisibility(View.GONE);
    }

}