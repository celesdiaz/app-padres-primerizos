package frgp.utn.edu.app_padresprimerizos;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;

import dao.BebeDAO;

public class FormularioBebeFragment extends DialogFragment {

    private TextInputEditText edtNombreBebe;
    private TextInputEditText edtFechaNacimiento;
    private MaterialButton btnGuardarBebe;
    private MaterialButton btnCancelarBebe;
    private LocalDate FechaNacimientoSeleccionada;
    private OnBebeGuardadoListener listener;

    public interface OnBebeGuardadoListener
    {
        void onBebeGuardado();
    }

    public void setOnBebeGuardadoListener(OnBebeGuardadoListener listener)
    {
        this.listener = listener;
    }

    public FormularioBebeFragment()
    {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_formulario_bebe, container, false);

        edtNombreBebe = view.findViewById(R.id.edt_nombre_bebe);
        edtFechaNacimiento = view.findViewById(R.id.edt_fecha_nacimiento);

        edtFechaNacimiento.setOnClickListener(v -> mostrarCalendarioBebe());

        btnGuardarBebe = view.findViewById(R.id.btn_guardar_bebe);

        btnGuardarBebe.setOnClickListener(v -> guardarBebe());

        btnCancelarBebe = view.findViewById(R.id.btn_cancelar_bebe);

        btnCancelarBebe.setOnClickListener(v -> dismiss());

        return view;
    }

    private void mostrarCalendarioBebe ()
    {
        LocalDate fechaActual = LocalDate.now();

        DatePickerDialog calendario = new DatePickerDialog(requireContext(),
                (DatePicker view, int year, int month, int day) ->
                {
                    FechaNacimientoSeleccionada = LocalDate.of(year, month + 1, day);
                    String fechaTexto = day + "/" + (month + 1) + "/" + year;
                    edtFechaNacimiento.setText(fechaTexto);
                },
                fechaActual.getYear(),
                fechaActual.getMonthValue() -1,
                fechaActual.getDayOfMonth()
        );
        calendario.getDatePicker().setMaxDate(System.currentTimeMillis());

        calendario.show();
    }


    private void guardarBebe()
    {
        String nombre = edtNombreBebe.getText().toString().trim();

        if(nombre.isEmpty())
        {
            edtNombreBebe.setError("Ingrese el nombre del bebé");
            return;
        }
        if(FechaNacimientoSeleccionada == null)
        {
            edtFechaNacimiento.setError("Seleccione la fecha de nacimiento");
            return;
        }

        SharedPreferences preferences = requireContext().getSharedPreferences("sesion", Context.MODE_PRIVATE);
        int idUsuario = preferences.getInt("id_usuario", -1);

        if(idUsuario == -1)
        {
            Toast.makeText(requireContext(), "No se encontro la sesion", Toast.LENGTH_SHORT).show();
            return;
        }

        Bebe bebe = new Bebe();

        bebe.setIdUsuario(idUsuario);
        bebe.setNombre(nombre);
        bebe.setFechaNacimiento(FechaNacimientoSeleccionada);

        BebeDAO bebeDAO = new BebeDAO(requireContext());
        long resultado = bebeDAO.insertarBebe(bebe);

        if(resultado != -1)
        {
            Toast.makeText(requireContext(), "El bebe fue registrado correctamente", Toast.LENGTH_SHORT).show();
            if(listener != null)
            {
                listener.onBebeGuardado();
            }
            dismiss();
        }
        else
        {
            Toast.makeText(requireContext(), "Error al registrar al bebe", Toast.LENGTH_SHORT).show();
        }

    }

}