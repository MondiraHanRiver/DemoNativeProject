package com.example.demonativeproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    WebView mWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mWebview = findViewById(R.id.webview);

        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.getSettings().setDomStorageEnabled(true);
        mWebview.addJavascriptInterface(new MyJavaScriptInterface(this), "ButtonRecognizer");

        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.e("response", "onPageFinished : " + url);
                loadEvent(clickListener());
            }

            private void loadEvent(String javascript) {
                Log.e("response", "loadEvent : " + javascript);
                mWebview.loadUrl("javascript:" + javascript);
            }

            private String clickListener() {
                Log.e("response", "clickListener");
                return getButtons() + "for(var i = 0; i < buttons.length; i++){\n" +
                        "\tbuttons[i].onclick = function(){ console.log('click worked.'); ButtonRecognizer.boundMethod('button clicked'); };\n" +
                        "}";
            }

            private String getButtons() {
                Log.e("response", "getButtons");
//                return "var buttons = document.getElementsByClassName('add-to-cart'); console.log(buttons.length + ' buttons');\n";
//                return "var buttons = document.getElementsByClassName('add-to-cart-btn'); console.log(buttons.length + ' buttons');\n";
                return "var buttons = document.getElementsByClassName('tts-speech-record'); console.log(buttons.length + ' buttons');\n";
            }
        });

        mWebview.loadUrl("https://www.divii.org/publisher/mobile/subscribed-book/10055/60/637");
//        mWebview.loadUrl("http://demo-vuejs.dvconsulting.org/login");




    }



    class MyJavaScriptInterface {

        private Context ctx;

        MyJavaScriptInterface(Context ctx) {
            this.ctx = ctx;
        }

        @JavascriptInterface
        public void boundMethod(String html) {
            Log.e("response", "boundMethod");
            new AlertDialog.Builder(ctx).setTitle("HTML").setMessage("It worked")
                    .setPositiveButton(android.R.string.ok, null).setCancelable(false).create().show();
        }

    }



}