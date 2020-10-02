package com.example.logintest.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class Word implements Parcelable {

    private String word;
    private String meaning;
    private String leaningRate;

    public Word(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
    }

    public Word(String word, String meaning, String leaningRate) {
        this.word = word;
        this.meaning = meaning;
        this.leaningRate = leaningRate;
    }

    protected Word(Parcel in) {
        word = in.readString();
        meaning = in.readString();
    }

    public static final Creator<Word> CREATOR = new Creator<Word>() {
        @Override
        public Word createFromParcel(Parcel in) {
            return new Word(in);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getLeaningRate() {
        return leaningRate;
    }

    public void setLeaningRate(String leaningRate) {
        this.leaningRate = leaningRate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(word);
        parcel.writeString(meaning);

    }
}
