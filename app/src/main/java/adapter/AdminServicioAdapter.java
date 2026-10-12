package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import frgp.utn.edu.app_padresprimerizos.R;
import frgp.utn.edu.app_padresprimerizos.Servicio;

public class AdminServicioAdapter extends ArrayAdapter<Servicio> {

    public interface AdminServicioListener {
        void onEditarClick(Servicio servicio);
        void onEliminarClick(Servicio servicio);
    }

    private AdminServicioListener listener;

    public AdminServicioAdapter(Context context, ArrayList<Servicio> lista,
                                AdminServicioListener listener) {
        super(context, 0, lista);
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    public View createItemView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_admin_servicio, parent, false);
        }

        Servicio servicio = getItem(position);

        TextView tvNombre = view.findViewById(R.id.tvNombreAdminServicio);
        TextView tvTipo = view.findViewById(R.id.tvTipoAdminServicio);
        ImageButton btnEditar = view.findViewById(R.id.btnEditarServicio);
        ImageButton btnEliminar = view.findViewById(R.id.btnEliminarServicio);

        tvNombre.setText(servicio.getNombre());
        tvTipo.setText(servicio.getTipo().name());

        btnEditar.setOnClickListener(v -> listener.onEditarClick(servicio));
        btnEliminar.setOnClickListener(v -> listener.onEliminarClick(servicio));

        return view;
    }
}