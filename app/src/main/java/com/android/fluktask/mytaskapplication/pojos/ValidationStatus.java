package com.android.fluktask.mytaskapplication.pojos;

/**
 * Created by admin on 6/1/2017.
 */

public class ValidationStatus {

    private boolean status;
    private String message;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ValidationStatus{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
