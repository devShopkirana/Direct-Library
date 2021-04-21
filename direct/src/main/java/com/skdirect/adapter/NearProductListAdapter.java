package com.skdirect.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.skdirect.BuildConfig;
import com.skdirect.R;
import com.skdirect.activity.ProductDetailsActivity;
import com.skdirect.databinding.ItemNearProductListBinding;
import com.skdirect.model.NearProductListModel;
import com.skdirect.utils.MySingltonApplication;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NearProductListAdapter extends RecyclerView.Adapter<NearProductListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<NearProductListModel> nearProductListList;

    public NearProductListAdapter(Context context, ArrayList<NearProductListModel> nearProductListList) {
        this.context = context;
        this.nearProductListList = nearProductListList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_near_product_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NearProductListModel nearProductListModel = nearProductListList.get(position);
        holder.mBinding.tvItemName.setText(nearProductListModel.getProductName());

        holder.mBinding.tvMrpTitle.setText(MySingltonApplication.getInstance().dbHelper.getString(R.string.txt_mrp));
        holder.mBinding.tvSellingPriceTitle.setText(MySingltonApplication.getInstance().dbHelper.getString(R.string.txt_selling_price));
        holder.mBinding.tvInclusiveAll.setText(MySingltonApplication.getInstance().dbHelper.getString(R.string.txt_Inclusive));
        holder.mBinding.tvNearByViewAll.setText(MySingltonApplication.getInstance().dbHelper.getString(R.string.detail));

        if (nearProductListModel.getMrp() == nearProductListModel.getSellingPrice()) {
            holder.mBinding.llSellingPrice.setVisibility(View.GONE);
            holder.mBinding.tvMrp.setText("₹ " + nearProductListModel.getMrp());
        } else {
            holder.mBinding.tvMrp.setText("₹ " + nearProductListModel.getMrp());
            holder.mBinding.tvMrp.setPaintFlags(holder.mBinding.tvMrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.mBinding.llSellingPrice.setVisibility(View.VISIBLE);
            holder.mBinding.tvSellingPrice.setText("₹ " + nearProductListModel.getSellingPrice());
        }

        if (nearProductListModel.getOffPercentage() != 0.0) {
            holder.mBinding.tvMagrginOff.setVisibility(View.VISIBLE);
            holder.mBinding.tvMagrginOff.setText("" + nearProductListModel.getOffPercentage() + "%\n OFF");
        } else {
            holder.mBinding.tvMagrginOff.setVisibility(View.GONE);
        }


        if (nearProductListModel.getNoofView() > 0) {
            holder.mBinding.llNoOfView.setVisibility(View.VISIBLE);
            holder.mBinding.tvItemView.setText(String.valueOf(nearProductListModel.getNoofView()));
        } else {
            holder.mBinding.llNoOfView.setVisibility(View.INVISIBLE);
        }

        if (nearProductListModel.getImagePath()!=null && !nearProductListModel.getImagePath().contains("http")) {
            Picasso.get().load(BuildConfig.apiEndpoint+nearProductListModel.getImagePath()).error(R.drawable.ic_top_seller).into(holder.mBinding.imItemImage);
        }else {
            Picasso.get().load(nearProductListModel.getImagePath()).error(R.drawable.ic_top_seller).into(holder.mBinding.imItemImage);
        }

        holder.mBinding.tvNearByViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menuIntent = new Intent(context, ProductDetailsActivity.class);
                menuIntent.putExtra("ID",nearProductListModel.getId());
                menuIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(menuIntent);

            }
        });




    }

    @Override
    public int getItemCount() {
        return nearProductListList == null ? 0 : nearProductListList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemNearProductListBinding mBinding;

        public ViewHolder(ItemNearProductListBinding Binding) {
            super(Binding.getRoot());
            this.mBinding = Binding;
        }
    }
}
