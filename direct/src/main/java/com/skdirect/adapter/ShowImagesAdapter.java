package com.skdirect.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.skdirect.BuildConfig;
import com.skdirect.R;
import com.skdirect.activity.ShowImageActivity;
import com.skdirect.model.ImageListModel;
import com.skdirect.utils.Utils;
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

    @NotNull
    @Override
    public Object instantiateItem(@NotNull ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = imageLayout.findViewById(R.id.iv_item_image);

        if (imageListModels.get(position).getImagePath() != null && !imageListModels.get(position).getImagePath().contains("http")) {
            Picasso.get().load(BuildConfig.apiEndpoint+imageListModels.get(position).getImagePath()).into(imageView);
        }else {
            Picasso.get().load(imageListModels.get(position).getImagePath()).into(imageView);
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ShowImageActivity.class).putExtra("ImageData",imageListModels));
            }
        });

        view.addView(imageLayout, 0);

        return imageLayout;
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
