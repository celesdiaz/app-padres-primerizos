package frgp.utn.edu.app_padresprimerizos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import adapter.ListViewDirectorioAdapter;

public class DirectorioFragment extends Fragment {

    private ArrayList<Servicio> lista = new ArrayList<>();
    private ListView listView;

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

        cargarServiciosDePrueba();

        ListViewDirectorioAdapter adapter = new ListViewDirectorioAdapter(requireContext(), lista);
        listView.setAdapter(adapter);

        return vista;
    }

    private void cargarServiciosDePrueba() {
        lista.add(crearServicio(1, TipoServicio.FARMACIA, "Farmacia San Martín", "San Martín 450", "11 4455-1234", "8 a 22 hs"));
        lista.add(crearServicio(2, TipoServicio.PEDIATRIA, "Dra. Ferreyra", "Belgrano 120", "11 4455-5678", "Lun a Vie 9 a 17 hs"));
        lista.add(crearServicio(3, TipoServicio.GUARDERIA, "Jardín Arco Iris", "Mitre 880", "11 4455-9012", "7 a 18 hs"));
        lista.add(crearServicio(4, TipoServicio.PANALERA, "Pañalera Bebé Feliz", "Sarmiento 310", "11 4455-3456", "9 a 20 hs"));
    }

    private Servicio crearServicio(int id, TipoServicio tipo, String nombre, String ubicacion, String telefono, String horario) {
        Servicio servicio = new Servicio();
        servicio.setIdServicio(id);
        servicio.setTipo(tipo);
        servicio.setNombre(nombre);
        servicio.setUbicacion(ubicacion);
        servicio.setTelefono(telefono);
        servicio.setHorario(horario);
        return servicio;
    }
}
