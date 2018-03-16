package com.promise.pdfreader.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.promise.pdfreader.App;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * @author: liulei
 * @date:2018/3/15
 */
public abstract class BaseActivity extends AppCompatActivity {


    Toast toast = null;

    MaterialDialog materialDialog;
    MaterialProgressBar materialProgressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeSetContentView();
        setContentView(getContentRes());
        initButterKnife();
        initView();
        initData();

    }



    public void showProgress(String content){
        materialDialog = new MaterialDialog.Builder(this)
                .content(content)
                .progressIndeterminateStyle(false)
                .cancelable(false)
                .progress(true,0)
                .build();
        materialDialog.show();




    }



    public void dismissProgress(){
        materialDialog.dismiss();

    }

    private void initButterKnife() {
        ButterKnife.bind(this);
    }

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void beforeSetContentView();

    public abstract int getContentRes();

    public void toast(String msg){
        if (TextUtils.isEmpty(msg)) return;

        if (toast == null) {
            toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        }else {
            toast.setText(msg);
        }
        toast.show();
    }


    public void openPage(Class clazz, Bundle bundle){

        Intent intent = new Intent(this,clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);

    }

    public void openPage(Class clazz) {
        this.openPage(clazz,null);
    }

    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
//            Manifest.permission.WRITE_CALENDAR,
//            Manifest.permission.CAMERA,
//            Manifest.permission.WRITE_CONTACTS,
//            Manifest.permission.ACCESS_FINE_LOCATION,
//            Manifest.permission.RECORD_AUDIO,
//            Manifest.permission.READ_PHONE_STATE,
//            Manifest.permission.BODY_SENSORS,
//            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    List<String> mPermissionList = new ArrayList<>();

    private static final int PERMISSION_REQUEST_CODE = 0; // 系统权限管理页面的参数



    public void checkPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mPermissionList.clear();
            //检查是否已获取权限
            for (int i = 0; i < PERMISSIONS.length; i++) {
                if (ContextCompat.checkSelfPermission(this, PERMISSIONS[i]) != PackageManager.PERMISSION_GRANTED) {
                    mPermissionList.add(PERMISSIONS[i]);
                }
            }

            if (!mPermissionList.isEmpty()) {//获取权限
                String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
                ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
            }else {

            }

        }


    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            for (int i = 0; i < grantResults.length; i++) {

                int grantResult = grantResults[i];
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i]);
                    if (showRequestPermission) {//
                        ActivityCompat.requestPermissions(this, new String[]{mPermissionList.get(i)}, PERMISSION_REQUEST_CODE);

                    } else {
                        toast("部分权限已禁止，应用无法启动。请到设置--应用手动开启权限");//已经禁止
                        App.getInstance().exitApp();
                    }
                }
            }
        }
    }


}
