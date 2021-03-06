package com.example.logintest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.logintest.Utils.MobileSize;
import com.example.logintest.adapter.DragonCardViewAdapter;
import com.example.logintest.domain.Dragon;
import com.example.logintest.manager.SharedPrefManager;
import com.example.logintest.volley.URLs;
import com.example.logintest.volley.VolleySingleton;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DragonListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DragonListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = "DRAGON_LIST_FRAGMENT";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ViewPager viewPager;
    DragonCardViewAdapter adapter;
    List<Dragon> models;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DragonListFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DragonListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DragonListFragment newInstance(String param1, String param2) {
        DragonListFragment fragment = new DragonListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        models = new ArrayList<>();
        /*
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/

    }
    @Override
    public void onResume() {
        super.onResume();
        final String userId = SharedPrefManager.getInstance(getContext()).getUser().getUserId();
        //adapter = new DragonCardViewAdapter(models,getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_DRAGON_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray array = new JSONArray(response);
                            models.clear();
                            for (int i = 0; i < array.length(); i++) {

                                String replaceUrl = array.getJSONObject(i).getString("dragonImage").replace("../",URLs.ROOT_URL);

                                int hungryValue = Integer.parseInt(array.getJSONObject(i).getString("hungryValue"));
                                int dragonId = Integer.parseInt(array.getJSONObject(i).getString("dragonId"));
                                models.add(new Dragon(replaceUrl,hungryValue,dragonId));
                                //models 변경되었다는것 알리기
                            }
                            adapter.notifyDataSetChanged();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
        viewPager.setAdapter(adapter);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View mainView = inflater.inflate(R.layout.fragment_dragon_list,null);

        final TextView dragonNum = mainView.findViewById(R.id.frag_dragonList_dragonNum_tv);

        MobileSize mobileSize = new MobileSize();
        mobileSize.getStandardSize((MainActivity)getActivity());
        float displayYHeight = mobileSize.getStandardSize_Y();

        mobileSize.setLayoutMargin(dragonNum,0, (int)(displayYHeight*0.1),0,0);

        adapter = new DragonCardViewAdapter(models,mainView.getContext());

        final String userId = SharedPrefManager.getInstance(getContext()).getUser().getUserId();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_DRAGON_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {

                                String replaceUrl = array.getJSONObject(i).getString("dragonImage").replace("../",URLs.ROOT_URL);
                                Log.d(TAG, "onResponse: "+array.getJSONObject(i));

                                int hungryValue = Integer.parseInt(array.getJSONObject(i).getString("hungryValue"));
                                int dragonId = Integer.parseInt(array.getJSONObject(i).getString("dragonId"));
                                models.add(new Dragon(replaceUrl,hungryValue,dragonId));
                            }

                            adapter.notifyDataSetChanged();//models 변경되었다는것 알리기

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

        dragonNum.setText("1/"+models.size());

        viewPager = mainView.findViewById(R.id.frag_dragonList_viewPager);
        mobileSize.setLayoutHeight(viewPager,(int)(displayYHeight*0.5));
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130,0,130,0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                dragonNum.setText((position+1)+"/"+models.size());
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return mainView;
    }
}