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
import com.skdirect.databinding.ItemVariationListBinding;
import com.skdirect.interfacee.BottomBarInterface;
import com.skdirect.model.VariationListModel;
import com.skdirect.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BottomListAdapter extends RecyclerView.Adapter<BottomListAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<VariationListModel> variationList;
    private BottomBarInterface bottomBarInterface;

    public BottomListAdapter(Context context, ArrayList<VariationListModel> variationListModels, BottomBarInterface bottomBarInter) {
        this.context = context;
        this.variationList = variationListModels;
        bottomBarInterface = bottomBarInter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_variation_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VariationListModel variationListModel = variationList.get(position);
        String varientName = "";
        holder.mBinding.tvItemPrice.setText("₹ " + variationListModel.getMrp());
        holder.mBinding.tvItemPriceOff.setText("₹ " + variationListModel.getSellingPrice());
        holder.mBinding.tvPercent.setText("( " + variationListModel.getOffPercentage() + ")" + "%  Off");
        holder.mBinding.tvItemPrice.setPaintFlags(holder.mBinding.tvItemPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        if (variationListModel.getProductVariantAttributeDC() != null && variationListModel.getProductVariantAttributeDC().size() > 0) {
            for (int i = 0; i < variationListModel.getProductVariantAttributeDC().size(); i++) {
                varientName = varientName + variationListModel.getProductVariantAttributeDC().get(i).getAttributeValue() + " ";
            }
        }
        holder.mBinding.tvItemName.setText(varientName);
        if (variationListModel.getImageList() != null && variationListModel.getImageList().size() > 0) {

            if (variationListModel.getImageList().get(position).getImagePath() != null && !variationListModel.getImageList().get(position).getImagePath().contains("http")) {
                Picasso.get().load(BuildConfig.apiEndpoint+variationListModel.getImageList().get(position).getImagePath()).into(holder.mBinding.ivImage);
            }else {
                Picasso.get().load(variationListModel.getImageList().get(position).getImagePath()).into(holder.mBinding.ivImage);
            }
        }
        holder.mBinding.llTopNearBySeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomBarInterface.onOnClick(variationList,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return variationList == null ? 0 : variationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemVariationListBinding mBinding;

        public ViewHolder(ItemVariationListBinding Binding) {
            super(Binding.getRoot());
            this.mBinding = Binding;
        }
    }
}
