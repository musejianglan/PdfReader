package com.promise.pdfreader.ui;

import android.graphics.Canvas;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;
import com.promise.pdfreader.R;
import com.promise.pdfreader.dao.GreenDaoManager;
import com.promise.pdfreader.entities.PdfInfoEntity;
import com.promise.pdfreader.uitils.LogUtil;

import java.io.File;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

public class ReaderActivity extends BaseActivity implements OnDrawListener{


    @BindView(R.id.pdf_title)
    TextView pdfTitle;

    @BindView(R.id.pdfView)
    PDFView pdfView;

    long pdfId;
    PdfInfoEntity pdfInfoEntity;

    private int pageCount;

    @Override
    protected void initView() {

        pdfId = getIntent().getExtras().getLong("pdfId");
        pdfInfoEntity = GreenDaoManager.getInstance().getDaoSession().getPdfInfoEntityDao().load(pdfId);
        if (pdfInfoEntity == null) {
            toast("未查到相应文件");
            finish();
        }

    }

    @Override
    protected void initData() {

        pdfTitle.setText(pdfInfoEntity.getFileName());

        pdfView.fromFile(new File(pdfInfoEntity.getPath()))
                .defaultPage(pdfInfoEntity.getCurrentPage())
                .onDrawAll(this)
                .onPageChange(new OnPageChangeListener() {
                    @Override
                    public void onPageChanged(int page, int pageCount) {

                        pdfInfoEntity.setCurrentPage(page);
                        pdfInfoEntity.setPageCount(pageCount);

                    }
                })
                .onPageScroll(new OnPageScrollListener() {
                    @Override
                    public void onPageScrolled(int page, float positionOffset) {
                        LogUtil.d(page+"<<<<<=========>>>>>"+positionOffset);
                    }
                })
                .onError(new OnErrorListener() {
                    @Override
                    public void onError(Throwable t) {

                    }
                })
                .onPageError(new OnPageErrorListener() {
                    @Override
                    public void onPageError(int page, Throwable t) {

                    }
                })
                .load();

//        pageCount = pdfView.getPageCount();




    }

    @Override
    protected void beforeSetContentView() {

    }

    @Override
    public int getContentRes() {
        return R.layout.activity_reader;
    }

    @OnClick(R.id.back)
    public void back(){
        finish();

    }

    @Override
    protected void onPause() {
        super.onPause();
        pdfInfoEntity.setUpdateTime(new Date());
        GreenDaoManager.getInstance().getDaoSession().getPdfInfoEntityDao().update(pdfInfoEntity);
    }

    @Override
    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {



    }
}
