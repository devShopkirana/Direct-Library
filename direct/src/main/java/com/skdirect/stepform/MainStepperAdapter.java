package com.skdirect.stepform;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.skdirect.model.OrderStatusDC;
import com.skdirect.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class MainStepperAdapter extends VerticalStepperAdapter {
    private List<OrderStatusDC> OrderStatusDCList;


    public MainStepperAdapter(Context context, List<OrderStatusDC> list) {
        super(context);
        this.OrderStatusDCList = list;
    }

    @NonNull
    @Override
    public CharSequence getTitle(int position) {
        return OrderStatusDCList.get(position).getStatus();
    }

    @Nullable
    @Override
    public CharSequence getSummary(int position) {
//        return list.get(position).getMessage();
        return Utils.getDateFormate(OrderStatusDCList.get(position).getCreatedDate());
    }

    @Nullable
    @Override
    public CharSequence getDate(int position) {
//        return Utils.getDateTimeFormate(list.get(position).getDate());
        return "";
    }

    @Override
    public int getCount() {
        return OrderStatusDCList == null ? 0 : OrderStatusDCList.size();
    }

    @Override
    public Void getItem(int position) {
        return null;
    }

    @NonNull
    @Override
    public View onCreateContentView(Context context, int position) {
        return new MainItemView(context);
    }
}