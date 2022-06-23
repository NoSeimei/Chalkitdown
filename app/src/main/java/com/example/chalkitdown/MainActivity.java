package com.example.chalkitdown;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    private Button loginButton;
    private TextView inputLogin;
    private TextView inputPassword;

    private TextView registerButton;
    private TextView forgottenButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = _Connexion.connect();

        loginButton = (Button) findViewById(R.id.buttonConnexion);
        loginButton.setOnClickListener(this);

        inputLogin = (TextView) findViewById(R.id.editLogin);
        inputPassword = (TextView) findViewById(R.id.editPassword);

        registerButton = (TextView) findViewById(R.id.textRegister);
        registerButton.setOnClickListener(this);

        forgottenButton = (TextView) findViewById(R.id.textForgotPassword);
        forgottenButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.textRegister:
                startActivity(new Intent(this, UsersRegister.class));
                break;
            case R.id.buttonConnexion:
                starLogin();
                break;
            case R.id.textForgotPassword:
                startActivity(new Intent(this, MdpOublie.class));
                break;

        }
    }

    public void starLogin(){

        String login = inputLogin.getText().toString().trim();
        String pswd = inputPassword.getText().toString().trim();

        if(login.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(login).matches()){
            inputLogin.setError("Email pas correct !");
            inputLogin.requestFocus();
            return;
        }
        else if(pswd.isEmpty())
        {
            inputLogin.setError("Champ mot de passe vide !");
            inputLogin.requestFocus();
            return;
        }
try{
        mAuth.signInWithEmailAndPassword(login, pswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this, ProfileUser.class));
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Echec de la connexion !", Toast.LENGTH_LONG).show();
                }
            }
        });
}catch (Exception ex){
    Log.d("LoginUser", ex.toString());
}
    }
}