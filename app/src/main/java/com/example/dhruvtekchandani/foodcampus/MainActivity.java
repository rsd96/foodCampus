package com.example.dhruvtekchandani.foodcampus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_map:
                startActivity(new Intent(this,MapsActivityView.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    DatabaseReference databaseRestaurantInformation;
    ListView listViewRestaurantInformation;
    List<RestaurantInformation> restaurantList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("main", "working ... ");

        listViewRestaurantInformation = (ListView) findViewById(R.id.restaurantImage);
        databaseRestaurantInformation = FirebaseDatabase.getInstance().getReference("RestaurantInformation");
        restaurantList = new ArrayList<>();



        databaseRestaurantInformation.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                restaurantList.clear();
                for(DataSnapshot restaurantSnapshot : dataSnapshot.getChildren()){
                    RestaurantInformation restaurantInformation = restaurantSnapshot.getValue(RestaurantInformation.class);
                    restaurantList.add(restaurantInformation);
                }
                RestaurantList adapter = new RestaurantList(MainActivity.this, restaurantList);
                listViewRestaurantInformation.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listViewRestaurantInformation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, RestaurantDetailView.class);
                RestaurantInformation restaurantInformation = restaurantList.get(i);
                intent.putExtra("RestaurantName",restaurantInformation.getRestName());
                intent.putExtra("RestaurantTimings",restaurantInformation.getRestTimingsOffSession());
                intent.putExtra("RestaurantPayment",restaurantInformation.getPaymentMethod());
                intent.putExtra("RestaurantLocation",restaurantInformation.getRestLocation());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();


    }
}

