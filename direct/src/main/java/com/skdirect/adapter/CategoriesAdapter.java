package com.skdirect.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.skdirect.R;
import com.skdirect.activity.SearchActivity;
import com.skdirect.databinding.ItemAllCategoriesBinding;
import com.skdirect.databinding.ItemCategoriesBinding;
import com.skdirect.model.AllCategoriesModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private Context context;
    private ArrayList<AllCategoriesModel>allCategoriesList;

    public CategoriesAdapter(Context context, ArrayList<AllCategoriesModel>allCategories) {
        this.context = context;
        this.allCategoriesList = allCategories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_categories, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AllCategoriesModel allCategoriesModel = allCategoriesList.get(position);
        holder.mBinding.tvCetegoryName.setText(allCategoriesModel.getName());
        if (allCategoriesModel.getImagePath()!=null){
                Picasso.get().load(allCategoriesModel.getImagePath()).error(R.drawable.no_image).into(holder.mBinding.imItemImage);
        }else {
            Picasso.get()
                    .load(R.drawable.ic_lost_items)
                    .placeholder(R.drawable.ic_lost_items)
                    .error(R.drawable.ic_lost_items)
                    .into(holder.mBinding.imItemImage);
        }
        holder.mBinding.LLMainCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, SearchActivity.class).putExtra("CateogryId",allCategoriesModel.getId()).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });


    }

    @Override
    public int getItemCount() {
        return allCategoriesList == null ? 0 : allCategoriesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemCategoriesBinding mBinding;

        public ViewHolder(ItemCategoriesBinding Binding) {
            super(Binding.getRoot());
            this.mBinding = Binding;
        }
    }
}
