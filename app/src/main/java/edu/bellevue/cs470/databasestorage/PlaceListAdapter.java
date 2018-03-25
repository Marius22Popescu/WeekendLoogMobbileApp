package edu.bellevue.cs470.databasestorage;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.bellevue.cs470.databasestorage.data.WeekendLogContract;
import edu.bellevue.cs470.databasestorage.data.WeekendLogDbHelper;


public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListAdapter.PlaceViewHolder> {

    // Holds on to the cursor to display the waitlist
    private Cursor mCursor;
    private Context mContext;
    private String mPlaceType;

    public PlaceListAdapter(Context context, Cursor cursor, String placeType) {
                                //
        this.mContext = context;
        this.mCursor = cursor;
        this.mPlaceType = placeType;
    }

    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get the RecyclerView item layout
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.guest_list_item, parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlaceViewHolder holder, int position) {
        // Move the mCursor to the position of the item to be displayed
        if (!mCursor.moveToPosition(position))
            return; // bail if returned null

        // Update the view holder with the information needed to display, getting data from db
        String name = mCursor.getString(mCursor.getColumnIndex(WeekendLogContract.WeekendLogEntry.COLUMN_PLACE_NAME));
        String address = mCursor.getString(mCursor.getColumnIndex(WeekendLogContract.WeekendLogEntry.COLUMN_ADDRESS));
        String comment = mCursor.getString(mCursor.getColumnIndex(WeekendLogContract.WeekendLogEntry.COLUMN_COMMENTS));
        String type = mCursor.getString(mCursor.getColumnIndex(WeekendLogContract.WeekendLogEntry.COLUMN_TYPE));
        //INCLUDE PICTURE  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        // Retrieve the id from the cursor and

        //Log.d("THE TEST 2 !!!!!!!:", mPlaceType); // This is just for testing !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!????

        long id = mCursor.getLong(mCursor.getColumnIndex(WeekendLogContract.WeekendLogEntry._ID));
        //Sort tha db function of the type tag
        if(mPlaceType.equals(type)) {
        // Display the place name
        holder.nameTextView.setText(name);
        // Display the address
        holder.addressTextView.setText(address);
        // Display the comment
        holder.commentTextView.setText(comment);
        //INCLUDE PICTURE  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        }

        // Set the tag of the itemview in the holder to the id
        holder.itemView.setTag(id);

    }


    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    // Create a new function called swapCursor that takes the new cursor and returns void
    /**
     * Swaps the Cursor currently held in the adapter with a new one
     * and triggers a UI refresh
     *
     * @param newCursor the new cursor that will replace the existing one
     */
    public void swapCursor(Cursor newCursor) {
        // Inside, check if the current cursor is not null, and close it if so
        // Always close the previous mCursor first
        if (mCursor != null) mCursor.close();
        // Update the local mCursor to be equal to  newCursor
        mCursor = newCursor;
        // Check if the newCursor is not null, and call this.notifyDataSetChanged() if so
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    /**
     * Inner class to hold the views needed to display a single item in the recycler-view
     */
    class PlaceViewHolder extends RecyclerView.ViewHolder {

        // Will display the guest name
        TextView nameTextView;
        // Will display the address
        TextView addressTextView;
        // Will display the comments
        TextView commentTextView;

        //INCLUDE PICTURE  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * TextViews
         *
         * @param itemView The View that you inflated in
         *                 {@link PlaceListAdapter#onCreateViewHolder(ViewGroup, int)}
         */
        public PlaceViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.name_text_view);
            addressTextView = (TextView) itemView.findViewById(R.id.address_text_view);
            commentTextView = (TextView) itemView.findViewById(R.id.comments_text_view);
        }

    }
}