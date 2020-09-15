package com.example.logintest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.logintest.Utils.MobileSize;

public class RegisterSecondActivity extends AppCompatActivity {

    TextView authTextView;
    EditText emailEditText;
    LinearLayout phoneLayout;
    LinearLayout authLayout;
    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_second);

        MobileSize mobileSize = new MobileSize();
        mobileSize.getStandardSize(this);
        float displayXHeight = mobileSize.getStandardSize_X();
        float displayYHeight = mobileSize.getStandardSize_Y();
        float authImageSize = displayYHeight/5;

        authTextView = findViewById(R.id.authTextView);
        emailEditText = findViewById(R.id.ac_register_email_et);
        phoneLayout = findViewById(R.id.ac_register_phone_layout);
        authLayout = findViewById(R.id.ac_register_auth_layout);
        signUpButton = findViewById(R.id.ac_register_sign_up_bt);

        mobileSize.setLayoutHeight(authTextView, (int) authImageSize);
        mobileSize.setLayoutHeight(emailEditText, (int) (displayYHeight-authImageSize) / 10);
        mobileSize.setLayoutHeight(phoneLayout, (int) (displayYHeight-authImageSize) / 10);
        mobileSize.setLayoutHeight(authLayout, (int) (displayYHeight-authImageSize) / 10);
        mobileSize.setLayoutHeight(signUpButton, (int) (displayYHeight-authImageSize) / 10);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.ac_res_two_main_toolbar);
        mToolbar.setTitle(R.string.register_title);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}