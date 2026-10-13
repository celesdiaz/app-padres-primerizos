package frgp.utn.edu.app_padresprimerizos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.time.format.DateTimeFormatter;

import dao.GuardiaDAO;

public class FichaGuardiaFragment extends Fragment {

    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public FichaGuardiaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_ficha_guardia, container, false);

        vista.findViewById(R.id.btnAtrasGuardia).setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().popBackStack());

        int id = requireArguments().getInt("idGuardia");

        Guardia guardia = new GuardiaDAO(requireContext()).obtenerPorId(id);

        if (guardia == null) {
            Toast.makeText(requireContext(), "La guardia ya no existe", Toast.LENGTH_SHORT).show();
            requireActivity().getSupportFragmentManager().popBackStack();

            return vista;
        }

        ((TextView) vista.findViewById(R.id.tvNombreFichaGuardia)).setText(guardia.getNombre());
        ((TextView) vista.findViewById(R.id.tvTipoFichaGuardia)).setText(guardia.getTipo().getEtiqueta());
        ((TextView) vista.findViewById(R.id.tvTelefonoFichaGuardia)).setText(guardia.getTelefono());
        ((TextView) vista.findViewById(R.id.tvUbicacionFichaGuardia)).setText(guardia.getUbicacion());
        ((TextView) vista.findViewById(R.id.tvFechaFichaGuardia))
                .setText("Fecha: " + guardia.getFechaGuardia().format(FORMATO_FECHA));
        ((TextView) vista.findViewById(R.id.tvHorarioFichaGuardia))
                .setText("Horario: " + guardia.getHorario());

        vista.findViewById(R.id.tvTelefonoFichaGuardia).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + guardia.getTelefono()));
            if (intent.resolveActivity(requireContext().getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(requireContext(), "No hay una app para llamar", Toast.LENGTH_SHORT).show();
            }
        });

        vista.findViewById(R.id.btnVerUbicacionFichaGuardia).setOnClickListener(v -> {
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(guardia.getUbicacion()));
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
