package com.odinarts.android.iq;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

public class DumbActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dumb);

        Bundle bundle = getIntent().getExtras();
        String how = bundle.getString("how");
        String srcUrl = bundle.getString("srcUrl");

        WebView webView = (WebView)findViewById(R.id.webViewDetails);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setUseWideViewPort(true);
        webView.loadUrl(srcUrl);
        ((TextView)findViewById(R.id.textViewDumbStatus)).setText(how);
    }
}
