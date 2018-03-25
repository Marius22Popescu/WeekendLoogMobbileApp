package edu.bellevue.cs470.databasestorage;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import edu.bellevue.cs470.databasestorage.data.WeekendLogContract;
import edu.bellevue.cs470.databasestorage.data.TestUtil;
import edu.bellevue.cs470.databasestorage.data.WeekendLogDbHelper;


public class MainActivity2 extends AppCompatActivity {
    private PlaceListAdapter mAdapter;

    // Create a local field SQLiteDatabase called mDb
    private SQLiteDatabase mDb;
    // Create local EditText fields for mNewGuestNameEditText and mNewPartySizeEditText
    private EditText mNewPlaceNameEditText;
    private EditText mNewAddressEditText;
    private EditText mNewCommentsEditText;
    private ImageView mNewImmageView;
    private String theType;
    private  Bitmap imageBitmap;

    //  Create a constant string LOG_TAG that is equal to the class.getSimpleName()
    private final static String LOG_TAG = MainActivity2.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2_main);

        RecyclerView weekendLogRecyclerView;

        // Set local attributes to corresponding views
        weekendLogRecyclerView = (RecyclerView) this.findViewById(R.id.all_guests_list_view);
        // Set the Edit texts and ImageView to the corresponding views using findViewById
        mNewPlaceNameEditText = (EditText) this.findViewById(R.id.place_name_edit_text);
        mNewAddressEditText = (EditText) this.findViewById(R.id.address_edit_text);
        mNewCommentsEditText = (EditText) this.findViewById(R.id.comments_edit_text);
        mNewImmageView = (ImageView) this.findViewById(R.id.photo);

        //Use the getIntent method to store the Intent that started this Activity in a variable
        Intent intentThatStartedThisActivity = getIntent();
        // get the tag insite a string
        if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
            theType = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
        }

     //   Log.d("THE TEST 1 !!!!!!", theType); // This is just for testing !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!?????????????????????????????


        // Set layout for the RecyclerView, because it's a list we are using the linear layout
        weekendLogRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create a WeekendLog DbHelper instance, pass "this" to the constructor
        // Create a DB helper (this will create the DB if run for the first time)
        WeekendLogDbHelper dbHelper = new WeekendLogDbHelper(this);

        // Get a writable database reference using getWritableDatabase and store it in mDb
        // Keep a reference to the mDb until paused or killed. Get a writable database
        // because you will be adding places
        mDb = dbHelper.getWritableDatabase();

        //call insertFakeData in TestUtil and pass the database reference mDb
        //Fill the database with fake data
        TestUtil.insertFakeData(mDb);

        //  Run the getAllPlaces function and store the result in a Cursor variable
        Cursor cursor = getAllPlaces();

        //  Pass the resulting cursor count to the adapter
        // Create an adapter for that cursor to display the data
        mAdapter = new PlaceListAdapter(this, cursor, theType);
                                     //
        // Link the adapter to the RecyclerView
        weekendLogRecyclerView.setAdapter(mAdapter);

        //  Create a new ItemTouchHelper with a SimpleCallback that handles both LEFT and RIGHT swipe directions
        // Create an item touch helper to handle swiping items off the list
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            // Override onMove and simply return false inside
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //do nothing, we only care about swiping
                return false;
            }

            // Override onSwiped
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Inside, get the viewHolder's itemView's tag and store in a long variable id
                //get the id of the item being swiped
                long id = (long) viewHolder.itemView.getTag();
                // call removeGuest and pass through that id
                //remove from DB
                removePlace(id);
                // call swapCursor on mAdapter passing in getAllGuests() as the argument
                //update the list
                mAdapter.swapCursor(getAllPlaces());
            }

            // attach the ItemTouchHelper to the weekendLogRecyclerView
        }).attachToRecyclerView(weekendLogRecyclerView);


    }


     //This method will save the InstanceState
    /***************************
     @Override
     protected void onSaveInstanceState(Bundle outState) {
     super.onSaveInstanceState(outState);
     //this will save the user inputs if the user change the app orientation
     outState.putString("name", mNewPlaceNameEditText.toString());
     outState.putString("address", mNewAddressEditText.toString());
     outState.putString("comment", mNewCommentsEditText.toString());
     }
     //This method will restore the saveInstanceState using OnRestoreInstanceState implementation
     @Override
     protected void onRestoreInstanceState(Bundle savedInstanceState) {
     super.onRestoreInstanceState(savedInstanceState);
     if (savedInstanceState != null) {
     //this will restore the user inputs
     mNewPlaceNameEditText.setText(savedInstanceState.getString("name"));
     mNewAddressEditText.setText(savedInstanceState.getString("address"));
     mNewCommentsEditText.setText(savedInstanceState.getString("comment"));
     }
     }   *******************/

