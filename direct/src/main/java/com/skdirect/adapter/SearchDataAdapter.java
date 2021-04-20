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
import com.skdirect.activity.SellerProfileActivity;
import com.skdirect.databinding.ItemCategoriesBinding;
import com.skdirect.databinding.ItemSearchBinding;
import com.skdirect.model.AllCategoriesModel;
import com.skdirect.model.SearchDataModel;
import com.skdirect.utils.MyApplication;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchDataAdapter extends RecyclerView.Adapter<SearchDataAdapter.ViewHolder> {

    private Context context;
    private ArrayList<SearchDataModel.TableOneTwo> list;

    public SearchDataAdapter(Context context, ArrayList<SearchDataModel.TableOneTwo> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_search, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SearchDataModel.TableOneTwo model = list.get(position);
        holder.mBinding.tvSaller.setText(model.getShopName());
        holder.mBinding.tvViewMore.setText(MyApplication.getInstance().dbHelper.getString(R.string.view_more));
        holder.mBinding.tvCityName.setText(model.getAddressOne()+"\n"+model.getAddressTwo()+", "+model.getCityName());

        if (model.getDistance()!=0.0) {
            holder.mBinding.tvLocatin.setText(String.format("%.2f", model.getDistance()) + " " + "Km");
        }else {
            holder.mBinding.tvLocatin.setText("");
        }

        if (model.getImagePath()!=null && !model.getImagePath().contains("http")) {
            Picasso.get().load(BuildConfig.apiEndpoint+model.getImagePath()).error(R.drawable.ic_top_seller).into(holder.mBinding.imItemImage);
        }else {
            Picasso.get().load(model.getImagePath()).error(R.drawable.ic_top_seller).into(holder.mBinding.imItemImage);
        }


        holder.mBinding.tvViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, SellerProfileActivity.class).putExtra("ID",model.getProductList().get(0).getSellerId()));
            }
        });


        SearchItemAdapter searchDataAdapter = new SearchItemAdapter(context, model.getProductList());
        holder.mBinding.rvSearchItem.setAdapter(searchDataAdapter);

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemSearchBinding mBinding;

        public ViewHolder(ItemSearchBinding Binding) {
            super(Binding.getRoot());
            this.mBinding = Binding;
        }
    }
}
