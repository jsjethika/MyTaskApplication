package com.android.fluktask.mytaskapplication.fragment;

/**
 * Created by admin on 6/2/2017.
 */


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.fluktask.mytaskapplication.AppTool;
import com.android.fluktask.mytaskapplication.NetworkUtil;
import com.android.fluktask.mytaskapplication.OnGPlusLoginListener;
import com.android.fluktask.mytaskapplication.OnNetworkChangeListener;
import com.android.fluktask.mytaskapplication.R;
import com.android.fluktask.mytaskapplication.ValidationUtils;
import com.android.fluktask.mytaskapplication.pojos.ValidationStatus;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;


@EFragment(R.layout.fragment_login)
public class LoginFragment extends Fragment implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener, OnNetworkChangeListener, View.OnKeyListener, View.OnFocusChangeListener {

    private static final String TAG = LoginFragment.class.getSimpleName();
    private static final int RC_SIGN_IN = 1;

    private GoogleApiClient googleApiClient;
    private ProgressDialog progressDialog;

    @ViewById(R.id.loginLayout)
    LinearLayout loginLayout;

    @ViewById(R.id.alreadyLoginLayout)
    LinearLayout alreadyLoginLayout;

    @ViewById(R.id.buttonLogin)
    Button buttonLogin;

    @ViewById(R.id.editTextEamil)
    EditText editTextEmail;

    @ViewById(R.id.errorText)
    TextView errorText;

    @ViewById(R.id.editTextPassword)
    EditText editTextPassword;

    String userEmailInput, passWordInput;

    ValidationStatus validationStatus;


    boolean isNetworkAvailable = false;

    OnGPlusLoginListener gPlusLoginListener;

    public void setgPlusLoginListener(OnGPlusLoginListener gPlusLoginListener) {
        this.gPlusLoginListener = gPlusLoginListener;
    }


    @AfterViews
    public void initFragment() {

        isNetworkAvailable = AppTool.networkStatus();
        NetworkUtil.setOnNetworkChangeListener(this);

        initializeControls();
        initializeGPlusSettings();

    }


    private void initializeControls(){

        buttonLogin.setOnClickListener(this);
        editTextPassword.setOnKeyListener(this);
        editTextEmail.setOnFocusChangeListener(this);
        editTextPassword.setOnFocusChangeListener(this);

    }


    private void initializeGPlusSettings(){

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

    }


    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> optionalPendingResult = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (optionalPendingResult.isDone()) {
            // user already signin
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = optionalPendingResult.get();
            handleGPlusSignInResult(result,false);
        } else {
            // user not signin
            showProgressDialog();
            optionalPendingResult.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleGPlusSignInResult(googleSignInResult,true);
                }
            });
        }

    }


    @Override
    public void onChange(String status) {
        isNetworkAvailable = AppTool.networkStatus(status);
    }


    @Override
    public void onClick(View v) {
        if (!isNetworkAvailable) {
            AppTool.showToast(getActivity().getResources().getString(R.string.networkMsg));
        }else {
            setInputValidation();
        }

    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        if (keyCode == EditorInfo.IME_ACTION_GO ||
                keyCode == EditorInfo.IME_ACTION_DONE ||
                event.getAction() == KeyEvent.ACTION_DOWN &&
                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

            switch (v.getId()){
                case R.id.editTextPassword:

                    if (!isNetworkAvailable) {
                        AppTool.showToast(getActivity().getResources().getString(R.string.networkMsg));
                    }else {
                        setInputValidation();
                    }

                    break;
            }
        }

        return false;
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            errorText.setVisibility(View.GONE);
        }
    }


    void setInputValidation(){

        userEmailInput = editTextEmail.getText().toString();
        passWordInput = editTextPassword.getText().toString();

        validationStatus = ValidationUtils.isValidLoginEmailPassword(userEmailInput, passWordInput);

        showProgressDialog();

        if (validationStatus.isStatus() == false) {
            hideProgressDialog();
            setErrorMessage(validationStatus.getMessage());
        } else {

            errorText.setVisibility(View.GONE);
            hideProgressDialog();
            signIn();
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    void setErrorMessage(String errorMessage) {
        errorText.setVisibility(View.VISIBLE);
        errorText.setText(errorMessage);
    }

    private void handleGPlusSignInResult(GoogleSignInResult result,boolean signInStatus) {

        if(null != result){
            Log.d(TAG, "handleSignInResult:" + result.isSuccess());
            updateUI(signInStatus);
            if (result.isSuccess()) {
                // Signed in successfully, show authenticated UI.
                GoogleSignInAccount googleSignInAccount = result.getSignInAccount();
                gPlusLoginListener.onLoginUpdate(googleApiClient);
                gPlusLoginListener.onLoginData(googleSignInAccount);

            } else {
                // Signed out, show unauthenticated UI.
                setErrorMessage(result.getStatus().getStatusMessage());
            }
        }else {
            AppTool.showToast("GoogleSignInResult is null");
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleGPlusSignInResult(result,true);
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {

                // try to start the intent to resolve error

                connectionResult.startResolutionForResult(getActivity(), RC_SIGN_IN);

            } catch (IntentSender.SendIntentException e) {
                Log.e(TAG, "Could not resolve ConnectionResult.",e);

                	if (googleApiClient != null) {
                        googleApiClient.disconnect();
                		googleApiClient.connect();
                	}
            }
        }else {
              AppTool.showToast(connectionResult.getErrorMessage());
        }

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


    private void updateUI(boolean isSignedIn) {
        if (isSignedIn) {
            loginLayout.setVisibility(View.GONE);
            alreadyLoginLayout.setVisibility(View.VISIBLE);
        } else {
            loginLayout.setVisibility(View.VISIBLE);
            alreadyLoginLayout.setVisibility(View.GONE);
        }
    }


}
