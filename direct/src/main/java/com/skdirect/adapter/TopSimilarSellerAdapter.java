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
import com.skdirect.activity.SellerProfileActivity;
import com.skdirect.databinding.ItemTopSellerBinding;
import com.skdirect.databinding.ItemTopSimilarSellerBinding;
import com.skdirect.model.TopSellerModel;
import com.skdirect.model.TopSimilatSellerModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TopSimilarSellerAdapter extends RecyclerView.Adapter<TopSimilarSellerAdapter.ViewHolder> {

    private ProductDetailsActivity context;
    private ArrayList<TopSimilatSellerModel> topSimilatSellerList;

    public TopSimilarSellerAdapter(ProductDetailsActivity context, ArrayList<TopSimilatSellerModel> topSellerList) {
        this.context = context;
        this.topSimilatSellerList = topSellerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_top_similar_seller, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TopSimilatSellerModel topSellerModel = topSimilatSellerList.get(position);
        holder.mBinding.tvSellerName.setText(topSellerModel.getProductName());

        holder.mBinding.tvMesserment.setText("("+topSellerModel.getMeasurement()+" "+topSellerModel.getUom()+")");

        if (topSellerModel.getMrp()==topSellerModel.getSellingPrice()){
            holder.mBinding.tvMrp.setVisibility(View.GONE);
            holder.mBinding.tvSellingPrice.setText("₹ "+String.format("%.2f", topSellerModel.getSellingPrice()));
        }else {
            holder.mBinding.tvMrp.setText("₹ "+Math.round(topSellerModel.getMrp()));
            holder.mBinding.tvMrp.setPaintFlags(holder.mBinding.tvMrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.mBinding.tvSellingPrice.setText("₹ "+Math.round(topSellerModel.getSellingPrice()));
        }



        if (topSellerModel.getNoOfView()>0){
            holder.mBinding.llNoOfView.setVisibility(View.VISIBLE);
            holder.mBinding.tvItemView.setText(String.valueOf(topSellerModel.getNoOfView()));

        }else
        {

        }


        if (topSellerModel.getImagePath()!=null && !topSellerModel.getImagePath().contains("http")) {
            Picasso.get().load(BuildConfig.apiEndpoint+topSellerModel.getImagePath()).error(R.drawable.ic_top_seller).into(holder.mBinding.ivImage);
        }else {
            Picasso.get().load(topSellerModel.getImagePath()).placeholder(R.drawable.ic_top_seller).into(holder.mBinding.ivImage);
        }

        holder.mBinding.llTopSellar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.finish();
                Intent menuIntent = new Intent(context, ProductDetailsActivity.class);
                menuIntent.putExtra("ID",topSellerModel.getId());
                context.startActivity(menuIntent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return topSimilatSellerList == null ? 0 : topSimilatSellerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemTopSimilarSellerBinding mBinding;

        public ViewHolder(ItemTopSimilarSellerBinding Binding) {
            super(Binding.getRoot());
            this.mBinding = Binding;
        }
    }
}
