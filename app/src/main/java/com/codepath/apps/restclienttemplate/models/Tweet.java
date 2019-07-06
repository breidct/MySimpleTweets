package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Tweet {

    //list all the attributes
    public String body;
    public long uid; //ID for tweet
    public User user;
    public String createdAt;
    public boolean liked;
    public boolean retweet;
    public int reTweetCount;
    public int likeCount;
    //public String imageUrl;

    //deserialize the JSON
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.liked = false;
        tweet.retweet = false;
        //extract all the values from JSON
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.reTweetCount = jsonObject.getInt("retweet_count");
        tweet.likeCount = jsonObject.getInt("favorite_count");
        //tweet.imageUrl = jsonObject.getString("display_url");
        return tweet;
    }

}
