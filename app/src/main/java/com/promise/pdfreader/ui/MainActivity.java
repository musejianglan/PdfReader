package com.promise.pdfreader.ui;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.promise.pdfreader.App;
import com.promise.pdfreader.R;
import com.promise.pdfreader.dao.GreenDaoManager;
import com.promise.pdfreader.entities.PdfInfoEntity;
import com.promise.pdfreader.greendao.gen.PdfInfoEntityDao;
import com.promise.pdfreader.ui.adapter.PdfAdapter;
import com.promise.pdfreader.uitils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private List<PdfInfoEntity> pdfs = new ArrayList<>();

    private PdfInfoEntityDao pdfInfoEntityDao;

    private PdfAdapter pdfAdapter;

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            toast("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            App.getInstance().exitApp();
        }
    }


    @Override
    protected void initView() {

        checkPermission();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        pdfAdapter = new PdfAdapter(this,pdfs);
        recyclerView.setAdapter(pdfAdapter);

    }

    @Override
    protected void initData() {



        pdfAdapter.setOnItemClickListener(new PdfAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PdfInfoEntity pdfInfoEntity, int position) {

                // TODO reader
                Bundle bundle = new Bundle();
                bundle.putLong("pdfId",pdfInfoEntity.getId());
                openPage(ReaderActivity.class,bundle);

            }

            @Override
            public void onItemLongClick(final PdfInfoEntity pdfInfoEntity, final int position) {
//
                new MaterialDialog.Builder(MainActivity.this)
                        .title("警告")
                        .content("是否将文件从书架移除")
                        .positiveText("删除")
                        .negativeText("取消")
//                        .onAny((dialog, which) -> showToast(which.name() + "!"))
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                GreenDaoManager.getInstance().getDaoSession().getPdfInfoEntityDao().delete(pdfInfoEntity);
                                pdfs.remove(position);
//                                pdfAdapter.notifyDataSetChanged();
                                pdfAdapter.notifyItemRemoved(position);
                                pdfAdapter.notifyItemRangeRemoved(position,pdfs.size());
                            }
                        })
                        .show();
            }
        });
        Log.d("569",getChannel(this));

    }

    private String getChannel(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo appInfo = pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return appInfo.metaData.getString("UMENG_CHANNEL");
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return "";
    }

    @Override
    protected void onResume() {
        super.onResume();
        pdfInfoEntityDao = GreenDaoManager.getInstance().getDaoSession().getPdfInfoEntityDao();

        pdfs = pdfInfoEntityDao.loadAll();
        pdfAdapter.setPdfs(pdfs);
    }

    @Override
    protected void beforeSetContentView() {
    }

    @OnClick(R.id.setting)
    public void settings(){
        openPage(SettingActivity.class);
    }

    @OnClick(R.id.add_pdf)
    public void addPDF(){

//        startActivity(new Intent(MainActivity.this,FindPdfActivity.class));
        openPage(FindPdfActivity.class);



    }

    @Override
    public int getContentRes() {
        return R.layout.activity_main;
    }
}
