package com.example.logintest.Utils;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;

public class MobileSize {

    private int standardSize_X, standardSize_Y;
    private float density;

    public Point getScreenSize(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return  size;
    }

    public void getStandardSize(Activity activity) {
        Point ScreenSize = getScreenSize(activity);
        density  = activity.getResources().getDisplayMetrics().density;

        standardSize_X = (int) (ScreenSize.x / density);
        standardSize_Y = (int) (ScreenSize.y / density);
    }

    public int getStandardSize_X() {
        return standardSize_X;
    }

    public void setStandardSize_X(int standardSize_X) {
        this.standardSize_X = standardSize_X;
    }

    public int getStandardSize_Y() {
        return standardSize_Y;
    }

    public void setStandardSize_Y(int standardSize_Y) {
        this.standardSize_Y = standardSize_Y;
    }

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }
}
