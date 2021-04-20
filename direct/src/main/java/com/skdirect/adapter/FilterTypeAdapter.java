package com.skdirect.adapter;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.skdirect.R;
import com.skdirect.databinding.ItemsFilterTypeBinding;
import com.skdirect.interfacee.FilterTypeInterface;

import java.util.ArrayList;

public class FilterTypeAdapter extends RecyclerView.Adapter<FilterTypeAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String>filterTypelist;
    private int previousPosition = 0;
    private final FilterTypeInterface filterTypeInterface;
    public FilterTypeAdapter(Context context, ArrayList<String> filterTypelist,FilterTypeInterface filterTypeInterface) {
        this.context = context;
        this.filterTypelist = filterTypelist;
        this.filterTypeInterface = filterTypeInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.items_filter_type, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mBinding.tvFilterType.setText(filterTypelist.get(position));
        holder.mBinding.llMainFilterType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previousPosition = position;
                notifyDataSetChanged();
            }
        });
        if(position == previousPosition){
            filterTypeInterface.clickFilterTypeInterface(filterTypelist.get(position),position);
            holder.mBinding.tvFilterType.setTextColor(Color.parseColor("#000000"));
            holder.mBinding.llMainFilterType.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }
        else {
            holder.mBinding.tvFilterType.setTextColor(Color.parseColor("#FF9e9b9b"));
            holder.mBinding.llMainFilterType.setBackgroundColor(ContextCompat.getColor(context, R.color.light_grey1));
        }
    }

    @Override
    public int getItemCount() {
        return filterTypelist == null ? 0 : filterTypelist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemsFilterTypeBinding mBinding;

        public ViewHolder(ItemsFilterTypeBinding Binding) {
            super(Binding.getRoot());
            this.mBinding = Binding;
        }
    }
}
