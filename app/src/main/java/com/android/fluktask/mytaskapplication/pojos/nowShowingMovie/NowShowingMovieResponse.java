package com.android.fluktask.mytaskapplication.pojos.nowShowingMovie;

import java.util.Arrays;

/**
 * Created by admin on 6/2/2017.
 */

public class NowShowingMovieResponse {

    private Results[] results;

    private Dates dates;

    private int page;

    private int total_pages;

    private int total_results;

    public Results[] getResults() {
        return results;
    }

    public void setResults(Results[] results) {
        this.results = results;
    }

    public Dates getDates() {
        return dates;
    }

    public void setDates(Dates dates) {
        this.dates = dates;
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
        return "NowShowingMovieResponse{" +
                "results=" + Arrays.toString(results) +
                ", dates=" + dates +
                ", page=" + page +
                ", total_pages=" + total_pages +
                ", total_results=" + total_results +
                '}';
    }
}
