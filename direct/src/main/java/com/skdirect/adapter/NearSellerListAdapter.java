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
import com.skdirect.databinding.ItemNearProductListBinding;
import com.skdirect.databinding.ItemNearSallerListBinding;
import com.skdirect.model.NearBySallerModel;
import com.skdirect.model.NearProductListModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NearSellerListAdapter extends RecyclerView.Adapter<NearSellerListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<NearBySallerModel> nearBySallerList;

    public NearSellerListAdapter(Context context, ArrayList<NearBySallerModel> nearBySallerList) {
        this.context = context;
        this.nearBySallerList = nearBySallerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_near_saller_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NearBySallerModel  nearBySallerModel = nearBySallerList.get(position);
        holder.mBinding.tvSallerName.setText(nearBySallerModel.getShopName());
        holder.mBinding.tvSallerCity.setText(nearBySallerModel.getCity());
        holder.mBinding.tvSallerState.setText(nearBySallerModel.getState()+" "+nearBySallerModel.getPincode());


        if (nearBySallerModel.getImagePath()!=null && !nearBySallerModel.getImagePath().contains("http")) {
            Picasso.get().load(BuildConfig.apiEndpoint+nearBySallerModel.getImagePath()).error(R.drawable.ic_top_seller).into(holder.mBinding.imItemImage);
        }else {
            Picasso.get().load(nearBySallerModel.getImagePath()).placeholder(R.drawable.ic_top_seller).into(holder.mBinding.imItemImage);
        }

        holder.mBinding.LLMainCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menuIntent = new Intent(context, SellerProfileActivity.class);
                menuIntent.putExtra("ID",nearBySallerModel.getId());
                context.startActivity(menuIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return nearBySallerList == null ? 0 : nearBySallerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemNearSallerListBinding mBinding;

        public ViewHolder(ItemNearSallerListBinding Binding) {
            super(Binding.getRoot());
            this.mBinding = Binding;
        }
    }
}
