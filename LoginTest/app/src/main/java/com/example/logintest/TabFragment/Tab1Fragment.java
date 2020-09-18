package com.example.logintest.TabFragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
import com.example.logintest.MainActivity;
import com.example.logintest.R;
import com.example.logintest.adapter.ItemListAdapter;
import com.example.logintest.domain.Collection;
import com.example.logintest.domain.Dragon;
import com.example.logintest.domain.Inven;
import com.example.logintest.manager.SharedPrefManager;
import com.example.logintest.volley.URLs;
import com.example.logintest.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Tab1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab1Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Collection> list;
    private ItemListAdapter adapter;
    private Spinner spinner;

    public Tab1Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab1Fragment newInstance(String param1, String param2) {
        Tab1Fragment fragment = new Tab1Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.frag_dragon_tab_recyclerView);
        spinner = view.findViewById(R.id.frag_dragon_tab_spinner);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(),3);
        recyclerView.setLayoutManager(gridLayoutManager);

        ArrayAdapter dragonAdapter = ArrayAdapter.createFromResource(getContext(), R.array.dragon_sort,R.layout.support_simple_spinner_dropdown_item);
        dragonAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(dragonAdapter);

        list = new ArrayList<>();
        String userId = SharedPrefManager.getInstance(getContext()).getUser().getUserId();
        String url = URLs.URL_DRAGON_COLLECTION+"?userId="+ userId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                String level1 = array.getJSONObject(i).getString("level1").replace("../",URLs.ROOT_URL);
                                String level2 = array.getJSONObject(i).getString("level2").replace("../",URLs.ROOT_URL);
                                String level3 = array.getJSONObject(i).getString("level3").replace("../",URLs.ROOT_URL);
                                String level1Name = array.getJSONObject(i).getString("level1Name");
                                String level2Name = array.getJSONObject(i).getString("level2Name");
                                String level3Name = array.getJSONObject(i).getString("level3Name");
                                Boolean procession = Boolean.valueOf(array.getJSONObject(i).getString("procession"));
                                Integer userLevel = Integer.parseInt(array.getJSONObject(i).getString("dragonLevel"));
                                if(procession){
                                    if(userLevel>=30){
                                        list.add(new Collection(level1, level1Name,true));
                                        list.add(new Collection(level2, level2Name,true));
                                        list.add(new Collection(level3, level3Name,true));
                                    }else if(userLevel>=20&&userLevel<30){
                                        list.add(new Collection(level1, level1Name,true));
                                        list.add(new Collection(level2, level2Name,true));
                                        list.add(new Collection(level3, level3Name,false));
                                    }else if(userLevel>=10&&userLevel<20){
                                        list.add(new Collection(level1, level1Name,true));
                                        list.add(new Collection(level2, level2Name,false));
                                        list.add(new Collection(level3, level3Name,false));
                                    }else{
                                        list.add(new Collection(level1, level1Name,false));
                                        list.add(new Collection(level2, level2Name,false));
                                        list.add(new Collection(level3, level3Name,false));
                                    }
                                }else{
                                    list.add(new Collection(level1, level1Name,false));
                                    list.add(new Collection(level2, level2Name,false));
                                    list.add(new Collection(level3, level3Name,false));
                                }
                                adapter.notifyDataSetChanged();
                                System.out.println(array.getJSONObject(i).getString("level1"));
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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

        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);

        adapter = new ItemListAdapter(getContext(),list, (MainActivity)getActivity());
        recyclerView.setAdapter(adapter);


        return view;
    }
}