package com.example.logintest.TabFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
 * Use the {@link BackgroundTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BackgroundTabFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Collection> list;
    private List<Collection> foundList;
    private List<Collection> notFoundList;
    private ItemListAdapter adapter;
    private Spinner spinner;
    private TextView count;

    public BackgroundTabFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BackgroundTabFragment newInstance(String param1, String param2) {
        BackgroundTabFragment fragment = new BackgroundTabFragment();
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
        final View view = inflater.inflate(R.layout.fragment_background_tab, container, false);
        final RecyclerView recyclerView = view.findViewById(R.id.frag_background_tab_recyclerView);
        spinner = view.findViewById(R.id.frag_background_tab_spinner);
        count = view.findViewById(R.id.frag_background_tab_count_tv);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(),3);
        recyclerView.setLayoutManager(gridLayoutManager);

        //spinner setting
        ArrayAdapter dragonAdapter = ArrayAdapter.createFromResource(getContext(), R.array.itemList_sort,R.layout.spinner_layout);
        dragonAdapter.setDropDownViewResource(R.layout.spinner_layout);
        spinner.setAdapter(dragonAdapter);


        list = new ArrayList<>();
        foundList = new ArrayList<>();
        notFoundList = new ArrayList<>();
        String userId = SharedPrefManager.getInstance(getContext()).getUser().getUserId();
        final String url = URLs.URL_BACKGROUND_COLLECTION+"?userId="+ userId;

        //spinner select listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                if(spinner.getItemAtPosition(position).equals("All")){
                    count.setText(list.size()+" selected");
                    adapter = new ItemListAdapter(getContext(),list, (MainActivity)getActivity());
                    recyclerView.setAdapter(adapter);

                }else if(spinner.getItemAtPosition(position).equals("Found")){
                    foundList.clear();
                    for(int i=0;i<list.size();i++){
                        if(list.get(i).isPossession()){
                            foundList.add(list.get(i));
                        }
                    }
                    count.setText(foundList.size()+" selected");
                    adapter = new ItemListAdapter(getContext(),foundList, (MainActivity)getActivity());
                    recyclerView.setAdapter(adapter);
                }else if(spinner.getItemAtPosition(position).equals("Not Found")){
                    notFoundList.clear();
                    for(int i=0;i<list.size();i++){
                        if(!list.get(i).isPossession()){
                            notFoundList.add(list.get(i));
                        }
                    }
                    count.setText(notFoundList.size()+" selected");
                    adapter = new ItemListAdapter(getContext(),notFoundList, (MainActivity)getActivity());
                    //adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                String backgroundImage = array.getJSONObject(i).getString("backgroundImage").replace("../",URLs.ROOT_URL);
                                String backgroundName = array.getJSONObject(i).getString("backgroundName");
                                Boolean procession = Boolean.valueOf(array.getJSONObject(i).getString("procession"));
                                setItemList(procession, backgroundImage, backgroundName);
                                adapter.notifyDataSetChanged();
                                count.setText(list.size()+" selected");
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
                return params;
            }
        };

        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
        adapter = new ItemListAdapter(getContext(),list, (MainActivity)getActivity());
        recyclerView.setAdapter(adapter);
        return view;
    }
    public void setItemList(Boolean procession, String backgroundImage, String backgroundName){
        if(procession){
                list.add(new Collection(backgroundImage, backgroundName,true));
        }else{
            list.add(new Collection(backgroundImage, backgroundName,false));
        }
    }
}