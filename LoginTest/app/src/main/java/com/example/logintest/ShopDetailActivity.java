package com.example.logintest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.logintest.Utils.MobileSize;
import com.example.logintest.domain.ShopItem;
import com.example.logintest.manager.SharedPrefManager;
import com.example.logintest.volley.URLs;
import com.example.logintest.volley.VolleySingleton;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYouListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShopDetailActivity extends AppCompatActivity {

    private TextView coinText, title, description, price, checkCount, checkPrice, totalPrice;
    private ImageView image;
    private FloatingActionButton closeBtn, minusBtn, plusBtn;
    private CheckBox writeSelf;
    private EditText count;
    private LinearLayout background;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        Intent intent = getIntent();

        image = findViewById(R.id.ac_shopDetail_productImage_iv);
        coinText = findViewById(R.id.ac_shopDetail_coin_tv);
        title = findViewById(R.id.ac_shopDetail_name_tv);
        description = findViewById(R.id.ac_shopDetail_description_tv);
        price = findViewById(R.id.ac_shopDetail_price_tv);
        checkCount = findViewById(R.id.ac_shopDetail_checkCount_tv);
        checkPrice = findViewById(R.id.ac_shopDetail_checkPrice_tv);
        totalPrice = findViewById(R.id.ac_shopDetail_totalPrice_tv);
        closeBtn = findViewById(R.id.ac_shopDetail_close_btn);
        minusBtn = findViewById(R.id.ac_shopDetail_minus_btn);
        plusBtn = findViewById(R.id.ac_shopDetail_plus_btn);
        writeSelf = findViewById(R.id.ac_shopDetail_writeSelf_cb);
        count = findViewById(R.id.ac_shopDetail_count_et);
        background = findViewById(R.id.ac_shopDetail_background_ll);

        final ShopItem item = (ShopItem)intent.getSerializableExtra("name");
        title.setText(item.getName());
        if(item.getImage().contains(".svg")){
            GlideToVectorYou
                    .init()
                    .with(getApplicationContext())
                    .withListener(new GlideToVectorYouListener() {
                        @Override
                        public void onLoadFailed() { System.out.println("failed load image"); }

                        @Override
                        public void onResourceReady() { }
                    })
                    .load(Uri.parse(item.getImage()), image);
        }else if(item.getImage().contains(".png")){
            Glide.with(getApplicationContext()).load(item.getImage()).into(image);
        }
        description.setText(item.getDescription());
        price.setText(item.getPrice()+"");
        checkPrice.setText(item.getPrice()+"");
        totalPrice.setText(item.getPrice()+"");

        MobileSize mobileSize = new MobileSize();
        mobileSize.getStandardSize(this);
        float displayXHeight = mobileSize.getStandardSize_X();
        float displayYHeight = mobileSize.getStandardSize_Y();
        mobileSize.setLayoutWidth(image,(int)(displayXHeight*0.3));
        mobileSize.setLayoutHeight(image,(int)(displayYHeight*0.3));
        mobileSize.setLayoutMargin(background,0,-(int)(displayYHeight*0.15),0,0);
        mobileSize.setLayoutMargin(title,0,(int)(displayYHeight*0.1),0,(int)(displayYHeight*0.02));
        mobileSize.setLayoutMargin(price,0,0,0,(int)(displayYHeight*0.01));
        mobileSize.setLayoutMargin(description,0,0,0,(int)(displayYHeight*0.05));


        writeSelf.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    count.setFocusableInTouchMode(true);
                    count.setEnabled(true);
                    showSoftKeyboard(count);
                    count.requestFocus();
                }else{
                    count.setFocusableInTouchMode(false);
                    count.setEnabled(false);
                }
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {//뒤로가기 이벤트
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        count.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String countText = count.getText().toString();
                String priceText = checkPrice.getText().toString();
                if(countText.equals("")||countText==null){
                    checkCount.setText("0");
                    totalPrice.setText("0");
                }else {
                    checkCount.setText(countText);
                    totalPrice.setText(Integer.parseInt(priceText)*Integer.parseInt(countText)+"");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String countText = count.getText().toString();
                if(countText!=null){
                    if(countText.equals("")){
                        count.setText("0");
                    }else if(Integer.parseInt(countText)==0){
                        count.setText("0");
                    }else {
                        count.setText((Integer.parseInt(countText) - 1) + "");
                    }
                }
            }
        });
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String countText = count.getText().toString();
                if(countText!=null){
                    if(countText.equals("")){
                        count.setText("0");
                    }else {
                        count.setText((Integer.parseInt(countText) + 1) + "");
                    }
                }
            }
        });
        final String userId = SharedPrefManager.getInstance(getApplicationContext()).getUser().getUserId();

        //get coin
        StringRequest getCoinRequest = new StringRequest(Request.Method.GET, URLs.URL_+"?userId="+userId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);
                            int coin = object.getInt("coin");
                            coinText.setText(coin+"");
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
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //params.put("userId", "111111");
                //params.put("dragonId", "3");
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(getCoinRequest);//get coin end

        StringRequest buyRequest = new StringRequest(Request.Method.GET, URLs.URL_SHOP_BUY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
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
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userId", userId);
                params.put("productId", item.getProductId()+"");
                params.put("buyAmount", count.getText().toString()+"");
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(buyRequest);
    }

    public void showSoftKeyboard(View view) { //키보드 오픈 이벤트
        if(view.requestFocus()){
            InputMethodManager imm =(InputMethodManager)
                    getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {//edit text focus event
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}