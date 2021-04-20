package com.skdirect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.skdirect.R;
import com.skdirect.databinding.ItemsFilterBrandBinding;
import com.skdirect.interfacee.FilterCategoryInterface;
import com.skdirect.model.FilterCategoryDetails;
import com.skdirect.utils.TextUtils;

import java.util.ArrayList;

public class BrandsFilterAdapter extends RecyclerView.Adapter<BrandsFilterAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<FilterCategoryDetails> filterTypelist;
    private final FilterCategoryInterface filterCategoryInterface;


    public BrandsFilterAdapter(Context context, ArrayList<FilterCategoryDetails> filterTypelist, FilterCategoryInterface filterCategoryInterface) {
        this.context = context;
        this.filterTypelist = filterTypelist;
        this.filterCategoryInterface = filterCategoryInterface;
    }

    public void setData(ArrayList<FilterCategoryDetails> filterTypelist) {
        this.filterTypelist = filterTypelist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.items_filter_brand, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (!TextUtils.isNullOrEmpty(filterTypelist.get(position).getLabel())) {
            holder.mBinding.tvFilterCategoryName.setText(filterTypelist.get(position).getLabel());
        } else {
            holder.mBinding.tvFilterCategoryName.setText("No Brand");
        }

        holder.mBinding.rbBrandItemSelect.setChecked(filterTypelist.get(position).isSelected());

        holder.mBinding.rbBrandItemSelect.setOnClickListener(v -> {
            if (filterTypelist.get(position).isSelected()) {
                filterTypelist.get(position).setSelected(false);
                holder.mBinding.rbBrandItemSelect.setChecked(false);
                filterCategoryInterface.clickFilterBrandyInterface(filterTypelist.get(position).getValue(), filterTypelist.get(position).getLabel(), true);
            } else {
                filterTypelist.get(position).setSelected(true);
                holder.mBinding.rbBrandItemSelect.setChecked(true);
                filterCategoryInterface.clickFilterBrandyInterface(filterTypelist.get(position).getValue(), filterTypelist.get(position).getLabel(), false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filterTypelist == null ? 0 : filterTypelist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemsFilterBrandBinding mBinding;

        public ViewHolder(ItemsFilterBrandBinding Binding) {
            super(Binding.getRoot());
            this.mBinding = Binding;
        }
    }
}
