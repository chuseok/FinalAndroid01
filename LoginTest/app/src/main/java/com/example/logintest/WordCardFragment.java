package com.example.logintest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.logintest.Utils.MobileSize;
import com.example.logintest.dialog.WrongAnswerDialog;
import com.example.logintest.dialog.WrongAnswerDialogCallback;
import com.example.logintest.domain.Model;
import com.example.logintest.domain.Word;
import com.example.logintest.volley.URLs;
import com.example.logintest.volley.VolleySingleton;

import org.json.JSONArray;

import java.util.ArrayList;


public class WordCardFragment extends Fragment {

    private static final String TAG = "WORD_CARD_FRAGMENT";

    private static final String ARG_PARAM1 = "word";
    private static final String ARG_PARAM2 = "position";
    private static final String ARG_PARAM3 = "model";

    private ArrayList<Word> mParam1;
    private int mParam2;
    private Model mParam3;
    String answer = "";
    String word = "";
    String meaning = "";
    Word wordObj;
    int wordTotal = 0;
    int knowTotal = 0;
    int unknownTotal = 0;

    float displayXHeight;
    float displayYHeight;

    private TextView wordTextView, wordDialogTextView;
    private EditText answerEditText;
    private Button answerButton;
    private WrongAnswerDialog wrongAnswerDialog;

    private OnAnswerSelectedListner onAnswerSelectedListner;
    private WrongAnswerDialogCallback wrongAnswerDialogCallback;

    public interface OnAnswerSelectedListner {
        void onAnswerSelected(int position, int wordTotal);
//        void onAnswerCorrect(int knowTotal, int unknownTotal);
        void onAnswerCorrect(int position);

    }



    public void setOnWrongAnswerSetListner(WrongAnswerDialogCallback wrongAnswerDialogCallback) {
        this.wrongAnswerDialogCallback = wrongAnswerDialogCallback;
    }
    public void setOnAnswerSelectedListner(OnAnswerSelectedListner onAnswerSelectedListner) {
        this.onAnswerSelectedListner = onAnswerSelectedListner;
    }
    /*public void setOnWordCardCallbackListner(wordCardCallback wordCardCallback) {
        this.wordCardCallback = wordCardCallback;
    }*/
    public WordCardFragment() {
    }

    public static WordCardFragment newInstance(ArrayList<Word> param1, Model param3, int param2) {
        WordCardFragment fragment = new WordCardFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, param1);
        args.putParcelable(ARG_PARAM3, param3);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onAnswerSelectedListner = (OnAnswerSelectedListner) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getParcelableArrayList(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
            mParam3 = getArguments().getParcelable(ARG_PARAM3);
        }

/*        if (savedInstanceState == null) {
            getChildFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_word_card_container, CardFrontFragment.newInstance(mParam1, mParam2))
                    .commit();
        }뒤집기~*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_word_card, container, false);


        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        wordTextView = view.findViewById(R.id.fragment_word_card_word);

        answerEditText = view.findViewById(R.id.fragment_answer_edit);
        answerButton = view.findViewById(R.id.fragment_answer_button);



        MobileSize mobileSize = new MobileSize();
        getStandardSize(mobileSize);
        mobileSize.setLayoutWidth(answerEditText, (int) (displayXHeight / 3));

        wordTotal = mParam1.size();
        wordObj = mParam1.get(mParam2);
        word = wordObj.getWord();
        meaning = wordObj.getMeaning();
        wordTextView.setText(word);
        answerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer = answerEditText.getText().toString();

                if (answer.equals(meaning)) {
                    Log.d(TAG, "result : 정답~");
                    Log.d(TAG, "answer : " + answer + ", getMeaning : " + meaning);

                    updateRateFromQuestion();
                    onAnswerSelectedListner.onAnswerCorrect(mParam2);
                    onAnswerSelectedListner.onAnswerSelected(mParam2, wordTotal);
                } else {
                    Log.d(TAG, "result : 오답~");
                    Log.d(TAG, "answer : " + answer + ", getMeaning : " + meaning);
                    Log.d(TAG, "position : " + mParam2 + ", wordTotal : " + wordTotal);
                    if((mParam2+1)==wordTotal) {
                        Log.d(TAG, "position : " + mParam2 + ", wordTotal : " + wordTotal);
                        onAnswerSelectedListner.onAnswerSelected(mParam2, wordTotal);
                        return;
                    }

                    wrongAnswerDialog = new WrongAnswerDialog(getContext(), continueListener);
                    wrongAnswerDialog.onDialogSet(word, meaning, answer);
                    wrongAnswerDialog.show();

                    wrongAnswerDialog.setCanceledOnTouchOutside(true);
                    wrongAnswerDialog.setCancelable(true);
                    wrongAnswerDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    wrongAnswerDialog.show();
                }
            }
        });

    }

    private void getStandardSize(MobileSize mobileSize) {
        mobileSize.getStandardSize(getActivity());
        displayXHeight = mobileSize.getStandardSize_X();
        displayYHeight = mobileSize.getStandardSize_Y();
    }



    private void updateRateFromQuestion() {
        String url = URLs.URL_STUDY_MODIFY_RATE_FROM_QUESTION + "?userId=" + mParam3.getId() + "&bookTitle=" + mParam3.getTitle() + "&word=" + word;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "wordCardFragment", Toast.LENGTH_SHORT).show();
                    }
                }) {
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    private View.OnClickListener continueListener = new View.OnClickListener() {
        public void onClick(View v) {
            wrongAnswerDialog.dismiss();
            onAnswerSelectedListner.onAnswerSelected(mParam2, wordTotal);
        }
    };

}


