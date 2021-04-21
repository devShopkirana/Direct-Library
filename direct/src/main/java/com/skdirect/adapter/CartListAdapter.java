package com.skdirect.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.skdirect.BuildConfig;
import com.skdirect.R;
import com.skdirect.databinding.ItemCartListBinding;
import com.skdirect.interfacee.CartItemInterface;
import com.skdirect.model.CartModel;
import com.skdirect.utils.MySingltonApplication;
import com.skdirect.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<CartModel> cartItemList;
    private final CartItemInterface cartItemInterface;


    public CartListAdapter(Context context, ArrayList<CartModel> cartItemList, CartItemInterface cartItemInterface) {
        this.context = context;
        this.cartItemList = cartItemList;
        this.cartItemInterface = cartItemInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_cart_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartModel cartModel = cartItemList.get(position);
        holder.mBinding.tvProductName.setText(cartModel.getProductName());
        holder.mBinding.tvSellerName.setText(MySingltonApplication.getInstance().dbHelper.getString(R.string.seller_a) + " " + cartModel.getShopName());
        holder.mBinding.tvMrp.setText("₹ " + cartModel.getPrice());
        holder.mBinding.tvSelectedQty.setText("" + cartModel.getQuantity());
        holder.mBinding.tvRomove.setText(MySingltonApplication.getInstance().dbHelper.getString(R.string.remove));

        if (cartModel.getImagePath() != null && !cartModel.getImagePath().contains("http")) {
            Picasso.get().load(BuildConfig.apiEndpoint + cartModel.getImagePath()).into(holder.mBinding.imItemImage);
        } else {
            Picasso.get().load(cartModel.getImagePath()).into(holder.mBinding.imItemImage);
        }

        if (cartModel.getDiscountAmount() > 0.0) {
            double DiscountAmount = cartModel.getPrice() - cartModel.getDiscountAmount();
            holder.mBinding.tvDiscount.setVisibility(View.VISIBLE);
            holder.mBinding.tvDiscount.setText("₹ " + DiscountAmount);
            holder.mBinding.tvMrp.setText("₹ " + cartModel.getMrp());
            holder.mBinding.tvMrp.setPaintFlags(holder.mBinding.tvMrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        holder.mBinding.tvQtyPlus.setOnClickListener(view -> {
            if (cartModel.getMaxOrderQuantity() != null && Integer.parseInt(cartModel.getMaxOrderQuantity()) > 0 && cartModel.getQuantity() >= Integer.parseInt(cartModel.getMaxOrderQuantity())) {
                Utils.setToast(context, context.getString(R.string.order_quantity));
            } else {
                cartModel.setQuantity(cartModel.getQuantity() + 1);
                holder.mBinding.tvSelectedQty.setText("" + cartModel.getQuantity());
                cartItemInterface.plusButtonOnClick(cartModel);
            }
        });
        holder.mBinding.tvQtyMinus.setOnClickListener(view -> {
            cartModel.setQuantity(cartModel.getQuantity() - 1);
            holder.mBinding.tvSelectedQty.setText("" + cartModel.getQuantity());
            cartItemInterface.minusButtonOnClick(cartModel, position);
        });
    }

    @Override
    public int getItemCount() {
        return cartItemList == null ? 0 : cartItemList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemCartListBinding mBinding;

        public ViewHolder(ItemCartListBinding Binding) {
            super(Binding.getRoot());
            this.mBinding = Binding;
            mBinding.tvRomove.setOnClickListener(view -> {
                cartItemInterface.removeButtonOnClick(cartItemList.get(getAdapterPosition()), getAdapterPosition());
            });
        }
    }
}