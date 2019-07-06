package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {
    private List<Tweet> mTweets;
    Context context;
    //pass in the tweets array
    public TweetAdapter(List<Tweet> tweets){
        mTweets = tweets;
    }
    //for each row, inflate the layout

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        Tweet tweet = mTweets.get(i);
        View tweetView = inflater.inflate(R.layout.item_tweet, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        if(tweet.liked){
            viewHolder.btnLike.setImageResource(R.drawable.ic_vector_heart);
            viewHolder.btnLike.setColorFilter(Color.RED);
        }else{
            viewHolder.btnLike.setImageResource(R.drawable.ic_vector_heart_stroke);
            viewHolder.btnLike.setColorFilter(Color.BLACK);
        }
        return viewHolder;
    }


    //bind the values

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        //get the data at a position
        Tweet tweet = mTweets.get(position);
        //populate the view
        viewHolder.tvUsername.setText(tweet.user.name);
        viewHolder.tvBody.setText(tweet.body);
        viewHolder.tvTimeStamp.setText(getRelativeTimeAgo(tweet.createdAt));
        viewHolder.tvRetweetNum.setText(String.valueOf(tweet.reTweetCount));
        viewHolder.tvLikeNum.setText(String.valueOf(tweet.likeCount));

        viewHolder.btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ReplyActivity.class);
                intent.putExtra("user_id",mTweets.get(position).uid);
                intent.putExtra("screen_name",mTweets.get(position).user.screenName);
                context.startActivity(intent);
            }
        });

        viewHolder.clTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDetails = new Intent(context,DetailsActivity.class);
                intentDetails.putExtra("Tweet", Parcels.wrap(mTweets.get(position)));
                intentDetails.putExtra("Time",getRelativeTimeAgo(mTweets.get(position).createdAt));
                context.startActivity(intentDetails);
            }
        });

        Glide.with(context)
                .load(tweet.user.profileImageUrl)
                .into(viewHolder.ivProfileImage);
    }

    //create viewholder class

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvBody;
        public TextView tvTimeStamp;
        public ImageButton btnComment;
        public ConstraintLayout clTweet;
        public ImageButton btnLike;
        public TextView tvRetweetNum;
        public TextView tvLikeNum;


        public ViewHolder(View itemView){
            super(itemView);

            //perform findbyId
            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUserName);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvTimeStamp = (TextView) itemView.findViewById(R.id.tvTimeStamp);
            btnComment = (ImageButton) itemView.findViewById(R.id.btnComment);
            clTweet = (ConstraintLayout) itemView.findViewById(R.id.clTweet);
            btnLike = itemView.findViewById(R.id.btnLike);
            tvRetweetNum = itemView.findViewById(R.id.tvRetweetNum);
            tvLikeNum = itemView.findViewById(R.id.tvLikeNum);


        }
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    public void clear() {
        mTweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        mTweets.addAll(list);
        notifyDataSetChanged();
    }
}
