package com.skdirect.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.skdirect.BuildConfig;
import com.skdirect.R;
import com.skdirect.activity.ShowImageActivity;
import com.skdirect.databinding.ViewPagerItemdBinding;
import com.skdirect.model.ImageListModel;
import com.skdirect.utils.TextUtils;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ShowImagesAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<ImageListModel> imageListModels;
    private LayoutInflater inflater;

    public ShowImagesAdapter(Context context, ArrayList<ImageListModel> imageListModels1) {
        this.context = context;
        this.imageListModels = imageListModels1;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NotNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ViewPagerItemdBinding mBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.view_pager_itemd, null, false);
        View itemView = mBinding.getRoot();
        try {
            if (!TextUtils.isNullOrEmpty(imageListModels.get(position).getImagePath())) {
                if (!imageListModels.get(position).getImagePath().contains("http")) {
                    Picasso.get().load(BuildConfig.apiEndpoint + imageListModels.get(position).getImagePath()).into(mBinding.itemImaged);
                } else {
                    Picasso.get().load(imageListModels.get(position).getImagePath()).into(mBinding.itemImaged);
                }
            }
            mBinding.itemImaged.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, ShowImageActivity.class).putExtra("ImageData", imageListModels));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        container.addView(itemView);
        return itemView;
    }

    @Override
    public int getCount() {
        return imageListModels.size();
    }

    @Override
    public boolean isViewFromObject(View view, @NotNull Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
