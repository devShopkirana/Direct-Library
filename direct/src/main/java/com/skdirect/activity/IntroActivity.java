package com.skdirect.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.skdirect.R;
import com.skdirect.databinding.ActivityIntrodBinding;
import com.skdirect.utils.DBHelper;
import com.skdirect.utils.DirectSDK;
import com.skdirect.utils.SharePrefs;

public class IntroActivity extends AppCompatActivity {
    private ActivityIntrodBinding mBinding;
    private IntroActivity activity;
    private DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_introd);
        activity = this;
        dbHelper = DirectSDK.getInstance().dbHelper;
        initViews();
    }

    private void initViews() {


        mBinding.tvSellerHead.setText(dbHelper.getString(R.string.buy_locally_at_best_prices_and_get_it_home_delivered));
        mBinding.tvBuyer.setText(dbHelper.getString(R.string.continue_button));


        mBinding.tvBuyer.setOnClickListener(view -> {
            SharePrefs.getInstance(activity).putBoolean(SharePrefs.IS_SELLER, false);
            SharePrefs.getInstance(activity).putBoolean(SharePrefs.IS_SHOW_INTRO, true);
            Intent i = new Intent(activity, PlaceSearchActivity.class);
            startActivity(i);
            finish();
        });
        mBinding.tvSeller.setOnClickListener(view -> {
            SharePrefs.getInstance(activity).putBoolean(SharePrefs.IS_SELLER, true);
            SharePrefs.getInstance(activity).putBoolean(SharePrefs.IS_SHOW_INTRO, true);
            Intent i = new Intent(activity, PlaceSearchActivity.class);
            startActivity(i);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}