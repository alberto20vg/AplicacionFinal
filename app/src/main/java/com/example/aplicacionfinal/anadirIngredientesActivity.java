package com.example.aplicacionfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicacionfinal.ui.datos.DatosFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class anadirIngredientesActivity extends AppCompatActivity {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView textNombre, textFoto;
    Button btnCancelar, btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_ingredientes);

        textNombre = findViewById(R.id.addIngredientesNombreId);
        textFoto = findViewById(R.id.addIngredientesFotoId);
        btnCancelar = findViewById(R.id.addIngredientesBtnCancelId);
        btnGuardar = findViewById(R.id.addIngredientesbtnGuardarId);


        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Object> data = new HashMap<>();
                data.put("nombre", textNombre.getText().toString());
                data.put("img", textFoto.getText().toString());

                db.collection("Ingredientes")
                        .add(data)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(anadirIngredientesActivity.this, "No funciona", Toast.LENGTH_SHORT).show();
                                //Todo hacer string para error
                            }
                        });
                Intent intent = new Intent(anadirIngredientesActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(anadirIngredientesActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}