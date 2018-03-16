package com.promise.pdfreader.ui;

import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.promise.pdfreader.R;

import butterknife.BindView;
import butterknife.OnClick;

public class WebActivity extends BaseActivity {

    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.web_title)
    TextView title;

    String url;

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

        url = getIntent().getExtras().getString("weburl");
        if (TextUtils.isEmpty(url)) {
            finish();
            return;
        }

        webview.loadUrl(url);

        webview.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webview.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    showProgress("正在加载网页，请稍等");
                } else {
                    dismissProgress();
                }


            }

            @Override
            public void onReceivedTitle(WebView view, String title) {

                WebActivity.this.title.setText(title);
            }
        });
    }

    @Override
    protected void beforeSetContentView() {

    }

    @Override
    public int getContentRes() {
        return R.layout.activity_web;
    }

    @OnClick(R.id.back)
    public void back(){
        finish();
    }

}
