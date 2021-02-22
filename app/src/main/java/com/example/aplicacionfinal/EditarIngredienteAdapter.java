package com.example.aplicacionfinal;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditarIngredienteAdapter extends FirestoreRecyclerAdapter<Ingredientes, EditarIngredienteAdapter.ViewHolder> {
    Context context;
    FirebaseFirestore mFirestore;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public EditarIngredienteAdapter(Activity activity, FirestoreRecyclerOptions<Ingredientes> options) {
        super(options);
        context = activity;
    }


    @NonNull
    @Override
    public EditarIngredienteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mFirestore = FirebaseFirestore.getInstance();
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_ingredientes, viewGroup, false);
        return new EditarIngredienteAdapter.ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Ingredientes model) {
        DocumentSnapshot platoDocument =
                getSnapshots().getSnapshot(holder.getAdapterPosition());
        final String id = platoDocument.getId();
        DocumentReference docRef = mFirestore.collection("Ingredientes").document(id.trim());

        holder.textViewNombre.setText(model.getNombre());
        Glide.with(context).load(model.getImg()).into(holder.imageViewImagen);

        if (!EditarDetallesActivity.listaIngredientes.contains(docRef)) {
            holder.imageViewImagen.setAlpha((float) 0.4);
        }else{
            holder.imageViewImagen.setAlpha((float) 1);
        }
        holder.btnAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!EditarDetallesActivity.listaIngredientes.contains(docRef)) {
                    holder.imageViewImagen.setAlpha((float) 0.4);
                }else{
                    holder.imageViewImagen.setAlpha((float) 1);
                }
                EditarDetallesActivity.añadirIngrediente(docRef);

                if (!EditarDetallesActivity.listaIngredientes.contains(docRef)) {
                    holder.imageViewImagen.setAlpha((float) 0.4);
                }else{
                    holder.imageViewImagen.setAlpha((float) 1);
                }

            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNombre;
        ImageView imageViewImagen;
        CardView cardView;
        ImageButton btnAñadir;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombre = itemView.findViewById(R.id.idNombreIngrediente2);
            imageViewImagen = itemView.findViewById(R.id.idImagenIngrediente2);
            cardView = itemView.findViewById(R.id.cardViewIngrediente);
            btnAñadir = itemView.findViewById(R.id.button13);
        }
    }
}
