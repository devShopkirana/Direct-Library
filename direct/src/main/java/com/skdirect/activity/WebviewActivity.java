package com.skdirect.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;

import com.skdirect.R;
import com.skdirect.databinding.ActivityWebviewBinding;
import com.skdirect.utils.DirectSDK;
import com.skdirect.utils.SharePrefs;
import com.skdirect.utils.TextUtils;

public class WebviewActivity extends AppCompatActivity {
    ActivityWebviewBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_webview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable( new ColorDrawable( getResources().getColor(R.color.colorAccentDir )));

        if (getIntent().getExtras() != null) {
            if(getIntent().getStringExtra("FunctionName").equalsIgnoreCase("PrivacyPolicy"))
            {
               String privacy = SharePrefs.getInstance(this).getString(SharePrefs.PRIVACY_POLICY);
               if(!TextUtils.isNullOrEmpty(privacy))
               {
                   mBinding.webView.loadUrl(privacy);
               }
                setTitle(DirectSDK.getInstance().dbHelper.getString(R.string.webview_title_name_privacy));
            } else if(getIntent().getStringExtra("FunctionName").equalsIgnoreCase("TermsCondition"))
            {
                String terms = SharePrefs.getInstance(this).getString(SharePrefs.TERMS_CONDITION);
                if(!TextUtils.isNullOrEmpty(terms))
                {
                    mBinding.webView.loadUrl(terms);
                }
                setTitle(DirectSDK.getInstance().dbHelper.getString(R.string.terms_and_condition));
            } else
            {
                String aboutApp = SharePrefs.getInstance(this).getString(SharePrefs.ABOUT_APP);
                if(!TextUtils.isNullOrEmpty(aboutApp))
                {
                    mBinding.webView.loadUrl(aboutApp);
                }
                setTitle(DirectSDK.getInstance().dbHelper.getString(R.string.webview_title_name_about_direct));
            }
        }

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}