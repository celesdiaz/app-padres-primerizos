package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import frgp.utn.edu.app_padresprimerizos.R;
import frgp.utn.edu.app_padresprimerizos.Servicio;

public class ListViewDirectorioAdapter extends ArrayAdapter<Servicio> {

    private final List<Servicio> items;

    public ListViewDirectorioAdapter(@NonNull Context context, @NonNull List<Servicio> items) {
        super(context, 0, items);
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Nullable
    @Override
    public Servicio getItem(int position) {
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
                    .inflate(R.layout.item_servicio, parent, false);
        }

        Servicio servicio = items.get(position);

        TextView tvNombre = view.findViewById(R.id.nombreListView);
        TextView tvTipo = view.findViewById(R.id.tipoListView);
        TextView tvUbicacion = view.findViewById(R.id.ubicacionListView);
        TextView tvHorario = view.findViewById(R.id.horarioListView);

        tvNombre.setText(servicio.getNombre());
        tvTipo.setText(servicio.getTipo().name());
        tvUbicacion.setText(servicio.getUbicacion());
        tvHorario.setText(servicio.getHorario());

        return view;
    }


}
