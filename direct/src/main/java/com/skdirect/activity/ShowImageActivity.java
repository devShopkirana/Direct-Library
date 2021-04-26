package com.skdirect.activity;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;


import com.skdirect.R;
import com.skdirect.adapter.ProductImagesAdapter;
import com.skdirect.databinding.FragmentImageShowdBinding;
import com.skdirect.model.ImageListModel;
import com.skdirect.utils.DirectSDK;

import java.util.ArrayList;


public class ShowImageActivity extends AppCompatActivity {
    private FragmentImageShowdBinding mBinding;
    private ArrayList<ImageListModel> irImagesModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.fragment_image_showd);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            irImagesModels = (ArrayList<ImageListModel>) getIntent().getSerializableExtra("ImageData");
        }
        initialization();
    }

    private void initialization() {

        mBinding.toolbarTittle.tvTittle.setText(DirectSDK.getInstance().dbHelper.getString(R.string.product));
        mBinding.toolbarTittle.arrowToolbar.setOnClickListener(v -> {
            onBackPressed();
        });

        ViewPager mPager = mBinding.pager;
        mPager.setAdapter(new ProductImagesAdapter(this, irImagesModels));
    }


    @Override
    public void applyOverrideConfiguration(Configuration overrideConfiguration) {
        if (overrideConfiguration != null) {
            int uiMode = overrideConfiguration.uiMode;
            overrideConfiguration.setTo(getBaseContext().getResources().getConfiguration());
            overrideConfiguration.uiMode = uiMode;
        }
        super.applyOverrideConfiguration(overrideConfiguration);
    }
}
