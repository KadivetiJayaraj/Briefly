package com.briflynews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.briflynews.BackgroundServices.GetNews;
import com.briflynews.Interfaces.NewsInterface;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
    implements NewsInterface.GetNews, SlidePageAdapter.ItemListener {
  ArrayList<NewsModel> dataNewsModelList = new ArrayList<NewsModel>();
  VerticalViewPager viewPager;
  SlidePageAdapter slidePageAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    viewPager = findViewById(R.id.card_view);

    GetNews getNews = new GetNews(this, this);
    getNews.loadNews();
  }

  @Override
  public void onGetNewsDetail(String returnValue, ArrayList<NewsModel> newsModelArrayList) {

    if (returnValue.equals("SUCCESS")) {
      this.dataNewsModelList = newsModelArrayList;
      slidePageAdapter = new SlidePageAdapter(this, dataNewsModelList, this);
      viewPager.setAdapter(slidePageAdapter);
    }
  }

  @Override
  public void onItemClick(String url) {
    Intent i = new Intent(MainActivity.this, WebViewActivity.class);
    i.putExtra("url", url);
    startActivity(i);
    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
  }
}
