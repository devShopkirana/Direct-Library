package com.skdirect.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skdirect.R;
import com.skdirect.activity.SearchActivity;
import com.skdirect.adapter.SellerShopListAdapter;
import com.skdirect.api.CommonClassForAPI;
import com.skdirect.databinding.FragmentShopdBinding;
import com.skdirect.interfacee.SearchInterface;
import com.skdirect.model.ShopMainModel;
import com.skdirect.model.TopSellerModel;
import com.skdirect.utils.DirectSDK;
import com.skdirect.utils.Utils;
import com.skdirect.viewmodel.SearchViewMode;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import io.reactivex.observers.DisposableObserver;

public class ShopFragment extends Fragment implements SearchInterface {
    private FragmentShopdBinding mBinding;
    private SearchActivity activity;

    private final int skipCount = 0;
    private final int takeCount = 7;
    private final int pastVisiblesItems = 0;
    private final int visibleItemCount = 0;
    private final int totalItemCount = 0;
    private boolean loading = true;
    private SearchViewMode searchViewMode;
    private String searchSellerName;
    private SellerShopListAdapter sellerShopListAdapter;
    private final ArrayList<TopSellerModel> sallerShopList = new ArrayList<>();
    private int cateogryId;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (SearchActivity) context;
    }

    @SuppressLint("ValidFragment")
    public ShopFragment(String searchSellName, int allCategoriesID) {
        searchSellerName = searchSellName;
        cateogryId = allCategoriesID;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_shopd, container, false);
        searchViewMode = ViewModelProviders.of(this).get(SearchViewMode.class);
        initViews();
        callShopAPi();
        activity.searchInterfaceS = this;

        return mBinding.getRoot();
    }


    @Override
    public void onSearchClick(String query, int categoryId) {
        searchSellerName = query;
        cateogryId = categoryId;
        callShopAPi();
    }


    private void initViews() {
        mBinding.tvNotDataFound.setText(DirectSDK.getInstance().dbHelper.getString(R.string.no_data_found));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mBinding.rvSearch.setLayoutManager(layoutManager);
        sellerShopListAdapter = new SellerShopListAdapter(getActivity(), sallerShopList);
        mBinding.rvSearch.setAdapter(sellerShopListAdapter);

        mBinding.rvSearch.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                if (dy > 0) {
//                    visibleItemCount = layoutManager.getChildCount();
//                    totalItemCount = layoutManager.getItemCount();
//                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();
//                    if (loading) {
//                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
//                            loading = false;
//                            skipCount = skipCount + 7;
//                            // mBinding.progressBar.setVisibility(View.VISIBLE);
//                            callShopAPi();
//                        }
//                    }
//                }
            }
        });
        sallerShopList.clear();
    }

    private void callShopAPi() {
        Utils.hideKeyboard(getActivity());
        if (Utils.isNetworkAvailable(activity)) {
            Utils.showProgressDialog(activity);
            sallerShopList.clear();
            getShopData();
        } else {
            Utils.setToast(activity, DirectSDK.getInstance().dbHelper.getString(R.string.no_internet_connection));
        }
    }

    private void getShopData() {
        CommonClassForAPI.getInstance(activity).getTopSellerItem(shopObserver,
                skipCount, takeCount, searchSellerName,
                (String.valueOf(cateogryId).equals("0")) ? null : String.valueOf(cateogryId));
    }

    private final DisposableObserver<ShopMainModel> shopObserver = new DisposableObserver<ShopMainModel>() {
        @Override
        public void onNext(@NotNull ShopMainModel shopMainModel) {
            try {
                Utils.hideProgressDialog();
                if (shopMainModel != null && shopMainModel.getResultItem() != null && shopMainModel.getResultItem().size() > 0) {
                    mBinding.tvNotDataFound.setVisibility(View.GONE);
                    sallerShopList.clear();
                    sallerShopList.addAll(shopMainModel.getResultItem());
                    sellerShopListAdapter.notifyDataSetChanged();
                    loading = true;
                } else {
                    mBinding.tvNotDataFound.setVisibility(View.VISIBLE);
                    sallerShopList.clear();
                    sellerShopListAdapter.notifyDataSetChanged();
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
