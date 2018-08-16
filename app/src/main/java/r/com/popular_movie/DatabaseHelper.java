package r.com.popular_movie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Movie;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="movie.db";
    public static final String TABLE_NAME = "favorite";
    public static final String ID = "ID";
    public static final String COLUMN_MOVIEID = "movieid";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_USERRATING = "userrating";
    public static final String COLUMN_POSTER_PATH = "posterpath";
    public static final String COLUMN_PLOT_SYNOPSIS = "overview";
    private static final int DATABASE_VERSION = 1;

    public static final String LOGTAG = "FAVORITE";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String create_table="CREATE TABLE " + TABLE_NAME + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_MOVIEID + " INTEGER, " +
                COLUMN_TITLE + " TEXT NOT NULL, " +
                COLUMN_USERRATING + " REAL NOT NULL, " +
                COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                COLUMN_PLOT_SYNOPSIS + " TEXT NOT NULL" +
                "); ";

                db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public void  insertData(MovieDataModel movie){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COLUMN_MOVIEID,movie.getId());
        contentValues.put(COLUMN_TITLE,movie.getTitle());
        contentValues.put(COLUMN_USERRATING, movie.getVote_average());
        contentValues.put(COLUMN_POSTER_PATH,movie.getPosterPath());
        contentValues.put(COLUMN_PLOT_SYNOPSIS,movie.getOverview());
        db.insert(TABLE_NAME,null,contentValues);
    }

    public Cursor getData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }
}
