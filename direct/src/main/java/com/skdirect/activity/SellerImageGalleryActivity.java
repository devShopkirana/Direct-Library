package com.skdirect.activity;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.skdirect.BuildConfig;
import com.skdirect.R;
import com.skdirect.adapter.ProductImagesAdapter;
import com.skdirect.databinding.FragmentImageShoBinding;
import com.skdirect.databinding.FragmentSellerImageBinding;
import com.skdirect.model.ImageListModel;
import com.skdirect.utils.TextUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class SellerImageGalleryActivity extends AppCompatActivity {
    private FragmentSellerImageBinding mBinding;
    private String irImagesModels, sellerShopName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.fragment_seller_image);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            irImagesModels = getIntent().getStringExtra("ImageData");
            sellerShopName = getIntent().getStringExtra("ShopName");
        }
        initialization();
    }

    private void initialization() {

        if (!TextUtils.isNullOrEmpty(sellerShopName)) {
            mBinding.toolbarTittle.tvTittle.setText(sellerShopName);
        }

        if (irImagesModels != null && !irImagesModels.contains("http")) {
            Picasso.get().load(BuildConfig.apiEndpoint + irImagesModels).into(mBinding.ivItemImage);
        } else {
            Picasso.get().load(irImagesModels).into(mBinding.ivItemImage);
        }

        mBinding.toolbarTittle.arrowToolbar.setOnClickListener(v -> {
            onBackPressed();
        });


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
