package com.example.logintest.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class MobileSize {

    private float standardSize_X, standardSize_Y;
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

        standardSize_X = ScreenSize.x;
        standardSize_Y = ScreenSize.y;
    }

    public static float dpFromPx(final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public void setLayoutHeight(View view, int height) {
        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) view.getLayoutParams();
        params.height = (int) height;
        view.setLayoutParams(params);
    }

    public void setLayoutWidth(View view, int width) {
        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) view.getLayoutParams();
        params.width = (int) width;
        view.setLayoutParams(params);
    }

    public void setLayoutMargin(View view, int left, int top, int right, int bottom) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        params.setMargins(left, top, right, bottom);
        view.requestLayout();
    }

    public void setLayoutPadding(View view, int left, int top, int right, int bottom) {
        float density = getDensity();
        view.setPadding(left, top, right, bottom);
    }


    public float getStandardSize_X() {
        return standardSize_X;
    }

    public void setStandardSize_X(int standardSize_X) {
        this.standardSize_X = standardSize_X;
    }

    public float getStandardSize_Y() {
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
