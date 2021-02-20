package com.example.aplicacionfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class verDetallesPlatos extends AppCompatActivity {
    private static final String TAG = "";
    TextView texto;
    ImageView foto;
    Activity at = this;
    static VerIngredienteAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_detalles_platos);
        Bundle bundle = getIntent().getExtras();
        String valorRecibido = getIntent().getStringExtra("verPlato");

        texto = findViewById(R.id.textView2);
        foto = findViewById(R.id.imageView3);


        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

        DocumentReference docRef = mFirestore.collection("Platos").document(valorRecibido);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Glide.with(at).load(document.getString("img")).into(foto);
                        texto.setText(document.getString("nombre"));

                        Plato plato = document.toObject(Plato.class);

                        ArrayList<DocumentReference> lista = plato.getLista();

                        RecyclerView recyclerViewIngredientes = findViewById(R.id.recyclerVerIngredientes);
                        recyclerViewIngredientes.setLayoutManager(new LinearLayoutManager(getParent()));


                        mAdapter = new VerIngredienteAdapter(getParent(), lista);

                        mAdapter.notifyDataSetChanged();
                        recyclerViewIngredientes.setAdapter(mAdapter);



                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }

        });
    }

/*
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

 */

}