package frgp.utn.edu.app_padresprimerizos;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PlaceholderFragment extends Fragment {

    private static final String ARG_TITULO = "titulo";

    public static PlaceholderFragment nuevo(String titulo) {
        PlaceholderFragment f = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITULO, titulo);
        f.setArguments(args);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        TextView tv = new TextView(requireContext());
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(20);
        String titulo = getArguments() != null ? getArguments().getString(ARG_TITULO) : "";
        tv.setText(titulo + "\n(pendiente)");
        return tv;
    }
}