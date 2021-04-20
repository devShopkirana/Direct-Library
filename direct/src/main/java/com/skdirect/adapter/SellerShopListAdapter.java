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
import com.skdirect.databinding.ItemNearSallerListBinding;
import com.skdirect.databinding.ItemSallerShopListBinding;
import com.skdirect.model.NearBySallerModel;
import com.skdirect.model.TopSellerModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SellerShopListAdapter extends RecyclerView.Adapter<SellerShopListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<TopSellerModel> sallerShopList;

    public SellerShopListAdapter(Context context, ArrayList<TopSellerModel> SallerShopList) {
        this.context = context;
        this.sallerShopList = SallerShopList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_saller_shop_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TopSellerModel  topSellerModel = sallerShopList.get(position);
        holder.mBinding.tvSallerName.setText(topSellerModel.getShopName());
        holder.mBinding.tvSallerCity.setText(topSellerModel.getAddressOne());
        holder.mBinding.tvSallerState.setText(topSellerModel.getAddressTwo());
        holder.mBinding.tvState.setText(topSellerModel.getCity()+"");


        if (topSellerModel.getImagePath()!=null && !topSellerModel.getImagePath().contains("http")) {
            Picasso.get().load(BuildConfig.apiEndpoint+topSellerModel.getImagePath()).error(R.drawable.ic_top_seller).into(holder.mBinding.imItemImage);
        }else {
            Picasso.get().load(topSellerModel.getImagePath()).error(R.drawable.ic_top_seller).into(holder.mBinding.imItemImage);
        }


        holder.mBinding.LLMainCat.setOnClickListener(new View.OnClickListener() {
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
        return sallerShopList == null ? 0 : sallerShopList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemSallerShopListBinding mBinding;

        public ViewHolder(ItemSallerShopListBinding Binding) {
            super(Binding.getRoot());
            this.mBinding = Binding;
        }
    }
}
