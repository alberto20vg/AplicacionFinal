package com.example.aplicacionfinal.ui.cuenta;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.aplicacionfinal.LoginActivity;
import com.example.aplicacionfinal.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CuentaFragment extends Fragment {


    private FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_cuenta, container, false);

        Button cerrarSesion = root.findViewById(R.id.btnCerrarSesion);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mAuth = FirebaseAuth.getInstance();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                mGoogleSignInClient.revokeAccess();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        TextView usuario = root.findViewById(R.id.texto_nombre);
        TextView correo = root.findViewById(R.id.texto_email);
        ImageView imagen = root.findViewById(R.id.imageView);
        ImageView imagen2 = root.findViewById(R.id.imageView2);


        String name = "";
        Uri photoUrl = null;
        String email = user.getEmail();

        Glide.with(this)
                .load(R.drawable.cuenta)
                .into(imagen);

        if (user.getDisplayName()=="") {
            Glide.with(this)
                    .load(R.drawable.email)
                    .into(imagen2);
            name = getString(R.string.nonAssociatedName);
        }else{
            Glide.with(this)
                    .load(user.getPhotoUrl())
                    .into(imagen2);
            name = user.getDisplayName();
        }

        usuario.setText(name);
        correo.setText(email);

        return root;
    }
}