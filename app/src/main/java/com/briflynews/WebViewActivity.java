package com.briflynews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

public class WebViewActivity extends AppCompatActivity {
  private WebView webView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_web_view);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    ImageView browserBack = findViewById(R.id.back);
    browserBack.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            finish();
          }
        });
    String url = getIntent().getStringExtra("url");
    webView = (WebView) findViewById(R.id.webview);
    webView.setWebViewClient(new MyWebViewClient());
    WebSettings webSettings = webView.getSettings();
    webSettings.setJavaScriptEnabled(true);
    webView.loadUrl(url);
  }

  private class MyWebViewClient extends WebViewClient {

    public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {}

    public void onReceivedError(
        WebView view, int errorCode, String description, String failingUrl) {}

    public void onPageFinished(WebView view, String url) {}

    @Override
    public void onPageStarted(android.webkit.WebView view, String url, Bitmap favicon) {}
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {

    int id = item.getItemId();
    switch (id) {
      case android.R.id.home:
        onBackPressed();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_menu, menu);

    return super.onCreateOptionsMenu(menu);
  }
}
