package com.pickurapps.usernameavailabilitychecker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Website> mWebsiteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebsiteList = new ArrayList<>();
        mWebsiteList.add(new Website("Facebook", R.drawable.ic_facebook, Color.parseColor("#3b5998")));
        mWebsiteList.add(new Website("Instagram", R.drawable.ic_instagram, Color.parseColor("#ffdc80")));
        mWebsiteList.add(new Website("Twitter", R.drawable.ic_twitter, Color.parseColor("#1da1f2")));
        mWebsiteList.add(new Website("Youtube", R.drawable.ic_youtube, Color.parseColor("#ff0000")));
        mWebsiteList.add(new Website("Pinterest", R.drawable.ic_pinterest, Color.parseColor("#bd081c")));
        mWebsiteList.add(new Website("Spotify", R.drawable.ic_spotify, Color.parseColor("#1db954")));
        mWebsiteList.add(new Website("Reddit", R.drawable.ic_reddit, Color.parseColor("#ff4500")));
        mWebsiteList.add(new Website("Github", R.drawable.ic_github, Color.parseColor("#c9510c")));
        mWebsiteList.add(new Website("Ebay", R.drawable.ic_ebay, Color.parseColor("#f5af02")));
        mWebsiteList.add(new Website("Behance", R.drawable.ic_behance, Color.parseColor("#1769ff")));

        RecyclerView recyclerView = findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this, mWebsiteList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(recyclerViewAdapter);

    }
}
