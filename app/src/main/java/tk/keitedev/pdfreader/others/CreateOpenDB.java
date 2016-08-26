package tk.keitedev.pdfreader.others;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by daniel on 20/08/2016.
 */
public class CreateOpenDB extends SQLiteOpenHelper {


    public CreateOpenDB(Context context, String nameDB, CursorFactory factory, int version) {
        super(context, nameDB, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE books(id INTEGER PRIMARY KEY NOT NULL, title VARCHAR(255) NOT NULL, path TEXT NOT NULL)");
        db.execSQL("CREATE TABLE history(idBook INTEGER NOT NULL, lastPage INTEGER NOT NULL, totalPages INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion < newVersion){
            db.execSQL("DROP TABLE books");
            db.execSQL("DROP TABLE history");
            onCreate(db);
        }

    }
}
