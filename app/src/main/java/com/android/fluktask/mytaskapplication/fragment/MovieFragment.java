package com.android.fluktask.mytaskapplication.fragment;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import com.android.fluktask.mytaskapplication.AppRestClient;
import com.android.fluktask.mytaskapplication.AppTool;
import com.android.fluktask.mytaskapplication.NetworkUtil;
import com.android.fluktask.mytaskapplication.OnNetworkChangeListener;
import com.android.fluktask.mytaskapplication.R;
import com.android.fluktask.mytaskapplication.adapter.NowShowingMovieGridAdapter;
import com.android.fluktask.mytaskapplication.adapter.PopularMovieGridAdapter;
import com.android.fluktask.mytaskapplication.adapter.TopRatedMovieGridAdapter;
import com.android.fluktask.mytaskapplication.adapter.UpcomingMovieGridAdapter;
import com.android.fluktask.mytaskapplication.pojos.nowShowingMovie.NowShowingMovieResponse;
import com.android.fluktask.mytaskapplication.pojos.popularMovie.PopularMovieResponse;
import com.android.fluktask.mytaskapplication.pojos.ratedMovie.TopRatedMovieResponse;
import com.android.fluktask.mytaskapplication.pojos.upcomingMovie.UpcomingMovieResponse;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;


/**
 * Created by admin on 6/2/2017.
 */


@EFragment(R.layout.fragment_movie)
public class MovieFragment extends Fragment implements OnNetworkChangeListener, AdapterView.OnItemSelectedListener {

    private static final String TAG = MovieFragment.class.getName();

    boolean isNetworkAvailable = false;

    @ViewById(R.id.movieContentLayout)
    RelativeLayout movieContentLayout;

    @ViewById(R.id.movieGrid)
    GridView movieGrid;

    @ViewById(R.id.spinnerMovie)
    Spinner spinnerMovie;

    PopularMovieGridAdapter popularMovieGridAdapter;
    NowShowingMovieGridAdapter nowShowingMovieGridAdapter;
    TopRatedMovieGridAdapter topRatedMovieGridAdapter;
    UpcomingMovieGridAdapter upcomingMovieGridAdapter;

    String spinnerMovieData = "";

    private ProgressDialog progressDialog;

    @AfterViews
    public void initFragment() {

        setHasOptionsMenu(true);

        isNetworkAvailable = AppTool.networkStatus();
        NetworkUtil.setOnNetworkChangeListener(this);

        initializeControls();

    }

    private void initializeControls(){

        //set the default according to value
        spinnerMovie.setSelection(0);

        spinnerMovieData = spinnerMovie.getSelectedItem().toString();
        Log.d(TAG, "spinnerMovieData: " + spinnerMovieData);

        spinnerMovie.setOnItemSelectedListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        showProgressDialog();
        getPopularMovies();
    }


    @Override
    public void onChange(String status) {
        isNetworkAvailable = AppTool.networkStatus(status);
    }


    void getPopularMovies(){
        if (!isNetworkAvailable) {
            return;
        }
        callPopularMovieProcess();
    }



    @Background
    void callPopularMovieProcess() {
        Log.d(TAG, "getPopularMovies");

        AppRestClient vCarRestClient = new AppRestClient();
        PopularMovieResponse popularMovieResponse = vCarRestClient.getPopularMovies();


        if (popularMovieResponse != null) {
            callPopularMovieProcessFinish(popularMovieResponse);

        } else {
            showToastErrorMsg("popularMovieResponse is null... ");
        }
    }

    @UiThread
    public void callPopularMovieProcessFinish(PopularMovieResponse popularMovieResponse) {
        hideProgressDialog();
        movieContentLayout.setVisibility(View.VISIBLE);

        popularMovieGridAdapter = new PopularMovieGridAdapter(getActivity(),popularMovieResponse);
        movieGrid.setAdapter(popularMovieGridAdapter);
    }

