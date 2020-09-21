package com.example.logintest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.logintest.Utils.MobileSize;

public class SplashActivity extends AppCompatActivity {

    ImageView splashImageView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

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

        class LoadData extends AsyncTask<String, Integer, String> {
            @Override
            protected String doInBackground(String... strings) {
                //단어장 제목, 학습 진행률

                publishProgress(1);
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                publishProgress(0);

                //onPreExcuted( ) 이미지를 띄워놓기 등 스레드 작업 이전에 수행할 동작

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //onPostExcuted( ) 리턴값을 받아서 동작을 구현 이 두개는 메인 스레드에서 실행됨

            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
            }
        }

    }
}