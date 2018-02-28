package com.example.dhruvtekchandani.foodcampus;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by dhruvtekchandani on 2/6/18.
 */

public class RestaurantList extends ArrayAdapter<RestaurantInformation> {


    private Activity context;

    private List<RestaurantInformation> restaurantList;



    public RestaurantList(Activity context, List<RestaurantInformation> restaurantList ){
        super(context,R.layout.activity_restaurant_view,restaurantList);
        this.context = context;
        this.restaurantList = restaurantList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_rest_list_content,null,true);
        TextView restaurantTextView = (TextView) listViewItem.findViewById(R.id.restaurantName);
        TextView foodTypeTextView = (TextView) listViewItem.findViewById(R.id.foodType);
        TextView averageCostTextView = (TextView) listViewItem.findViewById(R.id.averageCost);
        ImageView restaurantImageView = (ImageView) listViewItem.findViewById(R.id.restaurantImage);

        final ProgressBar pbRestList = listViewItem.findViewById(R.id.pbRestList);
        RestaurantInformation restaurantInformation = restaurantList.get(position);
        restaurantTextView.setText(restaurantInformation.getRestName());
        foodTypeTextView.setText(restaurantInformation.getFoodAvailable());
        averageCostTextView.setText("Avg Cost: "+"$"+restaurantInformation.getRestAvgPrice() + " per person");
       // Picasso.with(context).load(restaurantInformation.getRestImageName()).networkPolicy(NetworkPolicy.OFFLINE).into(restaurantImageView);
        Picasso.with(context).load(restaurantInformation.getRestImageName()).into(restaurantImageView,
                new Callback() {
                    @Override
                    public void onSuccess() {
                        pbRestList.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });
        return listViewItem;
    }
}
