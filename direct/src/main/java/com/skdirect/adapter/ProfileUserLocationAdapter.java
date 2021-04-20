package com.skdirect.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.skdirect.R;
import com.skdirect.activity.EditAddressActivity;
import com.skdirect.activity.NewAddressActivity;
import com.skdirect.databinding.ItemProfileUserLocationBinding;
import com.skdirect.interfacee.MakeDefaultInterface;
import com.skdirect.model.UserLocationModel;

import java.util.ArrayList;

public class ProfileUserLocationAdapter extends RecyclerView.Adapter<ProfileUserLocationAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<UserLocationModel> locationModelList;
    public int selectedPosition = 0;
    private MakeDefaultInterface makeDefaultInterface;

    public ProfileUserLocationAdapter(Context context, ArrayList<UserLocationModel> locationModels, int position, MakeDefaultInterface defaultInterface) {
        this.context = context;
        this.locationModelList = locationModels;
        selectedPosition = position;
        makeDefaultInterface = defaultInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_profile_user_location, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserLocationModel userLocationModel = locationModelList.get(position);
        holder.mBinding.imgVerified.setChecked(selectedPosition == position);

        if (selectedPosition == position) {
            holder.mBinding.imgVerified.setVisibility(View.VISIBLE);
            holder.mBinding.imgVerified.setBackground(context.getResources().getDrawable(R.drawable.correct));
        } else {
            holder.mBinding.imgVerified.setVisibility(View.INVISIBLE);
            holder.mBinding.imgVerified.setBackground(context.getResources().getDrawable(R.drawable.ic_right));
        }

        holder.mBinding.tvShopName.setText(userLocationModel.getAddressOne());
        holder.mBinding.tvAddresTwo.setText(userLocationModel.getAddressTwo());
        holder.mBinding.tvAddresThree.setText(userLocationModel.getAddressThree());
        holder.mBinding.tvCityName.setText(userLocationModel.getCity() + " - " + userLocationModel.getPincode() + " (" + userLocationModel.getState() + ")");


        holder.mBinding.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenuView(holder,userLocationModel,position);
            }
        });


    }

    private void PopupMenuView(ViewHolder holder, UserLocationModel userLocationModel, int position) {
        PopupMenu popup = new PopupMenu(context,holder.mBinding.ivOpcationValue);
        popup.inflate(R.menu.options_menu);



        if (selectedPosition==position){
            popup.getMenu().findItem(R.id.menu_delet).setVisible(false);
        }
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.menu_make_defult) {
                    makeDefaultInterface.defaultOnClick(userLocationModel, position);
                    return true;
                } else if (itemId == R.id.menu_edit) {
                    editAdddressView(userLocationModel);
                    return true;
                } else if (itemId == R.id.menu_delet) {
                    makeDefaultInterface.deleteLocationClick(userLocationModel);
                    return true;
                }
                return false;
            }
        });

        popup.show();
    }

    private void editAdddressView(UserLocationModel userLocationModel) {
        context.startActivity(new Intent(context, EditAddressActivity.class).putExtra("UserEditData",userLocationModel));
    }


    @Override
    public int getItemCount() {
        return locationModelList == null ? 0 : locationModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemProfileUserLocationBinding mBinding;

        public ViewHolder(ItemProfileUserLocationBinding Binding) {
            super(Binding.getRoot());
            this.mBinding = Binding;

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
