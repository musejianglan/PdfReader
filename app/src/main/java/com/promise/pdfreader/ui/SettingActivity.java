package com.promise.pdfreader.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.promise.pdfreader.R;

import butterknife.OnClick;

public class SettingActivity extends BaseActivity {


    private String url;

    @OnClick(R.id.support)
    public void support(){
        url = "file:///android_asset/support.html";
        Bundle bundle = new Bundle();
        bundle.putString("weburl",url);
        openPage(WebActivity.class,bundle);

    }

    @OnClick(R.id.give_mark)
    public void give_mark(){

        try{
            Uri uri = Uri.parse("market://details?id="+getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }catch(Exception e){
            toast("您的手机没有安装Android应用市场");
            e.printStackTrace();
        }

    }

    @OnClick(R.id.help)
    public void help(){
        url = "file:///android_asset/help.html";
        Bundle bundle = new Bundle();
        bundle.putString("weburl",url);
        openPage(WebActivity.class,bundle);

    }

    @OnClick(R.id.share)
    public void share(){
//        url = "file:///android_asset/share.html";
//        Bundle bundle = new Bundle();
//        bundle.putString("weburl",url);
//        openPage(WebActivity.class,bundle);
    }


    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void beforeSetContentView() {

    }

    @Override
    public int getContentRes() {
        return R.layout.activity_setting;
    }

    @OnClick(R.id.back)
    public void back(){
        finish();
    }

}
