package adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

import frgp.utn.edu.app_padresprimerizos.Bebe;
import frgp.utn.edu.app_padresprimerizos.R;

public class BebeAdapter extends RecyclerView.Adapter<BebeAdapter.BebeViewHolder> {
    private ArrayList<Bebe> bebes;

    public BebeAdapter(ArrayList<Bebe> bebes) {
        this.bebes = bebes;
    }

    @Override
    public BebeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bebe, parent, false);
        return new BebeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BebeViewHolder holder, int position) {
        Bebe bebe = bebes.get(position);

        holder.txtNombreBebe.setText(bebe.getNombre());

        LocalDate fechaNacimiento = bebe.getFechaNacimiento();

        String fechaTexto =
                fechaNacimiento.getDayOfMonth() + "/" +
                        fechaNacimiento.getMonthValue() + "/" +
                        fechaNacimiento.getYear();

        holder.txtFechaBebe.setText(fechaTexto);

        Period edad = Period.between(fechaNacimiento, LocalDate.now());

        String edadTexto;

        if(edad.getYears() == 0)
        {
            edadTexto = edad.getMonths() + " meses";
        }
        else
        {
            edadTexto = edad.getYears() + " años y " +
                    edad.getMonths() + " meses";
        }

        holder.txtEdadBebe.setText(edadTexto);
    }

    @Override
    public int getItemCount() {
        return bebes.size();
    }

    public static class BebeViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombreBebe, txtEdadBebe, txtFechaBebe;

        public BebeViewHolder(View itemView) {
            super(itemView);
            txtNombreBebe = itemView.findViewById(R.id.txt_nombre_bebe);
            txtEdadBebe = itemView.findViewById(R.id.txt_edad_bebe);
            txtFechaBebe = itemView.findViewById(R.id.txt_fecha_bebe);

        }
    }
}