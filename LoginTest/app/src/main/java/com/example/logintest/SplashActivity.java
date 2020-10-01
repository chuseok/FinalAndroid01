package com.example.logintest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.example.logintest.Utils.MobileSize;
import com.example.logintest.manager.SharedPrefManager;
import com.example.logintest.volley.URLs;
import com.example.logintest.volley.VolleySingleton;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SPLASH_ACTIVITY";

    ImageView splashImageView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setupWindowAnimations();
        
        splashImageView = findViewById(R.id.splashImageView);
        progressBar = findViewById(R.id.progress);

        MobileSize mobileSize = new MobileSize();
        mobileSize.getStandardSize(this);
        float displayXHeight = mobileSize.getStandardSize_X();
        float displayYHeight = mobileSize.getStandardSize_Y();
        float authImageHeight = displayYHeight/5;
        float authImageWidth = (displayXHeight/5) * 3;

        mobileSize.setLayoutMargin(splashImageView, 0, (int) (displayYHeight/3), 0, 0);
        mobileSize.setLayoutHeight(splashImageView, (int) authImageHeight);
        mobileSize.setLayoutWidth(splashImageView, (int) authImageWidth);
        mobileSize.setLayoutWidth(progressBar, (int) displayXHeight / 4);
        mobileSize.setLayoutMargin(progressBar, 0, (int) (displayYHeight / 20), 0, 0);

        getData();
    }

    private void setupWindowAnimations() {
        Slide slide = (Slide) TransitionInflater.from(this).inflateTransition(R.transition.activity_slide);
        getWindow().setExitTransition(slide);
    }

    class LoadDataAsyncTask extends AsyncTask<String, Integer, Boolean> {

        ArrayList<String> idArray = new ArrayList<>();
        ArrayList<String> titleArray = new ArrayList<>();
        ArrayList<String> learningRateArray = new ArrayList<>();

        public LoadDataAsyncTask() {
        };

        @Override
        protected Boolean doInBackground(String... strings) {
            //단어장 제목, 학습 진행률

            StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_STUDY_GETLIST,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONArray array = new JSONArray(response);

                                String userId = SharedPrefManager.getInstance(getApplicationContext()).getUser().getUserId();

                                Log.d(TAG, "userId : " + userId);
                                for (int i = 0; i < array.length(); i++) {
                                    int count=0;
                                    int percent = 0;
                                    if (userId.equalsIgnoreCase(array.getJSONObject(i).getString("userId"))) {
                                        idArray.add(array.getJSONObject(i).getString("userId"));
                                        titleArray.add(array.getJSONObject(i).getString("wordTitle"));
                                        learningRateArray.add(array.getJSONObject(i).getString("learningRate"));
                                    }

                                }
                                Thread.sleep(1000);

                                Intent MainActivity = new Intent(getApplicationContext(), MainActivity.class);
                                MainActivity.putStringArrayListExtra("wordId", idArray);
                                MainActivity.putStringArrayListExtra("wordTitle", titleArray);
                                MainActivity.putStringArrayListExtra("learningRate", learningRateArray);
                                startActivity(MainActivity);
                                finish();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String character = null;
                    try {
                        character = new String(response.data, "UTF-8");
                        return Response.success(character, HttpHeaderParser.parseCacheHeaders(response));
                    } catch (UnsupportedEncodingException e) {
                        return Response.error(new ParseError(e));
                    } catch (Exception e) {
                        // log error
                        return Response.error(new ParseError(e));
                    }
                }
            };

            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

            return true;
        }
    }

    private void getData() {
        LoadDataAsyncTask loadData = new LoadDataAsyncTask();
        loadData.execute();
    }
}