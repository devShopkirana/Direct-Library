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
import com.skdirect.activity.SearchActivity;
import com.skdirect.databinding.ItemAllCategoriesBinding;
import com.skdirect.databinding.ItemTopSellerBinding;
import com.skdirect.model.AllCategoriesModel;
import com.skdirect.model.TopSellerModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AllCategoriesAdapter extends RecyclerView.Adapter<AllCategoriesAdapter.ViewHolder> {

    private Context context;
    private ArrayList<AllCategoriesModel>allCategoriesList;

    public AllCategoriesAdapter(Context context, ArrayList<AllCategoriesModel>allCategories) {
        this.context = context;
        this.allCategoriesList = allCategories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_all_categories, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AllCategoriesModel allCategoriesModel = allCategoriesList.get(position);
        holder.mBinding.tvCetegory.setText(allCategoriesModel.getCategoryName());


       /* if (allCategoriesModel.getImagePath()!=null){
            Picasso.get().load(allCategoriesModel.getImagePath()).error(R.drawable.no_image).into(holder.mBinding.ivImage);

        }*/

        if (allCategoriesModel.getImagePath()!=null && !allCategoriesModel.getImagePath().contains("http")) {
            Picasso.get().load(BuildConfig.apiEndpoint+allCategoriesModel.getImagePath()).error(R.drawable.ic_top_seller).into(holder.mBinding.ivImage);
        }else {
            Picasso.get().load(allCategoriesModel.getImagePath()).placeholder(R.drawable.ic_top_seller).into(holder.mBinding.ivImage);
        }

        holder.mBinding.llMainCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, SearchActivity.class).putExtra("CateogryId",allCategoriesModel.getId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return allCategoriesList == null ? 0 : allCategoriesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemAllCategoriesBinding mBinding;

        public ViewHolder(ItemAllCategoriesBinding Binding) {
            super(Binding.getRoot());
            this.mBinding = Binding;
        }
    }
}
