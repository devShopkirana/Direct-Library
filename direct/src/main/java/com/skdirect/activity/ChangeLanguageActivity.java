package com.skdirect.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.skdirect.R;
import com.skdirect.adapter.LanguageListAdapter;
import com.skdirect.databinding.ActivityChangeLanguageBinding;
import com.skdirect.interfacee.OnLanguageClick;
import com.skdirect.utils.DirectSDK;
import com.skdirect.utils.SharePrefs;
import com.skdirect.utils.Utils;

import java.util.ArrayList;


public class ChangeLanguageActivity extends AppCompatActivity implements View.OnClickListener, OnLanguageClick {
    private ActivityChangeLanguageBinding mBinding;
    private ArrayList<DataSnapshot> languageList;
    private LanguageListAdapter adapter;
    private ChangeLanguageActivity activity;
    private DataSnapshot dataPostSnapshot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_change_language);
        activity = this;
        initView();
    }

    private void initView() {
        Utils.showProgressDialog(activity);

        mBinding.toolbarTittle.tvTittle.setText(DirectSDK.getInstance().dbHelper.getString(R.string.change_language));
        mBinding.toolbarTittle.ivBackPress.setOnClickListener(this);

        languageList = new ArrayList<>();
        adapter = new LanguageListAdapter(this, languageList, this);
        mBinding.rvLanguage.setAdapter(adapter);

        String selectedLanguage = SharePrefs.getInstance(activity).getString(SharePrefs.SELECTED_LANGUAGE);
        if (selectedLanguage == null || selectedLanguage.equals("")) {
                SharePrefs.getInstance(activity).putString(SharePrefs.SELECTED_LANGUAGE, "English");
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference language = database.getReference();
        language.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                dataPostSnapshot = snapshot;
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    languageList.add(postSnapshot);
                }
                adapter.notifyDataSetChanged();
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
        new Handler().postDelayed(() -> {
            Utils.hideProgressDialog();
        }, 2000);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back_press) {
            onBackPressed();
        }
    }

    @Override
    public void onSelectLanguage(int position) {
        Utils.showProgressDialog(activity);
        DirectSDK.getInstance().dbHelper.deleteAndUpdateTable(dataPostSnapshot);
    }
}