package com.android.fluktask.mytaskapplication;

import com.android.fluktask.mytaskapplication.pojos.ValidationStatus;

/**
 * Created by admin on 6/1/2017.
 */

public class ValidationUtils {

    // Validating user inputs email and password for null

    static public ValidationStatus isValidLoginEmailPassword(String userEmail, String password) {
        ValidationStatus validationStatus = new ValidationStatus();
        validationStatus.setStatus(true);
        validationStatus.setMessage("");

        if (null == userEmail || null == password && userEmail.length() == 0 || password.length() == 0) {
            validationStatus.setStatus(false);
            validationStatus.setMessage("Invalid userEmail and password");
            return validationStatus;
        }


        if (null == userEmail || userEmail.length() == 0) {
            validationStatus.setStatus(false);
            validationStatus.setMessage("Invalid userEmail");
            return validationStatus;
        }

        if (null == password || password.length() == 0) {
            validationStatus.setStatus(false);
            validationStatus.setMessage("Invalid password");
            return validationStatus;
        }

        return validationStatus;
    }
}
