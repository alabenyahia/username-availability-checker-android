package com.pickurapps.usernameavailabilitychecker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    List<Website> mWebsiteList;
    private final String FACEBOOK_BASE_URL = "https://username-availability.herokuapp.com/check/facebook/";
    private final String INSTAGRAM_BASE_URL = "https://username-availability.herokuapp.com/check/instagram/";
    private final String TWITTER_BASE_URL = "https://username-availability.herokuapp.com/check/twitter/";
    //private final String YOUTUBE_BASE_URL = "https://username-availability.herokuapp.com/check/youtube/";
    private final String PINTEREST_BASE_URL = "https://username-availability.herokuapp.com/check/pinterest/";
    //private final String SPOTIFY_BASE_URL = "https://username-availability.herokuapp.com/check/spotify/";
    //private final String REDDIT_BASE_URL = "https://username-availability.herokuapp.com/check/reddit/";
    private final String GITHUB_BASE_URL = "https://username-availability.herokuapp.com/check/github/";
    //private final String EBAY_BASE_URL = "https://username-availability.herokuapp.com/check/ebay/";
    private final String BEHANCE_BASE_URL = "https://username-availability.herokuapp.com/check/behance/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextInputEditText textInputEditText = findViewById(R.id.textInputEditText);
        textInputEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    String editTextString = textInputEditText.getText().toString();
                    if (editTextString != null && editTextString.length() > 0){
                        String facebookFinalUrl = FACEBOOK_BASE_URL + editTextString;
                        requestingJsonFromApi(facebookFinalUrl);
                        String instagramFinalUrl = INSTAGRAM_BASE_URL + editTextString;
                        requestingJsonFromApi(instagramFinalUrl);
                        String twitterFinalUrl = TWITTER_BASE_URL + editTextString;
                        requestingJsonFromApi(twitterFinalUrl);
                        String pinterestFinalUrl = PINTEREST_BASE_URL + editTextString;
                        requestingJsonFromApi(pinterestFinalUrl);
                        String githubFinalUrl = GITHUB_BASE_URL + editTextString;
                        requestingJsonFromApi(githubFinalUrl);
                        String behanceFinalUrl = BEHANCE_BASE_URL + editTextString;
                        requestingJsonFromApi(behanceFinalUrl);
                    }
                    LinearLayout rootLayout = findViewById(R.id.rootLayout);
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(rootLayout.getWindowToken(), 0);
                    }
                    return true;
                }
                return false;
            }
        });

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

    private void requestingJsonFromApi(String url) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //Log.d("jsondata", response.toString());
                JsonData jsonData = JsonData.fromJson(response);
                Log.d("jsondata", jsonData.toString());
            }

            @Override
            public  void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                Log.e("requesterror", e.toString());
                Log.d("requesterror", "Status code " +statusCode);
                Toast.makeText(MainActivity.this, "Request Failed check your internet", Toast.LENGTH_SHORT);
            }
        });
    }

}
