package frgp.utn.edu.app_padresprimerizos;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import dao.ServicioDAO;


public class FormularioServicioFragment extends DialogFragment {
    private static final String[] ETIQUETAS = {"Farmacia", "Pediatría", "Guardería", "Pañalera"};
    private static final TipoServicio[] TIPOS = {
            TipoServicio.FARMACIA, TipoServicio.PEDIATRIA, TipoServicio.GUARDERIA, TipoServicio.PANALERA
    };

    private Spinner spinnerTipo;
    private EditText etNombre, etUbicacion, etTelefono, etHorario;
    private Servicio servicioEditar;
    private Runnable onGuardar;
    private ServicioDAO servicioDAO;

    public FormularioServicioFragment(Servicio servicio, Runnable onGuardar) {
        this.servicioEditar = servicio;
        this.onGuardar = onGuardar;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_formulario_servicio, container, false);

        TextView tvTitulo = view.findViewById(R.id.tvTituloFormServicio);
        spinnerTipo = view.findViewById(R.id.spinnerTipoServicio);
        etNombre = view.findViewById(R.id.etNombreServicio);
        etUbicacion = view.findViewById(R.id.etUbicacionServicio);
        etTelefono = view.findViewById(R.id.etTelefonoServicio);
        etHorario = view.findViewById(R.id.etHorarioServicio);
        Button btnGuardar = view.findViewById(R.id.btnGuardarServicio);
        Button btnCancelar = view.findViewById(R.id.btnCancelarServicio);

        servicioDAO = new ServicioDAO(requireContext());

        ArrayAdapter<String> adapterTipo = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_dropdown_item, ETIQUETAS);
        spinnerTipo.setAdapter(adapterTipo);

        if (servicioEditar != null) {
            tvTitulo.setText("Editar servicio");
            etNombre.setText(servicioEditar.getNombre());
            etUbicacion.setText(servicioEditar.getUbicacion());
            etTelefono.setText(servicioEditar.getTelefono());
            etHorario.setText(servicioEditar.getHorario());

            for (int i = 0; i < TIPOS.length; i++) {
                if (TIPOS[i] == servicioEditar.getTipo()) {
                    spinnerTipo.setSelection(i);
                    break;
                }
            }
        } else {
            tvTitulo.setText("Nuevo servicio");
        }
        btnGuardar.setOnClickListener(v -> guardarServicio());
        btnCancelar.setOnClickListener(v -> dismiss());

        return view;
    }

    private void guardarServicio() {
        String nombre = etNombre.getText().toString().trim();
        String ubicacion = etUbicacion.getText().toString().trim();
        String telefono = etTelefono.getText().toString().trim();
        String horario = etHorario.getText().toString().trim();
        TipoServicio tipo = TIPOS[spinnerTipo.getSelectedItemPosition()];

        if (nombre.isEmpty() || ubicacion.isEmpty() || telefono.isEmpty() || horario.isEmpty()) {
            Toast.makeText(getContext(), "Completá todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (servicioEditar != null) {
            servicioEditar.setTipo(tipo);
            servicioEditar.setNombre(nombre);
            servicioEditar.setUbicacion(ubicacion);
            servicioEditar.setTelefono(telefono);
            servicioEditar.setHorario(horario);

            int filas = servicioDAO.modificarServicio(servicioEditar);

            if (filas == 0) {
                Toast.makeText(getContext(), "El servicio ya no existe", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Servicio actualizado", Toast.LENGTH_SHORT).show();
            }
        } else {
            Servicio nuevo = new Servicio();
            nuevo.setTipo(tipo);
            nuevo.setNombre(nombre);
            nuevo.setUbicacion(ubicacion);
            nuevo.setTelefono(telefono);
            nuevo.setHorario(horario);

            servicioDAO.insertarServicios(nuevo);
            Toast.makeText(getContext(), "Servicio agregado", Toast.LENGTH_SHORT).show();
        }

        onGuardar.run();
        dismiss();
    }
}