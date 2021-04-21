package com.skdirect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.skdirect.BuildConfig;
import com.skdirect.R;
import com.skdirect.databinding.ItemDetailsItemBinding;
import com.skdirect.model.OrderItemModel;
import com.skdirect.utils.MySingltonApplication;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrderDetailsItemAdapter extends RecyclerView.Adapter<OrderDetailsItemAdapter.ViewHolder> {

    private Context context;
    private ArrayList<OrderItemModel> orderItemModels;

    public OrderDetailsItemAdapter(Context context,  ArrayList<OrderItemModel> itemModels) {
        this.context = context;
        this.orderItemModels = itemModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_details_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderItemModel orderItemModel = orderItemModels.get(position);
        holder.mBinding.tvItemName.setText(orderItemModel.getProductName());
        holder.mBinding.tvQuantity.setText(MySingltonApplication.getInstance().dbHelper.getString(R.string.qty)+" "+String.valueOf(orderItemModel.getQuantity()));
        holder.mBinding.tvMrp.setText("₹ " +orderItemModel.getMrp());

        if (orderItemModel.getImagePath()!=null && !orderItemModel.getImagePath().contains("http")) {
            Picasso.get().load(BuildConfig.apiEndpoint+orderItemModel.getImagePath()).error(R.drawable.ic_top_seller).into(holder.mBinding.imItemImage);
        }else {
            Picasso.get().load(orderItemModel.getImagePath()).error(R.drawable.ic_top_seller).into(holder.mBinding.imItemImage);
        }

    }

    @Override
    public int getItemCount() {
        return orderItemModels == null ? 0 : orderItemModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemDetailsItemBinding mBinding;

        public ViewHolder(ItemDetailsItemBinding Binding) {
            super(Binding.getRoot());
            this.mBinding = Binding;
        }
    }
}
