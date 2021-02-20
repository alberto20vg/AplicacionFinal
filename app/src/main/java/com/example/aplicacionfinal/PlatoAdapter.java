package com.example.aplicacionfinal;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PlatoAdapter extends FirestoreRecyclerAdapter<Plato, PlatoAdapter.ViewHolder> {
    Context context;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */


    public PlatoAdapter(FragmentActivity activity, @NonNull FirestoreRecyclerOptions<Plato> options) {
        super(options);
        context = activity;

    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Plato plato) {
        holder.textViewNombre.setText(plato.getNombre());
        Glide.with(context).load(plato.getImg()).into(holder.imageViewImagen);


        holder.btnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentSnapshot paltoDocument =
                        getSnapshots().getSnapshot(holder.getAdapterPosition());
                final String id = paltoDocument.getId();
                Bundle idPlato = new Bundle();
                idPlato.putString("Id", id);

                Intent intent = new Intent(context, verDetallesPlatos.class);
                intent.putExtra("verPlato", id);
                context.startActivity(intent);

            }
        });

        holder.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentSnapshot lugarDocument =
                        getSnapshots().getSnapshot(holder.getAdapterPosition());
                final String id = lugarDocument.getId();
                Bundle idPlato = new Bundle();
                idPlato.putString("Id", id);

                Intent intent = new Intent(context, EditarDetallesActivity.class);
                intent.putExtra("EditarPlato", id);
                context.startActivity(intent);
            }
        });

        holder.btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setMessage("¿Esta seguro de borrar " + holder.textViewNombre.getText() + "?")
                        .setTitle("Borrar Plato");

                builder.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        FirebaseFirestore mFirebaseFireStore = FirebaseFirestore.getInstance();
                        DocumentSnapshot lugarDocument =
                                getSnapshots().getSnapshot(holder.getAdapterPosition());
                        final String id_borrar = lugarDocument.getId();
                        mFirebaseFireStore.collection("Platos").document(id_borrar)
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(context, "Plato Borrado", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("Borrado", "Error deleting document", e);
                                    }
                                });

                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                    }
                });

                AlertDialog dialog = builder.create();
                builder.show();

            }
        });


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_platos, viewGroup, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNombre;
        ImageView imageViewImagen;
        ImageButton btnBorrar,btnVer, btnEditar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombre = itemView.findViewById(R.id.idNombreIngrediente2);
            imageViewImagen = itemView.findViewById(R.id.idImagenIngrediente2);
            btnBorrar = itemView.findViewById(R.id.button23);
            btnVer = itemView.findViewById(R.id.button13);
            btnEditar = itemView.findViewById(R.id.button33);

        }
    }
}
