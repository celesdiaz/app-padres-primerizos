package adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import frgp.utn.edu.app_padresprimerizos.R;
import frgp.utn.edu.app_padresprimerizos.Guardia;

public class ListViewGuardiaAdapter extends ArrayAdapter<Guardia> {

    private final List<Guardia> items;

    public ListViewGuardiaAdapter(@NonNull Context context, @NonNull List<Guardia> items) {
        super(context, 0, items);
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Nullable
    @Override
    public Guardia getItem(int position) {
        return items.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        return createItemView(position, convertView, parent);
    }

    public View createItemView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View view = convertView;
        if (view == null){
            view = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_guardia, parent, false);
        }

        Guardia guardia = items.get(position);

        TextView tvNombre = view.findViewById(R.id.tvNombreGuardia);
        TextView tvTipo = view.findViewById(R.id.tvTipoGuardia);
        TextView tvUbicacion = view.findViewById(R.id.tvUbicacionGuardia);
        TextView tvHorario = view.findViewById(R.id.tvHorarioGuardia);
        Button btnVerUbicacion = view.findViewById(R.id.btnVerUbicacionGuardia);

        tvNombre.setText(guardia.getNombre());
        tvTipo.setText(guardia.getTipo().name());
        tvUbicacion.setText(guardia.getUbicacion());
        tvHorario.setText(guardia.getHorario());

        btnVerUbicacion.setOnClickListener(v -> {
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(guardia.getUbicacion()));
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(getContext().getPackageManager()) != null) {
                getContext().startActivity(mapIntent);
            } else {
                Toast.makeText(getContext(), "Google Maps no está instalado", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }


}
