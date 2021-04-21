package com.skdirect.firebase;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.skdirect.utils.MySingltonApplication;
import com.skdirect.utils.SharePrefs;


public class FirebaseLanguageFetch {
    private final Context context;

    public FirebaseLanguageFetch(Context context) {
        this.context = context;
    }

    public void fetchLanguage() {
        try {
            new UpdateLanguageAsync().execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class UpdateLanguageAsync extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            MySingltonApplication.getInstance().dbHelper.deleteDataFromTable();
            DatabaseReference language = database.getReference();
            language.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Log.e("Count-", dataSnapshot.getChildrenCount() + "");
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        String selectedLanguage = SharePrefs.getInstance(context).getString(SharePrefs.SELECTED_LANGUAGE);
                        if (selectedLanguage == null || selectedLanguage.equals("")) {
                            if (postSnapshot.getKey().equals("English")) {
                                SharePrefs.getInstance(context).putString(SharePrefs.SELECTED_LANGUAGE, "English");
                                for (DataSnapshot langSnapshot : postSnapshot.getChildren()) {
                                    MySingltonApplication.getInstance().dbHelper.insertStrings(langSnapshot.getKey(), langSnapshot.getValue() + "");
                                }
                            }
                        } else {
                            if (selectedLanguage.equals(postSnapshot.getKey())) {
                                for (DataSnapshot langSnapshot : postSnapshot.getChildren()) {
                                    MySingltonApplication.getInstance().dbHelper.insertStrings(langSnapshot.getKey(), langSnapshot.getValue() + "");
                                }
                            }

                        }
                    }
                    SharePrefs.getInstance(context).putBoolean(SharePrefs.IS_FETCH_LANGUAGE, false);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

        }
    }
}