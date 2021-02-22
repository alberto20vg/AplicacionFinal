package com.example.aplicacionfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
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
import java.util.HashMap;
import java.util.Map;

public class EditarDetallesActivity extends AppCompatActivity {

    private static final String TAG = "";
    EditText nombreTexto, fotoTexto, descripcionTexto;
    Button btnGuardar, btnCancelar;
    Activity at = this;
    Plato plato;
    static EditarIngredienteAdapter mAdapter;
    static DocumentReference docRef;
    static ArrayList<DocumentReference> listaIngredientes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_detalles);


        nombreTexto = findViewById(R.id.editTextNombre);
        fotoTexto = findViewById(R.id.editTextUrl);
        descripcionTexto = findViewById(R.id.editTextDescripcion);
        btnCancelar = findViewById(R.id.btnCancelarEdit);
        btnGuardar = findViewById(R.id.btnGuardarEdit);

        Bundle bundle = getIntent().getExtras();
        String valorRecibido = getIntent().getStringExtra("EditarPlato");

        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();


        docRef = mFirestore.collection("Platos").document(valorRecibido);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        plato = document.toObject(Plato.class);

                        listaIngredientes = plato.getLista();

                        nombreTexto.setText(document.getString("nombre"));
                        fotoTexto.setText(document.getString("img"));
                        descripcionTexto.setText(document.getString("descripcion"));

                        actualizar();

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        RecyclerView recyclerViewIngredientes = findViewById(R.id.recyclerViewEditar);
        recyclerViewIngredientes.setLayoutManager(new LinearLayoutManager(this));

        Query query = mFirestore.collection("Ingredientes");

        FirestoreRecyclerOptions<Ingredientes> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Ingredientes>().setQuery(query, Ingredientes.class).build();

        mAdapter = new EditarIngredienteAdapter(this, firestoreRecyclerOptions);
        mAdapter.notifyDataSetChanged();
        recyclerViewIngredientes.setAdapter(mAdapter);


        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Object> data = new HashMap<>();
                data.put("nombre", nombreTexto.getText().toString());
                data.put("img", fotoTexto.getText().toString());
                data.put("descripcion", descripcionTexto.getText().toString());
                data.put("lista", listaIngredientes);

                docRef
                        .set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
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


                Intent intent = new Intent(EditarDetallesActivity.this, MainActivity.class);
                startActivity(intent);


            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditarDetallesActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }

    public static void añadirIngrediente(DocumentReference e) {
        if (listaIngredientes.contains(e)) {
            listaIngredientes.remove(e);

        } else {
            listaIngredientes.add(e);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }

    public void actualizar() {
        mAdapter.notifyDataSetChanged();
    }
}