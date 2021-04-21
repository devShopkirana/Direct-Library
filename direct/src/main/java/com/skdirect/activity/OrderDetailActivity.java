package com.skdirect.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.skdirect.R;
import com.skdirect.adapter.OrderDetailsItemAdapter;
import com.skdirect.adapter.OrderStatusStepperAdapter;
import com.skdirect.databinding.ActivityOrderDeatilsBinding;
import com.skdirect.model.MallMainModelBolleanResult;
import com.skdirect.model.MyOrderModel;
import com.skdirect.model.OrderDetailsModel;
import com.skdirect.model.OrderItemModel;
import com.skdirect.model.OrderStatusDC;
import com.skdirect.stepform.MainStepperAdapter;
import com.skdirect.utils.DBHelper;
import com.skdirect.utils.MySingltonApplication;
import com.skdirect.utils.Utils;
import com.skdirect.viewmodel.OrderDetailsViewMode;

import java.util.ArrayList;

public class OrderDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityOrderDeatilsBinding mBinding;
    private MyOrderModel myOrderModel;
    private OrderDetailsViewMode orderDetailsViewMode;
    private final ArrayList<OrderStatusDC> OrderStatusDCList = new ArrayList<>();
    private MainStepperAdapter mainStepperAdapter;
    private OrderStatusStepperAdapter orderStatusStepperAdapter;
    private DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_order_deatils);
        orderDetailsViewMode = ViewModelProviders.of(this).get(OrderDetailsViewMode.class);
        dbHelper = MySingltonApplication.getInstance().dbHelper;
        getIntentData();
        initView();
        callOrderDetails();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_back_press) {
            onBackPressed();
        } else if (id == R.id.tv_cancle_order) {
            cancleOrder();
        }
    }


    private void initView() {
        mBinding.toolbarTittle.tvTittle.setText(dbHelper.getString(R.string.my_order));
        mBinding.toolbarTittle.ivBackPress.setOnClickListener(this);
        mBinding.tvCancleOrder.setOnClickListener(this);

        mBinding.tvShippingDetails.setText(dbHelper.getString(R.string.shipping_details));
        mBinding.tvItemCount.setText(dbHelper.getString(R.string.price_details));
        mBinding.tvOrderAmountTitle.setText(dbHelper.getString(R.string.order_amount));
        mBinding.tvTotalSavingTitle.setText(dbHelper.getString(R.string.total_saving));
        mBinding.tvDeliveryChargeTitle.setText(dbHelper.getString(R.string.delivery_charge));
        mBinding.tvTotalAmountTitle.setText(dbHelper.getString(R.string.total_amount));
        mBinding.tvCancleOrder.setText(dbHelper.getString(R.string.cancel_order));

        mBinding.rMyOrder.setNestedScrollingEnabled(false);

    }

    private void getIntentData() {
        myOrderModel = (MyOrderModel) getIntent().getSerializableExtra("myOrderModel");

    }

    private void callOrderDetails() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            Utils.showProgressDialog(OrderDetailActivity.this);
            callOrderDetailsAPI();
            callOrderItemsAPI();
        } else {
            Utils.setToast(getApplicationContext(), dbHelper.getString(R.string.no_internet_connection));
        }
    }

    private void callOrderDetailsAPI() {
        orderDetailsViewMode.getOrderDetailsRequest(myOrderModel.getId());
        orderDetailsViewMode.getOrderDetailsViewMode().observe(this, new Observer<OrderDetailsModel>() {
            @Override
            public void onChanged(OrderDetailsModel orderDetailsModel) {
                Utils.hideProgressDialog();
                if (orderDetailsModel != null) {

                    OrderStatusDCList.add(new OrderStatusDC(orderDetailsModel.getOrderDate(), "Ordered"));
                    OrderStatusDCList.addAll(orderDetailsModel.getOrderStatusDC());
                    mBinding.tvOrderNumber.setText(dbHelper.getString(R.string.order_no) + " " + orderDetailsModel.getId());
                    mBinding.tvCreatedOrder.setText(dbHelper.getString(R.string.order_on) + " : " + Utils.getDateFormate(orderDetailsModel.getOrderDate()));
                    mBinding.tvSellerName.setText(dbHelper.getString(R.string.shop_name) + " : " + orderDetailsModel.getShopName());
                    mBinding.tvPaymentType.setText(dbHelper.getString(R.string.payment_mode) + " :  " + orderDetailsModel.getPaymentMode());
                    mBinding.tvAddreshOne.setText(orderDetailsModel.getAddressOne());
                    mBinding.tvSellerAddress.setText(orderDetailsModel.getAddressThree());
                    mBinding.tvCity.setText(orderDetailsModel.getCity());
                    mBinding.tvState.setText(orderDetailsModel.getState());
                    mBinding.tvOrderAmount.setText("₹ " + orderDetailsModel.getTotalPrice());
                    mBinding.tvOrderAmountTotal.setText("₹ " + orderDetailsModel.getTotalItemAmount());

                    if (orderDetailsModel.getTotalSavingAmount() != 0.0) {
                        mBinding.rlTotalSaving.setVisibility(View.VISIBLE);
                        mBinding.tvTotalSaving.setText("-₹ " + orderDetailsModel.getTotalSavingAmount());
                        double totalSavingDiscountAmount = orderDetailsModel.getTotalPrice() + orderDetailsModel.getTotalSavingAmount();
                        mBinding.tvOrderAmount.setText("₹ " + totalSavingDiscountAmount);
                    } else {
                        mBinding.rlTotalSaving.setVisibility(View.GONE);
                    }

                    if (orderDetailsModel.getTotalDeliveryCharge() > 0.0) {
                        mBinding.rlDeliveryCharge.setVisibility(View.VISIBLE);
                        double totalSavingDiscountAmount = orderDetailsModel.getTotalItemAmount();
                        double deliveryCharge = orderDetailsModel.getTotalPrice() + orderDetailsModel.getTotalDeliveryCharge();
                        mBinding.tvOrderAmount.setText("₹ " + totalSavingDiscountAmount);
                        mBinding.tvTotalSaving.setText("-₹ " + orderDetailsModel.getTotalSavingAmount());
                        mBinding.tvDeliveryCharge.setText("" + orderDetailsModel.getTotalDeliveryCharge());
                        mBinding.tvOrderAmountTotal.setText("₹ " + orderDetailsModel.getTotalPrice());
                    } else {
                        mBinding.rlDeliveryCharge.setVisibility(View.GONE);
                    }


                }

                if (orderDetailsModel.getOrderStatusDC().size() > 0) {
                    for (int i = 0; i < orderDetailsModel.getOrderStatusDC().size(); i++) {

                        if (orderDetailsModel.getOrderStatusDC().get(i).getStatus().equals("Pending")) {
                            mBinding.tvCancleOrder.setVisibility(View.VISIBLE);
                        } else if (orderDetailsModel.getOrderStatusDC().get(i).getStatus().equals("Accepted")) {
                            mBinding.tvCancleOrder.setVisibility(View.VISIBLE);
                        } else {
                            mBinding.tvCancleOrder.setVisibility(View.GONE);
                        }
                    }
                    orderStatusStepperAdapter = new OrderStatusStepperAdapter(OrderDetailActivity.this, OrderStatusDCList);
                    mBinding.rvOrderSteps.setAdapter(orderStatusStepperAdapter);
                    //setFullHeight();
                } else {

                }
            }

        });
    }

    private void callOrderItemsAPI() {
        orderDetailsViewMode.getOrderDetailsItemsRequest(myOrderModel.getId());
        orderDetailsViewMode.getOrderDetailsItemsViewMode().observe(this, new Observer<ArrayList<OrderItemModel>>() {
            @Override
            public void onChanged(ArrayList<OrderItemModel> orderItemModels) {
                Utils.hideProgressDialog();
                if (orderItemModels.size() > 0) {
                    OrderDetailsItemAdapter orderDetailsItemAdapter = new OrderDetailsItemAdapter(OrderDetailActivity.this, orderItemModels);
                    mBinding.rMyOrder.setAdapter(orderDetailsItemAdapter);
                    mBinding.tvItemCount.setText(dbHelper.getString(R.string.price_details_order) + orderItemModels.size() + " " + dbHelper.getString(R.string.items_order));
                }

            }

        });
    }

    private void cancleOrder() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            Utils.showProgressDialog(OrderDetailActivity.this);
            orderCancleFromUser();
        } else {
            Utils.setToast(getApplicationContext(), dbHelper.getString(R.string.no_internet_connection));
        }
    }

    private void orderCancleFromUser() {
        orderDetailsViewMode.getCancelOrderVMRequest(myOrderModel.getId());
        orderDetailsViewMode.getCancelOrderVM().observe(this, new Observer<MallMainModelBolleanResult>() {
            @Override
            public void onChanged(MallMainModelBolleanResult result) {
                Utils.hideProgressDialog();
                if (result.isSuccess()) {
                    Utils.setToast(getApplicationContext(), result.getSuccessMessage());
                    onBackPressed();
                } else {
                    Utils.setToast(getApplicationContext(), result.getErrorMessage());
                }
            }

        });

    }
}