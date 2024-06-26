package fr.arthur.mentalgame;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.arthur.mentalgame.database.DataBaseHelper;
import fr.arthur.mentalgame.database.ScoreBaseHelper;
import fr.arthur.mentalgame.database.ScoreDao;
import fr.arthur.mentalgame.entities.Score;

public class ScoreActivity extends AppCompatActivity {

    private RecyclerView recyclerViewScores;
    private ScoreDao scoreDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_score);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerViewScores = findViewById(R.id.recyclerViewScores);
        recyclerViewScores.setLayoutManager(new LinearLayoutManager(this));

        scoreDao = new ScoreDao(new ScoreBaseHelper(this, "db", 1));

        List<Score> scores = scoreDao.getAllScores();
        ScoreAdapter adapter = new ScoreAdapter(scores);
        recyclerViewScores.setAdapter(adapter);
    }
}
