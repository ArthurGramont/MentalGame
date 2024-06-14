package fr.arthur.mentalgame;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

import fr.arthur.mentalgame.database.ScoreDao;
import fr.arthur.mentalgame.entities.Score;

public class GameActivity extends AppCompatActivity {
    private MenuItem itemScore;
    private MenuItem itemVie1;
    private MenuItem itemVie2;
    private MenuItem itemVie3;
    private TextView textViewResultat;
    private TextView textViewCalcul;
    private TextView textViewResultatUser;
    private Button bouton1;
    private Button bouton2;
    private Button bouton3;
    private Button bouton4;
    private Button bouton5;
    private Button bouton6;
    private Button bouton7;
    private Button bouton8;
    private Button bouton9;
    private Button bouton0;
    private Button boutonSupprimer;
    private Button boutonEnvoyer;
    private Integer premierTerme;
    private Integer deuxiemeTerme;
    private Integer resultat;
    private Integer resultatUser;
    private ScoreDao scoreDao;
    private Integer vie = 3;
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        premierTerme = random.nextInt(10);
        deuxiemeTerme = random.nextInt(10);

        bouton0 = findViewById(R.id.button0);
        bouton1 = findViewById(R.id.button1);
        bouton2 = findViewById(R.id.button2);
        bouton3 = findViewById(R.id.button3);
        bouton4 = findViewById(R.id.button4);
        bouton5 = findViewById(R.id.button5);
        bouton6 = findViewById(R.id.button6);
        bouton7 = findViewById(R.id.button7);
        bouton8 = findViewById(R.id.button8);
        bouton9 = findViewById(R.id.button9);
        boutonEnvoyer = findViewById(R.id.buttonEnter);
        boutonSupprimer = findViewById(R.id.buttonSuppr);

        bouton0.setOnClickListener(view -> appuieBoutonChiffre((Button) view));
        bouton1.setOnClickListener(view -> appuieBoutonChiffre((Button) view));
        bouton2.setOnClickListener(view -> appuieBoutonChiffre((Button) view));
        bouton3.setOnClickListener(view -> appuieBoutonChiffre((Button) view));
        bouton4.setOnClickListener(view -> appuieBoutonChiffre((Button) view));
        bouton5.setOnClickListener(view -> appuieBoutonChiffre((Button) view));
        bouton6.setOnClickListener(view -> appuieBoutonChiffre((Button) view));
        bouton7.setOnClickListener(view -> appuieBoutonChiffre((Button) view));
        bouton8.setOnClickListener(view -> appuieBoutonChiffre((Button) view));
        bouton9.setOnClickListener(view -> appuieBoutonChiffre((Button) view));
        boutonEnvoyer.setOnClickListener(view -> appuieBoutonChiffre((Button) view));
        boutonSupprimer.setOnClickListener(view -> appuieBoutonChiffre((Button) view));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menujeu, menu);
        itemScore = menu.findItem(R.id.score);
        itemVie1 = menu.findItem(R.id.item_vie1);
        itemVie2 = menu.findItem(R.id.item_vie2);
        itemVie3 = menu.findItem(R.id.item_vie3);
        boutonSupprimer.setOnClickListener(suppr -> {
            textViewResultatUser.setText("");
            resultatUser = 0;
        });
        boutonEnvoyer.setOnClickListener(envoyer -> {
            faisLeCalcul(premierTerme, deuxiemeTerme);
            verifCalcul(resultat, resultatUser);
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void appuieBoutonChiffre(Button bouton){
        ajouterCharactere(bouton);
        modifierTerme(Integer.parseInt(bouton.getText().toString()));
    }

    private void modifierTerme(Integer aAjouter) {
        premierTerme = 10 * premierTerme + aAjouter;
    }


    private void ajouterCharactere(Button bouton){
        textViewCalcul.setText(textViewCalcul.getText().toString() + bouton.getText().toString());
    }

    private void faisLeCalcul(int a, int b) {
        textViewResultat.setText(String.valueOf(premierTerme + deuxiemeTerme));
    }

    private void verifCalcul(int resultat, int resultatUser) {
        if (resultat == resultatUser) {
            Score score = new Score();
            score.setScore(score.getScore() + 1);
            itemScore.setTitle("Score : " + score.getScore());
        } else {
            vie--;
        }
    }

    private void prochainCalcul() {
        premierTerme = random.nextInt();
        deuxiemeTerme = random.nextInt(10);
        textViewCalcul.setText("");
        textViewResultat.setText("");
        textViewResultatUser.setText("");
    }
}