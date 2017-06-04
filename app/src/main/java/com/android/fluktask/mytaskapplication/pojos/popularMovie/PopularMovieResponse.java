package com.android.fluktask.mytaskapplication.pojos.popularMovie;

import java.util.Arrays;

/**
 * Created by admin on 6/2/2017.
 */

public class PopularMovieResponse {

    private Results[] results;

    private int page;

    private int total_pages;

    private int total_results;

    public Results[] getResults() {
        return results;
    }

    public void setResults(Results[] results) {
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    @Override
    public String toString() {
        return "PopularMovieResponse{" +
                "results=" + Arrays.toString(results) +
                ", page=" + page +
                ", total_pages=" + total_pages +
                ", total_results=" + total_results +
                '}';
    }
}
