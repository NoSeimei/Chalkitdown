package com.example.chalkitdown;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class MdpOublie extends AppCompatActivity {

    private EditText textEmail;
    private Button buttonForgotPass;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mdp_oublie);

        textEmail = (EditText) findViewById(R.id.textEmail);
        buttonForgotPass = (Button) findViewById(R.id.buttonForgotPass);

        auth = FirebaseAuth.getInstance();

        buttonForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                resetPass();
            }
        });
    }
    private void resetPass(){
        String email = textEmail.getText().toString().trim();
        if (email.isEmpty()){
            textEmail.setError("l'Email est requis!");
            textEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            textEmail.setError("Veuillez entrer une adresse valide");
            textEmail.requestFocus();
            return;
        }
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MdpOublie.this, "Veuillez vérifier votre boîte de reception.", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(MdpOublie.this, "Échec de l'opération.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}