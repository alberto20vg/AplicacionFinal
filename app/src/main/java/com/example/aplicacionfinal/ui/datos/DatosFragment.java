package com.example.aplicacionfinal.ui.datos;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.aplicacionfinal.MainActivity;
import com.example.aplicacionfinal.R;
import com.example.aplicacionfinal.anadirIngredientesActivity;
import com.example.aplicacionfinal.anadirPlatosActivity;

public class DatosFragment extends Fragment {


    ImageButton btnPlato, btnIngrediente;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_datos, container, false);
        btnPlato = root.findViewById(R.id.btnPlatos);
        btnIngrediente = root.findViewById(R.id.btnIngredientes);

        btnPlato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), anadirPlatosActivity.class);
                startActivity(intent);
            }
        });

        btnIngrediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), anadirIngredientesActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
}