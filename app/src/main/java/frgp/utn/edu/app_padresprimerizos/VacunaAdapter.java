package frgp.utn.edu.app_padresprimerizos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class VacunaAdapter extends RecyclerView.Adapter<VacunaAdapter.VacunaViewHolder> {

    private ArrayList<Vacuna> vacunas;
    private Context context;
    private OnVacunaListener listener;

    public interface OnVacunaListener {
        void onEliminar(Vacuna vacuna);
        void onEditar(Vacuna vacuna);
    }

    public VacunaAdapter(ArrayList<Vacuna> vacunas, Context context, OnVacunaListener listener) {
        this.vacunas = vacunas;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public VacunaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_vacuna, parent, false);
        return new VacunaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VacunaViewHolder holder, int position) {
        Vacuna vacuna = vacunas.get(position);

        holder.tvNombreVacuna.setText(vacuna.getNombre());
        holder.tvEdadAplicacion.setText("Edad de aplicación: " + vacuna.getEdadAplicacion() + " meses");

        holder.btnEditarVacuna.setOnClickListener(v -> listener.onEditar(vacuna));
        holder.btnEliminarVacuna.setOnClickListener(v -> listener.onEliminar(vacuna));
    }

    @Override
    public int getItemCount() {
        return vacunas.size();
    }

    public static class VacunaViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreVacuna, tvEdadAplicacion;
        MaterialButton btnEditarVacuna, btnEliminarVacuna;

        public VacunaViewHolder(View itemView) {
            super(itemView);
            tvNombreVacuna = itemView.findViewById(R.id.tv_nombre_vacuna);
            tvEdadAplicacion = itemView.findViewById(R.id.tv_edad_aplicacion);
            btnEditarVacuna = itemView.findViewById(R.id.btn_editar_vacuna);
            btnEliminarVacuna = itemView.findViewById(R.id.btn_eliminar_vacuna);
        }
    }
}