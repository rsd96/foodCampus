package com.example.dhruvtekchandani.foodcampus;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by dhruvtekchandani on 2/12/18.
 */

public class ImageAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<String> mMenuImageList;
    ImageAdapter(Context context, ArrayList<String> menuImageList){
        mContext = context;
        mMenuImageList = menuImageList;
    }

    @Override
    public int getCount() {
        return mMenuImageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final PhotoView photoView = new PhotoView(mContext);
        photoView.setScaleType(ImageView.ScaleType.CENTER);
        photoView.setAdjustViewBounds(true);
        Picasso.with(mContext)
                .load(mMenuImageList.get(position))
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(photoView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        // Try again online if cache failed
                        Picasso.with(mContext)
                                .load(mMenuImageList.get(position))
                                .placeholder(R.drawable.placeholder)
                                .into(photoView);
                    }
                });
        //Picasso.with(mContext).setIndicatorsEnabled(true);
        container.addView(photoView, 0);
        return photoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView)object);
    }
}
