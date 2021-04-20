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
import com.skdirect.activity.OrderDetailActivity;
import com.skdirect.activity.ReviewActivity;
import com.skdirect.databinding.ItemMyOrderBinding;
import com.skdirect.model.MyOrderModel;
import com.skdirect.utils.DBHelper;
import com.skdirect.utils.MyApplication;
import com.skdirect.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<MyOrderModel> orderModelArrayList;
    public DBHelper dbHelper;

    public MyOrderAdapter(Context context, ArrayList<MyOrderModel> deliveryOptionList) {
        this.context = context;
        this.orderModelArrayList = deliveryOptionList;
        dbHelper = MyApplication.getInstance().dbHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_my_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.mBinding.tvAddReview.setHint(dbHelper.getString(R.string.add_review));

        MyOrderModel myOrderModel = orderModelArrayList.get(position);
        holder.mBinding.tvOrderStatus.setText(myOrderModel.getOrderStatus() + dbHelper.getString(R.string.text_on) + Utils.getDateFormate(myOrderModel.getCreatedDate()));
        holder.mBinding.tvProductName.setText(myOrderModel.getProductName());
        holder.mBinding.tvOrderNumber.setText(dbHelper.getString(R.string.text_order_number) + myOrderModel.getId());
        holder.mBinding.tvSaller.setText(myOrderModel.getShopName());
        holder.mBinding.ratingbar.setVisibility(View.GONE);

        if (myOrderModel.getImagePath() != null && !myOrderModel.getImagePath().contains("http")) {
            Picasso.get().load(BuildConfig.apiEndpoint + myOrderModel.getImagePath()).error(R.drawable.ic_top_seller).into(holder.mBinding.imItemImage);
        } else {
            Picasso.get().load(myOrderModel.getImagePath()).error(R.drawable.ic_top_seller).into(holder.mBinding.imItemImage);
        }


        if (myOrderModel.getOrderStatus().equals("Pending")) {
            holder.mBinding.tvAddReview.setVisibility(View.GONE);
            holder.mBinding.ratingbar.setVisibility(View.GONE);

        } else if (myOrderModel.getOrderStatus().equals("Cancelled")) {
            holder.mBinding.tvAddReview.setVisibility(View.GONE);
            holder.mBinding.ratingbar.setVisibility(View.GONE);

        } else if (myOrderModel.getOrderStatus().equals("Accepted")) {
            holder.mBinding.tvAddReview.setVisibility(View.GONE);
            holder.mBinding.ratingbar.setVisibility(View.GONE);

        } else if (myOrderModel.getOrderStatus().equals("Shipped")) {
            holder.mBinding.tvAddReview.setVisibility(View.GONE);
            holder.mBinding.ratingbar.setVisibility(View.GONE);

        } else if (myOrderModel.getOrderStatus().equals("Delivered") && myOrderModel.getRating() == 0) {
            holder.mBinding.tvAddReview.setVisibility(View.VISIBLE);
        } else {
            holder.mBinding.ratingbar.setVisibility(View.VISIBLE);
            holder.mBinding.ratingbar.setRating(myOrderModel.getRating());
            holder.mBinding.tvAddReview.setVisibility(View.GONE);
        }

        holder.mBinding.tvAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, ReviewActivity.class).putExtra("OrderID",myOrderModel.getId()));
            }
        });
        holder.mBinding.LLMainCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, OrderDetailActivity.class).putExtra("myOrderModel",myOrderModel));
            }
        });

    }

    @Override
    public int getItemCount() {
        return orderModelArrayList == null ? 0 : orderModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemMyOrderBinding mBinding;

        public ViewHolder(ItemMyOrderBinding Binding) {
            super(Binding.getRoot());
            this.mBinding = Binding;

        }
    }
}
