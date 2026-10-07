package frgp.utn.edu.app_padresprimerizos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import dao.ContactoDAO;

public class FormularioContactoFragment extends DialogFragment {
    private EditText etNombre, etTelefono;
    private Spinner spinnerRelacion;
    private CheckBox checkboxPrincipal;
    private Button btnGuardar, btnCancelar;
    private Contacto contactoEditar;
    private int idUsuario;
    private Runnable onGuardar;
    private ContactoDAO contactoDAO;

    public FormularioContactoFragment(Contacto contacto, int idUsuario, Runnable onGuardar) {
        this.contactoEditar = contacto;
        this.idUsuario = idUsuario;
        this.onGuardar = onGuardar;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_formulario_contacto, container, false);

        etNombre = view.findViewById(R.id.et_nombre);
        etTelefono = view.findViewById(R.id.et_telefono);
        spinnerRelacion = view.findViewById(R.id.spinner_relacion);
        checkboxPrincipal = view.findViewById(R.id.checkbox_principal);
        btnGuardar = view.findViewById(R.id.btn_guardar);
        btnCancelar = view.findViewById(R.id.btn_cancelar);

        contactoDAO = new ContactoDAO(requireContext());

        // Configurar spinner
        String[] tiposContacto = {"Pediatra", "Farmacia", "Hospital", "Clínica", "Otros"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, tiposContacto);
        spinnerRelacion.setAdapter(adapter);

        // Si es editar, cargar datos
        if (contactoEditar != null) {
            etNombre.setText(contactoEditar.getNombre());
            etTelefono.setText(contactoEditar.getTelefono());
            checkboxPrincipal.setChecked(contactoEditar.isPrincipal());

            // Seleccionar tipo de contacto en spinner
            for (int i = 0; i < tiposContacto.length; i++) {
                if (tiposContacto[i].equals(contactoEditar.getTipoContacto())) {
                    spinnerRelacion.setSelection(i);
                    break;
                }
            }
        }

        // Botón guardar
        btnGuardar.setOnClickListener(v -> guardarContacto());

        // Botón cancelar
        btnCancelar.setOnClickListener(v -> dismiss());

        return view;
    }

    private void guardarContacto() {
        String nombre = etNombre.getText().toString().trim();
        String telefono = etTelefono.getText().toString().trim();
        String tipoContacto = spinnerRelacion.getSelectedItem().toString();
        boolean esPrincipal = checkboxPrincipal.isChecked();

        // Validar
        if (nombre.isEmpty() || telefono.isEmpty()) {
            Toast.makeText(getContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (contactoEditar != null) {
            // Editar
            contactoEditar.setNombre(nombre);
            contactoEditar.setTelefono(telefono);
            contactoEditar.setTipoContacto(tipoContacto);
            contactoEditar.setPrincipal(esPrincipal);
            contactoDAO.modificarContacto(contactoEditar);
            Toast.makeText(getContext(), "Contacto actualizado", Toast.LENGTH_SHORT).show();
        } else {
            // Crear nuevo
            Contacto nuevoContacto = new Contacto();
            nuevoContacto.setNombre(nombre);
            nuevoContacto.setTelefono(telefono);
            nuevoContacto.setTipoContacto(tipoContacto);
            nuevoContacto.setPrincipal(esPrincipal);
            nuevoContacto.setIdUsuario(idUsuario);
            contactoDAO.insertarContacto(nuevoContacto, idUsuario);
            Toast.makeText(getContext(), "Contacto agregado", Toast.LENGTH_SHORT).show();
        }

        onGuardar.run(); // Recargar lista
        dismiss();
    }
}