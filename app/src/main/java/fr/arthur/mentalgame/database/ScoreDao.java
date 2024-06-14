package fr.arthur.mentalgame.database;

import android.content.ContentValues;
import android.database.Cursor;

import fr.arthur.mentalgame.entities.Score;

public class ScoreDao extends BaseDao<Score> {
    public static String score = "SCORE_JOUEUR";
    public static String name = "NAME";
    public static String tableName = "SCORE";

    public ScoreDao(DataBaseHelper helper) {
        super(helper);
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
        score1.setScore(cursor.getInt(indexName));
        return score1;
    }
}
