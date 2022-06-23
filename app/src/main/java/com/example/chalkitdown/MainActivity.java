package com.example.chalkitdown;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView loginButton;
    private TextView registerButton;
    private TextView forgottenButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginuser);

        loginButton = (TextView) findViewById(R.id.buttonConnexion);
        loginButton.setOnClickListener(this);

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

            case R.id.textForgotPassword:
                startActivity(new Intent(this, MdpOublie.class));
                break;
        }
    }
}