package com.example.logintest.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.logintest.DragonDetailActivity;
import com.example.logintest.MainActivity;
import com.example.logintest.R;
import com.example.logintest.Utils.MobileSize;

public class DragonDialog extends Dialog {

    private DragonDialog dragonDialog;
    private DragonDialogListener dragonDialogListener;
    private int dragonId;
    public DragonDialog(@NonNull Context context, int dragonId) {
        super(context);
        this.dragonId = dragonId;
    }

    public void setDragonDialogListener(DragonDialogListener dragonDialogListener) {
        this.dragonDialogListener = dragonDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.5f;
        getWindow().setAttributes(lpWindow);//흐리게 설정


        setContentView(R.layout.custom_dialog);
        dragonDialog = this;


        Button reviveBtn = this.findViewById(R.id.dialog_revive_btn);
        Button backBtn = this.findViewById(R.id.dialog_back_btn);

        reviveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dragonDialogListener.reviveDragon(dragonId);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dragonDialog.dismiss();
                dragonDialogListener.cancel();
            }
        });

    }
}
