package com.briflynews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {
  Handler handler;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash_screen);
    handler = new Handler();
    handler.postDelayed(
        new Runnable() {
          @Override
          public void run() {

            if (NoInternet.isNetworkAvailable(getApplicationContext())) {
              Intent i = new Intent(SplashScreen.this, MainActivity.class);
              startActivity(i);
              overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
              finish();
            } else {
              Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG)
                  .show();
            }
          }
        },
        3000);
  }
}
