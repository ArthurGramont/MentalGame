package fr.arthur.mentalgame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button boutonJouer;
    private Button boutonScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boutonJouer = findViewById(R.id.bouton_jouer);
        boutonScore = findViewById(R.id.bouton_score);

        boutonJouer.setOnClickListener((view -> {
                    Intent intent = new Intent(this, GameActivity.class);
                    startActivity(intent);
                }
        ));
        boutonScore.setOnClickListener((view -> {
            Intent intent = new Intent(this, ScoreActivity.class);
            startActivity(intent);
        }
        ));
    }
}