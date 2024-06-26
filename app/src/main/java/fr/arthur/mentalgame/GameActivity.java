package fr.arthur.mentalgame;

import android.content.Intent;
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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;


import java.util.Random;

import fr.arthur.mentalgame.database.ScoreBaseHelper;
import fr.arthur.mentalgame.database.ScoreDao;
import fr.arthur.mentalgame.entities.Score;

public class GameActivity extends AppCompatActivity {
    private MenuItem itemScore;
    private MenuItem itemVie1;
    private MenuItem itemVie2;
    private MenuItem itemVie3;
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
    private Integer resultatUser = 0;
    private ScoreDao scoreDao;
    private Integer vie = 3;
    Random random = new Random();
    private Integer score = 0;

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
        scoreDao = new ScoreDao(new ScoreBaseHelper(this, "db", 1));



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
        textViewCalcul = findViewById(R.id.text_calcul);
        textViewResultatUser = findViewById(R.id.text_resultat_user);

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
        boutonSupprimer.setOnClickListener(suppr -> {
            textViewResultatUser.setText("");
            resultatUser = 0;
        });
        boutonEnvoyer.setOnClickListener(envoyer -> {
            afficherCalcul(faisLeCalcul(premierTerme, deuxiemeTerme), resultatUser);
        });

        textViewCalcul.setText(premierTerme + " + " + deuxiemeTerme);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menujeu, menu);
        itemScore = menu.findItem(R.id.score);
        itemVie1 = menu.findItem(R.id.item_vie1);
        itemVie2 = menu.findItem(R.id.item_vie2);
        itemVie3 = menu.findItem(R.id.item_vie3);
        return super.onCreateOptionsMenu(menu);
    }

    private void appuieBoutonChiffre(Button bouton){
        ajouterCharactere(bouton);
    }

    private void ajouterCharactere(Button bouton){
        textViewResultatUser.setText(textViewResultatUser.getText().toString() + bouton.getText().toString());
        try {
            resultatUser = Integer.parseInt(textViewResultatUser.getText().toString());
        } catch (NumberFormatException e) {
            resultatUser = 0;
        }
    }

    private int faisLeCalcul(int a, int b) {
        resultat = a + b;
        return resultat;
    }

    private void afficherCalcul(int resultat, Integer resultatUser) {
        Score scoreJoueur = new Score();
        if (resultatUser == null) {
            resultatUser = 0;
        }
        if (resultat == resultatUser.intValue()) {
            score++;
            itemScore.setTitle("Score : " + score);
            if (score < 10) {
                prochainCalculEasy();
            } else if (score < 20) {
                prochainCalculMedium();
            } else if (score < 30) {
                prochainCalculHard();
            } else if (score < 40) {
                prochainCalculUltraHard();
            } else {
                prochainCalculImpossible();
            }
        } else {
            vie--;
            modifVie();
            if (vie == 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Fin de la partie");
                builder.setMessage("Entrez votre nom (3 caractères) :");

                final EditText input = new EditText(this);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String playerName = input.getText().toString().trim().toUpperCase();

                        if (playerName.length() < 3) {
                            playerName = String.format("%-3s", playerName).replace(' ', 'X');
                        } else if (playerName.length() > 3) {
                            playerName = playerName.substring(0, 3);
                        }

                        Score scoreJoueur = new Score();
                        scoreJoueur.setScore(score);
                        scoreJoueur.setName(playerName);
                        scoreDao.create(scoreJoueur);

                        Intent intent = new Intent(GameActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

                builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                        Intent intent = new Intent(GameActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

                builder.show();
            }
        }
    }

    private void prochainCalculEasy() {
        premierTerme = random.nextInt(10);
        deuxiemeTerme = random.nextInt(10);
        textViewCalcul.setText(premierTerme + " + " + deuxiemeTerme);
        resetResultats();
    }

    private void prochainCalculMedium() {
        premierTerme = random.nextInt(100);
        deuxiemeTerme = random.nextInt(100);
        textViewCalcul.setText(premierTerme + " + " + deuxiemeTerme);
        resetResultats();
    }

    private void prochainCalculHard() {
        premierTerme = random.nextInt(1000);
        deuxiemeTerme = random.nextInt(1000);
        textViewCalcul.setText(premierTerme + " + " + deuxiemeTerme);
        resetResultats();
    }

    private void prochainCalculUltraHard() {
        premierTerme = random.nextInt(10000);
        deuxiemeTerme = random.nextInt(10000);
        textViewCalcul.setText(premierTerme + " + " + deuxiemeTerme);
        resetResultats();
    }

    private void prochainCalculImpossible() {
        premierTerme = random.nextInt(100000);
        deuxiemeTerme = random.nextInt(100000);
        textViewCalcul.setText(premierTerme + " + " + deuxiemeTerme);
        resetResultats();
    }

    private void resetResultats() {
        textViewResultatUser.setText("");
        resultatUser = 0;
    }

    private void modifVie() {
        if (vie == 2) {
            itemVie3.setVisible(false);
        } else if (vie == 1) {
            itemVie2.setVisible(false);
        } else if (vie == 0) {
            itemVie1.setVisible(false);
        }
    }
}