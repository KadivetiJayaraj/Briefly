package com.briflynews.Interfaces;

import com.briflynews.NewsModel;

import java.util.ArrayList;

public interface NewsInterface {
  interface GetNews {
    void onGetNewsDetail(String returnValue, ArrayList<NewsModel> newsModelArrayList);
  }
}
