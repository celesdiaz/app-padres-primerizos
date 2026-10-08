package frgp.utn.edu.app_padresprimerizos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.time.LocalDate;
import java.util.ArrayList;

import adapter.ListViewGuardiaAdapter;
import dao.GuardiaDAO;

public class GuardiasFragment extends Fragment {

    private ListView listView;
    private Spinner spinner;
    private GuardiaDAO dao;
    private ListViewGuardiaAdapter adapter;

    public GuardiasFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_guardias, container, false);
        listView = vista.findViewById(R.id.listViewGuardias);
        spinner = vista.findViewById(R.id.spinnerTipoGuardia);

        dao = new GuardiaDAO(requireContext());

        ArrayList<Guardia> lista = dao.obtenerActivas(null);

        if (lista.isEmpty()) {
            cargarGuardiasDePrueba();
            lista = dao.obtenerActivas(null);
        }

        adapter = new ListViewGuardiaAdapter(requireContext(), lista);
        listView.setAdapter(adapter);
        listView.setEmptyView(vista.findViewById(R.id.tvSinGuardias));

        String[] opciones = {"Todas", "Médica", "Farmacéutica"};
        ArrayAdapter<String> adapterTipo = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item,
                opciones);
        adapterTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterTipo);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                actualizarLista();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        return vista;
    }

    private void actualizarLista() {
        TipoGuardia tipo = tipoSeleccionado(spinner.getSelectedItemPosition());

        adapter.clear();
        adapter.addAll(dao.obtenerActivas(tipo));
    }

    private TipoGuardia tipoSeleccionado(int posicion) {
        switch (posicion) {
            case 1: return TipoGuardia.MEDICA;
            case 2: return TipoGuardia.FARMACEUTICA;
            default: return null;   // 0 = "Todas"
        }
    }

    private void cargarGuardiasDePrueba() {
        dao.insertar(crearGuardia(TipoGuardia.FARMACEUTICA, "Farmacia Del Sol", "Mitre 120", "11 4455-1111", "20 a 8 hs"));
        dao.insertar(crearGuardia(TipoGuardia.FARMACEUTICA, "Farmacia Central", "Belgrano 450", "11 4455-2222", "22 a 8 hs"));
        dao.insertar(crearGuardia(TipoGuardia.MEDICA, "Clínica Norte", "Av. Libertador 1200", "11 4455-3333", "Las 24 hs"));
        dao.insertar(crearGuardia(TipoGuardia.MEDICA, "Sanatorio San José", "Sarmiento 800", "11 4455-4444", "Las 24 hs"));
    }

    private Guardia crearGuardia(TipoGuardia tipo, String nombre, String ubicacion, String telefono, String horario) {
        Guardia guardia = new Guardia();
        guardia.setTipo(tipo);
        guardia.setNombre(nombre);
        guardia.setUbicacion(ubicacion);
        guardia.setTelefono(telefono);
        guardia.setHorario(horario);
        guardia.setFechaGuardia(LocalDate.now());
        return guardia;
    }
}
