package com.example.chess_no_engine;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AndTheWinnerIs extends AppCompatActivity {

    TextView winner;
    ImageView winnerpiece;
    FirebaseDatabase db;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_and_the_winner_is);
        winner = findViewById(R.id.WinnerColor);
        winnerpiece = findViewById(R.id.WinnerImage);

        db = FirebaseDatabase.getInstance();
        reference = db.getReference("Users");
        Intent intent = new Intent();
        String winnercolor = intent.getStringExtra("Winner");
        String username = intent.getStringExtra("username");
        if(intent.getBooleanExtra("is_bot", false)) {
            reference.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int loseScore = snapshot.child(username).child("loseScore").getValue(int.class);
                    int winScore = snapshot.child(username).child("winScore").getValue(int.class);
                    int drawScore = snapshot.child(username).child("drawScore").getValue(int.class);
                    if (winnercolor == "white") {
                        reference.child(username).child("winScore").setValue(winScore + 1);
                    } else if (winnercolor == "black") {
                        reference.child(username).child("loseScore").setValue(loseScore + 1);
                    } else {
                        reference.child(username).child("drawScore").setValue(drawScore + 1);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(AndTheWinnerIs.this, "Database error", Toast.LENGTH_SHORT).show();
                }
            });
        }
        winner.setText(winnercolor + "Wins!");
        if(winnercolor == "white"){
            winnerpiece.setImageResource(R.drawable.kingcharles);
        }
        else if (winnercolor == "black") {
            winnerpiece.setImageResource(R.drawable.tachalarip);
        }
        else {
            winner.setText("Draw!");
        }
    }
}