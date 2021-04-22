package com.skdirect.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;

import com.google.firebase.database.DataSnapshot;
import com.skdirect.activity.SplashActivity;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "DLanguageDB";
    public static final String TABLE_NAME = "LanguageTable";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_KEY = "keystring";
    public static final String COLUMN_VALUE = "value";
    private final Context context;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (id integer primary key, keystring text,value text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS stringTable");
        onCreate(db);
    }


    public void insertStrings(String key, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_KEY, key);
        contentValues.put(COLUMN_VALUE, value);
        db.insert(TABLE_NAME, null, contentValues);
    }

    public String getString(int key) {
        String value = "";
        String stringKey = context.getResources().getResourceEntryName(key);
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("select * from LanguageTable where keystring= '" + stringKey + "'", null);
            if (cur != null && cur.getCount() > 0) {
                cur.moveToFirst();
                value = cur.getString(cur.getColumnIndex(COLUMN_VALUE));
                cur.close();
            } else {
                value = context.getResources().getString(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
            value = context.getResources().getString(key);
        }
        return value;
    }


    public boolean updateTableValue(String key, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_KEY, key);
        contentValues.put(COLUMN_VALUE, value);
        db.update(TABLE_NAME, contentValues, "keystring = ? ", new String[]{key});
        return true;
    }

    public Integer deleteTable(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public void deleteDataFromTable() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                SQLiteDatabase db = DBHelper.this.getWritableDatabase();
                db.delete(TABLE_NAME, null, null);
                return null;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void deleteAndUpdateTable(DataSnapshot dataPostSnapshot) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                SQLiteDatabase db = DBHelper.this.getWritableDatabase();
                db.delete(TABLE_NAME, null, null);

                for (DataSnapshot postSnapshot : dataPostSnapshot.getChildren()) {
                    String selectedLanguage = SharePrefs.getInstance(DirectSDK.getInstance().context).getString(SharePrefs.SELECTED_LANGUAGE);
                    if (selectedLanguage.equals(postSnapshot.getKey())) {
                        for (DataSnapshot langSnapshot : postSnapshot.getChildren()) {
                            DirectSDK.getInstance().dbHelper.insertStrings(langSnapshot.getKey(), langSnapshot.getValue() + "");
                        }
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Utils.hideProgressDialog();
                context.startActivity(new Intent(context, SplashActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}