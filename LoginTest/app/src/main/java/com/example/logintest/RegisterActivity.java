package com.example.logintest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.logintest.Utils.MobileSize;

public class RegisterActivity extends AppCompatActivity {

    TextView authTextView;
    TextView loginTextView;
    EditText userIdEditText;
    EditText userPwdEditText;
    EditText userPwdConfirmEditText;
    Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        MobileSize mobileSize = new MobileSize();
        mobileSize.getStandardSize(this);
        float displayXHeight = mobileSize.getStandardSize_X();
        float displayYHeight = mobileSize.getStandardSize_Y();
        float authImageSize = displayYHeight/5;
        authTextView = findViewById(R.id.authTextView);
        loginTextView = findViewById(R.id.loginTextView);
        userIdEditText = findViewById(R.id.ac_register_userId_et);
        userPwdEditText = findViewById(R.id.ac_register_userPwd_et);
        userPwdConfirmEditText = findViewById(R.id.ac_register_userPwd_Confirm_et);
        nextButton = findViewById(R.id.ac_register_next_bt);

        mobileSize.setLayoutHeight(authTextView, (int) authImageSize);
        mobileSize.setLayputMargin(loginTextView, 0, (int) ((displayYHeight-authImageSize) / 10) * 3, 0, 0);
        mobileSize.setLayoutHeight(userIdEditText, (int) (displayYHeight-authImageSize) / 10);
        mobileSize.setLayoutHeight(userPwdEditText, (int) (displayYHeight-authImageSize) / 10);
        mobileSize.setLayoutHeight(userPwdConfirmEditText, (int) (displayYHeight-authImageSize) / 10);
        mobileSize.setLayoutHeight(nextButton, (int) (displayYHeight-authImageSize) / 10);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SecondRegisterActivity = new Intent(getApplicationContext(), RegisterSecondActivity.class);
                startActivity(SecondRegisterActivity);
            }
        });

        Toolbar mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
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