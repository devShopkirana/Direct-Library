package com.skdirect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.skdirect.R;
import com.skdirect.databinding.ItemUserLocationBinding;
import com.skdirect.model.UserLocationModel;

import java.util.ArrayList;

public class UserLocationAdapter extends RecyclerView.Adapter<UserLocationAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<UserLocationModel> locationModelList;
    private int selectedPosition = 0;

    public UserLocationAdapter(Context context, ArrayList<UserLocationModel> locationModels, int position) {
        this.context = context;
        this.locationModelList = locationModels;
        selectedPosition = position;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_user_location, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserLocationModel userLocationModel = locationModelList.get(position);
        holder.mBinding.imgVerified.setChecked(selectedPosition == position);

        if (selectedPosition == position) {
            holder.mBinding.imgVerified.setVisibility(View.VISIBLE);
            holder.mBinding.imgVerified.setBackground(context.getResources().getDrawable(R.drawable.correct));
        } else {
            holder.mBinding.imgVerified.setVisibility(View.GONE);
            //holder.mBinding.imgVerified.setBackground(context.getResources().getDrawable(R.drawable.ic_right));
        }


       /* if (userLocationModel.isPrimaryAddress()){
            holder.mBinding.imgVerified.setBackground(context.getResources().getDrawable(R.drawable.correct));
        }else
        {
            holder.mBinding.imgVerified.setBackground(context.getResources().getDrawable(R.drawable.ic_right));
        }*/


        holder.mBinding.tvShopName.setText(userLocationModel.getAddressOne());
        holder.mBinding.tvAddresh.setText(userLocationModel.getAddressTwo());
        holder.mBinding.tvAddreshTree.setText(userLocationModel.getAddressThree());
        holder.mBinding.tvCityName.setText(userLocationModel.getCity() + " - " + userLocationModel.getPincode() + " (" + userLocationModel.getState() + ")");


    }


    public UserLocationModel getSelectedData() {
        return locationModelList.get(selectedPosition);
    }

    @Override
    public int getItemCount() {
        return locationModelList == null ? 0 : locationModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemUserLocationBinding mBinding;
        public ViewHolder(ItemUserLocationBinding Binding) {
            super(Binding.getRoot());
            this.mBinding = Binding;
            mBinding.LLAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int copyOfLastCheckedPosition = selectedPosition;
                    selectedPosition = getAdapterPosition();
                    notifyItemChanged(copyOfLastCheckedPosition);
                    notifyItemChanged(selectedPosition);
                }
            });
            mBinding.imgVerified.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int copyOfLastCheckedPosition = selectedPosition;
                    selectedPosition = getAdapterPosition();
                    notifyItemChanged(copyOfLastCheckedPosition);
                    notifyItemChanged(selectedPosition);

                }
            });
        }
    }
}
