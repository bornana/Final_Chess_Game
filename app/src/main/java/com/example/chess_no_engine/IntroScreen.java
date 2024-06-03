package com.example.chess_no_engine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class IntroScreen extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword;

    Button signIn;

    TextView signUp;
    FirebaseDatabase db;
    DatabaseReference reference;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_screen);

        editTextEmail = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.password);
        signIn = findViewById(R.id.sign_in);
        signUp = findViewById(R.id.sign_up);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroScreen.this, RegisterPage.class);
                startActivity(intent);
                finish();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(IntroScreen.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(IntroScreen.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                db = FirebaseDatabase.getInstance();
                reference = db.getReference("Users");
                reference.child(email).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            String storedPassword = snapshot.child("password").getValue(String.class);
                            if(!storedPassword.isEmpty() && storedPassword.equals(password)){
                                Toast.makeText(IntroScreen.this, "Login Successful !", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(IntroScreen.this, Play_Options.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Toast.makeText(IntroScreen.this, "Authentication Failed !", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else {
                            Toast.makeText(IntroScreen.this, "Authentication Failed !", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(IntroScreen.this, "Database error", Toast.LENGTH_SHORT).show();
                    }
                });


                firebaseAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(IntroScreen.this, "Login Succssful !", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(IntroScreen.this, Play_Options.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    Toast.makeText(IntroScreen.this, "Aunthenticaion Failed !", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}