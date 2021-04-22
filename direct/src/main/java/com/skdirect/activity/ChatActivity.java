package com.skdirect.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.skdirect.R;
import com.skdirect.databinding.FragmentChatBinding;
import com.skdirect.utils.DBHelper;
import com.skdirect.utils.DirectSDK;

public class ChatActivity  extends AppCompatActivity {
    private FragmentChatBinding mBinding;
    public DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.fragment_chat);
        dbHelper = DirectSDK.getInstance().dbHelper;
        initViews();
    }

    private void initViews() {
        dbHelper = DirectSDK.getInstance().dbHelper;
        mBinding.toolbarTittle.tvTittle.setText(dbHelper.getString(R.string.chat));
        mBinding.toolbarTittle.ivBackPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mBinding.tvCmngSoon.setText(dbHelper.getString(R.string.coming_soon));

    }
}
