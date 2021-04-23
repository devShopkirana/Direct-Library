package com.skdirect.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.skdirect.R;
import com.skdirect.databinding.ItemLanguagedBinding;
import com.skdirect.interfacee.OnLanguageClick;
import com.skdirect.utils.SharePrefs;

import java.util.ArrayList;


public class LanguageListAdapter extends RecyclerView.Adapter<LanguageListAdapter.ViewHolder> {
    private final ArrayList<DataSnapshot> list;
    private final Activity activity;
    private final OnLanguageClick onLanguageClick;

    public LanguageListAdapter(Activity activity, ArrayList<DataSnapshot> list, OnLanguageClick onLanguageClick) {
        this.activity = activity;
        this.list = list;
        this.onLanguageClick = onLanguageClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_languaged, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataSnapshot dataSnapshot = list.get(position);
        holder.mBinding.tvLanguage.setText(dataSnapshot.getKey());
        holder.mBinding.cbLanguage.setChecked(SharePrefs.getInstance(activity).getString(SharePrefs.SELECTED_LANGUAGE).equalsIgnoreCase(dataSnapshot.getKey()));

        holder.mBinding.RLLanguage.setOnClickListener(view -> {
            SharePrefs.getInstance(activity).putString(SharePrefs.SELECTED_LANGUAGE, dataSnapshot.getKey());
            onLanguageClick.onSelectLanguage(position);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemLanguagedBinding mBinding;

        public ViewHolder(ItemLanguagedBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }
    }
}