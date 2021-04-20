package com.skdirect.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.skdirect.R;
import com.skdirect.activity.OfferActivity;
import com.skdirect.databinding.ItemCouponListBinding;
import com.skdirect.model.response.OfferResponse;
import com.skdirect.utils.MyApplication;

import java.util.ArrayList;

public class OfferListAdapter extends RecyclerView.Adapter<OfferListAdapter.ViewHolder> {
    private final OfferActivity activity;
    private final ArrayList<OfferResponse.Coupon> list;


    public OfferListAdapter(OfferActivity activity, ArrayList<OfferResponse.Coupon> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_coupon_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OfferResponse.Coupon model = list.get(position);
        holder.mBinding.btnApply.setText(MyApplication.getInstance().dbHelper.getString(R.string.apply));
        holder.mBinding.tvName.setText(model.getCouponName());
        holder.mBinding.tvDes.setText(MyApplication.getInstance().dbHelper.getString(R.string.inr) + " " + model.getAmount() + " " + MyApplication.getInstance().dbHelper.getString(R.string.off_upto_inr) + " " + model.getMaxAmount());
        holder.mBinding.tvTerms.setText(MyApplication.getInstance().dbHelper.getString(R.string.inr) + " " + model.getAmount() + " " + MyApplication.getInstance().dbHelper.getString(R.string.off_upto_inr) + " " + model.getMaxAmount() + " " + MyApplication.getInstance().dbHelper.getString(R.string.on_order_of) + model.getMaxAmount() + " " + MyApplication.getInstance().dbHelper.getString(R.string.or_above));
        holder.mBinding.tvCouponCode.setText("" + model.getCouponCode());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemCouponListBinding mBinding;

        public ViewHolder(ItemCouponListBinding Binding) {
            super(Binding.getRoot());
            this.mBinding = Binding;
            mBinding.btnApply.setOnClickListener(v -> {
                activity.callApplyCoupon(getAdapterPosition(), list.get(getAdapterPosition()).getId());
            });
        }
    }
}
