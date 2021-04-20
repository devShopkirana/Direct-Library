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
import com.skdirect.activity.ProductDetailsActivity;
import com.skdirect.activity.SellerProfileActivity;
import com.skdirect.databinding.ItemTopNearByBinding;
import com.skdirect.databinding.ItemTopSellerBinding;
import com.skdirect.model.TopNearByItemModel;
import com.skdirect.model.TopSellerModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TopSellerAdapter extends RecyclerView.Adapter<TopSellerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<TopSellerModel>topSellerList;

    public TopSellerAdapter(Context context, ArrayList<TopSellerModel> topSellerList) {
        this.context = context;
        this.topSellerList = topSellerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_top_seller, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TopSellerModel topSellerModel = topSellerList.get(position);
        holder.mBinding.tvSellerName.setText(topSellerModel.getShopName());

        if (topSellerModel.getImagePath()!=null && !topSellerModel.getImagePath().contains("http")) {
            Picasso.get().load(BuildConfig.apiEndpoint+topSellerModel.getImagePath()).error(R.drawable.ic_top_seller).into(holder.mBinding.ivImage);
        }else {
            Picasso.get().load(topSellerModel.getImagePath()).placeholder(R.drawable.ic_top_seller).into(holder.mBinding.ivImage);
        }

        holder.mBinding.llTopSellar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menuIntent = new Intent(context, SellerProfileActivity.class);
                menuIntent.putExtra("ID",topSellerModel.getId());
                context.startActivity(menuIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return topSellerList == null ? 0 : topSellerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemTopSellerBinding mBinding;

        public ViewHolder(ItemTopSellerBinding Binding) {
            super(Binding.getRoot());
            this.mBinding = Binding;
        }
    }
}
