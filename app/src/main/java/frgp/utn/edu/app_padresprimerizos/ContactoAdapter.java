package frgp.utn.edu.app_padresprimerizos;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactoAdapter extends RecyclerView.Adapter<ContactoAdapter.ContactoViewHolder> {
    private ArrayList<Contacto> contactos;
    private Context context;
    private OnContactoListener listener;

    public interface OnContactoListener {
        void onEliminar(Contacto contacto);
        void onEditar(Contacto contacto);
    }

    public ContactoAdapter(ArrayList<Contacto> contactos, Context context, OnContactoListener listener) {
        this.contactos = contactos;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ContactoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_contacto, parent, false);
        return new ContactoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactoViewHolder holder, int position) {
        Contacto contacto = contactos.get(position);

        holder.tvNombre.setText(contacto.getNombre());
        holder.tvRelacion.setText("Tipo: " + contacto.getTipoContacto());
        holder.tvTelefono.setText("Tel: " + contacto.getTelefono());

        if (contacto.isPrincipal()) {
            holder.tvPrincipal.setText("★ Principal");
            holder.tvPrincipal.setVisibility(View.VISIBLE);
        } else {
            holder.tvPrincipal.setVisibility(View.GONE);
        }

        // Botón llamar
        holder.btnLlamar.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + contacto.getTelefono()));
            context.startActivity(intent);
        });

        // Botón editar
        holder.btnEditar.setOnClickListener(v -> listener.onEditar(contacto));

        // Botón eliminar
        holder.btnEliminar.setOnClickListener(v -> listener.onEliminar(contacto));
    }

    @Override
    public int getItemCount() {
        return contactos.size();
    }

    public static class ContactoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvRelacion, tvTelefono, tvPrincipal;
        Button btnLlamar, btnEditar, btnEliminar;

        public ContactoViewHolder(View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tv_nombre_contacto);
            tvRelacion = itemView.findViewById(R.id.tv_relacion);
            tvTelefono = itemView.findViewById(R.id.tv_telefono);
            tvPrincipal = itemView.findViewById(R.id.tv_principal);
            btnLlamar = itemView.findViewById(R.id.btn_llamar);
            btnEditar = itemView.findViewById(R.id.btn_editar_contacto);
            btnEliminar = itemView.findViewById(R.id.btn_eliminar_contacto);
        }
    }
}