package com.example.dhruvtekchandani.foodcampus;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by dhruvtekchandani on 2/12/18.
 */

public class ImageAdapter extends PagerAdapter {
    private Context mContext;
    private int[] mImageIds = new int[]{R.drawable.delishmenu1,R.drawable.delishmenu2,R.drawable.delishmenu3,R.drawable.delishmenu4,R.drawable.delishmenu5,R.drawable.delishmenu6};
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
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(mContext);
        photoView.setScaleType(ImageView.ScaleType.CENTER);
        photoView.setAdjustViewBounds(true);
        //ImageView imageView = new ImageView(mContext);
        //imageView.setScaleType(ImageView.ScaleType.FIT_START);
        //imageView.setAdjustViewBounds(true);
        Picasso.with(mContext).load(mMenuImageList.get(position)).into(photoView);
        container.addView(photoView, 0);
        return photoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView)object);
    }
}
