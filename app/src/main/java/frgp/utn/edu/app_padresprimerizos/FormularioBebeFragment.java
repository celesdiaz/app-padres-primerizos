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

    public FormularioBebeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_formulario_bebe, container, false);

        edtNombreBebe = view.findViewById(R.id.edt_nombre_bebe);
        edtFechaNacimiento = view.findViewById(R.id.edt_fecha_nacimiento);

        edtFechaNacimiento.setOnClickListener(v -> mostrarCalendarioBebe());

        btnGuardarBebe = view.findViewById(R.id.btn_guardar_bebe);

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

}