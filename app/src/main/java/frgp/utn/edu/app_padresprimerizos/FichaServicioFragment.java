package frgp.utn.edu.app_padresprimerizos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import dao.ServicioDAO;


public class FichaServicioFragment extends Fragment {


    public FichaServicioFragment() {
        // Required empty public constructor
    }


    public static FichaServicioFragment newInstance(String param1, String param2) {
        FichaServicioFragment fragment = new FichaServicioFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_ficha_servicio, container, false);

        vista.findViewById(R.id.btnAtras).setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().popBackStack());
        

        int id = requireArguments().getInt("idServicio");

        Servicio servicio = new ServicioDAO(requireContext()).obtenerPorId(id);

        if (servicio == null) {
            Toast.makeText(requireContext(), "El servicio ya no existe", Toast.LENGTH_SHORT).show();
            requireActivity().getSupportFragmentManager().popBackStack();

            return vista;
        }
        ((TextView) vista.findViewById(R.id.tvNombre)).setText(servicio.getNombre());
        ((TextView) vista.findViewById(R.id.tvTipo)).setText(servicio.getTipo().name());
        ((TextView) vista.findViewById(R.id.tvTelefono)).setText(servicio.getTelefono());
        ((TextView) vista.findViewById(R.id.tvUbicacion)).setText(servicio.getUbicacion());
        ((TextView) vista.findViewById(R.id.tvHorario)).setText(servicio.getHorario());

        vista.findViewById(R.id.tvTelefono).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + servicio.getTelefono()));
            if (intent.resolveActivity(requireContext().getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(requireContext(), "No hay una app para llamar", Toast.LENGTH_SHORT).show();
            }
        });

        vista.findViewById(R.id.btnVerUbicacion).setOnClickListener(v -> {
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(servicio.getUbicacion()));
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(requireContext().getPackageManager()) != null) {
                startActivity(mapIntent);
            } else {
                Toast.makeText(requireContext(), "Google Maps no está instalado", Toast.LENGTH_SHORT).show();
            }
        });

        return vista;
    }
}