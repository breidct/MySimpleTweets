package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ReplyActivity extends AppCompatActivity {

    TwitterClient client;
    EditText etTweet;
    MenuItem miActionProgressItem;
    Long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        etTweet = (EditText) findViewById(R.id.hotdog);
        String replyText = "@"+ getIntent().getStringExtra("screen_name") + " ";
        etTweet.setText(replyText);
        client = TwitterApp.getRestClient(ReplyActivity.this);
        userId = getIntent().getLongExtra("user_id",0);
    }

    public void OnClick(View view){
        showProgressBar();
        client.replyTweet(etTweet.getText().toString(),userId, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    finish();
            }
        });
        hideProgressBar();

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.compose_main, menu);
        return true;
    }
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Store instance of the menu item containing progress
        miActionProgressItem = menu.findItem(R.id.miActionProgress);
        // Extract the action-view from the menu item
        ProgressBar v =  (ProgressBar) MenuItemCompat.getActionView(miActionProgressItem);
        // Return to finish
        return super.onPrepareOptionsMenu(menu);
    }
    public void showProgressBar() {
        // Show progress item
        miActionProgressItem.setVisible(true);
    }

    public void hideProgressBar() {
        // Hide progress item
        miActionProgressItem.setVisible(false);
    }
}
