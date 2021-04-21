package com.skdirect.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.skdirect.adapter.NearProductListAdapter;
import com.skdirect.databinding.ActivityProductListBinding;
import com.skdirect.model.FilterPostModel;
import com.skdirect.model.NearProductListMainModel;
import com.skdirect.model.NearProductListModel;
import com.skdirect.model.PaginationModel;
import com.skdirect.utils.Constant;
import com.skdirect.utils.DBHelper;
import com.skdirect.utils.MySingltonApplication;
import com.skdirect.utils.Utils;
import com.skdirect.viewmodel.NearProductListViewMode;

import java.util.ArrayList;

public class NearByItemProductListActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityProductListBinding mBinding;
    private NearProductListViewMode nearProductListViewMode;
    private final ArrayList<NearProductListModel> nearProductList = new ArrayList<>();
    private int skipCount = 0;
    private int takeCount = 10;
    private int pastVisiblesItems = 0;
    private int visibleItemCount = 0;
    private int totalItemCount = 0;
    private boolean loading = true;
    private NearProductListAdapter nearProductListAdapter;
    private final int REQUEST = 100;
    public DBHelper dbHelper;
    public String searchString;
    public boolean paginationFlag = false;
    FilterPostModel filterPostModel;
    // filer variable
    private int categoryId = 0;
    private String discount = "";
    private ArrayList<String> brandList = new ArrayList<>();
    private ArrayList<Integer> priceList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_product_list);
        nearProductListViewMode = ViewModelProviders.of(this).get(NearProductListViewMode.class);
        dbHelper = MySingltonApplication.getInstance().dbHelper;
        initView();
        callProductList();
        setString();
    }

    private void setString() {
        mBinding.tvSort.setText(dbHelper.getString(R.string.sort));
        mBinding.tvFilter.setText(dbHelper.getString(R.string.filter));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (nearProductListAdapter != null) {
            nearProductListAdapter = new NearProductListAdapter(getApplicationContext(), nearProductList);
            mBinding.rvNearByProduct.setAdapter(nearProductListAdapter);
            nearProductListAdapter.notifyDataSetChanged();
        }
    }

    private void initView() {
        mBinding.toolbarTittle.tvTittle.setText(dbHelper.getString(R.string.product_list));
        mBinding.toolbarTittle.ivBackPress.setOnClickListener(this);
        mBinding.shimmerViewContainer.startShimmer();
        nearProductList.clear();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        mBinding.rvNearByProduct.setLayoutManager(layoutManager);
        nearProductListAdapter = new NearProductListAdapter(getApplicationContext(), nearProductList);
        mBinding.rvNearByProduct.setAdapter(nearProductListAdapter);

        mBinding.rvNearByProduct.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                            skipCount = skipCount + 10;
                            // mBinding.progressBar.setVisibility(View.VISIBLE);
                            if (paginationFlag) {
                                filterPostModel.setSkip(skipCount);
                                callFilterAPI();
                            } else {
                                callProductList();
                            }

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
                    nearProductList.clear();
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
                        nearProductList.clear();
                        callProductList();
                        return true;
                    } else {
                        Utils.setLongToast(NearByItemProductListActivity.this, "Please enter Item Name");
                    }
                }
                return false;
            }
        });
        mBinding.llFilter.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), FilterActivity.class);
            intent.putExtra("categoryId", categoryId);
            intent.putStringArrayListExtra("brandList", brandList);
            intent.putIntegerArrayListExtra("priceList", priceList);
            intent.putExtra("discount", discount);
            startActivityForResult(intent, REQUEST);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST) {
            if (data != null && resultCode == RESULT_OK) {
                categoryId = data.getExtras().getInt(Constant.Category);
                priceList = data.getExtras().getIntegerArrayList(Constant.Price);
                brandList = data.getExtras().getStringArrayList(Constant.Brands);
                discount = data.getExtras().getString(Constant.Discount);
                Log.e("ProductList", "Cate>>" + categoryId + "\n Price Min>>" + priceList.toString()
                        + "\n Brand>>" + brandList.toString() + "\n Discount>>" + discount);
                nearProductList.clear();
                paginationFlag = true;
                filterPostModel = new FilterPostModel(categoryId, false, skipCount, takeCount,
                        0, 0, brandList, priceList, discount);
                callFilterAPI();
            } else {
                System.out.println("Cancelled by user");
            }
        }
    }

    private void callFilterAPI() {
        nearProductListViewMode.getFilterProductListRequest(filterPostModel);
        nearProductListViewMode.getFilterProductList().observe(this, new Observer<NearProductListMainModel>() {
            @Override
            public void onChanged(NearProductListMainModel nearProductListList) {
                if (nearProductListList.isSuccess()) {
                    mBinding.shimmerViewContainer.stopShimmer();
                    mBinding.shimmerViewContainer.setVisibility(View.GONE);
                    if (nearProductListList.getResultItem().size() > 0) {
                        mBinding.rvNearByProduct.post(new Runnable() {
                            public void run() {
                                nearProductList.addAll(nearProductListList.getResultItem());
                                nearProductListAdapter.notifyDataSetChanged();
                                loading = true;
                            }
                        });

                    } else {
                        loading = false;
                    }
                }
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
        PaginationModel paginationModel = new PaginationModel(skipCount, takeCount, searchString);
        nearProductListViewMode.getNearProductListRequest(paginationModel);
        nearProductListViewMode.getNearProductList().observe(this, new Observer<NearProductListMainModel>() {
            @Override
            public void onChanged(NearProductListMainModel nearProductListList) {
                if (nearProductListList.isSuccess()) {
                    mBinding.shimmerViewContainer.stopShimmer();
                    mBinding.shimmerViewContainer.setVisibility(View.GONE);
                    if (nearProductListList.getResultItem().size() > 0) {
                        mBinding.rvNearByProduct.post(new Runnable() {
                            public void run() {
                                nearProductList.addAll(nearProductListList.getResultItem());
                                nearProductListAdapter.notifyDataSetChanged();
                                loading = true;
                            }
                        });

                    } else {
                        loading = false;
                    }
                }
            }
        });
    }

}
