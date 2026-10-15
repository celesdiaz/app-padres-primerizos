package frgp.utn.edu.app_padresprimerizos;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;
import java.util.ArrayList;

import adapter.ListViewGuardiaAdapter;
import dao.GuardiaDAO;

public class GuardiasFragment extends Fragment {

    private ListView listView;
    private Spinner spinner;
    private TextInputEditText etBuscador;
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
        etBuscador = vista.findViewById(R.id.etBuscadorGuardia);

        dao = new GuardiaDAO(requireContext());

        // carga inicial: solo si la tabla no tiene ninguna guardia
        if (dao.estaVacia()) {
            cargarCronograma();
        }

        ArrayList<Guardia> lista = dao.obtenerActivas(null, "");

        adapter = new ListViewGuardiaAdapter(requireContext(), lista);
        listView.setAdapter(adapter);
        listView.setEmptyView(vista.findViewById(R.id.tvSinGuardias));

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Guardia guardia = adapter.getItem(position);
            Bundle args = new Bundle();
            args.putInt("idGuardia", guardia.getIdGuardia());

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.contenedor_fragments, FichaGuardiaFragment.class, args)
                    .addToBackStack(null)
                    .commit();
        });

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

        // se filtra al tocar la tecla de busqueda del teclado
        etBuscador.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    actualizarLista();
                    handled = true;
                }
                return handled;
            }
        });

        return vista;
    }

    private void actualizarLista() {
        TipoGuardia tipo = tipoSeleccionado(spinner.getSelectedItemPosition());
        String texto = etBuscador.getText().toString();

        adapter.clear();
        adapter.addAll(dao.obtenerActivas(tipo, texto));
    }

    private TipoGuardia tipoSeleccionado(int posicion) {
        switch (posicion) {
            case 1: return TipoGuardia.MEDICA;
            case 2: return TipoGuardia.FARMACEUTICA;
            default: return null;   // 0 = "Todas"
        }
    }

