package com.skdirect.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.skdirect.R;
import com.skdirect.activity.CategoriesListActivity;
import com.skdirect.activity.MainActivity;
import com.skdirect.activity.MostVisitedSellerActivity;
import com.skdirect.activity.NearByItemProductListActivity;
import com.skdirect.activity.NearSellerActivity;
import com.skdirect.activity.NewSellerActivity;
import com.skdirect.activity.SearchActivity;
import com.skdirect.activity.SellerProductListActivity;
import com.skdirect.adapter.AllCategoriesAdapter;
import com.skdirect.adapter.MallCategorieBannerAdapter;
import com.skdirect.adapter.TopNearByItemAdapter;
import com.skdirect.adapter.TopSellerAdapter;
import com.skdirect.databinding.FragmentHomedBinding;
import com.skdirect.model.AllCategoresMainModel;
import com.skdirect.model.MallMainModel;
import com.skdirect.model.NearByMainModel;
import com.skdirect.model.TopSellerMainModel;
import com.skdirect.utils.DBHelper;
import com.skdirect.utils.DirectSDK;
import com.skdirect.utils.SharePrefs;
import com.skdirect.utils.Utils;
import com.skdirect.viewmodel.HomeViewModel;

import org.jetbrains.annotations.NotNull;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private FragmentHomedBinding mBinding;
    private MainActivity activity;
    private HomeViewModel homeViewModel;
    public DBHelper dbHelper;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_homed, container, false);
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        initViews();
        getMall();
        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        mBinding.etSearchSeller.setText("");
    }

    @Override
    public void onRefresh() {
        if (mBinding.llMainAppHome.getVisibility() == View.VISIBLE) {
            topNearByItem();
            getSellerAPi();
            getAllCategoriesAPi();
        } else {
            //mBinding.swiperefresh.setVisibility(View.GONE);
        }


    }

    private void getAllCategoriesAPi() {
        if (Utils.isNetworkAvailable(activity)) {
            Utils.showProgressDialog(activity);
            getTopSeller();
        } else {
            Utils.setToast(activity, activity.dbHelper.getString(R.string.no_connection));
        }
    }

    private void getSellerAPi() {
        if (Utils.isNetworkAvailable(activity)) {
            Utils.showProgressDialog(activity);
            getAllCategories();
        } else {
            Utils.setToast(activity, activity.dbHelper.getString(R.string.no_connection));
        }
    }

    private void topNearByItem() {
        if (Utils.isNetworkAvailable(activity)) {
            Utils.showProgressDialog(activity);
            getNearByItem();
        } else {
            Utils.setToast(activity, activity.dbHelper.getString(R.string.no_connection));
        }
    }

    private void nearBySeller() {
        if (Utils.isNetworkAvailable(activity)) {
            Utils.showProgressDialog(activity);
            getNearBySeller();
        } else {
            Utils.setToast(activity, activity.dbHelper.getString(R.string.no_connection));
        }
    }

    private void mostVisitedSeller() {
        if (Utils.isNetworkAvailable(activity)) {
            Utils.showProgressDialog(activity);
            getMostVisitedSeller();
        } else {
            Utils.setToast(activity, activity.dbHelper.getString(R.string.no_connection));
        }
    }

    private void newSeller() {
        if (Utils.isNetworkAvailable(activity)) {
            Utils.showProgressDialog(activity);
            getNewSeller();
        } else {
            Utils.setToast(activity, activity.dbHelper.getString(R.string.no_connection));
        }
    }

    private void getMall() {
        if (Utils.isNetworkAvailable(activity)) {
            Utils.showProgressDialog(activity);
            getMallApiData();
        } else {
            Utils.setToast(activity, activity.dbHelper.getString(R.string.no_connection));
        }
    }

    private void getMallApiData() {
        homeViewModel.getMallData().observe(this, new Observer<MallMainModel>() {
            @Override
            public void onChanged(MallMainModel mallMainModel) {
                Utils.hideProgressDialog();
                if (mallMainModel.isSuccess()) {
                    if (mallMainModel.getResultItem() != null) {
                        mBinding.rlHomeSearch.setVisibility(View.VISIBLE);
                        SharePrefs.getInstance(activity).putBoolean(SharePrefs.IS_Mall, true);
                        SharePrefs.getInstance(getActivity()).putString(SharePrefs.MALL_ID, mallMainModel.getResultItem().getId());
                        MallCategorieBannerAdapter mallCategorieBannerAdapter = new MallCategorieBannerAdapter(getActivity(), mallMainModel.getResultItem().getStoreCategoryList());
                        mBinding.rvStoreCategoryList.setAdapter(mallCategorieBannerAdapter);
                        mBinding.llMainAppHome.setVisibility(View.GONE);
                        mBinding.llMallHome.setVisibility(View.VISIBLE);
                    } else {
                        mBinding.llMainAppHome.setVisibility(View.VISIBLE);
                        mBinding.llMallHome.setVisibility(View.GONE);
                        /*topNearByItem();
                        getSellerAPi();
                        getAllCategoriesAPi();*/
                    }
                } else {
                    mBinding.llMainAppHome.setVisibility(View.VISIBLE);
                    mBinding.llMallHome.setVisibility(View.GONE);



                   /* topNearByItem();
                    getSellerAPi();
                    getAllCategoriesAPi();
                    nearBySeller();
                    mostVisitedSeller();
                    newSeller();*/
                }
            }
        });
    }

    private void getNearByItem() {
        homeViewModel.GetTopNearByItem().observe(this, new Observer<NearByMainModel>() {
            @Override
            public void onChanged(NearByMainModel nearByMainModel) {
                Utils.hideProgressDialog();
                if (nearByMainModel.isSuccess()) {
                    if (nearByMainModel.getResultItem().size() > 0) {
                        TopNearByItemAdapter topNearByItemAdapter = new TopNearByItemAdapter(getActivity(), nearByMainModel.getResultItem());
                        mBinding.rvNearByItem.setAdapter(topNearByItemAdapter);

                    } else {
                        mBinding.llNearByNotFound.setVisibility(View.VISIBLE);
                        mBinding.rvNearByItem.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    private void getTopSeller() {
        homeViewModel.GetTopSellerLiveData().observe(this, new Observer<TopSellerMainModel>() {
            @Override
            public void onChanged(TopSellerMainModel topSellerList) {
                Utils.hideProgressDialog();
                if (topSellerList.isSuccess()) {
                    if (topSellerList.getResultItem().size() > 0) {
                        TopSellerAdapter topSellerAdapter = new TopSellerAdapter(getActivity(), topSellerList.getResultItem());
                        mBinding.rvTopSeller.setAdapter(topSellerAdapter);
                    } else {
                        mBinding.llTopSellerNotFound.setVisibility(View.VISIBLE);
                        mBinding.rvTopSeller.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    private void getNearBySeller() {
        homeViewModel.GetNearSellerLiveData().observe(this, new Observer<TopSellerMainModel>() {
            @Override
            public void onChanged(TopSellerMainModel topSellerList) {
                Utils.hideProgressDialog();
                if (topSellerList.isSuccess()) {
                    if (topSellerList.getResultItem().size() > 0) {
                        TopSellerAdapter topSellerAdapter = new TopSellerAdapter(getActivity(), topSellerList.getResultItem());
                        mBinding.rvNearBySeller.setAdapter(topSellerAdapter);
                    } else {
                        mBinding.llNearBySeller.setVisibility(View.VISIBLE);
                        mBinding.rvNearBySeller.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    private void getMostVisitedSeller() {
        homeViewModel.GetMostVisitedSellerLiveData().observe(this, new Observer<TopSellerMainModel>() {
            @Override
            public void onChanged(TopSellerMainModel topSellerList) {
                Utils.hideProgressDialog();
                if (topSellerList.isSuccess()) {
                    if (topSellerList.getResultItem().size() > 0) {
                        TopSellerAdapter topSellerAdapter = new TopSellerAdapter(getActivity(), topSellerList.getResultItem());
                        mBinding.rvMostVisitedSeller.setAdapter(topSellerAdapter);
                    } else {
                        mBinding.llMostVisitedSeller.setVisibility(View.VISIBLE);
                        mBinding.rvMostVisitedSeller.setVisibility(View.GONE);
                    }
                }

            }
        });
    }

    private void getNewSeller() {
        homeViewModel.GetNewSellerLiveData().observe(this, new Observer<TopSellerMainModel>() {
            @Override
            public void onChanged(TopSellerMainModel topSellerList) {
                Utils.hideProgressDialog();
                if (topSellerList.isSuccess()) {
                    if (topSellerList.getResultItem().size() > 0) {
                        TopSellerAdapter topSellerAdapter = new TopSellerAdapter(getActivity(), topSellerList.getResultItem());
                        mBinding.rvNewSeller.setAdapter(topSellerAdapter);
                    } else {
                        mBinding.llNewSeller.setVisibility(View.VISIBLE);
                        mBinding.rvNewSeller.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    private void getAllCategories() {
        homeViewModel.getAllCategoriesLiveData().observe(this, new Observer<AllCategoresMainModel>() {
            @Override
            public void onChanged(AllCategoresMainModel allCategoriesList) {
                if (allCategoriesList.isSuccess()) {
                    if (allCategoriesList.getResultItem().size() > 0) {
                        AllCategoriesAdapter allCategoriesAdapter = new AllCategoriesAdapter(getActivity(), allCategoriesList.getResultItem());
                        mBinding.rvAllCetegory.setAdapter(allCategoriesAdapter);
                    } else {
                        mBinding.llAllCetegoryNotFound.setVisibility(View.VISIBLE);
                        mBinding.rvAllCetegory.setVisibility(View.GONE);
                    }
                }
            }
        });

    }

    private void initViews() {
        if (SharePrefs.getInstance(getActivity()).getBoolean(SharePrefs.IS_Mall)) {
            mBinding.rlHomeSearch.setVisibility(View.VISIBLE);
        } else {
            mBinding.rlHomeSearch.setVisibility(View.GONE);
        }

        dbHelper = DirectSDK.getInstance().dbHelper;
        mBinding.etSearchSeller.setHint(dbHelper.getString(R.string.search_seller));

        mBinding.tvNearItem.setText(dbHelper.getString(R.string.near_by_item));
        mBinding.tvNearByViewAll.setText(dbHelper.getString(R.string.view_more));
        mBinding.tvNearNoItem.setText(dbHelper.getString(R.string.no_item_found));
        mBinding.tvLoNotFound.setText(dbHelper.getString(R.string.no_loction_found));

        mBinding.tvNearSellar.setText(dbHelper.getString(R.string.near_by_seller));
        mBinding.tvNearBySellar.setText(dbHelper.getString(R.string.view_more));
        mBinding.tvNoSeller.setText(dbHelper.getString(R.string.no_item_found));
        mBinding.tvNoSellerLocation.setText(dbHelper.getString(R.string.no_loction_found));

        mBinding.tvAllCat.setText(dbHelper.getString(R.string.all_categories));
        mBinding.tvCetegory.setText(dbHelper.getString(R.string.view_more));
        mBinding.tvNoItemCat.setText(dbHelper.getString(R.string.no_item_found));
        mBinding.tvNoLocationCat.setText(dbHelper.getString(R.string.no_loction_found));

        mBinding.tvNearSeller2.setText(dbHelper.getString(R.string.by_seller));
        mBinding.tvNearSellerTwo.setText(dbHelper.getString(R.string.view_more));
        mBinding.tvNoSellerItem.setText(dbHelper.getString(R.string.no_item_found));
        mBinding.tvNoSelleronLocation.setText(dbHelper.getString(R.string.no_loction_found));

        mBinding.tvMaxSeller.setText(dbHelper.getString(R.string.most_visted_seller));
        mBinding.tvMostVisitedSeller.setText(dbHelper.getString(R.string.view_more));
        mBinding.tvNoMaxSeller.setText(dbHelper.getString(R.string.no_item_found));
        mBinding.tvNoMaxSellerLocation.setText(dbHelper.getString(R.string.no_loction_found));

        mBinding.tvNewSellerTitle.setText(dbHelper.getString(R.string.new_seller));
        mBinding.tvNewSeller.setText(dbHelper.getString(R.string.view_more));
        mBinding.tvNoNewSellerItem.setText(dbHelper.getString(R.string.no_item_found));
        mBinding.tvNoNewSellerItemLocation.setText(dbHelper.getString(R.string.no_loction_found));
        mBinding.mallNotFound.tvNoMallAvailble.setText(dbHelper.getString(R.string.no_mall_availble));
        //mBinding.swiperefresh.setOnRefreshListener(this);
        mBinding.rvNearByItem.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        mBinding.rvTopSeller.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        activity.appBarLayout.setVisibility(View.VISIBLE);

        viewAllButtonClick();

        mBinding.etSearchSeller.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                    searchData();
                    return true;
                }
                return false;
            }
        });
    }

    private void viewAllButtonClick() {
        mBinding.tvNearByViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), NearByItemProductListActivity.class));
            }
        });

        mBinding.tvNearBySellar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SellerProductListActivity.class));
            }
        });

        mBinding.tvCetegory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CategoriesListActivity.class));
            }
        });

        mBinding.tvNearSellerTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), NearSellerActivity.class));
            }
        });

        mBinding.tvMostVisitedSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MostVisitedSellerActivity.class));
            }
        });

        mBinding.tvNewSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), NewSellerActivity.class));
            }
        });
    }

    private void searchData() {
        String searchSellerName = mBinding.etSearchSeller.getText().toString().trim();
        startActivity(new Intent(getActivity(), SearchActivity.class).putExtra("searchSellerName", searchSellerName));
    }

}
