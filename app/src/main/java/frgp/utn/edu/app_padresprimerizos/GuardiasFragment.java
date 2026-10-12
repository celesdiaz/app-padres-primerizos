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

        // carga inicial: solo si la tabla no tiene ninguna guardia
        if (dao.estaVacia()) {
            cargarCronograma();
        }

        ArrayList<Guardia> lista = dao.obtenerActivas(null);

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

    // Farmacias de turno de Tigre: 11/10/2026 8:30 a 12/10/2026 8:30
    // Fuente: https://www.tigre.gob.ar/salud/farmacias (datos del Colegio de Farmacéuticos)
    private void cargarCronograma() {
        LocalDate fecha = LocalDate.of(2026, 10, 11);
        String horario = "8:30 a 8:30";

        dao.insertar(crearGuardia(TipoGuardia.FARMACEUTICA, "Farmacia Pacheco Norte",
                "Boulogne Sur Mer y Alberdi, Gral. Pacheco", "4736-1510", horario, fecha));
        dao.insertar(crearGuardia(TipoGuardia.FARMACEUTICA, "Farmacia Agnes",
                "Av. Hipólito Yrigoyen 1655, El Talar", "4740-5424", horario, fecha));
        dao.insertar(crearGuardia(TipoGuardia.FARMACEUTICA, "Farmacia Costanera",
                "Agustín García 8020, Rincón de Milberg", "4871-5666", horario, fecha));
        dao.insertar(crearGuardia(TipoGuardia.FARMACEUTICA, "Farmacia Científica Arata y España",
                "Arata y España, Don Torcuato", "4748-3594", horario, fecha));
        dao.insertar(crearGuardia(TipoGuardia.FARMACEUTICA, "Farmacia Lecumberry",
                "Ruta 197 y Balcarce, Los Troncos del Talar", "4715-0380", horario, fecha));
        dao.insertar(crearGuardia(TipoGuardia.FARMACEUTICA, "Farmacia Molina",
                "Av. Juan Domingo Perón y Chilavert, Benavidez", "03327-481340", horario, fecha));
        dao.insertar(crearGuardia(TipoGuardia.FARMACEUTICA, "Misana Farmacias Alto Brickell",
                "Luis García 1370 Local 2 y 3, Tigre", "11-5385-7486", horario, fecha));

        // Datos de ejemplo (no reales) para poder probar el filtro de guardias médicas
        dao.insertar(crearGuardia(TipoGuardia.MEDICA, "Clínica Norte",
                "Av. Libertador 1200", "11 4455-3333", "Las 24 hs", fecha));
        dao.insertar(crearGuardia(TipoGuardia.MEDICA, "Sanatorio San José",
                "Sarmiento 800", "11 4455-4444", "Las 24 hs", fecha));
    }

    private Guardia crearGuardia(TipoGuardia tipo, String nombre, String ubicacion,
                                 String telefono, String horario, LocalDate fecha) {
        Guardia guardia = new Guardia();
        guardia.setTipo(tipo);
        guardia.setNombre(nombre);
        guardia.setUbicacion(ubicacion);
        guardia.setTelefono(telefono);
        guardia.setHorario(horario);
        guardia.setFechaGuardia(fecha);
        return guardia;
    }
}
