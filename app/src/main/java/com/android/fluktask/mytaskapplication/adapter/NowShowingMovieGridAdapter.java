package com.android.fluktask.mytaskapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.fluktask.mytaskapplication.R;
import com.android.fluktask.mytaskapplication.pojos.nowShowingMovie.NowShowingMovieResponse;
import com.android.fluktask.mytaskapplication.pojos.nowShowingMovie.Results;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Created by admin on 6/2/2017.
 */

public class NowShowingMovieGridAdapter extends BaseAdapter {

    private static final String TAG = NowShowingMovieGridAdapter.class.getName();

    Activity activity;
    private LayoutInflater mInflater;

    NowShowingMovieResponse nowShowingMovieResponse;

    private List<Results> itemResponseList;
    private List<Results> responseList;

    public NowShowingMovieGridAdapter(Activity activity, NowShowingMovieResponse nowShowingMovieResponse) {

        this.activity = activity;
        this.nowShowingMovieResponse = nowShowingMovieResponse;
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        for(int i=0 ;i < nowShowingMovieResponse.getResults().length;i++){
            itemResponseList.add(this.nowShowingMovieResponse.getResults()[i]);
        }

        responseList = new ArrayList<>();
        responseList.addAll(itemResponseList);
    }


    @Override
    public int getCount() {

        return itemResponseList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {


        convertView = mInflater.inflate(R.layout.movieitem, null);
        ImageView movieImage = (ImageView) convertView.findViewById(R.id.movieImage);
        Picasso.with(activity).load(itemResponseList.get(position).getPoster_path()).into(movieImage);

        TextView movieName = (TextView) convertView.findViewById(R.id.movieName);
        movieName.setText(itemResponseList.get(position).getOriginal_title());

        return convertView;

    }


    public void filter(String charText) {

        charText = charText.toLowerCase(Locale.getDefault());

        itemResponseList.clear();
        if (charText.length() == 0) {
            itemResponseList.addAll(responseList);

        } else {
            for (Results items : responseList) {
                if (charText.length() != 0 && items.getTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                    itemResponseList.add(items);
                } else if (charText.length() != 0 && items.getOriginal_title().toLowerCase(Locale.getDefault()).contains(charText)) {
                    itemResponseList.add(items);
                }
            }
        }
        notifyDataSetChanged();
    }

}
