package com.briflynews.BackgroundServices;

/** Created by jeet on 10/9/17. */
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.briflynews.NewsModel;
import com.briflynews.NoInternet;
import com.briflynews.Interfaces.NewsInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class GetNews {
  private Context context;
  private String rtrnValue;
  private NewsInterface.GetNews GetNews;
  String API_KEY = "47a018215dfa45d4bd970265334b6ca7"; // ### YOUE NEWS API HERE ###
  static final String KEY_AUTHOR = "author";
  static final String KEY_TITLE = "title";
  static final String KEY_DESCRIPTION = "description";
  static final String KEY_URL = "url";
  static final String KEY_URLTOIMAGE = "urlToImage";
  static final String KEY_PUBLISHEDAT = "publishedAt";

  public GetNews(Context context, NewsInterface.GetNews listener) {
    this.context = context;
    try {
      GetNews = (NewsInterface.GetNews) listener;
    } catch (ClassCastException e) {
      throw new ClassCastException();
    }
  }

  public void loadNews() {

    if (NoInternet.isNetworkAvailable(context.getApplicationContext())) {
      DownloadNews newsTask = new DownloadNews();
      newsTask.execute();
    } else {
      Toast.makeText(context.getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG)
          .show();
    }
  }

  class DownloadNews extends AsyncTask<String, Void, String> {
    @Override
    protected void onPreExecute() {
      super.onPreExecute();
    }

    protected String doInBackground(String... args) {
      String xml = "";

      String urlParameters = "";
      xml =
          NoInternet.excuteGet(
              "https://newsapi.org/v2/everything?domains=techcrunch.com,thenextweb.com&apiKey="
                  + API_KEY,
              urlParameters);
      return xml;
    }

    @Override
    protected void onPostExecute(String xml) {
      if (xml.length() > 10) {
        rtrnValue = "SUCCESS";
        ArrayList<NewsModel> newsModels = new ArrayList<NewsModel>();
        try {
          JSONObject jsonResponse = new JSONObject(xml);
          JSONArray jsonArray = jsonResponse.optJSONArray("articles");

          for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            HashMap<String, String> map = new HashMap<String, String>();
            map.put(KEY_AUTHOR, jsonObject.optString(KEY_AUTHOR).toString());
            map.put(KEY_TITLE, jsonObject.optString(KEY_TITLE).toString());
            map.put(KEY_DESCRIPTION, jsonObject.optString(KEY_DESCRIPTION).toString());
            map.put(KEY_URL, jsonObject.optString(KEY_URL).toString());
            map.put(KEY_URLTOIMAGE, jsonObject.optString(KEY_URLTOIMAGE).toString());
            map.put(KEY_PUBLISHEDAT, jsonObject.optString(KEY_PUBLISHEDAT).toString());

            NewsModel newsModel = new NewsModel();
            newsModel.setAuthor(map.get(KEY_AUTHOR));
            newsModel.setTitle(map.get(KEY_TITLE));
            newsModel.setDescription(map.get(KEY_DESCRIPTION));
            newsModel.setUrl(map.get(KEY_URL));
            newsModel.setUrlToImage(map.get(KEY_URLTOIMAGE));
            newsModel.setPublishedAt(map.get(KEY_PUBLISHEDAT));
            newsModels.add(newsModel);
          }
          GetNews.onGetNewsDetail(rtrnValue, newsModels);

        } catch (JSONException e) {
          Toast.makeText(context.getApplicationContext(), "Unexpected error", Toast.LENGTH_SHORT)
              .show();
          rtrnValue = "ERROR";
          GetNews.onGetNewsDetail(rtrnValue, null);
        }
      } else {
        rtrnValue = "ERROR";
        Toast.makeText(context.getApplicationContext(), "No news found", Toast.LENGTH_SHORT).show();
        GetNews.onGetNewsDetail(rtrnValue, null);
      }
    }
  }
}
