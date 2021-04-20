package com.skdirect.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.gson.JsonObject;
import com.skdirect.R;
import com.skdirect.adapter.DeliveryOptionAdapter;
import com.skdirect.databinding.ActivityPaymentBinding;
import com.skdirect.interfacee.DeliveryOptionInterface;
import com.skdirect.model.CartItemModel;
import com.skdirect.model.DeliveryMainModel;
import com.skdirect.model.DeliveryOptionModel;
import com.skdirect.model.OrderPlaceRequestModel;
import com.skdirect.model.UserLocationModel;
import com.skdirect.model.response.OfferResponse;
import com.skdirect.utils.MyApplication;
import com.skdirect.utils.SharePrefs;
import com.skdirect.utils.Utils;
import com.skdirect.viewmodel.PaymentViewMode;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener, DeliveryOptionInterface {
    private ActivityPaymentBinding mBinding;
    private PaymentViewMode paymentViewMode;
    private CartItemModel cartItemModel;
    private final ArrayList<DeliveryOptionModel> deliveryOptionList = new ArrayList<>();
    private DeliveryOptionAdapter deliveryOptionAdapter;
    private OfferResponse.Coupon coupon;
    private int deliveryOption;
    private Integer userLocationId = null;
    private double cartTotal, totalAmount, discount = 0;
    private boolean isSelfPickup = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_payment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(MyApplication.getInstance().dbHelper.getString(R.string.title_activity_payment));

        paymentViewMode = ViewModelProviders.of(this).get(PaymentViewMode.class);
        getSharedData();
        initView();
        callUserLocation();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnRemove) {
            updateViews(false, coupon);
        } else if (id == R.id.btnOffer) {
            startActivityForResult(new Intent(getApplicationContext(), OfferActivity.class), 9);
        } else if (id == R.id.btnPlaceOrder) {
            if ((userLocationId != null && userLocationId != 0) || isSelfPickup) {
                orderPlaceAlertDialog();
            } else {
                startActivityForResult(new Intent(getApplicationContext(),
                        PrimaryAddressActivity.class).putExtra("UserLocationId", userLocationId), 2);
                //Utils.setToast(getApplicationContext(), MyApplication.getInstance().dbHelper.getString(R.string.please_select_address));
            }
        } else if (id == R.id.btnAdd) {
            startActivityForResult(new Intent(getApplicationContext(),
                    PrimaryAddressActivity.class).putExtra("UserLocationId", userLocationId), 2);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 2 && resultCode == RESULT_OK) {
            UserLocationModel userLocationModel = (UserLocationModel) data.getSerializableExtra("address");
            userLocationId = userLocationModel.getId();
            mBinding.tvShopName.setText(userLocationModel.getAddressOne());
            mBinding.tvAddresh.setText(userLocationModel.getAddressTwo());
            mBinding.tvAddreshTree.setText(userLocationModel.getAddressThree());
            mBinding.tvCityName.setText(userLocationModel.getCity() + " - " + userLocationModel.getPincode() + " (" + userLocationModel.getState() + ")");
            mBinding.liAddress.setVisibility(View.VISIBLE);
            mBinding.btnAdd.setText(MyApplication.getInstance().dbHelper.getString(R.string.change));
            mBinding.btnPlaceOrder.setClickable(true);
            mBinding.btnPlaceOrder.setBackgroundResource(R.drawable.rounded_drawer);
            mBinding.btnPlaceOrder.setTextColor(Color.parseColor("#58ccc1"));
        } else if (requestCode == 9 && resultCode == Activity.RESULT_OK) {
            mBinding.liCoupon.setVisibility(View.VISIBLE);
            coupon = data.getParcelableExtra("list");
            if (cartTotal > coupon.getMinOrderValue()) {
                discount = coupon.getAmount();
                totalAmount = cartTotal - discount;
                updateViews(true, coupon);
            }
        }
    }


    private void initView() {
        mBinding.tvOrderValueH.setText(MyApplication.getInstance().dbHelper.getString(R.string.total_order_value));
        mBinding.tvAmountH.setText(MyApplication.getInstance().dbHelper.getString(R.string.total_amount));
        mBinding.tvItemPrice.setText(MyApplication.getInstance().dbHelper.getString(R.string.price_details));
        mBinding.tvAddress.setText(MyApplication.getInstance().dbHelper.getString(R.string.address));
        mBinding.tvCodTitle.setText(MyApplication.getInstance().dbHelper.getString(R.string.cash_on_delivery));
        mBinding.tvDOption.setText(MyApplication.getInstance().dbHelper.getString(R.string.delivery_option));
        mBinding.btnPlaceOrder.setText(MyApplication.getInstance().dbHelper.getString(R.string.place_order));
        mBinding.tvSelectPromoTitle.setText(MyApplication.getInstance().dbHelper.getString(R.string.select_a_promo_code));
        mBinding.btnOffer.setText(MyApplication.getInstance().dbHelper.getString(R.string.view_offer));
        mBinding.tvPaymentTitle.setText(MyApplication.getInstance().dbHelper.getString(R.string.title_activity_payment));
        mBinding.btnAdd.setText(MyApplication.getInstance().dbHelper.getString(R.string.change));
        mBinding.tvDeleveryCharge.setText(MyApplication.getInstance().dbHelper.getString(R.string.delivery_charge));
        mBinding.btnOffer.setOnClickListener(this);
        mBinding.btnRemove.setOnClickListener(this);
        mBinding.btnPlaceOrder.setOnClickListener(this);
        mBinding.btnAdd.setOnClickListener(this);
    }

    private void getSharedData() {
        cartItemModel = (CartItemModel) getIntent().getSerializableExtra("cartItemSize");
        cartTotal = getIntent().getDoubleExtra("totalAmount", 0);
        totalAmount = cartTotal;

        Log.e("Total Amount ", "##### " + cartItemModel.getTotalAmount());

        mBinding.tvItemPrice.setText("Price Details ( " + cartItemModel.getCart().size() + " items)");
        mBinding.tvOrderValue.setText("₹ " + cartTotal);

        if (cartItemModel.getDeliveryChargePerOrder() != 0.0 && cartItemModel.getDeliveryChargePerOrder() != 0) {
            totalAmount = totalAmount + cartItemModel.getDeliveryChargePerOrder();
            mBinding.tvDeleveryChargeValue.setText(new DecimalFormat("##.##").format(cartItemModel.getDeliveryChargePerOrder()));
        } else {
            mBinding.tvDeleveryChargeValue.setText("Free");
        }
        mBinding.tvTotalAmount.setText("₹ " + totalAmount);
        mBinding.tvTotal.setText("₹ " + totalAmount);

    }

    private void callUserLocation() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            Utils.showProgressDialog(PaymentActivity.this);
            userLocationAPI();
            deliveryOptionAPI();
            checkOutItemApi();
        } else {
            Utils.setToast(getApplicationContext(), "No Internet Connection Please connect.");
        }
    }

    public void orderPlaceAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(MyApplication.getInstance().dbHelper.getString(R.string.Confirmation));
        builder.setMessage(MyApplication.getInstance().dbHelper.getString(R.string.order_place));
        builder.setPositiveButton(MyApplication.getInstance().dbHelper.getString(R.string.yes), (dialog, which) -> {
            if (Utils.isNetworkAvailable(getApplicationContext())) {
                Utils.showProgressDialog(this);
                orderPlaceAPI();
            } else {
                Utils.setToast(getApplicationContext(), "No Internet Connection Please connect.");
            }
        });

        builder.setNegativeButton(MyApplication.getInstance().dbHelper.getString(R.string.no), (dialog, which) -> {

        });

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    private void orderPlaceAPI() {
        if (isSelfPickup) {
            userLocationId = null;
        }
        OrderPlaceRequestModel orderPlaceRequestModel = new OrderPlaceRequestModel("CASH", deliveryOption, cartItemModel.getId(), userLocationId, SharePrefs.getInstance(this).getString(SharePrefs.MALL_ID));
        paymentViewMode.getOrderPlaceVMRequest(orderPlaceRequestModel);
        paymentViewMode.getOrderPlaceVM().observe(this, response -> {
            Utils.hideProgressDialog();
            if (response.isSuccess()) {
                // clear cart
                MyApplication.getInstance().cartRepository.truncateCart();
                orderPlaceDialog();
            } else {
                Utils.setToast(getApplicationContext(), response.getErrorMessage());
                if (response.getErrorMessage().equalsIgnoreCase("Currently we are not serving your Area") || response.getErrorMessage().equalsIgnoreCase("We don't deliver to your address. Please change your address")) {
                    mBinding.btnPlaceOrder.setClickable(false);
                    mBinding.btnPlaceOrder.setBackgroundResource(R.drawable.rounded_drawer_desable);
                    mBinding.btnPlaceOrder.setTextColor(Color.WHITE);
                }
            }
        });
    }

    private void orderPlaceDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(MyApplication.getInstance().dbHelper.getString(R.string.congratulation));
        dialog.setMessage(MyApplication.getInstance().dbHelper.getString(R.string.order_place__popoup));
        dialog.setPositiveButton(MyApplication.getInstance().dbHelper.getString(R.string.ok),
                (dialog1, which) -> {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
                });
        dialog.show();
    }

    private void updateViews(boolean isApplied, OfferResponse.Coupon coupon) {
        if (isApplied) {
            //mBinding.rlApplyOffer.setVisibility(View.GONE);
            mBinding.liOffer.setVisibility(View.VISIBLE);
            mBinding.liCoupon.setVisibility(View.VISIBLE);
            mBinding.tvOffer.setText("Code " + coupon.getCouponCode() + " Applied");
            mBinding.tvDes.setText(coupon.getAmount() + " OFF up to ₹" + coupon.getMaxAmount());
            mBinding.tvOfferAmount.setText("-₹ " + discount);
            mBinding.tvOfferTotal.setText("-₹ " + discount);
        } else {
            cartTotal = totalAmount;
            // mBinding.rlApplyOffer.setVisibility(View.VISIBLE);
            mBinding.liOffer.setVisibility(View.GONE);
            mBinding.liCoupon.setVisibility(View.GONE);
        }
        mBinding.tvTotalAmount.setText("₹ " + totalAmount);
        mBinding.tvTotal.setText("₹ " + totalAmount);
    }

    private void userLocationAPI() {
        paymentViewMode.getUserLocationVMRequest();
        paymentViewMode.getUserLocationVM().observe(this, locationModel -> {
            Utils.hideProgressDialog();
            if (locationModel.isSuccess() && locationModel.getResultItem().size() > 0) {
                for (int i = 0; i < locationModel.getResultItem().size(); i++) {
                    if (locationModel.getResultItem().get(i).isPrimaryAddress()) {
                        userLocationId = locationModel.getResultItem().get(i).getId();
                        mBinding.tvShopName.setText(locationModel.getResultItem().get(i).getAddressOne());
                        mBinding.tvAddresh.setText(locationModel.getResultItem().get(i).getAddressTwo());
                        mBinding.tvAddreshTree.setText(locationModel.getResultItem().get(i).getAddressThree());
                        mBinding.tvCityName.setText(locationModel.getResultItem().get(i).getCity() + " - " + locationModel.getResultItem().get(i).getPincode() + " (" + locationModel.getResultItem().get(i).getState() + ")");
                    }
                }
            } else {
                mBinding.liAddress.setVisibility(View.GONE);
                mBinding.btnAdd.setText(MyApplication.getInstance().dbHelper.getString(R.string.add_new_address));
            }
        });
    }

    private void deliveryOptionAPI() {
        paymentViewMode.getDeliveryOptionVMRequest(cartItemModel.getSellerId());
        paymentViewMode.getDeliveryOptionVM().observe(this, new Observer<DeliveryMainModel>() {
            @Override
            public void onChanged(DeliveryMainModel deliveryMainModel) {
                Utils.hideProgressDialog();
                if (deliveryMainModel.isSuccess()) {
                    deliveryOption = deliveryMainModel.getResultItem().get(0).getId();
                    if (deliveryMainModel.getResultItem().get(0).getDelivery().equals("Self Pickup")) {
                        deliveryOption = deliveryMainModel.getResultItem().get(0).getId();
                        mBinding.liAddressV.setVisibility(View.GONE);
                        isSelfPickup = true;
                    }
                    deliveryOptionList.addAll(deliveryMainModel.getResultItem());
                    deliveryOptionAdapter = new DeliveryOptionAdapter(getApplicationContext(), deliveryOptionList, PaymentActivity.this);
                    mBinding.rvDeliveryOption.setAdapter(deliveryOptionAdapter);
                }
            }
        });
    }

    private void checkOutItemApi() {
        paymentViewMode.getCheckOutItemVMRequest();
        paymentViewMode.getCheckOutItemVM().observe(this, new Observer<JsonObject>() {
            @Override
            public void onChanged(JsonObject aBoolean) {
                Utils.hideProgressDialog();

            }
        });
    }


    @Override
    public void onOnClick(DeliveryOptionModel deliveryOptionModel, int position) {
        deliveryOption = deliveryOptionModel.getId();
        if (deliveryOptionModel.getDelivery().equals("Self Pickup")) {
            mBinding.liAddressV.setVisibility(View.GONE);
            isSelfPickup = true;
        } else {
            mBinding.liAddressV.setVisibility(View.VISIBLE);
            isSelfPickup = false;
        }
    }
}