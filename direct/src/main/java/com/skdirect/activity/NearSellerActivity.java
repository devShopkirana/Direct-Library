package com.skdirect.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skdirect.R;
import com.skdirect.adapter.NearSellerListAdapter;
import com.skdirect.databinding.ActivityNearSalleBinding;
import com.skdirect.model.NearBySallerModel;
import com.skdirect.model.NearBySellerMainModel;
import com.skdirect.model.PaginationModel;
import com.skdirect.utils.DBHelper;
import com.skdirect.utils.MySingltonApplication;
import com.skdirect.utils.Utils;
import com.skdirect.viewmodel.NearSellerViewMode;

import java.util.ArrayList;

public class NearSellerActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityNearSalleBinding mBinding;
    private NearSellerViewMode NearSellerViewMode;
    private final ArrayList<NearBySallerModel> nearBySallerList = new ArrayList<>();
    private int skipCount = 0;
    private int takeCount = 10;
    private int pastVisiblesItems = 0;
    private int visibleItemCount = 0;
    private int totalItemCount = 0;
    private boolean loading = true;
    private String searchString;
    private NearSellerListAdapter nearSellerListAdapter;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_near_salle);
        NearSellerViewMode = ViewModelProviders.of(this).get(NearSellerViewMode.class);
        initView();
        callProductList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (nearSellerListAdapter!=null){
            nearSellerListAdapter = new NearSellerListAdapter(this,nearBySallerList);
            mBinding.rvSellerProduct.setAdapter(nearSellerListAdapter);
            nearSellerListAdapter.notifyDataSetChanged();
        }
    }
    private void initView() {
        mBinding.shimmerViewContainer.startShimmer();
        mBinding.toolbarTittle.tvTittle.setText("Seller List");
        mBinding.toolbarTittle.ivBackPress.setOnClickListener(this);
        nearBySallerList.clear();
        dbHelper = MySingltonApplication.getInstance().dbHelper;

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
        mBinding.rvSellerProduct.setLayoutManager(layoutManager);
        nearSellerListAdapter = new NearSellerListAdapter(getApplicationContext(),nearBySallerList);
        mBinding.rvSellerProduct.setAdapter(nearSellerListAdapter);

        mBinding.rvSellerProduct.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                            callProductList();
                        }
                    }
                }
            }
        });
        mBinding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchString = editable.toString().trim();
                if (searchString.length() == 0) {
                    skipCount = 0;
                    takeCount = 10;
                    nearBySallerList.clear();
                    callProductList();

                }
            }
        });

        mBinding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                    if (!searchString.isEmpty()) {
                        skipCount = 0;
                        takeCount = 10;
                        nearBySallerList.clear();
                        callProductList();
                        return true;
                    } else {
                        Utils.setLongToast(NearSellerActivity.this, "Please enter Item Name");
                    }
                }
                return false;
            }
        });

    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_back_press) {
            onBackPressed();
        }
    }

    private void callProductList() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            getProductListAPI();
        } else {
            Utils.setToast(getApplicationContext(), dbHelper.getString(R.string.no_connection));
        }
    }


    private void getProductListAPI() {
        NearSellerViewMode.getSellerProductListRequest(new PaginationModel(skipCount,takeCount,searchString));
        NearSellerViewMode.getSellerProductListVM().observe(this, new Observer<NearBySellerMainModel>() {
            @Override
            public void onChanged(NearBySellerMainModel nearBySaller) {
                if (nearBySaller.isSuccess()){
                    mBinding.shimmerViewContainer.stopShimmer();
                    mBinding.shimmerViewContainer.setVisibility(View.GONE);
                    if (nearBySaller.getResultItem().size()>0){
                        mBinding.rvSellerProduct.post(new Runnable() {
                            public void run() {
                                nearBySallerList.addAll(nearBySaller.getResultItem());
                                nearSellerListAdapter.notifyDataSetChanged();
                                loading = true;
                            }
                        });
                    }else
                    {
                        loading = false;
                    }
                }

            }
        });
    }
}
