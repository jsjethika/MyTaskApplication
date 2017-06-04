package com.android.fluktask.mytaskapplication;

import android.util.Log;
import com.android.fluktask.mytaskapplication.pojos.nowShowingMovie.NowShowingMovieResponse;
import com.android.fluktask.mytaskapplication.pojos.popularMovie.PopularMovieResponse;
import com.android.fluktask.mytaskapplication.pojos.ratedMovie.TopRatedMovieResponse;
import com.android.fluktask.mytaskapplication.pojos.upcomingMovie.UpcomingMovieResponse;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;


/**
 * Created by admin on 6/2/2017.
 */

public class AppRestClient {

    private static final String TAG =  AppRestClient.class.getName();


    public PopularMovieResponse getPopularMovies() {
       Log.d(TAG, "getPopularMovies");
        final PopularMovieResponse[] popularMovieResponses = {null};
        String getPopularMovieUrl = MyApplication.getContext().getResources().getString(R.string.popular_movie);

        String apikey = MyApplication.getContext().getResources().getString(R.string.API_Key_movie);

        getPopularMovieUrl = getPopularMovieUrl.replace("{api_key}", apikey);
        Log.d(TAG, getPopularMovieUrl);

        HttpManager.get(getPopularMovieUrl, null, new JsonHttpResponseHandler() {


            public void onSuccess(int statusCode, Header[] headers, JSONObject resultData) {

                popularMovieResponses[0] = AppRestTools.getInstance().getPopularMovieData(resultData.toString());
                Log.d(TAG, popularMovieResponses[0].toString());
            }

            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);

                popularMovieResponses[0] = null;
            }
        });
        return popularMovieResponses[0];
    }

    public TopRatedMovieResponse getTopRatedMovies() {
        Log.d(TAG, "getTopRatedMovies");
        final TopRatedMovieResponse[] topRatedMovieResponses = {null};
        String getTopRatedMovieUrl = MyApplication.getContext().getResources().getString(R.string.top_rated_movie);

        String apikey = MyApplication.getContext().getResources().getString(R.string.API_Key_movie);

        getTopRatedMovieUrl = getTopRatedMovieUrl.replace("{api_key}", apikey);
        Log.d(TAG, getTopRatedMovieUrl);

        HttpManager.get(getTopRatedMovieUrl, null, new JsonHttpResponseHandler() {


            public void onSuccess(int statusCode, Header[] headers, JSONObject resultData) {

                topRatedMovieResponses[0] = AppRestTools.getInstance().getTopRatedMovieData(resultData.toString());
                Log.d(TAG, topRatedMovieResponses[0].toString());
            }

            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);

                topRatedMovieResponses[0] = null;
            }
        });
        return topRatedMovieResponses[0];
    }

    public UpcomingMovieResponse getUpComingMovies() {
        Log.d(TAG, "getUpComingMovies");
        final UpcomingMovieResponse[] upcomingMovieResponses = {null};
        String getUpComingMovieUrl = MyApplication.getContext().getResources().getString(R.string.upcoming_movie);

        String apikey = MyApplication.getContext().getResources().getString(R.string.API_Key_movie);

        getUpComingMovieUrl = getUpComingMovieUrl.replace("{api_key}", apikey);
        Log.d(TAG, getUpComingMovieUrl);

        HttpManager.get(getUpComingMovieUrl, null, new JsonHttpResponseHandler() {


            public void onSuccess(int statusCode, Header[] headers, JSONObject resultData) {

                upcomingMovieResponses[0] = AppRestTools.getInstance().getUpcomingMovieData(resultData.toString());
                Log.d(TAG, upcomingMovieResponses[0].toString());
            }


            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);

                upcomingMovieResponses[0] = null;
            }
        });
        return upcomingMovieResponses[0];
    }


    public NowShowingMovieResponse getNowShowingMovies() {
        Log.d(TAG, "getNowShowingMovies");
        final NowShowingMovieResponse[] nowShowingMovieResponses = {null};
        String getNowShowingMovieUrl = MyApplication.getContext().getResources().getString(R.string.nowPlaying_movie);

        String apikey = MyApplication.getContext().getResources().getString(R.string.API_Key_movie);

        getNowShowingMovieUrl = getNowShowingMovieUrl.replace("{api_key}", apikey);
        Log.d(TAG, getNowShowingMovieUrl);

        HttpManager.get(getNowShowingMovieUrl, null, new JsonHttpResponseHandler() {


            public void onSuccess(int statusCode, Header[] headers, JSONObject resultData) {

                nowShowingMovieResponses[0] = AppRestTools.getInstance().getNowShowingMovieData(resultData.toString());
                Log.d(TAG, nowShowingMovieResponses[0].toString());
            }

            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);

                nowShowingMovieResponses[0] = null;
            }
        });
        return nowShowingMovieResponses[0];
    }
}
