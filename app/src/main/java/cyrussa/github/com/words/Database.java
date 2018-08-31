package cyrussa.github.com.words;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "History.db";
    private static final String TABLE_NAME = "History";
    private static final String COL_1 = "Title";
    private static final String COL_2 = "Artist";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("+ COL_1+ " TEXT, " + COL_2 + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insert(String title, String artist){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_1, title);
        cv.put(COL_2, artist);
        long returnedLong = db.insert(TABLE_NAME, null, cv);
    }

    public void clearTable(){
        onUpgrade(this.getWritableDatabase(), 2, 2);
    }

    public Cursor readAll(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return result;
    }
}
