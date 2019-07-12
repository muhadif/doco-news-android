
package com.docotel.muhadif.second.data.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.docotel.muhadif.second.util.DateUtil;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Article implements Parcelable {

    @SerializedName("author")
    private String mAuthor;
    @SerializedName("content")
    private String mContent;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("publishedAt")
    private String mPublishedAt;
    @SerializedName("source")
    private Source mSource;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("url")
    private String mUrl;
    @SerializedName("urlToImage")
    private String mUrlToImage;

    protected Article(Parcel in) {
        mAuthor = in.readString();
        mContent = in.readString();
        mDescription = in.readString();
        mPublishedAt = in.readString();
        mTitle = in.readString();
        mUrl = in.readString();
        mUrlToImage = in.readString();
    }

    public String getDate() throws ParseException {
        Date publishDate = DateUtil.convertToDate(getPublishedAt());

        Date dateNow =  new Date(System.currentTimeMillis());
        Map<TimeUnit,Long> diffDate =  DateUtil.computeDiff(publishDate, dateNow);

        if(diffDate.get(TimeUnit.DAYS) < 1) {
            return diffDate.get(TimeUnit.HOURS).toString() + " hours ago";
        } else {
           return "more than 1 day";
        }
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getPublishedAt() {
        return mPublishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        mPublishedAt = publishedAt;
    }

    public Source getSource() {
        return mSource;
    }

    public void setSource(Source source) {
        mSource = source;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getUrlToImage() {
        return mUrlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        mUrlToImage = urlToImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mAuthor);
        parcel.writeString(mContent);
        parcel.writeString(mDescription);
        parcel.writeString(mPublishedAt);
        parcel.writeString(mTitle);
        parcel.writeString(mUrl);
        parcel.writeString(mUrlToImage);
    }
}
