package com.skdirect.activity;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.skdirect.R;
import com.skdirect.adapter.ViewPagerAdapter;
import com.skdirect.databinding.ActivitySearchBinding;
import com.skdirect.fragment.ProductFragment;
import com.skdirect.fragment.ShopFragment;
import com.skdirect.interfacee.SearchInterface;
import com.skdirect.utils.DBHelper;
import com.skdirect.utils.MyApplication;
import com.skdirect.utils.TextUtils;
import com.skdirect.utils.Utils;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivitySearchBinding mBinding;

    private String searchSellerName;
    private int allCategoriesID;
    private DBHelper dbHelper;
    public SearchInterface searchInterface, searchInterfaceS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        dbHelper = MyApplication.getInstance().dbHelper;
        getIntentData();
        initView();
        setupViewPager(mBinding.viewpager);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_back_press) {
            onBackPressed();
        }
    }


    private void initView() {
        mBinding.etSearchSeller.setHint(dbHelper.getString(R.string.search_seller_by_name_or_product));
        mBinding.toolbarTittle.tvTittle.setText(dbHelper.getString(R.string.product_list));
        mBinding.toolbarTittle.ivBackPress.setOnClickListener(this);

        mBinding.etSearchSeller.setText(searchSellerName);
        mBinding.tabs.setupWithViewPager(mBinding.viewpager);

        mBinding.etSearchSeller.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                if (!TextUtils.isNullOrEmpty(mBinding.etSearchSeller.getText().toString())) {
                    searchInterface.onSearchClick(mBinding.etSearchSeller.getText().toString(), allCategoriesID);
                    searchInterfaceS.onSearchClick(mBinding.etSearchSeller.getText().toString(), allCategoriesID);
                    Utils.hideKeyboard(SearchActivity.this);
                }
                return true;
            }
            return false;
        });
    }

    public void getIntentData() {
        searchSellerName = getIntent().getStringExtra("searchSellerName");
        allCategoriesID = getIntent().getIntExtra("CateogryId", 0);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new ProductFragment(searchSellerName, allCategoriesID), dbHelper.getString(R.string.product));
        adapter.addFragment(new ShopFragment(searchSellerName, allCategoriesID), dbHelper.getString(R.string.shop));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
    }
}