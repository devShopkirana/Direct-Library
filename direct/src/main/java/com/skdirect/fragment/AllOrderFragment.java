package com.skdirect.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.skdirect.R;
import com.skdirect.activity.MyOrderActivity;
import com.skdirect.adapter.MyOrderAdapter;
import com.skdirect.api.CommonClassForAPI;
import com.skdirect.databinding.FragmentAllOrderdBinding;
import com.skdirect.model.MyOrderModel;
import com.skdirect.model.MyOrderRequestModel;
import com.skdirect.model.OrderModel;
import com.skdirect.utils.DBHelper;
import com.skdirect.utils.DirectSDK;
import com.skdirect.utils.SharePrefs;
import com.skdirect.utils.TextUtils;
import com.skdirect.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import io.reactivex.observers.DisposableObserver;

public class AllOrderFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private MyOrderActivity activity;
    private FragmentAllOrderdBinding mBinding;
    private final ArrayList<MyOrderModel> orderModelArrayList = new ArrayList<>();
    private int skipCount = 0;
    private int takeCount = 10;
    private int pastVisiblesItems = 0;
    private int visibleItemCount = 0;
    private int totalItemCount = 0;
    private int orderId = 0;
    private boolean loading = true;
    private MyOrderAdapter myOrderAdapter;
    boolean handled = false;
    public DBHelper dbHelper;
    private String statusId;
    private String orderstatus;
    private CommonClassForAPI commonClassForAPI;
    public AllOrderFragment(String id) {
        this.statusId = id;
        handled = false;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = ((MyOrderActivity) context);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_orderd, container, false);
        orderstatus = statusId;
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialization();
        mBinding.etSearchOrder.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    orderstatus = "";
                    searchData();
                    handled = true;
                }
                return handled;
            }
        });
        mBinding.etSearchOrder.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if(s.length()==0 && handled)
                {
                    orderId = 0;
                    orderModelArrayList.clear();
                    skipCount = 0;
                    orderstatus = statusId;
                    callMyOrder();

                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

    }
    private void searchData() {
        if(!TextUtils.isNullOrEmpty(mBinding.etSearchOrder.getText().toString()))
        {
            orderModelArrayList.clear();
            orderId = Integer.parseInt(mBinding.etSearchOrder.getText().toString().trim());
            skipCount = 0;
            callMyOrder();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (myOrderAdapter != null) {
            orderModelArrayList.clear();
            /*myOrderAdapter = new MyOrderAdapter(activity, orderModelArrayList);
            mBinding.rMyOrder.setAdapter(myOrderAdapter);*/
            mBinding.shimmerViewContainer.startShimmer();
            orderId = 0;
            callMyOrder();
        }
    }

    @Override
    public void onRefresh() {
        orderModelArrayList.clear();
        myOrderAdapter = new MyOrderAdapter(activity, orderModelArrayList);
        mBinding.rMyOrder.setAdapter(myOrderAdapter);
        orderId = 0;
        callMyOrder();
    }

    private void initialization() {
        dbHelper = DirectSDK.getInstance().dbHelper;
        mBinding.etSearchOrder.setHint(dbHelper.getString(R.string.search_order_by_order_id));
        mBinding.tvNoData.setHint(dbHelper.getString(R.string.no_data_found));
       // mBinding.swiperefresh.setOnRefreshListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, RecyclerView.VERTICAL,false);
        mBinding.rMyOrder.setLayoutManager(layoutManager);
        myOrderAdapter = new MyOrderAdapter(activity, orderModelArrayList);
        mBinding.rMyOrder.setAdapter(myOrderAdapter);

        mBinding.rMyOrder.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();
                    if (loading) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            loading = false;
                            skipCount=skipCount+10;
                            // mBinding.progressBar.setVisibility(View.VISIBLE);
                            orderId = 0;
                            callMyOrder();
                        }
                    }
                }
            }
        });
        orderModelArrayList.clear();
        orderId = 0;
       // callMyOrder();

    }

    private void callMyOrder() {
        if (Utils.isNetworkAvailable(activity)) {
           myOrderAPI();
        } else {
            Utils.setToast(activity, "No Internet Connection Please connect.");
        }
    }

    private void myOrderAPI() {
        commonClassForAPI = CommonClassForAPI.getInstance(getActivity());
        if (commonClassForAPI!=null) {
            MyOrderRequestModel myOrderRequestModel = new MyOrderRequestModel("",0,takeCount,skipCount,orderstatus, orderId, SharePrefs.getInstance(getActivity()).getString(SharePrefs.ASP_NET_USER_ID));
           // Utils.showProgressDialog(getActivity());
            commonClassForAPI.GetOrderMaster(observer,myOrderRequestModel);
        }
    }
    private final DisposableObserver<OrderModel> observer = new DisposableObserver<OrderModel>() {
        @Override
        public void onNext(@NotNull OrderModel orderStatusMainModel) {
            Utils.hideProgressDialog();
            try {
                    mBinding.shimmerViewContainer.stopShimmer();
                    mBinding.shimmerViewContainer.setVisibility(View.GONE);
                    mBinding.tlEmptyView.setVisibility(View.GONE);
                    mBinding.rMyOrder.setVisibility(View.VISIBLE);
                    if(orderStatusMainModel.isSuccess())
                    {
                        //orderModelArrayList.clear();
                        if (orderStatusMainModel.getMyOrderModelsList()!=null && orderStatusMainModel.getMyOrderModelsList().size()>0){
                            orderModelArrayList.addAll(orderStatusMainModel.getMyOrderModelsList());
                            myOrderAdapter.notifyDataSetChanged();
                            loading = true;
                        }else {
                            loading = false;
                            mBinding.tlEmptyView.setVisibility(View.VISIBLE);
                            mBinding.rMyOrder.setVisibility(View.GONE);
                        }
                    }else
                    {
                        loading = false;
                        if (orderModelArrayList.size()==0){
                            mBinding.tlEmptyView.setVisibility(View.VISIBLE);
                            mBinding.rMyOrder.setVisibility(View.GONE);
                        }

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


}
