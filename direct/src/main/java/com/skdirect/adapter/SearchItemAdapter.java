package com.skdirect.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.skdirect.BuildConfig;
import com.skdirect.R;
import com.skdirect.activity.ProductDetailsActivity;
import com.skdirect.databinding.ItemSearchDataBinding;
import com.skdirect.model.SearchDataModel;
import com.skdirect.utils.DirectSDK;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemAdapter.ViewHolder> {

    private Context context;
    private ArrayList<SearchDataModel.TableOneModel> list;

    public SearchItemAdapter(Context context, ArrayList<SearchDataModel.TableOneModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_search_data, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SearchDataModel.TableOneModel model = list.get(position);

        holder.mBinding.tvItemName.setText(model.getProductName());
        holder.mBinding.tvPrice.setText("₹ "+String.valueOf(model.getMrp()));
        holder.mBinding.tvSellingPrice.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        holder.mBinding.tvSellingPrice.setText("₹ "+String.valueOf(model.getSellingPrice()));
        holder.mBinding.tvTax.setText(DirectSDK.getInstance().dbHelper.getString(R.string.txt_Inclusive));
        holder.mBinding.tvQty.setText("Qty "+String.valueOf(model.getMeasurement())+ " PC");
        holder.mBinding.tvPice.setText("("+model.getMeasurement()+" "+model.getUom()+" )");
        double offPersentagePrice = model.getMrp()-model.getSellingPrice()/model.getMrp();
        DecimalFormat precision = new DecimalFormat("0.00");
        holder.mBinding.tvPriceOff.setText(precision.format(offPersentagePrice*100));

        if (model.getNoofView()>0){
            holder.mBinding.tvNoView.setVisibility(View.VISIBLE);
            holder.mBinding.tvNoView.setText(String.valueOf(model.getNoofView()));

        }

        if (model.getImagePath()!=null && !model.getImagePath().contains("http")) {
            Picasso.get().load(BuildConfig.apiEndpoint+model.getImagePath()).error(R.drawable.ic_top_seller).into(holder.mBinding.ivItemImage);
        }else {
            Picasso.get().load(model.getImagePath()).error(R.drawable.ic_top_seller).into(holder.mBinding.ivItemImage);
        }


        holder.mBinding.LLMainCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.startActivity(new Intent(context, ProductDetailsActivity.class).putExtra("ID",model.getId()));

            }
        });

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemSearchDataBinding mBinding;

        public ViewHolder(ItemSearchDataBinding Binding) {
            super(Binding.getRoot());
            this.mBinding = Binding;
        }
    }
}
