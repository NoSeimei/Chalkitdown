package com.example.chalkitdown;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.Random;

@Keep
public class User{

    public String nom, prenom, age,email, tel, password, phonecodevalidation;
    public Boolean isphonevalided;

    public User(){

    }

    public User(String nom, String prenom, String age, String email, String tel, String password)
    {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.email = email;
        this.tel = tel;
        this.password = password;
        this.phonecodevalidation = PhoneCodeGenerator().toString();
    }

    public Integer PhoneCodeGenerator(){
        return new Random().nextInt(80000) + 10000;
    }

}
