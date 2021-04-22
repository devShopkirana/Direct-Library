package com.skdirect.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.gson.JsonObject;
import com.skdirect.R;
import com.skdirect.api.CommonClassForAPI;
import com.skdirect.databinding.ActivityReviewBinding;
import com.skdirect.model.AddReviewModel;
import com.skdirect.utils.DBHelper;
import com.skdirect.utils.DirectSDK;
import com.skdirect.utils.TextUtils;
import com.skdirect.utils.Utils;

import org.jetbrains.annotations.NotNull;

import io.reactivex.observers.DisposableObserver;

public class ReviewActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityReviewBinding mBinding;
    private int orderID;
    DBHelper dbHelper;
    private CommonClassForAPI commonClassForAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_review);
        dbHelper = DirectSDK.getInstance().dbHelper;
        getIntentData();
        initView();
    }

    private void getIntentData() {
        orderID = getIntent().getIntExtra("OrderID", 0);

    }

    private void initView() {
        mBinding.tvRateExperience.setText(dbHelper.getString(R.string.rate_your_experience));
        mBinding.tvHowWouldRate.setText(dbHelper.getString(R.string.how_would_you_rate_the_seller));
        mBinding.tvComments.setText(dbHelper.getString(R.string.comments));
        mBinding.etEnterComment.setHint(dbHelper.getString(R.string.enter_comments));
        mBinding.btSaveRatting.setText(dbHelper.getString(R.string.save));

        mBinding.toolbarTittle.tvTittle.setText(dbHelper.getString(R.string.review));
        mBinding.toolbarTittle.tvUsingLocation.setVisibility(View.VISIBLE);
        mBinding.toolbarTittle.tvUsingLocation.setText(dbHelper.getString(R.string.order_id) + " " + orderID);
        mBinding.toolbarTittle.ivBackPress.setOnClickListener(this);
        mBinding.btSaveRatting.setOnClickListener(this);
        mBinding.ratingBar.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_back_press) {
            onBackPressed();
        } else if (id == R.id.bt_save_ratting) {
            checkRatting();
        } else if (id == R.id.ratingBar) {
            addRating();
        }
    }

    private void addRating() {


    }

    private void checkRatting() {
        if (mBinding.ratingBar.getRating() == 0.0) {
            Utils.setToast(this, dbHelper.getString(R.string.please_add_rating));
        } else if (TextUtils.isNullOrEmpty(mBinding.etEnterComment.getText().toString().trim())) {
            Utils.setToast(this, dbHelper.getString(R.string.please_enter_comments));
        } else {
            if (Utils.isNetworkAvailable(getApplicationContext())) {
                Utils.showProgressDialog(ReviewActivity.this);
                addReViewAPI();
            } else {
                Utils.setToast(getApplicationContext(), dbHelper.getString(R.string.no_internet_connection));
            }
        }
    }
    private void addReViewAPI() {
        commonClassForAPI = CommonClassForAPI.getInstance(this);
        if (commonClassForAPI!=null) {
            commonClassForAPI.getRating(observer,new AddReviewModel(orderID, mBinding.etEnterComment.getText().toString(), Math.round(mBinding.ratingBar.getRating())));
        }
    }
    private final DisposableObserver<JsonObject> observer = new DisposableObserver<JsonObject>() {
        @Override
        public void onNext(@NotNull JsonObject jsonObject) {
            Utils.hideProgressDialog();
            if (jsonObject.get("IsSuccess").getAsBoolean()) {
                startActivity(new Intent(ReviewActivity.this, MyOrderActivity.class));
                finish();
            }
        }
        @Override
        public void onError(Throwable e) {
            Utils.hideProgressDialog();
            e.printStackTrace();
        }
        @Override
        public void onComplete() {
        }
    };
}
