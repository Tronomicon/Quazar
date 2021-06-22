package com.example.QuazarApp;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NASA_API {

    @GET("planetary/apod")
    //Call<List<Photos>> getPhotos(@Query("api_key") String API_key);
    Call<ArrayList<Photos>> getPhotos(
            @Query("api_key") String API_key,
            @Query("thumbs") boolean thumbs,
            @Query("start_date") String startDate
            );
    @GET("mars-photos/api/v1/rovers/{rover}/latest_photos")
    Call<RoverPhotos> getRover(
            @Path("rover") String rover,
            @Query("api_key") String API_key,
            @Query("page") int page
    );
    //FOR NASA API

}
