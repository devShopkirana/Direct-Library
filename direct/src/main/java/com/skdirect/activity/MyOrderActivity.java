package com.skdirect.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.skdirect.R;
import com.skdirect.adapter.DynamicTabAdapter;
import com.skdirect.api.CommonClassForAPI;
import com.skdirect.databinding.ActivityMyOrderBinding;
import com.skdirect.model.OrderStatusDetails;
import com.skdirect.model.OrderStatusMainModel;
import com.skdirect.utils.DBHelper;
import com.skdirect.utils.MySingltonApplication;
import com.skdirect.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import io.reactivex.observers.DisposableObserver;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class MyOrderActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMyOrderBinding mBinding;
    public DBHelper dbHelper;
    private CommonClassForAPI commonClassForAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_order);
        commonClassForAPI = CommonClassForAPI.getInstance(this);
        initView();

    }

    private void initView() {
        dbHelper = MySingltonApplication.getInstance().dbHelper;
        mBinding.toolbarTittle.tvTittle.setText(dbHelper.getString(R.string.my_order));
        mBinding.toolbarTittle.ivBackPress.setOnClickListener(this);
        //mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
        if (commonClassForAPI!=null) {
            Utils.showProgressDialog(this);
            commonClassForAPI.getAllOrderStatusList(observer);
        }
    }

    private final DisposableObserver<OrderStatusMainModel> observer = new DisposableObserver<OrderStatusMainModel>() {
        @Override
        public void onNext(@NotNull OrderStatusMainModel orderStatusMainModel) {
            Utils.hideProgressDialog();
            try {
                if (orderStatusMainModel.isSuccess()) {
                    ArrayList<String> orderStatus = new ArrayList<>();
                    ArrayList<OrderStatusDetails> list = orderStatusMainModel.getResultItem();
                    for (int i = 0; i <list.size() ; i++) {
                        orderStatus.add(list.get(i).getStatus());
                    }
                    setupViewPager(mBinding.viewPager,orderStatus);
                }
            } catch (Exception e) {
                e.printStackTrace();
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_back_press) {
            onBackPressed();
        }

    }

    private void setupViewPager(ViewPager viewPager, ArrayList<String> statusList) {
        DynamicTabAdapter adapter = new DynamicTabAdapter(MyOrderActivity.this.getSupportFragmentManager(),
                BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, statusList);
        viewPager.setAdapter(adapter);
        mBinding.tabLayout.setupWithViewPager(viewPager);
    }

   /* private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AllOrderFragment(0), dbHelper.getString(R.string.order_tab_all));
        adapter.addFragment(new AllOrderFragment(1), dbHelper.getString(R.string.order_tab_pending));
        adapter.addFragment(new AllOrderFragment(2), dbHelper.getString(R.string.order_tab_accept));
        adapter.addFragment(new AllOrderFragment(3), dbHelper.getString(R.string.order_tab_shiped));
        adapter.addFragment(new AllOrderFragment(4), dbHelper.getString(R.string.order_tab_delivered));
        adapter.addFragment(new AllOrderFragment(6), dbHelper.getString(R.string.order_tab_cancelled));
        viewPager.setAdapter(adapter);
    }*/
}
