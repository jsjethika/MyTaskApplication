package com.android.fluktask.mytaskapplication;

import android.os.Looper;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import java.security.KeyStore;

/**
 * Created by admin on 6/2/2017.
 */

public class HttpManager {

  private static final String TAG = HttpManager.class.getName();

    private static int DEFAULT_TIMEOUT = 20 * 1000;

    // A SyncHttpClient is an AsyncHttpClient
    public static AsyncHttpClient syncHttpClient = new SyncHttpClient(true, 80, 8443);
    public static AsyncHttpClient asyncHttpClient = new AsyncHttpClient(true, 80, 8443);


    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        getClient().get(url, params, responseHandler);
    }


    /**
     * @return an async client when calling from the main thread, otherwise a sync client.
     */
    private static AsyncHttpClient getClient() {

        // Return the synchronous HTTP client when the thread is not prepared
        if (Looper.myLooper() == null) {
            syncHttpClient.setTimeout(DEFAULT_TIMEOUT);
            syncHttpClient.setConnectTimeout(DEFAULT_TIMEOUT);
            syncHttpClient.setResponseTimeout(DEFAULT_TIMEOUT);

                        // SSL
            AppSSLSocketFactory appSSLSocketFactory = null;
            try {

                KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                trustStore.load(null, null);
                appSSLSocketFactory = new AppSSLSocketFactory(trustStore);
                appSSLSocketFactory.setHostnameVerifier(AppSSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            } catch (Exception ex) {
                Log.e(TAG, ex.toString());
            }
            syncHttpClient.setSSLSocketFactory(appSSLSocketFactory);


            return syncHttpClient;
        }

        asyncHttpClient.setTimeout(DEFAULT_TIMEOUT);
        asyncHttpClient.setConnectTimeout(DEFAULT_TIMEOUT);
        asyncHttpClient.setResponseTimeout(DEFAULT_TIMEOUT);
        // SSL
        AppSSLSocketFactory appSSLSocketFactory = null;
        try {

            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            appSSLSocketFactory = new AppSSLSocketFactory(trustStore);
            appSSLSocketFactory.setHostnameVerifier(AppSSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
        asyncHttpClient.setSSLSocketFactory(appSSLSocketFactory);

        return asyncHttpClient;
    }


}

