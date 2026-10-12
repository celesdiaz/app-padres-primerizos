package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import frgp.utn.edu.app_padresprimerizos.Guardia;
import frgp.utn.edu.app_padresprimerizos.R;

public class AdminGuardiaAdapter extends ArrayAdapter<Guardia> {

    public interface AdminGuardiaListener {
        void onEditarClick(Guardia guardia);
        void onEliminarClick(Guardia guardia);
    }

    private AdminGuardiaListener listener;

    public AdminGuardiaAdapter(Context context, ArrayList<Guardia> lista,
                               AdminGuardiaListener listener) {
        super(context, 0, lista);
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    public View createItemView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_admin_guardia, parent, false);
        }

        Guardia guardia = getItem(position);

        TextView tvNombre = view.findViewById(R.id.tvNombreAdminGuardia);
        TextView tvTipo = view.findViewById(R.id.tvTipoAdminGuardia);
        ImageButton btnEditar = view.findViewById(R.id.btnEditarGuardia);
        ImageButton btnEliminar = view.findViewById(R.id.btnEliminarGuardia);

        tvNombre.setText(guardia.getNombre());
        tvTipo.setText(guardia.getTipo().name());

        btnEditar.setOnClickListener(v -> listener.onEditarClick(guardia));
        btnEliminar.setOnClickListener(v -> listener.onEliminarClick(guardia));

        return view;
    }
}