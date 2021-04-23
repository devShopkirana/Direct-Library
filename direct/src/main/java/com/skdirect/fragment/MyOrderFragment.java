package com.skdirect.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.skdirect.R;
import com.skdirect.activity.MainActivity;

import com.skdirect.databinding.FragmentMyOrderdBinding;
import com.skdirect.utils.DirectSDK;

public class MyOrderFragment extends Fragment {
    private FragmentMyOrderdBinding mBinding;
    private MainActivity activity;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_orderd, container, false);
        initViews();

        return mBinding.getRoot();
    }

    private void initViews() {
        mBinding.toolbarTittle.tvTittle.setText(DirectSDK.getInstance().dbHelper.getString(R.string.my_order));
        mBinding.toolbarTittle.ivBackPress.setVisibility(View.GONE);;
        activity.appBarLayout.setVisibility(View.GONE);
    }


}
