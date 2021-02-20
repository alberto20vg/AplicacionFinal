package com.example.aplicacionfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;


import java.lang.reflect.Array;
import java.util.ArrayList;

public class EditarDetallesActivity extends AppCompatActivity {

    private static final String TAG = "";
    EditText nombreTexto, fotoTexto;
    Button btnGuardar, btnCancelar;
    Activity at = this;
    Plato plato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_detalles);

        nombreTexto = findViewById(R.id.editTextNombre);
        fotoTexto = findViewById(R.id.editTextUrl);
        btnCancelar = findViewById(R.id.btnCancelarEdit);
        btnGuardar = findViewById(R.id.btnGuardarEdit);

        Bundle bundle = getIntent().getExtras();
        String valorRecibido = getIntent().getStringExtra("EditarPlato");

        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

        DocumentReference docRef = mFirestore.collection("Platos").document(valorRecibido);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        nombreTexto.setText(document.getString("nombre"));
                        fotoTexto.setText(document.getString("img"));

                        PlatoPureba prueba = document.toObject(PlatoPureba.class);

                        ArrayList<String> lista = prueba.getLista();

                        Toast.makeText(at, lista.get(0).toString(), Toast.LENGTH_SHORT).show();


                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        //poner un set en vez de un update y pasarle el objeto directamente
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                docRef
                        .update("nombre", nombreTexto.getText())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully updated!");
                                Toast.makeText(at, nombreTexto.getText(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error updating document", e);
                            }
                        });

            }
        });

        //estoy haciendo pruebas
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query = mFirestore.collection("Platos");

                Task<QuerySnapshot> prueba = query.whereArrayContains("nombre", "prueba1").get();

                prueba.toString();

                Toast.makeText(at, prueba.toString(), Toast.LENGTH_SHORT).show();

            }
        });


    }
}