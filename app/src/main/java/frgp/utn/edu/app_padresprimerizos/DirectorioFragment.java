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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.google.android.material.textfield.TextInputEditText;

import android.widget.TextView;

import dao.ServicioDAO;
import java.util.ArrayList;

import adapter.ListViewDirectorioAdapter;

public class DirectorioFragment extends Fragment {

    private ListView listView;
    private Spinner spinner;
    private TextInputEditText etBuscador;
    private ServicioDAO dao;
    private ListViewDirectorioAdapter adapter;

    public DirectorioFragment(){

    }

    public static DirectorioFragment newInstance(String param1, String param2){
        DirectorioFragment fragment = new DirectorioFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_directorio, container, false);
        listView = vista.findViewById(R.id.listViewServicios);
        etBuscador = vista.findViewById(R.id.etBuscador);

        dao = new ServicioDAO(requireContext());

        ArrayList<Servicio> lista = dao.getListaDeServicios();
        if (lista.isEmpty()) {
            cargarServiciosDePrueba(dao);
            lista = dao.getListaDeServicios();
        }

        adapter = new ListViewDirectorioAdapter(requireContext(), lista);
        listView.setAdapter(adapter);

        listView.setEmptyView(vista.findViewById(R.id.tvSinResultados));

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Servicio servicio = adapter.getItem(position);
            Bundle args = new Bundle();
            args.putInt("idServicio", servicio.getIdServicio());

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.contenedor_fragments, FichaServicioFragment.class, args)
                    .addToBackStack(null)
                    .commit();
        });

        spinner = vista.findViewById(R.id.spinnerTipo);
        String[] opciones = {"Todos", "Farmacia", "Pediatría", "Guardería", "Pañalera"};
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

    // Farmacias reales: directorio del Colegio de Farmacéuticos de San Fernando
// Fuente: https://colfarmasanfdo.org.ar/farmacias.html
// Los otros tres servicios son EJEMPLOS ficticios (no hay una fuente real cargada)
    private void cargarServiciosDePrueba(ServicioDAO dao) {
        farmacia(dao, "Antigua Andreu", "Constitución 1286, San Fernando", "4580-0932");
        farmacia(dao, "Blejer", "C. Casares 3914, Victoria, San Fernando", "4725-0540");
        farmacia(dao, "Bruno", "Bolloqui 190, Victoria, San Fernando", "4744-4511");
        farmacia(dao, "Bustelo", "Perón 1801, San Fernando", "4746-2045");
        farmacia(dao, "Canal San Fernando", "3 de Febrero y Colón, San Fernando", "4744-7712");
        farmacia(dao, "Cimino", "C. Casares 3164, Victoria, San Fernando", "1144400043");
        farmacia(dao, "Contreras", "Avellaneda 3757, Virreyes, San Fernando", "4714-2704");
        farmacia(dao, "De María", "H. Yrigoyen 2098, San Fernando", "4706-8135");
        farmacia(dao, "Del Rosario", "Avellaneda 3401, Virreyes, San Fernando", "4744-8356");
        farmacia(dao, "Del Virrey", "Avellaneda y Calle 4, Virreyes, San Fernando", "4549-6170");
        farmacia(dao, "Freire", "Avellaneda 4198, Virreyes, San Fernando", "4714-2542");
        farmacia(dao, "Grupo Fabole", "René Favaloro 3395, Victoria, San Fernando", "1162823395");
        farmacia(dao, "Iriondo", "Av. Libertador 3061, Victoria, San Fernando", "1125956114");
        farmacia(dao, "Lässig", "Perón 406, San Fernando", "1141846780");
        farmacia(dao, "Libertador 2200", "Perón 2302, Victoria, San Fernando", "1164980438");
        farmacia(dao, "Martín", "Perón 2498, Victoria, San Fernando", "1560769537");
        farmacia(dao, "Mastronardi", "Brandsen 2111, Virreyes, San Fernando", "4744-4185");
        farmacia(dao, "Melesi", "9 de Julio 1493, San Fernando", "4580-0935");
        farmacia(dao, "Mitre", "Constitución 652, San Fernando", "4744-3132");
        farmacia(dao, "Negri", "Perón 2916, Victoria, San Fernando", "4745-3327");
        farmacia(dao, "Martínez", "Avellaneda 5475, Virreyes, San Fernando", "7539-7539");
        farmacia(dao, "Pacheco Virreyes", "Avellaneda 2899, Virreyes, San Fernando", "1131908047");
        farmacia(dao, "9 de Julio", "9 de Julio 1232, San Fernando", "4580-0938");
        farmacia(dao, "Otharan", "Belgrano 1964, San Fernando", "1171886579");
        farmacia(dao, "Pacheco del Pueblo", "Necochea 1202, San Fernando", "1128969223");
        farmacia(dao, "Pacheco San Fernando", "Constitución 201, San Fernando", "1141625485");
        farmacia(dao, "Panamá", "Avellaneda 4810, Virreyes, San Fernando", "4714-3770");
        farmacia(dao, "Pelman", "Perón 3302, Victoria, San Fernando", "4744-2852");
        farmacia(dao, "San Far 202", "H. Yrigoyen 3636, San Fernando", "4714-6430");
        farmacia(dao, "Santamarina", "Santamarina 1394, Victoria, San Fernando", "4549-0451");
        farmacia(dao, "Savini (Alvear)", "Alvear 2800, San Fernando", "4725-1203");
        farmacia(dao, "Savini (Sobremonte)", "Sobremonte 299, San Fernando", "4745-1012");
        farmacia(dao, "Traynor (Scuseria)", "Constitución 1000, San Fernando", "4744-6818");
        farmacia(dao, "Virrey Sobremonte", "Avellaneda 1899, Virreyes, San Fernando", "4745-2022");

        // EJEMPLOS ficticios
        dao.insertarServicios(crearServicio(TipoServicio.PEDIATRIA, "Dra. Ferreyra", "Belgrano 120", "11 4455-5678", "Lun a Vie 9 a 17 hs"));
        dao.insertarServicios(crearServicio(TipoServicio.GUARDERIA, "Jardín Arco Iris", "Mitre 880", "11 4455-9012", "7 a 18 hs"));
        dao.insertarServicios(crearServicio(TipoServicio.PANALERA, "Pañalera Bebé Feliz", "Sarmiento 310", "11 4455-3456", "9 a 20 hs"));
    }

    private void farmacia(ServicioDAO dao, String nombre, String direccion, String telefono) {
        dao.insertarServicios(crearServicio(TipoServicio.FARMACIA, nombre, direccion, telefono, "No informado"));
    }

    private Servicio crearServicio(TipoServicio tipo, String nombre, String ubicacion, String telefono, String horario) {
        Servicio servicio = new Servicio();
        servicio.setTipo(tipo);
        servicio.setNombre(nombre);
        servicio.setUbicacion(ubicacion);
        servicio.setTelefono(telefono);
        servicio.setHorario(horario);
        return servicio;
    }
    private void actualizarLista() {
        TipoServicio tipo = tipoSeleccionado(spinner.getSelectedItemPosition());
        String texto = etBuscador.getText().toString();

        adapter.clear();
        adapter.addAll(dao.buscar(tipo, texto));
    }

    private TipoServicio tipoSeleccionado(int posicion) {
        switch (posicion) {
            case 1: return TipoServicio.FARMACIA;
            case 2: return TipoServicio.PEDIATRIA;
            case 3: return TipoServicio.GUARDERIA;
            case 4: return TipoServicio.PANALERA;
            default: return null;   // 0 = "Todos"
        }
    }
}