// Create a constant int request code
    static final int REQUEST_IMAGE_CAPTURE = 30210;
    //This method invokes an intent to capture a photo
    public void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    //This method retrieves the image and and displays it in a imageView
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            ImageView mImageView= (ImageView) findViewById(R.id.photo);
            mImageView.setImageBitmap(imageBitmap);
        }
    }

    public void addToWeekendLog(View view) {
        //First thing, check if any of the name and address EditTexts are empty, return if so
        if (mNewPlaceNameEditText.getText().length() == 0 ||
                mNewAddressEditText.getText().length() == 0 ) {
            return;
        }

        // call addNewPlace with the place name, address, comments and photo
        // Add guest info to mDb
        addNewPlace(mNewPlaceNameEditText.getText().toString(), mNewAddressEditText.getText().toString(), mNewCommentsEditText.getText().toString(), theType);
                             //   , theType
        //INCLUDE PICTURE  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // call mAdapter.swapCursor to update the cursor by passing in getAllGuests()
        // Update the cursor in the adapter to trigger UI to display the new list
        mAdapter.swapCursor(getAllPlaces());

        // To make the UI look nice, call .getText().clear() on both EditTexts, also call clearFocus() on mNewPartySizeEditText
        //clear UI text fields
        mNewPlaceNameEditText.getText().clear();
        mNewAddressEditText.getText().clear();
        mNewCommentsEditText.getText().clear();
        //INCLUDE PICTURE  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    }


    // Create a private method called getAllPlaces that returns a cursor

    /**
     * Query the mDb and get all places from the weekendLog table
     *
     * @return Cursor containing the list of guests
     */
    private Cursor getAllPlaces() {
        // Inside, call query on mDb passing in the table name and projection String [] order by COLUMN_TIMESTAMP
        return mDb.query(
                WeekendLogContract.WeekendLogEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                WeekendLogContract.WeekendLogEntry.COLUMN_TIMESTAMP
        );
    }

    private Long addNewPlace(String name, String address, String comment, String type) {      //INCLUDE PICTURE  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                                                       //
        // Inside, create a ContentValues instance to pass the values onto the insert query
        ContentValues cv = new ContentValues();
        // call put to insert the name value with the key COLUMN_PLACE_NAME
        cv.put(WeekendLogContract.WeekendLogEntry.COLUMN_PLACE_NAME, name);
        // call put to insert the address with the key COLUMN_ADDRESS
        cv.put(WeekendLogContract.WeekendLogEntry.COLUMN_ADDRESS, address);
        // call put to insert the comments with the key COLUMN_COMMENTS
        cv.put(WeekendLogContract.WeekendLogEntry.COLUMN_COMMENTS, comment);
        // call put to insert the type with the key COLUMN_TYPE
        cv.put(WeekendLogContract.WeekendLogEntry.COLUMN_TYPE, type);

        // call put to insert picture with the key COLUMN_PICTURE
        /*****
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] thumbnailBitmapBytes = stream.toByteArray();

        ContentValues values = new ContentValues();
        values.put("IMAGEID", "your_image_id");
        values.put("BYTES", thumbnailBitmapBytes);
        cv.put(WeekendLogContract.WeekendLogEntry.COLUMN_PICTURE, values);
        *******/



        // call insert to run an insert query on TABLE_NAME with the ContentValues created
        return mDb.insert(WeekendLogContract.WeekendLogEntry.TABLE_NAME, null, cv);
    }


    // Create a new function called removePlace that takes long id as input and returns a boolean

    /**
     * Removes the record with the specified id
     *
     * @param id the DB id to be removed
     * @return True: if removed successfully, False: if failed
     */
    private boolean removePlace(long id) {
        // Inside, call mDb.delete to pass in the TABLE_NAME and the condition that WeekendLogEntry._ID equals id
        return mDb.delete(WeekendLogContract.WeekendLogEntry.TABLE_NAME, WeekendLogContract.WeekendLogEntry._ID + "=" + id, null) > 0;
    }
    //This method will get the picture from the database
    /*****
    public static synchronized Bitmap getImage(String imageID, Context context) {
        SQLiteDatabase mDb;
        Bitmap bitmap = null;
        Cursor cs = null;

        try {
            String sql = "SELECT BYTES FROM TABLE_NAME WHERE IMAGEID = ?;";
            cs = mDb.rawQuery(sql, new String[]{imageID});

            if (cs != null && cs.moveToFirst()) {
                do {
                    byte[] bytes = cs.getBlob(0);

                    if (bytes != null) {
                        try {
                            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        } catch (Exception e) {
                            Log.e("TAG", "Exception", e);
                        }
                    } else {
                        Log.e("TAG", "IMAGE NOT FOUND");
                    }

                } while (cs.moveToNext());
            }

        } catch (Exception e) {
            Log.e("TAG", "Exception", e);
        } finally {
            if (cs != null) {
                cs.close();
            }
        }

        return bitmap;
    }  ****/

}

