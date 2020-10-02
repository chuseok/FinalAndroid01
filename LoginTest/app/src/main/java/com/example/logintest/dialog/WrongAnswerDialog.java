package com.example.logintest.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.logintest.R;
import com.example.logintest.WordCardFragment;


public class WrongAnswerDialog extends Dialog implements WrongAnswerDialogCallback {

    private Context context;
    private WrongAnswerDialogCallback wrongAnswerDialogCallback;
    private View.OnClickListener continueListener;
    private TextView titleTextView, continueTextView, wordTextView, meaningTextView, inputTextView;

    private String wordValue, meaningValue, inputValue;

    /*public WrongAnswerDialog(@NonNull Context context, WrongAnswerDialogListener wrongAnswerDialogListener) {
        super(context);
        this.context = context;
        this.wrongAnswerDialogListener = wrongAnswerDialogListener;
    }*/

    public WrongAnswerDialog(@NonNull Context context, View.OnClickListener continueListener) {
        super(context);
        this.context = context;
        this.continueListener = continueListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wrong_answer_dialog);

        WordCardFragment wordCardFragment = new WordCardFragment();
        wordCardFragment.setOnWrongAnswerSetListner(this);


        titleTextView = findViewById(R.id.wrong_answer_dialog_title_tv);
        wordTextView = findViewById(R.id.wrong_answer_dialog_word_tv);
        meaningTextView = findViewById(R.id.wrong_answer_dialog_answer_meaning);
        inputTextView = findViewById(R.id.wrong_answer_dialog_input);
        continueTextView = findViewById(R.id.wrong_answer_dialog_continue_tv);
        wordTextView.setText(wordValue);
        meaningTextView.setText(meaningValue);
        inputTextView.setText(inputValue);

        continueTextView.setOnClickListener(continueListener);
    }

    @Override
    public void onDialogSet(String word, String meaning, String inputText) {
        wordValue = word;
        meaningValue = meaning;
        inputValue = inputText;
    }
}
