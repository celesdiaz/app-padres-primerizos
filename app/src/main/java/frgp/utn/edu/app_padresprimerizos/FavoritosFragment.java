package frgp.utn.edu.app_padresprimerizos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import adapter.ListViewDirectorioAdapter;
import dao.FavoritoDAO;


public class FavoritosFragment extends Fragment {


    public FavoritosFragment() {
        // Required empty public constructor
    }


    public static FavoritosFragment newInstance(String param1, String param2) {
        FavoritosFragment fragment = new FavoritosFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_favoritos, container, false);
        ListView listView = vista.findViewById(R.id.listViewFavoritos);

        SharedPreferences prefs = requireContext().getSharedPreferences("sesion", Context.MODE_PRIVATE);
        int idUsuario = prefs.getInt("id_usuario", -1);

        FavoritoDAO favoritoDAO = new FavoritoDAO(requireContext());
        ArrayList<Servicio> lista = favoritoDAO.listarServicios(idUsuario);

        ListViewDirectorioAdapter adapter = new ListViewDirectorioAdapter(requireContext(), lista);
        listView.setAdapter(adapter);
        listView.setEmptyView(vista.findViewById(R.id.tvSinFavoritos));

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


        return vista;
    }
}