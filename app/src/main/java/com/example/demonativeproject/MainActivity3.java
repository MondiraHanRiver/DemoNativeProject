package com.example.demonativeproject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity3 extends AppCompatActivity {
    WebView mWebview;
    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        mWebview = findViewById(R.id.webview);

        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWebview.loadUrl("javascript:callJsFunction('mou');");
            }
        });


        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.getSettings().setDomStorageEnabled(true);
        mWebview.setWebChromeClient(new WebChromeClient());
        mWebview.addJavascriptInterface(new MainActivity3.JsObject(), "Android");

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

                Log.i("TAG", "onPageStarted " + url);
            }

        });

        mWebview.loadUrl("http://demo-vuejs.dvconsulting.org/login");

    }

    class JsObject {
        @JavascriptInterface
        public void receiveMessage(String data) { //handle data here
            // data => json response comes as a string
            Log.i("JsObject", "postMessage data=" + data);
            Toast.makeText(MainActivity3.this, data, Toast.LENGTH_SHORT).show();

        }
    }

}