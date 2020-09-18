package com.example.logintest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.dinuscxj.progressbar.CircleProgressBar;
import com.example.logintest.Utils.MobileSize;
import com.example.logintest.Utils.ViewPagerIndicatorView;
import com.example.logintest.adapter.InventoryAdapterCallBack;
import com.example.logintest.adapter.InventoryCardViewAdapter;
import com.example.logintest.dialog.DragonDialog;
import com.example.logintest.dialog.DragonDialogListener;
import com.example.logintest.domain.Inven;
import com.example.logintest.manager.SharedPrefManager;
import com.example.logintest.volley.URLs;
import com.example.logintest.volley.VolleySingleton;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYouListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DragonDetailActivity extends AppCompatActivity implements InventoryAdapterCallBack, DragonDialogListener {

    TextView levelText, coinText;
    ImageView dragonImage;
    ProgressBar hungryProgress;
    CircleProgressBar circleProgressBar;
    LinearLayout dragonPanel;
    ViewPager viewPager;
    InventoryCardViewAdapter adapter;
    ViewPagerIndicatorView indicatorView;
    List<Inven> invenList;
    DragonDialog dragonDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dragon_detail);
        final Toolbar toolbar = findViewById(R.id.ac_dragonDetail_toolbar);

        toolbar.setTitle("암기용");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//헤더 back button
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        levelText = findViewById(R.id.ac_dragonDetail_Level_tv);
        coinText = findViewById(R.id.ac_dragonDetail_coin_tv);
        dragonImage = findViewById(R.id.ac_dragonDetail_dragonImage_iv);
        circleProgressBar = findViewById(R.id.cpb_circlebar);
        hungryProgress = findViewById(R.id.ac_dragonDetail_hungry_pb);
        dragonPanel = findViewById(R.id.ac_dragonDetail_dragonPanel_ll);
        indicatorView = findViewById(R.id.indicator);
        invenList= new ArrayList<Inven>();
        MobileSize mobileSize = new MobileSize();
        mobileSize.getStandardSize(this);
        final float displayXHeight = mobileSize.getStandardSize_X();
        final float displayYHeight = mobileSize.getStandardSize_Y();
        mobileSize.setLayoutHeight(dragonPanel,(int)displayYHeight/10*6);
        mobileSize.setLayoutWidth(dragonImage,(int)(displayXHeight*0.4));

        //dragon value 값 받아오기
        String userId = SharedPrefManager.getInstance(getApplicationContext()).getUser().getUserId();
        final int dragonId = getIntent().getIntExtra("dragon",0);

        //dialog set
        dragonDialog = new DragonDialog(this,dragonId);
        dragonDialog.setCancelable(false);
        dragonDialog.setDragonDialogListener(this);


        String url = URLs.URL_DRAGON_GET+"?userId="+ userId +"&dragonId="+dragonId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);
                            int levelValue = Integer.parseInt(object.getString("dragonLevelValue"));
                            int dragonLevel = Integer.parseInt(object.getString("dragonTotalLevel"));
                            int hungryValue = Integer.parseInt(object.getString("hungryValue"));
                            int coin = Integer.parseInt(object.getString("coin"));
                            setLevelValue(levelValue);
                            setLevelText(dragonLevel);
                            coinText.setText(coin+"");
                            setFoodValue(hungryValue);
                            setDragonImage(object.getString("dragonImage"));
                            //System.out.println(object.getString("dragonImage"));
                            if(hungryValue==0){
                                dragonDialog.show();
                                Window DialogWindow = dragonDialog.getWindow();
                                DialogWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                DialogWindow.setLayout((int)(displayXHeight*0.8),(int)(displayYHeight*0.6));
                            }

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

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        //dragon value 값 받아오기 end

        //inventory list 값 받아오기
        StringRequest invenStringRequest = new StringRequest(Request.Method.GET, URLs.URL_INVEN_LIST+"?userId="+userId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {

                                String imagePath = array.getJSONObject(i).getString("productImage").replace("../",URLs.ROOT_URL);
                                int count = Integer.parseInt(array.getJSONObject(i).getString("count"));
                                int productId = Integer.parseInt(array.getJSONObject(i).getString("productId"));
                                invenList.add(new Inven(productId,imagePath,count,dragonId));
                                adapter.notifyDataSetChanged();
                            }
                            setDotIndicator(invenList.size());
                            indicatorView.setSelection(0);
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

        VolleySingleton.getInstance(this).addToRequestQueue(invenStringRequest);
        //inventory list 값 받아오기 end

        viewPager = findViewById(R.id.ac_dragonDetail_viewPager);
        mobileSize.setLayoutHeight(viewPager, (int) (displayYHeight*0.18));
        adapter = new InventoryCardViewAdapter(invenList,getApplicationContext());
        adapter.setOnShareClickedListener(this);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(10,0,10,0);



        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {

                indicatorView.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        indicatorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(view.getId());
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        //getSupportFragmentManager().beginTransaction().add(R.id.frag_nav, new DragonListFragment()).commit();
        onBackPressed();
        return true;
    }

    //Adapter implement
    @Override
    public void setLevelValue(int data) {
        circleProgressBar.setProgress(data);
    }

    @Override
    public void setFoodValue(int data) {
        hungryProgress.setProgress(data);
    }

    @Override
    public void setLevelText(int data) {
        levelText.setText(data+"");
    }

    @Override
    public void setDotIndicator(int data) {
        int count = data-2;
        if(count<0){
            count=data;
        }
        indicatorView.init(count,R.drawable.default_dot,R.drawable.selected_dot,15);
    }

    @Override
    public void setDragonImage(String path) {
        String replaceUrl = path.replace("../",URLs.ROOT_URL);
        if(replaceUrl.contains(".svg")){
            GlideToVectorYou
                    .init()
                    .with(getApplicationContext())
                    .withListener(new GlideToVectorYouListener() {
                        @Override
                        public void onLoadFailed() {
                            System.out.println("Image Failed");
                        }

                        @Override
                        public void onResourceReady() {
                            System.out.println("Image Ready");
                        }
                    })
                    .load(Uri.parse(replaceUrl), dragonImage);
        }else if(replaceUrl.contains(".png")){
            Glide.with(getApplicationContext()).load(replaceUrl).into(dragonImage);
        }


    }

    //DragonDialog implement
    @Override
    public void reviveDragon(int data) {
        final String userId = SharedPrefManager.getInstance(getApplicationContext()).getUser().getUserId();
        StringRequest reviveStringRequest = new StringRequest(Request.Method.GET, URLs.URL_DRAGON_REVIVE+"?userId="+userId+"&dragonId="+data,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object.getInt("checkCoin")==-1){
                                Toast.makeText(getApplicationContext(),"코인이 부족합니다.",Toast.LENGTH_SHORT).show();
                            }else{
                                setFoodValue(100);
                                coinText.setText(object.getInt("coin")+"");
                                dragonDialog.dismiss();
                            }
                        } catch (JSONException e) {
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //params.put("userId", userId);
                //params.put("dragonId", "3");
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(reviveStringRequest);
    }

    @Override
    public void cancel() {
        Intent i = new Intent();
        setResult(0,i);
        finish();
    }
}