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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;

import dao.BebeDAO;
import dao.RecordatorioDAO;

public class FormularioControlMedicoFragment extends DialogFragment {

    private TextView txtBebeControl;
    private TextInputEditText edtFechaControl;
    private TextInputEditText edtDescripcionControl;
    private MaterialButton btnGuardarControl;
    private MaterialButton btnCancelarControl;
    private LocalDate fechaControlSeleccionada;
    private Bebe bebe;

    public FormularioControlMedicoFragment()
    {

    }

    public void setBebe(Bebe bebe)
    {
        this.bebe = bebe;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_formulario_control_medico, container, false);

        txtBebeControl = view.findViewById(R.id.txt_bebe_control);
        edtFechaControl = view.findViewById(R.id.edt_fecha_control);
        edtFechaControl.setOnClickListener(v -> mostrarCalendarioControl());

        edtDescripcionControl = view.findViewById(R.id.edt_descripcion_control);
        btnGuardarControl = view.findViewById(R.id.btn_guardar_control);

        btnCancelarControl = view.findViewById(R.id.btn_cancelar_control);

        txtBebeControl.setText("Bebé: " + bebe.getNombre());

        btnCancelarControl.setOnClickListener(v -> dismiss());


        return view;
    }



    private void mostrarCalendarioControl ()
    {
        LocalDate fechaActual = LocalDate.now();

        DatePickerDialog calendario = new DatePickerDialog(requireContext(),
                (DatePicker view, int year, int month, int day) ->
                {
                    fechaControlSeleccionada = LocalDate.of(year, month + 1, day);
                    String fechaTexto = day + "/" + (month + 1) + "/" + year;
                    edtFechaControl.setText(fechaTexto);
                },
                fechaActual.getYear(),
                fechaActual.getMonthValue() -1,
                fechaActual.getDayOfMonth()
        );

        calendario.show();
    }


}