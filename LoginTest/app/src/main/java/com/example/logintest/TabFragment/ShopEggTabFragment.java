package com.example.logintest.TabFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import com.example.logintest.Utils.MobileSize;
import com.example.logintest.adapter.ItemListAdapter;
import com.example.logintest.adapter.ShopItemListAdapter;
import com.example.logintest.domain.Collection;
import com.example.logintest.domain.ShopItem;
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
 * Use the {@link ShopEggTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopEggTabFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private List<ShopItem> list;
    private ShopItemListAdapter adapter;

    public ShopEggTabFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment Shop_DragonTabFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShopEggTabFragment newInstance() {
        ShopEggTabFragment fragment = new ShopEggTabFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_shop_egg_tab, container, false);
        final RecyclerView recyclerView = view.findViewById(R.id.frag_shop_egg_tab_recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        /*
        MobileSize mobileSize = new MobileSize();
        mobileSize.getStandardSize(getActivity());
        float displayXHeight = mobileSize.getStandardSize_X();
        float displayYHeight = mobileSize.getStandardSize_Y();

         */

        list = new ArrayList<>();
        String userId = SharedPrefManager.getInstance(getContext()).getUser().getUserId();
        final String url = URLs.URL_SHOP_LIST;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                int productId = Integer.parseInt(array.getJSONObject(i).getString("productId"));
                                String productImage = array.getJSONObject(i).getString("productImage").replace("../",URLs.ROOT_URL);
                                String productName = array.getJSONObject(i).getString("productName");
                                String category = array.getJSONObject(i).getString("category");
                                String description = array.getJSONObject(i).getString("description");
                                int price = Integer.parseInt(array.getJSONObject(i).getString("price"));

                                //setItemList(procession, userLevel, level1, level2, level3, level1Name, level2Name, level3Name);
                                if(category.equals("egg")){
                                    list.add(new ShopItem(productId,productImage,productName,category,description,price));
                                }
                                adapter.notifyDataSetChanged();
                                //count.setText(list.size()+" selected");
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
        adapter = new ShopItemListAdapter(getContext(),list, (MainActivity)this.getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));

        return view;
    }
}