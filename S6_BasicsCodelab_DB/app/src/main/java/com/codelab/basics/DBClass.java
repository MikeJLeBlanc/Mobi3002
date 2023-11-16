package com.codelab.basics;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.concurrent.Executor;


/**
 * Created by w0091766 on 4/29/2016.
 */
public class DBClass extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 8;
    public static final String DATABASE_NAME = "DnDDatabase.db";

    public DBClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d("Save_v03", "DB onCreate()");

        db.execSQL("CREATE TABLE CharacterTable (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "charName VARCHAR(256)," +
                "strength INTEGER," +
                "wisdom INTEGER," +
                "intellect INTEGER," +
                "charisma INTEGER," +
                "luck INTEGER," +
                "accessCount INTEGER," +
                "date DATETIME DEFAULT CURRENT_TIMESTAMP)");

        db.execSQL(
                "INSERT INTO CharacterTable(charName, strength, wisdom, intellect, charisma, luck, accessCount) " +
                        "VALUES('Thalia Ironheart', 14, 10, 12, 15, 11, 0)");
        db.execSQL(
                "INSERT INTO CharacterTable(charName, strength, wisdom, intellect, charisma, luck, accessCount) " +
                        "VALUES('Gideon Stormbringer', 16, 8, 14, 13, 10, 0)");
        db.execSQL(
                "INSERT INTO CharacterTable(charName, strength, wisdom, intellect, charisma, luck, accessCount) " +
                        "VALUES('Seraphina Moonshadow', 12, 15, 9, 17, 8, 0)");
        db.execSQL(
                "INSERT INTO CharacterTable(charName, strength, wisdom, intellect, charisma, luck, accessCount) " +
                        "VALUES('Kael Brightblade', 13, 12, 11, 14, 16, 0)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        Log.d("Save_v03", "DB onUpgrade() to version " + DATABASE_VERSION);
        db.execSQL("DROP TABLE IF EXISTS CharacterTable");
        onCreate(db);
    }

    // Return 2D String array of the records suitable to display
    // master-detail type list data
    public String[][] get2DRecords() {
        // Real code would select * from DB table and populate
        // the first string with a title, and the 2nd string
        // with details which could be a concat of remaining
        // fields

        Log.d("DBClass.get2DRecords", "Start===========================");
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(
                "CharacterTable",  // The table to query
                null,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        // Returned Array is size of ResultSet
        Log.d("DBClass.get2DRecords", "c.getCount()=" + c.getCount());
        String[][] newArray = new String[c.getCount()][2];

        c.moveToFirst();
        long itemId = c.getLong(c.getColumnIndexOrThrow("id"));

        String key = "";
        String value = "";
        String keeperKey = "";

        do {
            int pos = c.getPosition();
            Log.d("DBClass.get2DRecords", "pos=" + pos);

            newArray[pos][0] = "";
            newArray[pos][1] = "";          // init so we can append later
            int colCount = c.getColumnCount();
            for (int i = 0; i < colCount; ++i) {
                switch (c.getType(i)) {
                    case Cursor.FIELD_TYPE_INTEGER:
                        key = c.getColumnName(i);
                        value = String.valueOf(c.getInt(i));
                        break;
                    case Cursor.FIELD_TYPE_STRING:
                        key = c.getColumnName(i);
                        value = c.getString(i);
                        break;
                }
                Log.d("DBClass.get2DRecords", "c.getPosition()=pos=" + pos);
                Log.d("DBClass.get2DRecords", "key=" + key + " value=" + value);
                newArray[pos][0] += key;
                newArray[pos][1] += value + " \n ";
                keeperKey = c.getString(1);
            }
            // Uncomment next line, key is better?
            newArray[pos][0] = keeperKey;    // Key is name of record
            Log.d("DBClass.get2DRecords", "Next Row");
        } while (c.moveToNext());  // Do while there is a next

        Log.d("DBClass.get2DRecords", "Dump array");
        for (String[] i : newArray) {
            for (String j : i) {
                Log.d("DBClass.get2DRecords", "j=>" + j);
            }
        }

        Log.d("DBClass.get2DRecords", "Sleep ..........................");

//        // Slow down just for fun to see what happens
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        Log.d("DBClass.get2DRecords", "End  ===========================");

        db.execSQL("UPDATE CharacterTable SET accessCount = accessCount + 1");


        return newArray;
    }




    // Return 2D String array of the records suitable to display
    // master-detail type list data ... DEBUG version
    public String[][] default_get2DRecords(){
        String[][] newArray = new String[2][2];
        newArray[0][0] = "Default";
        newArray[0][1] = "Data";
        newArray[1][0] = "Default2";
        newArray[1][1] = "Data2";
        return newArray;
    }
}
