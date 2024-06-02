package com.example.chess_no_engine;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
public class AndTheWinnerIs extends AppCompatActivity {

    TextView winner;
    ImageView winnerpiece;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_and_the_winner_is);
        winner = findViewById(R.id.WinnerColor);
        winnerpiece = findViewById(R.id.WinnerImage);

        Intent intent = new Intent();
        String winnercolor = intent.getStringExtra("Winner");
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