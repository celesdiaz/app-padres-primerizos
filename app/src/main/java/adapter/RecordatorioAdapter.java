package adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.time.LocalDate;
import java.util.ArrayList;

import frgp.utn.edu.app_padresprimerizos.EstadoRecordatorio;
import frgp.utn.edu.app_padresprimerizos.R;
import frgp.utn.edu.app_padresprimerizos.Recordatorio;
import frgp.utn.edu.app_padresprimerizos.TipoRecordatorio;

public class RecordatorioAdapter extends RecyclerView.Adapter<RecordatorioAdapter.RecordatorioViewHolder> {
    private ArrayList<Recordatorio> recordatoriosLista;
    private RecordatorioAdapter.OnCumplirRecordatorioListener listenerRecordatorio;

    public interface OnCumplirRecordatorioListener
    {
        void onCumplirRecordatorio(Recordatorio recordatorio);
    }

    public void SetOnCumplirRecordatorioListener(OnCumplirRecordatorioListener listenerRecordatorio)
    {
        this.listenerRecordatorio = listenerRecordatorio;
    }

    public RecordatorioAdapter(ArrayList<Recordatorio> recordatoriosLista){this.recordatoriosLista = recordatoriosLista;}
    @Override
    public RecordatorioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recordatorio, parent, false);
        return new RecordatorioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecordatorioAdapter.RecordatorioViewHolder holder, int position) {
        Recordatorio recordatorio = recordatoriosLista.get(position);

        if(recordatorio.getTipo() == TipoRecordatorio.VACUNA)
        {
            holder.txtTipoRecordatorio.setText("VACUNA");
        }
        else
        {
            holder.txtTipoRecordatorio.setText("CONTROL MÉDICO");
        }

        holder.txtDescripcionRecordatorio.setText(recordatorio.getDescripcion());

        LocalDate fecha = recordatorio.getFecha();

        String fechaTexto = fecha.getDayOfMonth() + "/" +
                fecha.getMonthValue() + "/" +
                fecha.getYear();

        holder.txtFechaRecordatorio.setText(fechaTexto);

        holder.txtEstadoRecordatorio.setText(recordatorio.getEstado().name());

        if(recordatorio.getEstado() == EstadoRecordatorio.CUMPLIDO)
        {
            holder.btnCumplirRecordatorio.setVisibility(View.GONE);
        }
        else
        {
            holder.btnCumplirRecordatorio.setVisibility(View.VISIBLE);
        }

        holder.btnCumplirRecordatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listenerRecordatorio != null)
                {
                    listenerRecordatorio.onCumplirRecordatorio(recordatorio);
                }
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return recordatoriosLista.size();
    }

    public static class RecordatorioViewHolder extends RecyclerView.ViewHolder{
        TextView txtTipoRecordatorio, txtDescripcionRecordatorio, txtFechaRecordatorio, txtEstadoRecordatorio;
        MaterialButton btnCumplirRecordatorio;

        public RecordatorioViewHolder(View itemView)
        {
            super(itemView);
            txtTipoRecordatorio = itemView.findViewById(R.id.txt_tipo_recordatorio);

            txtDescripcionRecordatorio = itemView.findViewById(R.id.txt_descripcion_recordatorio);

            txtFechaRecordatorio = itemView.findViewById(R.id.txt_fecha_recordatorio);

            txtEstadoRecordatorio = itemView.findViewById(R.id.txt_estado_recordatorio);

            btnCumplirRecordatorio = itemView.findViewById(R.id.btn_cumplido_recordatorio);
        }


    }

}