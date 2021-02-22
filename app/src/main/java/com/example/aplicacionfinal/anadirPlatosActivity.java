package com.example.aplicacionfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicacionfinal.ui.datos.DatosFragment;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class anadirPlatosActivity extends AppCompatActivity {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView textNombre, textFoto, textDescripcion;
    Button btnCancelar, btnGuardar;
    IngredienteAdapter mAdapter;
    static ArrayList<DocumentReference> listaIngredientes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_platos);
        textNombre = findViewById(R.id.addPlatoNombreId);
        textFoto = findViewById(R.id.addPlatoFotoId);
        textDescripcion = findViewById(R.id.addPlatoDescripcion);
        btnCancelar = findViewById(R.id.addPlatoBtnCancelId);
        btnGuardar = findViewById(R.id.addPlatobtnGuardarId);

        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        RecyclerView recyclerViewIngredientes = findViewById(R.id.recyclerView2);
        recyclerViewIngredientes.setLayoutManager(new LinearLayoutManager(this));


        Query query = mFirestore.collection("Ingredientes");

        FirestoreRecyclerOptions<Ingredientes> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Ingredientes>().setQuery(query, Ingredientes.class).build();

        mAdapter = new IngredienteAdapter(this, firestoreRecyclerOptions);
        mAdapter.notifyDataSetChanged();
        recyclerViewIngredientes.setAdapter(mAdapter);

        listaIngredientes = new ArrayList<>();
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Object> data = new HashMap<>();
                data.put("nombre", textNombre.getText().toString());
                data.put("img", textFoto.getText().toString());
                data.put("descripcion", textDescripcion.getText().toString());
                data.put("lista", listaIngredientes);
                db.collection("Platos")
                        .add(data)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(anadirPlatosActivity.this, "No funciona", Toast.LENGTH_SHORT).show();
                                //Todo hacer string para error
                            }
                        });
                Intent intent = new Intent(anadirPlatosActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(anadirPlatosActivity.this, MainActivity.class);
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
}