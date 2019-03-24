package com.example.gyk_02;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoWeb extends AppCompatActivity {

    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_web);
        ButterKnife.bind(this);

        String torrentUrl = "https://www.torrentoyunindir.com/";
        openWebPage(torrentUrl);
    }

    private void openWebPage(String torrentUrl) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(torrentUrl);

        final ProgressDialog progressDialog = ProgressDialog.show(this, "Torrent Oyun İndir", "Loading...", true);
        progressDialog.show();
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Toast.makeText(GoWeb.this, "Page loaded", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                Toast.makeText(GoWeb.this, "Error...", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}
