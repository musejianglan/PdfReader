package com.promise.pdfreader.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.promise.pdfreader.R;
import com.promise.pdfreader.dao.GreenDaoManager;
import com.promise.pdfreader.entities.PdfInfoEntity;
import com.promise.pdfreader.ui.adapter.PdfAdapter;
import com.promise.pdfreader.uitils.RxUtil;
import com.promise.pdfreader.uitils.SDCardUtil;

import org.reactivestreams.Subscriber;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class FindPdfActivity extends BaseActivity {

    private List<PdfInfoEntity> fileList = new ArrayList<>();

    @BindView(R.id.rv_find_pdf)
    RecyclerView recyclerView;

    PdfAdapter pdfAdapter;


    @Override
    protected void initView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        pdfAdapter = new PdfAdapter(this,fileList);
        recyclerView.setAdapter(pdfAdapter);
    }

    @Override
    protected void initData() {


        File sdCardRootFile = SDCardUtil.getSdCardRootFile();
        if (sdCardRootFile != null) {
            findPdfByRxjava(sdCardRootFile);
        }

        pdfAdapter.setOnItemClickListener(new PdfAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final PdfInfoEntity pdfInfoEntity, final int position) {



                new MaterialDialog.Builder(FindPdfActivity.this)
                        .title("提示")
                        .content("是否将文件添加到书架")
                        .positiveText("添加")
                        .negativeText("取消")
//                        .onAny((dialog, which) -> showToast(which.name() + "!"))
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                try {
                                    GreenDaoManager.getInstance().getDaoSession().getPdfInfoEntityDao().insert(pdfInfoEntity);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    toast("书架中已有该文件，禁止重复添加");
                                }
                            }
                        })
                        .show();

            }

            @Override
            public void onItemLongClick(PdfInfoEntity pdfInfoEntity, int position) {

            }
        });


    }

    @OnClick(R.id.back)
    public void back(){
        finish();

    }

    private void findPdfByRxjava(File rootFile) {


        showProgress("正在搜索PDF文件");

        Observable.just(rootFile)
                .flatMap(new Function<File, ObservableSource<File>>() {
                    @Override
                    public ObservableSource<File> apply(File file) throws Exception {
                        return RxUtil.listFiles(file);
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<File>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(File file) {
                        PdfInfoEntity pdfInfoEntity = new PdfInfoEntity();
                        pdfInfoEntity.setFileName(file.getName());
                        pdfInfoEntity.setPath(file.getPath());
                        pdfInfoEntity.setUpdateTime(new Date());
                        fileList.add(pdfInfoEntity);
                    }

                    @Override
                    public void onError(Throwable e) {

                        dismissProgress();
                    }

                    @Override
                    public void onComplete() {
                        if (fileList.size() > 0) {
                            pdfAdapter.setPdfs(fileList);
                        }

                        dismissProgress();

                    }
                });
    }

    @Override
    protected void beforeSetContentView() {

    }

    @Override
    public int getContentRes() {
        return R.layout.activity_find_pdf;
    }
}
