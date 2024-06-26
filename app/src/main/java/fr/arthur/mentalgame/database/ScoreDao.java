package fr.arthur.mentalgame.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fr.arthur.mentalgame.entities.Score;

public class ScoreDao extends BaseDao<Score> {
    public static String score = "SCORE_JOUEUR";
    public static String name = "NAME";
    public static String tableName = "SCORE";
    private SQLiteDatabase database;

    public ScoreDao(DataBaseHelper helper) {
        super(helper);
        this.database = helper.getReadableDatabase();
    }

    @Override
    protected String getTableName() {
        return tableName;
    }

    @Override
    protected void putValues(ContentValues values, Score entity) {
        values.put(score, entity.getScore());
        values.put(name, entity.getName());
    }

    @Override
    protected Score getEntity(Cursor cursor) {
        Score score1 = new Score();
        Integer indexScore = cursor.getColumnIndex(score);
        score1.setScore(cursor.getInt(indexScore));
        Integer indexName = cursor.getColumnIndex(name);
        score1.setName(cursor.getString(indexName));
        return score1;
    }

    // Ajout de la méthode getAllScores
    public List<Score> getAllScores() {
        List<Score> scores = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = database.query(tableName, null, null, null, null, null, score + " DESC");
            while (cursor.moveToNext()) {
                scores.add(getEntity(cursor));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return scores;
    }
}
