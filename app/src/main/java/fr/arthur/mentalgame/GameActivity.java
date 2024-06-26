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

import fr.arthur.mentalgame.database.ScoreBaseHelper;
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
        textViewResultat = findViewById(R.id.text_resultat);
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
        if (resultatUser == null) {
            resultatUser = 0;
        }
        if (resultat == resultatUser.intValue()) {
            textViewResultat.setText(String.valueOf(resultat));
            score++;
            itemScore.setTitle("Score : " + score);
            if (score < 10) {
                prochainCalculEasy();
            } else if (score < 20) {
                prochainCalculMedium();
            } else {
                prochainCalculHard();
            }
        } else {
            vie--;
            modifVie();
            if (vie == 0) {
                // Créer un objet Score avec le score actuel et le sauvegarder
                Score scoreObject = new Score();
                scoreObject.setScore(score);  // Assurez-vous que la classe Score a une méthode setScore
                scoreDao.create(scoreObject);  // Sauvegarde du score dans la base de données
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
        textViewResultat.setText("");
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