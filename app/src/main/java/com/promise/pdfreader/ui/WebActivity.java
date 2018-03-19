package com.promise.pdfreader.ui;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

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

        webview.getSettings().setJavaScriptEnabled(true);
        webview.addJavascriptInterface(new JavaScriptinterface(this),"android");

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
//                if (newProgress < 100) {
//                    showProgress("正在加载网页，请稍等");
//                } else {
//                    dismissProgress();
//                }


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

    public class JavaScriptinterface {
        Context context;
        public JavaScriptinterface(Context c) {
            context= c;
        }

        /**
         * 与js交互时用到的方法，在js里直接调用的
         */
        @JavascriptInterface
        public void showToast(String ssss) {

            Toast.makeText(context, ssss, Toast.LENGTH_LONG).show();
        }
    }

}
