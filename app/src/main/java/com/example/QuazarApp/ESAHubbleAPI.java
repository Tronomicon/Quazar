package com.example.QuazarApp;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ESAHubbleAPI {
    @GET("api/v3/external_feed/esa_feed")
    Call<ArrayList<NewsArticle>> getNews();
}
