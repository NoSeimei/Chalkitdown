package com.example.chalkitdown;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class UsersRegister extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private TextView registerButton;
    private EditText userFirstName, userLastName, userAge,
            userEmail, userPhone, userFirstPassword, userConfirmPassword, userPhoneValidation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_register);

        mAuth = FirebaseAuth.getInstance();



        registerButton = findViewById(R.id.buttonRegister);
        registerButton.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.buttonRegister:
                userRegister();
                break;
        }
    }

    private  void userRegister(){

        userLastName = (EditText) findViewById(R.id.editTextLastName);
        userFirstName = (EditText) findViewById(R.id.editTextFirstName);
        userAge = (EditText) findViewById(R.id.editTextAge);
        userEmail = (EditText) findViewById(R.id.editTextEmail);
        userPhone = (EditText) findViewById(R.id.editTextPhone);
        userFirstPassword = (EditText) findViewById(R.id.editTextFirstPassword);
        userConfirmPassword = (EditText) findViewById(R.id.editTextConfirmPassword);

        if(userLastName.getText().toString().trim().isEmpty()){
            userLastName.setError("Champ nom obligatoire !");
            userLastName.requestFocus();
            return;
        }
        else if(userFirstName.getText().toString().trim().isEmpty()){
            userFirstName.setError("Champ prénom obligatoire !");
            userFirstName.requestFocus();
            return;
        }
        else if(userAge.getText().toString().trim().isEmpty()){
            userAge.setError("Champ age obligatoire !");
            userAge.requestFocus();
            return;
        }
        else if(userEmail.getText().toString().trim().isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(userEmail.getText().toString().trim()).matches()){
            userEmail.setError("Champ email obligatoire !");
            userEmail.requestFocus();
            return;
        }
        else if(userPhone.getText().toString().trim().isEmpty()){
            userPhone.setError("Champ tel obligatoire !");
            userPhone.requestFocus();
            return;
        }
        else if(userFirstPassword.getText().toString().trim().isEmpty()){
            userFirstPassword.setError("Champ mod de passe obligatoire !");
            userFirstPassword.requestFocus();
            return;
        }
        else if(userConfirmPassword.getText().toString().trim().isEmpty()){
//
            if(userConfirmPassword.getText().toString().trim().length() < 6)
            {
                userConfirmPassword.setError("Le mot de passe doit avoir + 8 caractères !");
                userConfirmPassword.requestFocus();
                return;
            }
            userConfirmPassword.setError("Champ mod de passe obligatoire !");
            userConfirmPassword.requestFocus();
            return;
        }
        else if(!userFirstPassword.getText().toString().trim().equals(userConfirmPassword.getText().toString().trim()))
        {
            userConfirmPassword.setError("Les mot de passe ne correspondent pas !");
            userConfirmPassword.requestFocus();
            return;
        }
        //Insert into the FireBDD
        UserInsert(userLastName.getText().toString().trim(), userFirstName.getText().toString().trim(), userAge.getText().toString().trim(), userEmail.getText().toString().trim(), userPhone.getText().toString().trim(), userFirstPassword.getText().toString().trim());
    }

    public void UserInsert(String nom, String prenom, String age, String email, String tel, String password){
        try {


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            User user = new User(nom, prenom, age, email, tel, password);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(UsersRegister.this, "Inscription réussie !", Toast.LENGTH_LONG).show();
                                            }else{
                                                Toast.makeText(UsersRegister.this, "Echec de l'inscription !", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }
                        else {
                            Toast.makeText(UsersRegister.this, "Echec de l'inscription !", Toast.LENGTH_LONG).show();
                        }
                    }

                });
        }catch (Exception ex){
            Log.d("InsertUser", ex.toString());
        }
    }
}