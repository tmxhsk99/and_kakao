package com.example.kgitbank.kakao.utill;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.example.kgitbank.kakao.R;

public class Movie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie);
        final Context ctx = Movie.this;
        WebView webView = findViewById(R.id.webVIew);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://www.naver.com/");
        webView.setWebChromeClient(new WebChromeClient());

    }
}
