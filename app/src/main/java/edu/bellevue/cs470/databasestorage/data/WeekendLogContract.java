package edu.bellevue.cs470.databasestorage.data;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.provider.BaseColumns;

//The database contract!
public class WeekendLogContract {
    public static final class WeekendLogEntry implements BaseColumns {
        public static final String TABLE_NAME = "weekendLog";
        public static final String COLUMN_PLACE_NAME = "placeName";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_COMMENTS = "comments";
        public static final String COLUMN_TYPE = "type";
  //      public static final ContentValues COLUMN_PICTURE = "picture";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
