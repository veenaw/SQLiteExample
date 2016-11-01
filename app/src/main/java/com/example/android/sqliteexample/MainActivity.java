package com.example.android.sqliteexample;

import android.app.ListActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends ListActivity {
    private DatabaseOpenHelper mDbHelper;
 //   private SQLiteOpenHelper sqLiteOpenHelper;
    private SimpleCursorAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create a new DatabaseHelper
        mDbHelper = new DatabaseOpenHelper(this);

        // start with an empty database
        clearAll();

        // Insert records
        insertArtists();

        // Create a cursor
        Cursor c = readArtists();
        mAdapter = new SimpleCursorAdapter(this, R.layout.list_layout, c,
                DatabaseOpenHelper.columns, new int[] { R.id._id, R.id.name },
                0);

        setListAdapter(mAdapter);

        Button fixButton = (Button) findViewById(R.id.fix_button);
        fixButton.setOnClickListener(new View.OnClickListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {

                // execute database operations
                fix();

                // Redisplay data
                mAdapter.getCursor().requery();
                mAdapter.notifyDataSetChanged();
            }
        });
    }
    // Insert several artist records
    private void insertArtists() {

        ContentValues values = new ContentValues();

        values.put(DatabaseOpenHelper.ARTIST_NAME, "Lata Mangeshkar");
        mDbHelper.getWritableDatabase().insert(DatabaseOpenHelper.TABLE_NAME, null, values);

        values.clear();

        values.put(DatabaseOpenHelper.ARTIST_NAME, "SP B");
        mDbHelper.getWritableDatabase().insert(DatabaseOpenHelper.TABLE_NAME, null, values);
        values.clear();
        values.put(DatabaseOpenHelper.ARTIST_NAME, "Lady Gaga");
        mDbHelper.getWritableDatabase().insert(DatabaseOpenHelper.TABLE_NAME, null, values);
        values.clear();

        values.put(DatabaseOpenHelper.ARTIST_NAME, "Sonu Neegam");
        mDbHelper.getWritableDatabase().insert(DatabaseOpenHelper.TABLE_NAME, null, values);

        values.clear();

        values.put(DatabaseOpenHelper.ARTIST_NAME, "AR Rehman");
        mDbHelper.getWritableDatabase().insert(DatabaseOpenHelper.TABLE_NAME, null, values);
    }

    // Returns all artist records in the database
    private Cursor readArtists() {
        return mDbHelper.getWritableDatabase().query(DatabaseOpenHelper.TABLE_NAME,
                DatabaseOpenHelper.columns, null, new String[] {}, null, null,
                null);
    }

    // Modify the contents of the database
    private void fix() {

        // Sorry Lady Gaga :-(
        mDbHelper.getWritableDatabase().delete(DatabaseOpenHelper.TABLE_NAME,
                DatabaseOpenHelper.ARTIST_NAME + "=?",
                new String[] { "Lady Gaga" });

        // fix the Man in Black
        ContentValues values = new ContentValues();
        values.put(DatabaseOpenHelper.ARTIST_NAME, "Sonu Nigam");

        mDbHelper.getWritableDatabase().update(DatabaseOpenHelper.TABLE_NAME, values,
                DatabaseOpenHelper.ARTIST_NAME + "=?",
                new String[] { "Sonu Neegam" });

    }

    // Delete all records
    private void clearAll() {

        mDbHelper.getWritableDatabase().delete(DatabaseOpenHelper.TABLE_NAME, null, null);

    }

    // Close database
    @Override
    protected void onDestroy() {

        mDbHelper.getWritableDatabase().close();
        mDbHelper.deleteDatabase();

        super.onDestroy();

    }
}
