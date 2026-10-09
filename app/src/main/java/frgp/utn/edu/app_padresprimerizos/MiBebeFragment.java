package frgp.utn.edu.app_padresprimerizos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;


public class MiBebeFragment extends Fragment {

    private MaterialButton btnAgregarBebe;

    public MiBebeFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_mi_bebe, container, false);

        btnAgregarBebe = view.findViewById(R.id.btn_agregar_bebe);

        btnAgregarBebe.setOnClickListener(v ->
        {
            FormularioBebeFragment formulario = new FormularioBebeFragment();

            formulario.show(
                    getParentFragmentManager(),
                    "FormularioBebe"
            );
        });

        return view;
    }
}