/// Farmacias de turno de San Fernando (Víctoria, Virreyes y San Fernando): octubre 2026
// Fuente: Colegio de Farmacéuticos de San Fernando, https://colfarmasanfdo.org.ar/turnero.html
// Los turnos comienzan a las 8:30 del día indicado y terminan a las 8:30 del día siguiente
    private void cargarCronograma() {
        turno("2026-10-01", "Antigua Andreu", "Constitución 1286, San Fernando");
        turno("2026-10-01", "Del Virrey", "Avellaneda y Calle 4, Virreyes, San Fernando");
        turno("2026-10-02", "Cimino", "C. Casares 3164, Victoria, San Fernando");
        turno("2026-10-02", "Mitre", "Constitución 652, San Fernando");
        turno("2026-10-03", "Grupo Fabole", "René Favaloro 3395, Victoria, San Fernando");
        turno("2026-10-03", "Traynor", "Constitución 1000, San Fernando");
        turno("2026-10-04", "Pacheco Virreyes", "Avellaneda 2899, Virreyes, San Fernando");
        turno("2026-10-04", "Libertador 2200", "Perón 2302, Victoria, San Fernando");
        turno("2026-10-05", "Negri", "Perón 2916, Victoria, San Fernando");
        turno("2026-10-05", "Freire", "Avellaneda 4198, Virreyes, San Fernando");
        turno("2026-10-06", "Santamarina", "Santamarina 1394, Victoria, San Fernando");
        turno("2026-10-06", "Blejer", "C. Casares 3914, Victoria, San Fernando");
        turno("2026-10-07", "Antigua Andreu", "Constitución 1286, San Fernando");
        turno("2026-10-08", "Bustelo", "Perón 1801, San Fernando");
        turno("2026-10-08", "Savini", "Sobremonte 299, San Fernando");
        turno("2026-10-09", "Del Rosario", "Avellaneda 3401, Virreyes, San Fernando");
        turno("2026-10-09", "Pacheco del Pueblo", "Necochea 1202, San Fernando");
        turno("2026-10-10", "De María", "H. Yrigoyen 2098, San Fernando");
        turno("2026-10-10", "Virreyes Central", "Avellaneda 5438, Virreyes, San Fernando");
        turno("2026-10-11", "Canal San Fernando", "3 de Febrero y Colón, San Fernando");
        turno("2026-10-11", "Iriondo 609", "Av. Libertador 3061, Victoria, San Fernando");
        turno("2026-10-12", "Martín", "Perón 2498, Victoria, San Fernando");
        turno("2026-10-12", "Contreras", "Avellaneda 3757, Virreyes, San Fernando");
        turno("2026-10-13", "Melesi", "9 de Julio 1493, San Fernando");
        turno("2026-10-14", "Pacheco San Fernando", "Constitución 201, San Fernando");
        turno("2026-10-14", "Panamá", "Avellaneda 4810, Virreyes, San Fernando");
        turno("2026-10-15", "Pelman", "Perón 3302, Victoria, San Fernando");
        turno("2026-10-15", "Virrey Sobremonte", "Avellaneda 1899, Virreyes, San Fernando");
        turno("2026-10-16", "Mastronardi", "Brandsen 2111, Virreyes, San Fernando");
        turno("2026-10-16", "Bruno", "Bolloqui 190, Victoria, San Fernando");
        turno("2026-10-17", "Otharan", "Belgrano 1964, San Fernando");
        turno("2026-10-17", "Lässig", "Perón 406, San Fernando");
        turno("2026-10-18", "Antigua Andreu", "Constitución 1286, San Fernando");
        turno("2026-10-18", "Del Virrey", "Avellaneda y Calle 4, Virreyes, San Fernando");
        turno("2026-10-19", "Cimino", "C. Casares 3164, Victoria, San Fernando");
        turno("2026-10-19", "Mitre", "Constitución 652, San Fernando");
        turno("2026-10-20", "Grupo Fabole", "René Favaloro 3395, Victoria, San Fernando");
        turno("2026-10-20", "Traynor", "Constitución 1000, San Fernando");
        turno("2026-10-21", "Pacheco Virreyes", "Avellaneda 2899, Virreyes, San Fernando");
        turno("2026-10-21", "Libertador 2200", "Perón 2302, Victoria, San Fernando");
        turno("2026-10-22", "Negri", "Perón 2916, Victoria, San Fernando");
        turno("2026-10-22", "Freire", "Avellaneda 4198, Virreyes, San Fernando");
        turno("2026-10-23", "Santamarina", "Santamarina 1394, Victoria, San Fernando");
        turno("2026-10-23", "Blejer", "C. Casares 3914, Victoria, San Fernando");
        turno("2026-10-24", "Antigua Andreu", "Constitución 1286, San Fernando");
        turno("2026-10-25", "Bustelo", "Perón 1801, San Fernando");
        turno("2026-10-25", "Savini", "Sobremonte 299, San Fernando");
        turno("2026-10-26", "Del Rosario", "Avellaneda 3401, Virreyes, San Fernando");
        turno("2026-10-26", "Pacheco del Pueblo", "Necochea 1202, San Fernando");
        turno("2026-10-27", "De María", "H. Yrigoyen 2098, San Fernando");
        turno("2026-10-27", "Virreyes Central", "Avellaneda 5438, Virreyes, San Fernando");
        turno("2026-10-28", "Canal San Fernando", "3 de Febrero y Colón, San Fernando");
        turno("2026-10-28", "Iriondo 609", "Av. Libertador 3061, Victoria, San Fernando");
        turno("2026-10-29", "Martín", "Perón 2498, Victoria, San Fernando");
        turno("2026-10-29", "Contreras", "Avellaneda 3757, Virreyes, San Fernando");
        turno("2026-10-30", "Melesi", "9 de Julio 1493, San Fernando");
        turno("2026-10-31", "Pacheco San Fernando", "Constitución 201, San Fernando");
        turno("2026-10-31", "Panamá", "Avellaneda 4810, Virreyes, San Fernando");
        turno("2026-11-01", "Pelman", "Perón 3302, Victoria, San Fernando");
        turno("2026-11-01", "Virrey Sobremonte", "Avellaneda 1899, Virreyes, San Fernando");
    }

    private void turno(String fecha, String nombre, String direccion) {
        Guardia guardia = new Guardia();
        guardia.setTipo(TipoGuardia.FARMACEUTICA);
        guardia.setNombre(nombre);
        guardia.setUbicacion(direccion);
        guardia.setTelefono(telefonoDe(nombre));
        guardia.setHorario("8:30 a 8:30");
        guardia.setFechaGuardia(LocalDate.parse(fecha));
        dao.insertar(guardia);
    }

    // Teléfonos del directorio del Colegio de Farmacéuticos de San Fernando
    private String telefonoDe(String nombre) {
        switch (nombre) {
            case "Antigua Andreu": return "4580-0932";
            case "Del Virrey": return "4549-6170";
            case "Cimino": return "1144400043";
            case "Mitre": return "4744-3132";
            case "Grupo Fabole": return "1162823395";
            case "Traynor": return "4744-6818";
            case "Pacheco Virreyes": return "1131908047";
            case "Libertador 2200": return "1164980438";
            case "Negri": return "4745-3327";
            case "Freire": return "4714-2542";
            case "Santamarina": return "4549-0451";
            case "Blejer": return "4725-0540";
            case "Bustelo": return "4746-2045";
            case "Savini": return "4745-1012";
            case "Del Rosario": return "4744-8356";
            case "Pacheco del Pueblo": return "1128969223";
            case "De María": return "4706-8135";
            case "Canal San Fernando": return "4744-7712";
            case "Iriondo 609": return "1125956114";
            case "Martín": return "1560769537";
            case "Contreras": return "4714-2704";
            case "Melesi": return "4580-0935";
            case "Pacheco San Fernando": return "1141625485";
            case "Panamá": return "4714-3770";
            case "Pelman": return "4744-2852";
            case "Virrey Sobremonte": return "4745-2022";
            case "Mastronardi": return "4744-4185";
            case "Bruno": return "4744-4511";
            case "Otharan": return "1171886579";
            case "Lässig": return "1141846780";
            default: return "No informado";   // por ejemplo "Virreyes Central", que no figura en el directorio
        }
    }
}
