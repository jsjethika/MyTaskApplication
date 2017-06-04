package com.android.fluktask.mytaskapplication;

import com.android.fluktask.mytaskapplication.pojos.nowShowingMovie.NowShowingMovieResponse;
import com.android.fluktask.mytaskapplication.pojos.popularMovie.PopularMovieResponse;
import com.android.fluktask.mytaskapplication.pojos.ratedMovie.TopRatedMovieResponse;
import com.android.fluktask.mytaskapplication.pojos.upcomingMovie.UpcomingMovieResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by admin on 6/2/2017.
 */

public class AppRestTools {

    private static final String TAG = AppRestTools.class.getName();


    private static final Object monitor = new Object();
    private static AppRestTools appRestTools = null;

    public static AppRestTools getInstance() {
        if (appRestTools == null) {
            synchronized (monitor) {
                if (appRestTools == null)
                    appRestTools = new AppRestTools();
            }
        }
        return appRestTools;
    }


    public PopularMovieResponse getPopularMovieData(String jsonString) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(jsonString, PopularMovieResponse.class);
    }


    public TopRatedMovieResponse getTopRatedMovieData(String jsonString) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(jsonString, TopRatedMovieResponse.class);
    }


    public UpcomingMovieResponse getUpcomingMovieData(String jsonString) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(jsonString, UpcomingMovieResponse.class);
    }

    public NowShowingMovieResponse getNowShowingMovieData(String jsonString) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(jsonString, NowShowingMovieResponse.class);
    }

}
