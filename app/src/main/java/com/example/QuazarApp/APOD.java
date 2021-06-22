package com.example.QuazarApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APOD extends AppCompatActivity {
    private TextView errorView;
    private NASA_API api;
    private ArrayList<String> photoUrls;
    private ArrayList<String> titles;
    private ArrayList<String> descriptions;
    private ArrayList<String> dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_progress);
        //getting the id of text view
        //errorView = findViewById(R.id.errorView);


        Retrofit retrofit = new Retrofit.Builder()
                //FOR NASA API
                .baseUrl("https://api.nasa.gov/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(NASA_API.class);

        photoUrls = new ArrayList<String>();
        titles = new ArrayList<String>();
        descriptions = new ArrayList<String>();
        dates = new ArrayList<String>();

        getPhotos();

    }


    private void getPhotos() {

        String startDate = "2021-05-24";

        Call<ArrayList<Photos>> call = api.getPhotos("wBIgEUsZqnP4T3Y0NbgAdUzqzHZgOqvjXE0mtV5N", true, startDate);

        //Asynchronously sends the request (call), talks with server and processes response
        call.enqueue(new Callback<ArrayList<Photos>>() {
            @Override
            public void onResponse(Call<ArrayList<Photos>> call, Response<ArrayList<Photos>> response) {

                if (!response.isSuccessful()) {
                    //errorView.setText("Code: " + response.code());
                    return;
                }

                //getting the converted (JSON -> Java) objects
                ArrayList<Photos> apodResponse = response.body();

                //method to add the response into separate lists
                accessArrayList(apodResponse);
            }

            @Override
            public void onFailure(Call<ArrayList<Photos>> call, Throwable t) {
                //errorView.setText(t.getMessage());
                return;
            }
        });
    }

    private void accessArrayList(ArrayList<Photos> apodResponse) {

        for (Photos apodObject : apodResponse)
        {
            //if the APOD is a video, use the thumbnail instead
            if (apodObject.getMedia_type().equals("video"))
            {
                //adding to the front of the arrayList to get the most current data back (NASA's JSON starts with the oldest)
                photoUrls.add(0, apodObject.getThumbnail_url());
            }
            else
            {
                photoUrls.add(0, apodObject.getUrl());
            }
            titles.add(0, apodObject.getTitle());
            dates.add(0, apodObject.getDate());
            descriptions.add(0, apodObject.getExplanation());

        }

        //reference to view pager
        setContentView(R.layout.activity_a_p_o_d);
        ViewPager viewPager = findViewById(R.id.view_pager);


        //instance of pager adapter
        ViewPagerAdapter adapter = new ViewPagerAdapter(this, photoUrls, titles, descriptions, dates);

        //to set view pager with adapter
        viewPager.setAdapter(adapter);
    }

}