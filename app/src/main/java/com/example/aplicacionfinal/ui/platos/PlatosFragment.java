package com.example.aplicacionfinal.ui.platos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacionfinal.Plato;
import com.example.aplicacionfinal.PlatoAdapter;
import com.example.aplicacionfinal.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class PlatosFragment extends Fragment {


    private static final String TAG = "DocSnippets";
    Button btnMostrarDatos;
    RecyclerView recyclerViewPlatos;
    PlatoAdapter mAdapter;
    FirebaseFirestore mFirestore;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_platos, container, false);


        mFirestore = FirebaseFirestore.getInstance();
        recyclerViewPlatos = root.findViewById(R.id.recyclerPlatosFragment);
        recyclerViewPlatos.setLayoutManager(new LinearLayoutManager(getActivity()));


        Query query = mFirestore.collection("Platos");

        FirestoreRecyclerOptions<Plato> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Plato>().setQuery(query, Plato.class).build();

        mAdapter = new PlatoAdapter(getActivity(),firestoreRecyclerOptions);
        mAdapter.notifyDataSetChanged();
        recyclerViewPlatos.setAdapter(mAdapter);



        return root;
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