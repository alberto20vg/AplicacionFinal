package com.example.aplicacionfinal;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class VerIngredienteAdapter extends RecyclerView.Adapter<VerIngredienteAdapter.ViewHolderCarrito> {

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
    public ViewHolderCarrito onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_ingredientes_ver, parent, false);
        return new ViewHolderCarrito(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCarrito holder, int position) {

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


    public class ViewHolderCarrito extends RecyclerView.ViewHolder {

        ImageView imagen;
        TextView nombre;

        public ViewHolderCarrito(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.idNombreIngrediente2);
            imagen = itemView.findViewById(R.id.idImagenIngrediente2);

        }
    }


}