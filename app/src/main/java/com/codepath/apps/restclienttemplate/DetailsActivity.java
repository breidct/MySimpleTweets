package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class DetailsActivity extends AppCompatActivity {
    ImageView ivProfile;
    TextView tvName;
    TextView tvBody;
    TextView tvTime;
    Tweet tweet;
    ImageButton btnRetweet;
    ImageButton btnLike;
    private TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ivProfile = findViewById(R.id.ivProfile);
        tvName = findViewById(R.id.tvName);
        tvBody = findViewById(R.id.tvBody);
        tvTime = findViewById(R.id.tvTime);
        btnRetweet = findViewById(R.id.btnRetweet);
        btnLike = findViewById(R.id.btnLike);
        Intent intent = getIntent();
        tweet = Parcels.unwrap(intent.getParcelableExtra("Tweet"));
        String time = intent.getStringExtra("Time");
        if(tweet.liked){
            btnLike.setImageResource(R.drawable.ic_vector_heart);
            btnLike.setColorFilter(Color.RED);
        }else{
            btnLike.setImageResource(R.drawable.ic_vector_heart_stroke);
            btnLike.setColorFilter(Color.BLACK);
        }
        tvName.setText(tweet.user.name);
        tvBody.setText(tweet.body);
        tvTime.setText(time);

        Glide.with(DetailsActivity.this)
                .load(tweet.user.profileImageUrl)
                .into(ivProfile);

        client = TwitterApp.getRestClient(DetailsActivity.this);



    }
    public void onClickz(View view){
        //Intent i = new Intent(MovieDetailsActivity.this, MainActivity.class);
        //startActivity(i);
        finish();
    }

    public void onClick(View view){
        Intent intent = new Intent(DetailsActivity.this,ReplyActivity.class);
        intent.putExtra("user_id",tweet.uid);
        intent.putExtra("screen_name",tweet.user.screenName);
        startActivity(intent);
    }

    public void onLike(View view){
        if(tweet.liked){
            tweet.liked = false;
            Toast.makeText(this, "You unliked the post", Toast.LENGTH_SHORT).show();
            btnLike.setImageResource(R.drawable.ic_vector_heart_stroke);
            btnLike.setColorFilter(Color.BLACK);
            client.unlikeTweet(tweet.uid,new JsonHttpResponseHandler()
            {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    super.onSuccess(statusCode, headers, response);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                }
            });
        }else {
            tweet.liked = true;
            Toast.makeText(this, "You liked the post", Toast.LENGTH_SHORT).show();
            btnLike.setImageResource(R.drawable.ic_vector_heart);
            btnLike.setColorFilter(Color.RED);
            client.likeTweet(tweet.uid, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    super.onSuccess(statusCode, headers, response);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                }
            });
        }
    }

    public void onRetweet(View view) {
        if (tweet.retweet) {
            tweet.retweet = false;
            Toast.makeText(this, "You unretweeted this post", Toast.LENGTH_SHORT).show();
            client.unreTweet(tweet.uid, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    super.onSuccess(statusCode, headers, response);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                }
            });
        } else {
            tweet.retweet = true;
            Toast.makeText(this, "You retweeted this post", Toast.LENGTH_SHORT).show();
            client.reTweet(tweet.uid, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    super.onSuccess(statusCode, headers, response);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                }
            });
        }
    }

}