    @UiThread
    public void showToastErrorMsg(String error) {
        hideProgressDialog();
        AppTool.showToast(error);
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchItem.setVisible(true);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        //*** setOnQueryTextFocusChangeListener ***
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            // filter based on search

            @Override
            public boolean onQueryTextChange(String searchQuery) {

                switch (spinnerMovieData) {

                    case "Popular Movie":
                        popularMovieGridAdapter.filter(searchQuery.toString().trim());
                        break;

                    case "Now Showing Movie":
                        nowShowingMovieGridAdapter.filter(searchQuery.toString().trim());
                        break;

                    case "Rated Movie":
                        topRatedMovieGridAdapter.filter(searchQuery.toString().trim());
                        break;

                    case "Upcoming Movie":
                        upcomingMovieGridAdapter.filter(searchQuery.toString().trim());
                        break;
                }
                movieGrid.invalidate();
                return true;
            }
        });


    }

    // filter based on spinner field selected

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerMovieData = spinnerMovie.getSelectedItem().toString();

        switch (spinnerMovieData) {

            case "Popular Movie":
                getPopularMovies();
                break;

            case "Now Showing Movie":
                getNowShowingMovies();
                break;

            case "Rated Movie":
                getRatedMovies();
                break;

            case "Upcoming Movie":
                getUpcomingMovies();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    void getNowShowingMovies(){
        if (!isNetworkAvailable) {
            return;
        }
        showProgressDialog();
        callNowShowingMovies();
    }



    @Background
    void callNowShowingMovies() {
        Log.d(TAG, "callNowShowingMovies");

        AppRestClient vCarRestClient = new AppRestClient();
        NowShowingMovieResponse nowShowingMovieResponse = vCarRestClient.getNowShowingMovies();


        if (nowShowingMovieResponse != null) {
            callNowShowingMoviesProcessFinish(nowShowingMovieResponse);

        } else {
            showToastErrorMsg("nowShowingMovieResponse is null... ");
        }
    }

    @UiThread
    public void callNowShowingMoviesProcessFinish(NowShowingMovieResponse nowShowingMovieResponse) {
        hideProgressDialog();
        movieGrid.invalidate();

        nowShowingMovieGridAdapter = new NowShowingMovieGridAdapter(getActivity(),nowShowingMovieResponse);
        movieGrid.setAdapter(nowShowingMovieGridAdapter);
    }


    void getRatedMovies(){
        if (!isNetworkAvailable) {
            return;
        }
        showProgressDialog();
        callRatedMovies();
    }



    @Background
    void callRatedMovies() {
        Log.d(TAG, "callRatedMovies");

        AppRestClient vCarRestClient = new AppRestClient();
        TopRatedMovieResponse topRatedMovieResponse = vCarRestClient.getTopRatedMovies();


        if (topRatedMovieResponse != null) {
            callRatedMoviesProcessFinish(topRatedMovieResponse);

        } else {
            showToastErrorMsg("topRatedMovieResponse is null... ");
        }
    }

    @UiThread
    public void callRatedMoviesProcessFinish(TopRatedMovieResponse topRatedMovieResponse) {
        hideProgressDialog();
        movieGrid.invalidate();

        topRatedMovieGridAdapter = new TopRatedMovieGridAdapter(getActivity(),topRatedMovieResponse);
        movieGrid.setAdapter(topRatedMovieGridAdapter);
    }


    void getUpcomingMovies(){
        if (!isNetworkAvailable) {
            return;
        }
        showProgressDialog();
        callUpcomingMovies();
    }



    @Background
    void callUpcomingMovies() {
        Log.d(TAG, "callUpcomingMovies");

        AppRestClient vCarRestClient = new AppRestClient();
        UpcomingMovieResponse upcomingMovieResponse = vCarRestClient.getUpComingMovies();


        if (upcomingMovieResponse != null) {
            callUpcomingMoviesProcessFinish(upcomingMovieResponse);

        } else {
            showToastErrorMsg("UpcomingMovieResponse is null... ");
        }
    }

    @UiThread
    public void callUpcomingMoviesProcessFinish(UpcomingMovieResponse upcomingMovieResponse) {
        hideProgressDialog();
        movieGrid.invalidate();

        upcomingMovieGridAdapter = new UpcomingMovieGridAdapter(getActivity(),upcomingMovieResponse);
        movieGrid.setAdapter(upcomingMovieGridAdapter);
    }


    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(getActivity().getResources().getString(R.string.loading));
            progressDialog.setIndeterminate(true);
        }
        progressDialog.show();
    }

    private void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }

}
