package com.briflynews;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.PagerAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class SlidePageAdapter extends PagerAdapter {

  private Context context;
  String newsfeedUrl;
  String title;
  String descrption;
  String readText;
  String imageUrl;
  String publishedDate;
  private ItemListener mListener;

  ArrayList<NewsModel> dataNewsModelList;

  SlidePageAdapter(Context context, ArrayList<NewsModel> dataNewsModelList, ItemListener listener) {
    this.context = context;
    this.dataNewsModelList = dataNewsModelList;
    this.mListener = listener;
  }

  @Override
  public int getCount() {
    return dataNewsModelList.size();
  }

  @Override
  public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
    return view == object;
  }

  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  @NonNull
  @Override
  public Object instantiateItem(@NonNull ViewGroup container, final int position) {

    LayoutInflater layoutInflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    View view =
        Objects.requireNonNull(layoutInflater).inflate(R.layout.card_layout, container, false);

    final ImageView image = view.findViewById(R.id.image);
    final ImageView readMoreImage = view.findViewById(R.id.read_more_image);
    final TextView heading = view.findViewById(R.id.subheading);
    final TextView content = view.findViewById(R.id.content);
    final TextView readMore = view.findViewById(R.id.read_more);
    final TextView date = view.findViewById(R.id.moreAt);

    final RelativeLayout footer1 = view.findViewById(R.id.footer1);
    final RelativeLayout footer2 = view.findViewById(R.id.footer2);
    final RelativeLayout header = view.findViewById(R.id.header);

    final ImageView like = view.findViewById(R.id.like);
    final TextView like_count = view.findViewById(R.id.like_count);
    final ImageView share = view.findViewById(R.id.share);
    final ImageView bookmark = view.findViewById(R.id.bookmark);

    final NewsModel newsModel = dataNewsModelList.get(position);

    imageUrl = newsModel.urlToImage;
    title = newsModel.title;
    descrption = newsModel.description;
    readText = newsModel.author;
    newsfeedUrl = newsModel.url;

    publishedDate = newsModel.publishedAt;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    Date mDate = null;
    try {
      mDate = dateFormat.parse(publishedDate);
    } catch (ParseException e) {
      Log.e("ParceExc", e.getMessage());
    }
    long timeInMilliseconds = mDate.getTime();
    GlideLoader.loadImage(
        image, readMoreImage, context, R.drawable.procedure_placeholder, imageUrl);

    heading.setText(title);
    content.setText(descrption);
    readMore.setText(readText);
    date.setText(getTimeAgo(timeInMilliseconds));
    container.addView(view);

    footer2.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {

            if (mListener != null) {
              mListener.onItemClick(newsfeedUrl);
            }
          }
        });

    content.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {

            if (footer1.getVisibility() == View.INVISIBLE) {
              footer1.setVisibility(View.VISIBLE);
              footer2.setVisibility(View.INVISIBLE);
              header.setVisibility(View.VISIBLE);
            } else {
              footer1.setVisibility(View.INVISIBLE);
              footer2.setVisibility(View.VISIBLE);
              header.setVisibility(View.INVISIBLE);
            }
          }
        });

    image.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {

            if (footer1.getVisibility() == View.INVISIBLE) {
              footer1.setVisibility(View.VISIBLE);
              footer2.setVisibility(View.INVISIBLE);
              header.setVisibility(View.VISIBLE);
            } else {
              footer1.setVisibility(View.INVISIBLE);
              footer2.setVisibility(View.VISIBLE);
              header.setVisibility(View.INVISIBLE);
            }
          }
        });

    like.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {

            String tag = String.valueOf(like.getTag());

            if (tag.equals("like_outline")) {
              like.setImageResource(R.drawable.thumb_up);
              like_count.setText("1");
              like.setTag("like");
            } else if (tag.equals("like")) {
              like.setImageResource(R.drawable.thumb_up_outline);
              like_count.setText("");
              like.setTag("like_outline");
            }
          }
        });

    share.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, newsfeedUrl);
            intent.putExtra(android.content.Intent.EXTRA_TEXT, title);
            context.startActivity(Intent.createChooser(intent, "Share via"));
          }
        });

    heading.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {

            if (heading.getCurrentTextColor()
                == view.getResources().getColor(R.color.subHeadingColor)) {
              bookmark.setImageResource(R.drawable.bookmark);
              Toast.makeText(context, "News Bookmarked", Toast.LENGTH_SHORT).show();
              heading.setTextColor(view.getResources().getColor(R.color.bookmarkText));
              bookmark.setTag("bookmark");
            } else {
              bookmark.setImageResource(R.drawable.bookmark_outline);
              Toast.makeText(context, "Bookmark Removed", Toast.LENGTH_SHORT).show();
              heading.setTextColor(view.getResources().getColor(R.color.subHeadingColor));
              bookmark.setTag("bookmark_outline");
            }
          }
        });

    bookmark.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {

            String tag = String.valueOf(bookmark.getTag());

            if (tag.equals("bookmark_outline")) {
              bookmark.setImageResource(R.drawable.bookmark);
              Toast.makeText(context, "News Bookmarked", Toast.LENGTH_SHORT).show();
              heading.setTextColor(view.getResources().getColor(R.color.bookmarkText));
              bookmark.setTag("bookmark");
            } else if (tag.equals("bookmark")) {
              bookmark.setImageResource(R.drawable.bookmark_outline);
              Toast.makeText(context, "Bookmark Removed", Toast.LENGTH_SHORT).show();
              heading.setTextColor(view.getResources().getColor(R.color.subHeadingColor));
              bookmark.setTag("bookmark_outline");
            }
          }
        });

    return view;
  }

  public static String getTimeAgo(long time) {

    final int SECOND_MILLIS = 1000;
    final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    final int DAY_MILLIS = 24 * HOUR_MILLIS;

    if (time < 1000000000000L) {
      time *= 1000;
    }

    long now = System.currentTimeMillis();
    if (time > now || time <= 0) {
      return null;
    }

    final long diff = now - time;
    if (diff < MINUTE_MILLIS) {
      return "a moment ago";
    } else if (diff < 2 * MINUTE_MILLIS) {
      return "a minute ago";
    } else if (diff < 50 * MINUTE_MILLIS) {
      return diff / MINUTE_MILLIS + " minutes ago";
    } else if (diff < 90 * MINUTE_MILLIS) {
      return "an hour ago";
    } else if (diff < 24 * HOUR_MILLIS) {
      return diff / HOUR_MILLIS + " hours ago";
    } else if (diff < 48 * HOUR_MILLIS) {
      return "1 day ago";
    } else {
      return diff / DAY_MILLIS + " days ago";
    }
  }

  @Override
  public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    container.removeView((FrameLayout) object);
  }

  interface ItemListener {
    void onItemClick(String url);
  }
}
