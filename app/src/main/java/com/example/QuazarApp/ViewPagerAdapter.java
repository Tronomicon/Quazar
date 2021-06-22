package com.example.QuazarApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private List<String> imageUrls;
    private ArrayList<String> titles;
    private ArrayList<String> descriptions;
    private ArrayList<String> dates;

    //constructor; to pass our variable values to our adapter
    ViewPagerAdapter(Context context, List<String> imageUrls, ArrayList<String> titles, ArrayList<String> descriptions, ArrayList<String> dates)
    {
        this.context = context;
        this.imageUrls = imageUrls;
        this.titles = titles;
        this.descriptions = descriptions;
        this.dates = dates;
    }

    //to get the number of slideable pages
    @Override
    public int getCount() {
        return imageUrls.size();
    }

    //to make sure the data goes to the right page/view
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        //creating a new xml layout
        LayoutInflater inflater = (LayoutInflater)container.getContext
                ().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.slider_pager, null);

        //to load images into the imageView
        ImageView imageView = layout.findViewById(R.id.image);
        Picasso.get()
                .load(imageUrls.get(position))
                .fit()                  //resize image according to imageView
                .centerCrop()
                .into(imageView);       //where we want to load the url

        //to set image titles
        TextView titleView = layout.findViewById(R.id.imageTitle);
        titleView.setText(titles.get(position));

        //to set image dates
        TextView dateView = layout.findViewById(R.id.imageDate);
        dateView.setText(dates.get(position));

        //to set image descriptions
        TextView descView = layout.findViewById(R.id.imageDescription);
        descView.setText(descriptions.get(position));

        layout.findViewById(R.id.saveButton).setOnClickListener(v ->
                ImageShareSaveUtil.imageDownload(context, imageUrls.get(position), "APOD-" + dates.get(position) + ".jpg"));

        layout.findViewById(R.id.shareButton).setOnClickListener(v ->
                ImageShareSaveUtil.imageShare(context, imageUrls.get(position), "APOD-" + dates.get(position) + ".jpg"));

        container.addView(layout);

        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
