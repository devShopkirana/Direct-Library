package com.skdirect.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skdirect.R;
import com.skdirect.adapter.CategoriesAdapter;
import com.skdirect.databinding.ActivityCategoriesListBinding;
import com.skdirect.model.AllCategoresMainModel;
import com.skdirect.model.AllCategoriesModel;
import com.skdirect.model.PaginationModel;
import com.skdirect.utils.DBHelper;
import com.skdirect.utils.MySingltonApplication;
import com.skdirect.utils.Utils;
import com.skdirect.viewmodel.CategoriesViewMode;

import java.util.ArrayList;

public class CategoriesListActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityCategoriesListBinding mBinding;
    private CategoriesViewMode categoriesViewMode;
    private final ArrayList<AllCategoriesModel> allCategoriesList = new ArrayList<>();
    private int skipCount = 0;
    private int takeCount = 10;
    private int pastVisiblesItems = 0;
    private int visibleItemCount = 0;
    private int totalItemCount = 0;
    private boolean loading = true;
    private CategoriesAdapter categoriesAdapter;
    private DBHelper dbHelper;
    private String searchKeybord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_categories_list);
        categoriesViewMode = ViewModelProviders.of(this).get(CategoriesViewMode.class);
        dbHelper = MySingltonApplication.getInstance().dbHelper;
        initView();
        callProductList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (categoriesAdapter != null) {
            categoriesAdapter = new CategoriesAdapter(getApplicationContext(), allCategoriesList);
            mBinding.rvCategories.setAdapter(categoriesAdapter);
            categoriesAdapter.notifyDataSetChanged();
        }
    }

    private void initView() {
        mBinding.toolbarTittle.tvTittle.setText(dbHelper.getString(R.string.category_list));
        mBinding.toolbarTittle.ivBackPress.setOnClickListener(this);
        mBinding.shimmerViewContainer.startShimmer();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        mBinding.rvCategories.setLayoutManager(layoutManager);
        categoriesAdapter = new CategoriesAdapter(getApplicationContext(), allCategoriesList);
        mBinding.rvCategories.setAdapter(categoriesAdapter);

        mBinding.rvCategories.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                            callProductList();
                        }
                    }
                }
            }
        });
        allCategoriesList.clear();

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
            Utils.setToast(getApplicationContext(), "No Internet Connection Please connect.");
        }
    }


    private void getProductListAPI() {
        PaginationModel paginationModel = new PaginationModel(skipCount, takeCount, true);
        categoriesViewMode.getCategoriesViewModelRequest(paginationModel);
        categoriesViewMode.getCategoriesViewModel().observe(this, new Observer<AllCategoresMainModel>() {
            @Override
            public void onChanged(AllCategoresMainModel allCategoresMainModel) {
                if (allCategoresMainModel!= null && allCategoresMainModel.isSuccess()) {
                    mBinding.shimmerViewContainer.stopShimmer();
                    mBinding.shimmerViewContainer.setVisibility(View.GONE);
                    if (allCategoresMainModel.getResultItem() != null && allCategoresMainModel.getResultItem().size() > 0) {
                        mBinding.rvCategories.post(new Runnable() {
                            public void run() {
                                allCategoriesList.addAll(allCategoresMainModel.getResultItem());
                                categoriesAdapter.notifyDataSetChanged();
                                loading = true;
                            }
                        });
                    } else {
                        loading = false;
                        if (allCategoriesList.size() == 0) {

                        }
                    }
                }
            }
        });
    }
}