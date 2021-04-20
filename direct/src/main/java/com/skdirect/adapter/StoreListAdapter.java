package com.skdirect.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.skdirect.BuildConfig;
import com.skdirect.R;
import com.skdirect.activity.SellerProfileActivity;
import com.skdirect.databinding.ItemApparelsBinding;
import com.skdirect.databinding.ItemMallCategoriceBannerBinding;
import com.skdirect.model.StoreCategoryListModel;
import com.skdirect.model.StoreListModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<StoreListModel> storeList;

    public StoreListAdapter(Context context, ArrayList<StoreListModel> storeListModel) {
        this.context = context;
        this.storeList = storeListModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_apparels, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StoreListModel storeListModel = storeList.get(position);
        holder.mBinding.tvStoreList.setText(storeListModel.getShopName());

        if (storeListModel.getImagePath()!=null && !storeListModel.getImagePath().contains("http")) {
            Picasso.get().load(BuildConfig.apiEndpoint+storeListModel.getImagePath()).error(R.drawable.ic_top_seller).into(holder.mBinding.ivImage);
        }else {
            Picasso.get().load(storeListModel.getImagePath()).placeholder(R.drawable.ic_top_seller).into(holder.mBinding.ivImage);
        }

        holder.mBinding.LLApparels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SellerProfileActivity.class);
                intent.putExtra("ID",storeListModel.getSellerId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return storeList == null ? 0 : storeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemApparelsBinding mBinding;

        public ViewHolder(ItemApparelsBinding Binding) {
            super(Binding.getRoot());
            this.mBinding = Binding;
        }
    }
}
