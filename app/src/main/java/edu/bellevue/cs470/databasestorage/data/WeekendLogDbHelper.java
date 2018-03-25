package edu.bellevue.cs470.databasestorage.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import edu.bellevue.cs470.databasestorage.data.WeekendLogContract.*;


//  Extend the SQLiteOpenHelper class
public class WeekendLogDbHelper extends SQLiteOpenHelper {

    //  Create a static final String called DATABASE_NAME and set it to "weekendLog.db"
    // The database name
    private static final String DATABASE_NAME = "weekendLog.db";

    // Create a static final int called DATABASE_VERSION and set it to 1
    // If you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 8;

    // Create a Constructor that takes a context and calls the parent constructor
    // Constructor
    public WeekendLogDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Override the onCreate method
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // 6) Inside, create an String query called SQL_CREATE_WEEKEND_TABLE that will create the table
        // Create a table to hold weekendLog data
        final String SQL_CREATE_WEEKEND_TABLE = "CREATE TABLE " + WeekendLogEntry.TABLE_NAME + " (" +
                WeekendLogEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                WeekendLogEntry.COLUMN_PLACE_NAME + " TEXT NOT NULL, " +
                WeekendLogEntry.COLUMN_ADDRESS + " TEXT NOT NULL, " +
                WeekendLogEntry.COLUMN_COMMENTS + " TEXT NOT NULL, " +     //INCLUDE PICTURE  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                WeekendLogEntry.COLUMN_TYPE + " TEXT NOT NULL, " +
     //           WeekendLogEntry.COLUMN_PICTURE + " BYTES BLOB" +
                WeekendLogEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); ";

        // Execute the query by calling execSQL on sqLiteDatabase and pass the string query SQL_CREATE_WEEKEND_TABLE
        sqLiteDatabase.execSQL(SQL_CREATE_WEEKEND_TABLE);
    }

    // Override the onUpgrade method
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // For now simply drop the table and create a new one. This means if you change the
        // DATABASE_VERSION the table will be dropped.
        // In a production app, this method might be modified to ALTER the table
        // instead of dropping it, so that existing data is not deleted.
        //  Inside, execute a drop table query, and then call onCreate to re-create it
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WeekendLogEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}