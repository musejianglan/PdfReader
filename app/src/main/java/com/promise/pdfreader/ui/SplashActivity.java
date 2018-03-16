package com.promise.pdfreader.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.promise.pdfreader.R;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends BaseActivity {



    private void turnNext() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                openPage(MainActivity.class);
                finish();
            }
        },3000);
    }


    @Override
    protected void initView() {



    }

    @Override
    protected void initData() {

        turnNext();
    }

    @Override
    protected void beforeSetContentView() {

    }

    @Override
    public int getContentRes() {
        return R.layout.activity_splash;
    }
}
