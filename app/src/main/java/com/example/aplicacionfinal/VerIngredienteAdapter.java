package com.example.aplicacionfinal;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class VerIngredienteAdapter extends RecyclerView.Adapter<VerIngredienteAdapter.ViewHolder> {

    FirebaseFirestore mFirebaseFirestore;
    ArrayList<DocumentReference> lista;

    FirebaseAuth user;
    Context context;

    public VerIngredienteAdapter(Activity activity, ArrayList<DocumentReference> lista) {
        this.lista = lista;
        context = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_ingredientes_ver, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        user = FirebaseAuth.getInstance();
        mFirebaseFirestore = FirebaseFirestore.getInstance();

        lista.get(position).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Ingredientes m = documentSnapshot.toObject(Ingredientes.class);


                holder.nombre.setText(m.getNombre());

                Glide.with(context).load(m.getImg()).into(holder.imagen);


            }
        });


    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imagen;
        TextView nombre;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.idNombreIngrediente2);
            imagen = itemView.findViewById(R.id.idImagenIngrediente2);

        }
    }


}