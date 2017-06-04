package com.android.fluktask.mytaskapplication;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by admin on 6/2/2017.
 */

public interface OnGPlusLoginListener {

    void onLoginUpdate(GoogleApiClient googleApiClient);
    void onLoginData(GoogleSignInAccount googleSignInAccount);
}
