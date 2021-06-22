package com.example.QuazarApp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsFeed extends AppCompatActivity {

    private TextView errorView;
    private ESAHubbleAPI esaHubbleAPI;
    private ListView listView;
    private ArrayList<String> titles;
    private ArrayList<String> newsURLs;
    ArrayList<NewsArticle> articles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news);

        errorView = findViewById(R.id.errorView);
        listView = findViewById(R.id.listView);
        articles = new ArrayList<NewsArticle>();
        titles = new ArrayList<String>();
        newsURLs = new ArrayList<String>();

        Retrofit retrofit = new Retrofit.Builder()
                //FOR NASA API
                .baseUrl("http://hubblesite.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        esaHubbleAPI = retrofit.create(ESAHubbleAPI.class);

        getNews();
    }

    private void getNews()
    {
        Call<ArrayList<NewsArticle>> call = esaHubbleAPI.getNews();

        call.enqueue(new Callback<ArrayList<NewsArticle>>() {
            @Override
            public void onResponse(Call<ArrayList<NewsArticle>> call, Response<ArrayList<NewsArticle>> response) {

                if (!response.isSuccessful()) {
                    errorView.setText("Error Code: " + response.code());
                    return;
                }

                ArrayList<NewsArticle> apiResponse = response.body();

                displayNews(apiResponse);

            }
            @Override
            public void onFailure(Call<ArrayList<NewsArticle>> call, Throwable t) {
                errorView.setText(t.getMessage());
                return;
            }
        });
    }

    private void displayNews(ArrayList<NewsArticle> apiResponse)
    {
        for (NewsArticle article: apiResponse)
        {
            titles.add(article.getTitle());
            newsURLs.add(article.getLink());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, titles);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Uri uri = Uri.parse(newsURLs.get(position));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }
}
