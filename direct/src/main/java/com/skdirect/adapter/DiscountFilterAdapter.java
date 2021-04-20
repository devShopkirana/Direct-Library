package com.skdirect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.skdirect.R;
import com.skdirect.databinding.ItemsFilterDiscountBinding;
import com.skdirect.interfacee.FilterCategoryInterface;
import com.skdirect.model.FilterCategoryDetails;

import java.util.ArrayList;

public class DiscountFilterAdapter extends RecyclerView.Adapter<DiscountFilterAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<FilterCategoryDetails> filterTypelist;
    public int lastSelectedPosition = 0;
    private final FilterCategoryInterface filterCategoryInterface;

    public DiscountFilterAdapter(Context context, ArrayList<FilterCategoryDetails>  filterTypelist, FilterCategoryInterface filterCategoryInterface) {
        this.context = context;
        this.filterTypelist = filterTypelist;
        this.filterCategoryInterface = filterCategoryInterface;

    }
    public void setData(ArrayList<FilterCategoryDetails>  filterTypelist) {
        this.filterTypelist = filterTypelist;
        lastSelectedPosition = 0;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.items_filter_discount, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mBinding.tvFilterCategoryName.setText(filterTypelist.get(position).getLabel());
        // ((ViewHolderDiscount) vh).mBinding.rbDiscountItemSelect.setChecked(lastSelectedPosition == position);
        if(position == lastSelectedPosition){
            holder.mBinding.rbDiscountItemSelect.setChecked(true);
            filterCategoryInterface.clickFilterDiscountInterface(filterTypelist.get(position).getValue(),filterTypelist.get(position).getLabel());
        }
        else {
            holder.mBinding.rbDiscountItemSelect.setChecked(false);
        }

        holder.mBinding.rbDiscountItemSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastSelectedPosition = position;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return filterTypelist == null ? 0 : filterTypelist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemsFilterDiscountBinding mBinding;

        public ViewHolder(ItemsFilterDiscountBinding Binding) {
            super(Binding.getRoot());
            this.mBinding = Binding;
        }
    }
}
