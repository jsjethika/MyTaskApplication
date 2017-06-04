package com.android.fluktask.mytaskapplication.pojos.nowShowingMovie;

/**
 * Created by admin on 6/2/2017.
 */

public class Dates {

    private String minimum;

    private String maximum;

    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }

    public String getMaximum() {
        return maximum;
    }

    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }

    @Override
    public String toString() {
        return "Dates{" +
                "minimum='" + minimum + '\'' +
                ", maximum='" + maximum + '\'' +
                '}';
    }
}
