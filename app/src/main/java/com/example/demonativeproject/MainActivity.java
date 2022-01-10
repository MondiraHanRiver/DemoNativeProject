package com.example.demonativeproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    WebView mWebview;
    Button button1;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebview = findViewById(R.id.webview);

        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.getSettings().setDomStorageEnabled(true);
        mWebview.addJavascriptInterface(new JsObject(), "Android");

        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.e("response", "onPageFinished : " + url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mWebview.loadUrl("javascript:(function() {" +
                        "function receiveMessage(event) {\n" +
                        "Android.receiveMessage(JSON.stringify(event.data));\n" +
                        "}" +
                        "window.addEventListener(\"message\", receiveMessage, false);"+
                        "})()"
                );
                Log.i("TAG", "onPageStarted "+url);
            }

        });

        mWebview.loadUrl("http://demo-vuejs.dvconsulting.org/login");


    }

    class JsObject {
        @JavascriptInterface
        public void receiveMessage(String data) { //handle data here
            // data => json response comes as a string
            Log.i("JsObject", "postMessage data=" + data);
            Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();

        }
    }

}





