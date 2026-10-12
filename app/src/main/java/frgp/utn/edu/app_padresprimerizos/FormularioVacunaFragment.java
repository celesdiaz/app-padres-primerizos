package frgp.utn.edu.app_padresprimerizos;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import dao.VacunaDAO;

public class FormularioVacunaFragment extends DialogFragment {

    private TextInputEditText etNombreVacuna, etEdadAplicacion;
    private MaterialButton btnGuardar, btnCancelar;
    private Vacuna vacunaEditar;
    private Runnable onGuardar;
    private VacunaDAO vacunaDAO;

    public FormularioVacunaFragment(Vacuna vacuna, Runnable onGuardar) {
        this.vacunaEditar = vacuna;
        this.onGuardar = onGuardar;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_formulario_vacuna, container, false);

        etNombreVacuna = view.findViewById(R.id.et_nombre_vacuna);
        etEdadAplicacion = view.findViewById(R.id.et_edad_aplicacion);
        btnGuardar = view.findViewById(R.id.btn_guardar_vacuna);
        btnCancelar = view.findViewById(R.id.btn_cancelar_vacuna);

        vacunaDAO = new VacunaDAO(requireContext());

        if (vacunaEditar != null) {
            etNombreVacuna.setText(vacunaEditar.getNombre());
            etEdadAplicacion.setText(String.valueOf(vacunaEditar.getEdadAplicacion()));
        }

        btnGuardar.setOnClickListener(v -> guardarVacuna());
        btnCancelar.setOnClickListener(v -> dismiss());

        return view;
    }

    private void guardarVacuna() {
        String nombre = etNombreVacuna.getText().toString().trim();
        String edadTexto = etEdadAplicacion.getText().toString().trim();

        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(edadTexto)) {
            Toast.makeText(getContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        int edadAplicacion;
        try {
            edadAplicacion = Integer.parseInt(edadTexto);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "La edad debe ser un número válido", Toast.LENGTH_SHORT).show();
            return;
        }

        if (vacunaEditar != null) {
            vacunaEditar.setNombre(nombre);
            vacunaEditar.setEdadAplicacion(edadAplicacion);
            vacunaDAO.modificarVacuna(vacunaEditar);
            Toast.makeText(getContext(), "Vacuna actualizada", Toast.LENGTH_SHORT).show();
        } else {
            Vacuna nuevaVacuna = new Vacuna();
            nuevaVacuna.setNombre(nombre);
            nuevaVacuna.setEdadAplicacion(edadAplicacion);
            vacunaDAO.insertarVacuna(nuevaVacuna);
            Toast.makeText(getContext(), "Vacuna agregada", Toast.LENGTH_SHORT).show();
        }

        onGuardar.run();
        dismiss();
    }
}