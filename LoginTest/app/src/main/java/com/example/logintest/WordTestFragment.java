package com.example.logintest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.logintest.adapter.ShopItemListAdapter;
import com.example.logintest.adapter.WordTestAdapter;
import com.example.logintest.domain.Model;
import com.example.logintest.domain.ShopItem;
import com.example.logintest.domain.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WordTestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WordTestFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private List<Word> list;
    private WordTestAdapter adapter;

    public WordTestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment wordTestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WordTestFragment newInstance(String param1, String param2) {
        WordTestFragment fragment = new WordTestFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        final View view = inflater.inflate(R.layout.fragment_word_test, container, false);
        final RecyclerView recyclerView = view.findViewById(R.id.frag_word_test_recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),0));

        list = new ArrayList<>();
        list.add(new Word("낱말카드","플래시 카드를 이용한 단어 암기"));
        list.add(new Word("학습하기","단어 뜻 맞추기를 통한 단어 암기"));
        list.add(new Word("주관식","영어 단어 직접 입력"));
        list.add(new Word("랜덤 학습","학습이 랜덤으로 진행"));
        adapter = new WordTestAdapter(getContext(),list, (MainActivity)this.getActivity());
        recyclerView.setAdapter(adapter);

        return view;
    }
}