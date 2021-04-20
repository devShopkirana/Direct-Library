package com.skdirect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.skdirect.R;
import com.skdirect.databinding.ItemMallCategoriceBannerBinding;
import com.skdirect.model.StoreCategoryListModel;
import com.skdirect.utils.SnapHelperOneByOne;

import java.util.ArrayList;

public class MallCategorieBannerAdapter extends RecyclerView.Adapter<MallCategorieBannerAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<StoreCategoryListModel> storeCategoryList;

    public MallCategorieBannerAdapter(Context context, ArrayList<StoreCategoryListModel> storeCategoryListModel) {
        this.context = context;
        this.storeCategoryList = storeCategoryListModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_mall_categorice_banner, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StoreCategoryListModel storeCategoryListModel = storeCategoryList.get(position);
        holder.mBinding.tvMallCategoryName.setText(storeCategoryListModel.getCategoryName());

       /* LinearSnapHelper linearSnapHelper = new SnapHelperOneByOne();
        linearSnapHelper.attachToRecyclerView( holder.mBinding.rvStoreList);*/
        StoreListAdapter storeListAdapter = new StoreListAdapter(context,storeCategoryListModel.getStoreList());
        holder.mBinding.rvStoreList.setAdapter(storeListAdapter);
    }

    @Override
    public int getItemCount() {
        return storeCategoryList == null ? 0 : storeCategoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemMallCategoriceBannerBinding mBinding;

        public ViewHolder(ItemMallCategoriceBannerBinding Binding) {
            super(Binding.getRoot());
            this.mBinding = Binding;
        }
    }
}
