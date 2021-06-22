package com.example.QuazarApp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RoverPhotosActivity extends AppCompatActivity {
    private TextView errorView;
    private NASA_API api;
    private ArrayList<String> photoUrls;
    private ArrayList<String> titles;
    private ArrayList<String> descriptions;
    private ArrayList<String> dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_p_o_d);

        //getting the id of text view
        errorView = findViewById(R.id.errorView);

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

        getPhotos(getIntent().getStringExtra("rover"));

    }


    private void getPhotos(String rover) {

        Call<RoverPhotos> call = api.getRover(rover,"wBIgEUsZqnP4T3Y0NbgAdUzqzHZgOqvjXE0mtV5N", 1);

        //Asynchronously sends the request (call), talks with server and processes response
        call.enqueue(new Callback<RoverPhotos>() {
            @Override
            public void onResponse(Call<RoverPhotos> call, Response<RoverPhotos> response) {

                if (!response.isSuccessful()) {
                    errorView.setText("Code: " + response.code());
                    return;
                }

                //getting the converted (JSON -> Java) objects
                RoverPhotos roverResponse = response.body();

                //method to add the response into separate lists
                //assert roverResponse != null;
                accessArrayList(roverResponse);
            }

            @Override
            public void onFailure(Call<RoverPhotos> call, Throwable t) {
                errorView.setText(t.getMessage());
            }
        });
    }

    private void accessArrayList(RoverPhotos roverResponse) {

        for (RoverPhoto roverPhotoObject : roverResponse.getLatestPhotos())
        {
            //if the APOD is a video, use the thumbnail instead
            photoUrls.add(0, roverPhotoObject.getImg_src());
            titles.add(0, roverPhotoObject.getRover().getName());
            dates.add(0, roverPhotoObject.getEarth_date());
            descriptions.add(0, "Photo taken from " + roverPhotoObject.getRover().getName()+ "\n" + roverPhotoObject.getCamera().getFull_name() );

        }

        //reference to view pager
        ViewPager viewPager = findViewById(R.id.view_pager);

        //instance of pager adapter
        ViewPagerAdapter adapter = new ViewPagerAdapter(this, photoUrls, titles, descriptions, dates);

        //to set view pager with adapter
        viewPager.setAdapter(adapter);

    }

}