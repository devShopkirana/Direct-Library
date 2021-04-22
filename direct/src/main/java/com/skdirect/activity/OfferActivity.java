package com.skdirect.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.skdirect.R;
import com.skdirect.adapter.OfferListAdapter;
import com.skdirect.api.CommonClassForAPI;
import com.skdirect.databinding.ActivityOfferdBinding;
import com.skdirect.model.response.ApplyOfferResponse;
import com.skdirect.model.response.OfferResponse;
import com.skdirect.utils.DirectSDK;
import com.skdirect.utils.TextUtils;
import com.skdirect.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import io.reactivex.observers.DisposableObserver;

public class OfferActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityOfferdBinding mBinding;

    private ArrayList<OfferResponse.Coupon> list;
    private OfferListAdapter adapter;
    private CommonClassForAPI commonClassForAPI;
    private int position = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_offerd);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(DirectSDK.getInstance().dbHelper.getString(R.string.apply_coupon));
        mBinding.etOffer.setHint(DirectSDK.getInstance().dbHelper.getString(R.string.enter_promo_code));
        mBinding.btnApply.setText(DirectSDK.getInstance().dbHelper.getString(R.string.apply));
        mBinding.tvAvailableOfferTitle.setText(DirectSDK.getInstance().dbHelper.getString(R.string.available_offers));
        mBinding.tvEmpty.setText(DirectSDK.getInstance().dbHelper.getString(R.string.no_offer_found));

        initViews();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (TextUtils.isNullOrEmpty(mBinding.etOffer.getText().toString())) {
            Utils.setToast(getApplicationContext(), DirectSDK.getInstance().dbHelper.getString(R.string.enter_coupon_code));
        } else {
            callApplyCoupon(0, 0);
        }
    }


    private void initViews() {
        mBinding.btnApply.setOnClickListener(this);

        list = new ArrayList<>();
        adapter = new OfferListAdapter(this, list);
        mBinding.rvOffer.setAdapter(adapter);

        commonClassForAPI = CommonClassForAPI.getInstance(this);
        commonClassForAPI.getCouponList(couponObserver, DirectSDK.getInstance().cartRepository.getCartSellerId());
    }

    public void callApplyCoupon(int position, int id) {
        this.position = position;
        Utils.showProgressDialog(this);
        commonClassForAPI.applyCoupon(applyCouponObserver, id);
    }


    private final DisposableObserver<OfferResponse> couponObserver = new DisposableObserver<OfferResponse>() {
        @Override
        public void onNext(@NotNull OfferResponse model) {
            mBinding.progressOffer.setVisibility(View.GONE);
            mBinding.tvEmpty.setVisibility(View.GONE);
            try {
                if (model != null && model.isSuccess()) {
                    list.addAll(model.getResultItem());
                    adapter.notifyDataSetChanged();
                } else {
                    mBinding.tvEmpty.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            mBinding.progressOffer.setVisibility(View.INVISIBLE);
            mBinding.tvEmpty.setVisibility(View.VISIBLE);
        }

        @Override
        public void onComplete() {

        }
    };

    private final DisposableObserver<ApplyOfferResponse> applyCouponObserver = new DisposableObserver<ApplyOfferResponse>() {
        @Override
        public void onNext(@NotNull ApplyOfferResponse model) {
            Utils.hideProgressDialog();
            try {
                if (model != null && model.isSuccess()) {
                    Utils.setToast(getApplicationContext(), model.getSuccessMessage());

                    Intent intent = new Intent();
                    intent.putExtra("list", list.get(position));
                    setResult(Activity.RESULT_OK, intent);
                    onBackPressed();
                } else {
                    Utils.setToast(getApplicationContext(), model.getErrorMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            Utils.hideProgressDialog();
        }

        @Override
        public void onComplete() {
            Utils.hideProgressDialog();
        }
    };
}