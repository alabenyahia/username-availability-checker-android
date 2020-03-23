package com.pickurapps.usernameavailabilitychecker;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.textfield.TextInputEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    List<Website> mWebsiteList;
    private final String FACEBOOK_BASE_URL = "https://username-availability.herokuapp.com/check/facebook/";
    private final String INSTAGRAM_BASE_URL = "https://username-availability.herokuapp.com/check/instagram/";
    private final String TWITTER_BASE_URL = "https://username-availability.herokuapp.com/check/twitter/";
    private final String PINTEREST_BASE_URL = "https://username-availability.herokuapp.com/check/pinterest/";
    private final String GITHUB_BASE_URL = "https://username-availability.herokuapp.com/check/github/";
    private final String BEHANCE_BASE_URL = "https://username-availability.herokuapp.com/check/behance/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-6185803298667574/6899404241");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });

        mWebsiteList = new ArrayList<>();
        mWebsiteList.add(new Website("Facebook", R.drawable.ic_facebook, Color.parseColor("#3b5998")));
        mWebsiteList.add(new Website("Instagram", R.drawable.ic_instagram, Color.parseColor("#ffdc80")));
        mWebsiteList.add(new Website("Twitter", R.drawable.ic_twitter, Color.parseColor("#1da1f2")));
        mWebsiteList.add(new Website("Pinterest", R.drawable.ic_pinterest, Color.parseColor("#bd081c")));
        mWebsiteList.add(new Website("Github", R.drawable.ic_github, Color.parseColor("#c9510c")));
        mWebsiteList.add(new Website("Behance", R.drawable.ic_behance, Color.parseColor("#1769ff")));

        RecyclerView recyclerView = findViewById(R.id.recyclerview_id);
        final RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this, mWebsiteList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(recyclerViewAdapter);

        final TextInputEditText textInputEditText = findViewById(R.id.textInputEditText);
        textInputEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    String editTextString = textInputEditText.getText().toString();
                    if (editTextString != null && editTextString.length() > 0){
                        String facebookFinalUrl = FACEBOOK_BASE_URL + editTextString;
                        requestingJsonFromApi(facebookFinalUrl, false, mWebsiteList.get(0), recyclerViewAdapter);
                        String instagramFinalUrl = INSTAGRAM_BASE_URL + editTextString;
                        requestingJsonFromApi(instagramFinalUrl, false, mWebsiteList.get(1), recyclerViewAdapter);
                        String twitterFinalUrl = TWITTER_BASE_URL + editTextString;
                        requestingJsonFromApi(twitterFinalUrl, false, mWebsiteList.get(2), recyclerViewAdapter);
                        String pinterestFinalUrl = PINTEREST_BASE_URL + editTextString;
                        requestingJsonFromApi(pinterestFinalUrl, true, mWebsiteList.get(3), recyclerViewAdapter);
                        String githubFinalUrl = GITHUB_BASE_URL + editTextString;
                        requestingJsonFromApi(githubFinalUrl, false, mWebsiteList.get(4), recyclerViewAdapter);
                        String behanceFinalUrl = BEHANCE_BASE_URL + editTextString;
                        requestingJsonFromApi(behanceFinalUrl, false, mWebsiteList.get(5), recyclerViewAdapter);
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


    }

    private void requestingJsonFromApi(String url, final boolean isPinterest, final Website website, final RecyclerViewAdapter recyclerViewAdapter) {
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Loading...");
        progress.setIndeterminate(false);
        progress.setCancelable(false);
        progress.show();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                progress.dismiss();
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
                JsonData jsonData = JsonData.fromJson(response);
                if (!checkIfPossible(jsonData)) {
                    changeBgImgViewToColor(website, Color.RED, recyclerViewAdapter);
                }
                else if(checkIfUsernameIsUsable(jsonData, isPinterest)){
                    changeBgImgViewToColor(website, Color.GREEN, recyclerViewAdapter);
                }
                else {
                    changeBgImgViewToColor(website, Color.WHITE, recyclerViewAdapter);
                }
            }

            @Override
            public  void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                progress.dismiss();
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
                Toast.makeText(MainActivity.this, "Request Failed check your internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean checkIfUsernameIsUsable(JsonData jsonData, boolean isPinterest) {
        if (isPinterest) return jsonData.getAvatar() == null;
        else return jsonData.getStatus() != 200;
    }

    public boolean checkIfPossible(JsonData jsonData) {
        return jsonData.getPossible();
    }

    public void changeBgImgViewToColor(Website website, int color, RecyclerViewAdapter recyclerViewAdapter) {
        website.setBgColor(color);
        recyclerViewAdapter.notifyDataSetChanged();
    }

}
