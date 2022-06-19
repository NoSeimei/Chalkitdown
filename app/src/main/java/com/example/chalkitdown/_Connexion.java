package com.example.chalkitdown;

import com.google.firebase.auth.FirebaseAuth;

public class _Connexion {

    public static FirebaseAuth connect(){
        return FirebaseAuth.getInstance();
    }
}
