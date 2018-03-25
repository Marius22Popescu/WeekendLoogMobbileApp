package edu.bellevue.cs470.databasestorage.data;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;



public class TestUtil {
    public static void insertFakeData(SQLiteDatabase db){
        if(db == null){
            return;
        }
        //create a list of fake places
        List<ContentValues> list = new ArrayList<ContentValues>();

        ContentValues cv = new ContentValues();
        cv.put(WeekendLogContract.WeekendLogEntry.COLUMN_PLACE_NAME, "Golden Garden Beach");
        cv.put(WeekendLogContract.WeekendLogEntry.COLUMN_ADDRESS, "Golden Gardens Dr NW, Seattle, WA 98117");
        cv.put(WeekendLogContract.WeekendLogEntry.COLUMN_COMMENTS, "It is a nice and wide beach");
        cv.put(WeekendLogContract.WeekendLogEntry.COLUMN_TYPE, "Beach");
        list.add(cv);

        cv = new ContentValues();
        cv.put(WeekendLogContract.WeekendLogEntry.COLUMN_PLACE_NAME, "Mt St Helens");
        cv.put(WeekendLogContract.WeekendLogEntry.COLUMN_ADDRESS, "24000 Spirit Lake Hwy, Toutle, WA 98649");
        cv.put(WeekendLogContract.WeekendLogEntry.COLUMN_COMMENTS, "Mount St. Helens or Louwala-Clough is an active stratovolcano located in Skamania County, Washington.");
        cv.put(WeekendLogContract.WeekendLogEntry.COLUMN_TYPE, "Hiking");
        list.add(cv);

        cv = new ContentValues();
        cv.put(WeekendLogContract.WeekendLogEntry.COLUMN_PLACE_NAME, "Wild Waves");
        cv.put(WeekendLogContract.WeekendLogEntry.COLUMN_ADDRESS, "36201 Enchanted Pkwy S, Federal Way, WA 98003");
        cv.put(WeekendLogContract.WeekendLogEntry.COLUMN_COMMENTS, "Theme park with roller coasters & kid-friendly rides plus a water area with a pool & slides.");
        cv.put(WeekendLogContract.WeekendLogEntry.COLUMN_TYPE, "Park");
        list.add(cv);

        cv = new ContentValues();
        cv.put(WeekendLogContract.WeekendLogEntry.COLUMN_PLACE_NAME, "Four Seasons Hotel Seattle");
        cv.put(WeekendLogContract.WeekendLogEntry.COLUMN_ADDRESS, "99 Union St, Seattle, WA 98101");
        cv.put(WeekendLogContract.WeekendLogEntry.COLUMN_COMMENTS, "Posh rooms in an upmarket property with free Wi-Fi, regional fine dining, a spa & a rooftop pool.");
        cv.put(WeekendLogContract.WeekendLogEntry.COLUMN_TYPE, "Resort");
        list.add(cv);

        cv = new ContentValues();
        cv.put(WeekendLogContract.WeekendLogEntry.COLUMN_PLACE_NAME, "Assaggio Ristorante");
        cv.put(WeekendLogContract.WeekendLogEntry.COLUMN_ADDRESS, "2010 4th Ave, Seattle, WA 98121");
        cv.put(WeekendLogContract.WeekendLogEntry.COLUMN_COMMENTS, "Refined, but informal trattoria serves high-end Italian fare amid Renaissance-style murals.");
        cv.put(WeekendLogContract.WeekendLogEntry.COLUMN_TYPE, "Restaurant");
        list.add(cv);

        cv = new ContentValues();
        cv.put(WeekendLogContract.WeekendLogEntry.COLUMN_PLACE_NAME, "Parlor Live Bellevue");
        cv.put(WeekendLogContract.WeekendLogEntry.COLUMN_ADDRESS, "700 Bellevue Way NE #300, Bellevue, WA 98004");
        cv.put(WeekendLogContract.WeekendLogEntry.COLUMN_COMMENTS, "Expansive space housing pool & billiards tables, with full American restaurant menu & 2 bars.");
        cv.put(WeekendLogContract.WeekendLogEntry.COLUMN_TYPE, "Nightlife");
        list.add(cv);

        cv = new ContentValues();
        cv.put(WeekendLogContract.WeekendLogEntry.COLUMN_PLACE_NAME, "Stevens Pass");
        cv.put(WeekendLogContract.WeekendLogEntry.COLUMN_ADDRESS, "US-2, Skykomish, WA 98288");
        cv.put(WeekendLogContract.WeekendLogEntry.COLUMN_COMMENTS, "1,125-acre winter sports area with 37 runs, terrain park & night skiing, plus summertime bike park.");
        cv.put(WeekendLogContract.WeekendLogEntry.COLUMN_TYPE, "Winter");
        list.add(cv);

        cv = new ContentValues();
        cv.put(WeekendLogContract.WeekendLogEntry.COLUMN_PLACE_NAME, "Paramount Theatre");
        cv.put(WeekendLogContract.WeekendLogEntry.COLUMN_ADDRESS, "911 Pine St, Seattle, WA 98101");
        cv.put(WeekendLogContract.WeekendLogEntry.COLUMN_COMMENTS, "Historic performing arts venue that hosts major musicals, plus big-name music & comedy acts.");
        cv.put(WeekendLogContract.WeekendLogEntry.COLUMN_TYPE, "Culture");
        list.add(cv);

        cv = new ContentValues();
        cv.put(WeekendLogContract.WeekendLogEntry.COLUMN_PLACE_NAME, "Space Needle Park");
        cv.put(WeekendLogContract.WeekendLogEntry.COLUMN_ADDRESS, "378 Broad St, Seattle, WA 98109");
        cv.put(WeekendLogContract.WeekendLogEntry.COLUMN_COMMENTS, "Nice playground close to Space Needle");
        cv.put(WeekendLogContract.WeekendLogEntry.COLUMN_TYPE, "Playground");
        list.add(cv);

        //insert all guests in one transaction
        try
        {
            db.beginTransaction();
            //clear the table first
            db.delete (WeekendLogContract.WeekendLogEntry.TABLE_NAME,null,null);
            //go through the list and add one by one
            for(ContentValues c:list){
                db.insert(WeekendLogContract.WeekendLogEntry.TABLE_NAME, null, c);
            }
            db.setTransactionSuccessful();
        }
        catch (SQLException e) {
            //too bad :(
        }
        finally
        {
            db.endTransaction();
        }

    }
}
