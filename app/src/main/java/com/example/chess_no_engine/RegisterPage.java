package com.example.chess_no_engine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterPage extends AppCompatActivity {

    TextInputEditText editTextUsername, editTextPassword;

    Button signUp;

    TextView signIn;

    FirebaseDatabase db;
    DatabaseReference reference;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        editTextUsername = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        signIn = findViewById(R.id.sign_in);
        signUp = findViewById(R.id.sign_up);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterPage.this, IntroScreen.class);
                startActivity(intent);
                finish();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = String.valueOf(editTextUsername.getText());
                password = String.valueOf(editTextPassword.getText());

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterPage.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterPage.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                Users user = new Users(editTextUsername.getText().toString(), editTextPassword.getText().toString(), 0, 0, 0);
                db = FirebaseDatabase.getInstance();
                reference = db.getReference("Users");
                reference.child(editTextUsername.getText().toString()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegisterPage.this, "Registration Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterPage.this, Play_Options.class);
                            intent.putExtra("username", editTextUsername.getText().toString());
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(RegisterPage.this, "Authentication Failed !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

        });
    }
}