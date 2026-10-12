package frgp.utn.edu.app_padresprimerizos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import dao.GuardiaDAO;

public class FormularioGuardiaFragment extends DialogFragment {
    private static final String[] ETIQUETAS = {"Médica", "Farmacéutica"};
    private static final TipoGuardia[] TIPOS = {TipoGuardia.MEDICA, TipoGuardia.FARMACEUTICA};

    private Spinner spinnerTipo;
    private EditText etNombre, etUbicacion, etTelefono, etHorario, etFecha;
    private Guardia guardiaEditar;
    private LocalDate fechaInicial;
    private Runnable onGuardar;
    private GuardiaDAO guardiaDAO;

    // fechaInicial: la fecha que se propone al crear una guardia nueva
    public FormularioGuardiaFragment(Guardia guardia, LocalDate fechaInicial, Runnable onGuardar) {
        this.guardiaEditar = guardia;
        this.fechaInicial = fechaInicial;
        this.onGuardar = onGuardar;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_formulario_guardia, container, false);

        TextView tvTitulo = view.findViewById(R.id.tvTituloFormGuardia);
        spinnerTipo = view.findViewById(R.id.spinnerTipoFormGuardia);
        etNombre = view.findViewById(R.id.etNombreGuardia);
        etUbicacion = view.findViewById(R.id.etUbicacionGuardia);
        etTelefono = view.findViewById(R.id.etTelefonoGuardia);
        etHorario = view.findViewById(R.id.etHorarioGuardia);
        etFecha = view.findViewById(R.id.etFechaGuardia);
        Button btnGuardar = view.findViewById(R.id.btnGuardarGuardia);
        Button btnCancelar = view.findViewById(R.id.btnCancelarGuardia);

        guardiaDAO = new GuardiaDAO(requireContext());

        ArrayAdapter<String> adapterTipo = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_dropdown_item, ETIQUETAS);
        spinnerTipo.setAdapter(adapterTipo);

        if (guardiaEditar != null) {
            tvTitulo.setText("Editar guardia");
            etNombre.setText(guardiaEditar.getNombre());
            etUbicacion.setText(guardiaEditar.getUbicacion());
            etTelefono.setText(guardiaEditar.getTelefono());
            etHorario.setText(guardiaEditar.getHorario());
            etFecha.setText(guardiaEditar.getFechaGuardia().toString());

            for (int i = 0; i < TIPOS.length; i++) {
                if (TIPOS[i] == guardiaEditar.getTipo()) {
                    spinnerTipo.setSelection(i);
                    break;
                }
            }
        } else {
            tvTitulo.setText("Nueva guardia");
            etFecha.setText(fechaInicial.toString());
        }

        btnGuardar.setOnClickListener(v -> guardarGuardia());
        btnCancelar.setOnClickListener(v -> dismiss());

        return view;
    }

    private void guardarGuardia() {
        String nombre = etNombre.getText().toString().trim();
        String ubicacion = etUbicacion.getText().toString().trim();
        String telefono = etTelefono.getText().toString().trim();
        String horario = etHorario.getText().toString().trim();
        String textoFecha = etFecha.getText().toString().trim();
        TipoGuardia tipo = TIPOS[spinnerTipo.getSelectedItemPosition()];

        if (nombre.isEmpty() || ubicacion.isEmpty() || telefono.isEmpty()
                || horario.isEmpty() || textoFecha.isEmpty()) {
            Toast.makeText(getContext(), "Completá todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        LocalDate fecha;
        try {
            fecha = LocalDate.parse(textoFecha);
        } catch (DateTimeParseException e) {
            etFecha.setError("Usá el formato aaaa-mm-dd");
            return;
        }

        if (guardiaEditar != null) {
            guardiaEditar.setTipo(tipo);
            guardiaEditar.setNombre(nombre);
            guardiaEditar.setUbicacion(ubicacion);
            guardiaEditar.setTelefono(telefono);
            guardiaEditar.setHorario(horario);
            guardiaEditar.setFechaGuardia(fecha);

            int filas = guardiaDAO.modificar(guardiaEditar);

            if (filas == 0) {
                Toast.makeText(getContext(), "La guardia ya no existe", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Guardia actualizada", Toast.LENGTH_SHORT).show();
            }
        } else {
            Guardia nueva = new Guardia();
            nueva.setTipo(tipo);
            nueva.setNombre(nombre);
            nueva.setUbicacion(ubicacion);
            nueva.setTelefono(telefono);
            nueva.setHorario(horario);
            nueva.setFechaGuardia(fecha);

            guardiaDAO.insertar(nueva);
            Toast.makeText(getContext(), "Guardia agregada", Toast.LENGTH_SHORT).show();
        }

        onGuardar.run();
        dismiss();
    }
}
