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

    private void cargarServiciosDePrueba(ServicioDAO dao) {
        dao.insertarServicios(crearServicio(TipoServicio.FARMACIA, "Farmacia San Martín", "San Martín 450", "11 4455-1234", "8 a 22 hs"));
        dao.insertarServicios(crearServicio(TipoServicio.PEDIATRIA, "Dra. Ferreyra", "Belgrano 120", "11 4455-5678", "Lun a Vie 9 a 17 hs"));
        dao.insertarServicios(crearServicio(TipoServicio.GUARDERIA, "Jardín Arco Iris", "Mitre 880", "11 4455-9012", "7 a 18 hs"));
        dao.insertarServicios(crearServicio(TipoServicio.PANALERA, "Pañalera Bebé Feliz", "Sarmiento 310", "11 4455-3456", "9 a 20 hs"));
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
