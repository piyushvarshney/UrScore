package com.urscore.app.urscore;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

public class googleAct extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 420;
    private GoogleApiClient googleApiClient;
    private com.google.android.gms.common.SignInButton googlebtn;
    static String userName,imageUrl;

    private static final int PERMS_REQUEST_CODE = 123;
    DBHelper db;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googleact);

        TextView tView=(TextView)findViewById(R.id.tView);
        tView.setText("Finally "+HomeActivity.name+", login to Google!");

        context=this;
        db = DBHelper.getInstance(getApplicationContext());

        initializeControls();
        initializeGPlusSettings();

        // Configure sign-in to request the user's ID, email address, and basic profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        //below 3 lines for adding logo to action/title bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);

        /*Button resultBtn = (Button) findViewById(R.id.resultBtn);
        resultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(googleAct.this,result.class);
                startActivity(i);
            }
        });*/

    }//on create ends here

    private void initializeControls(){
        //for google
        googlebtn = (SignInButton) findViewById(R.id.googlebtn);
        googlebtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.googlebtn:
                signIn();
                break;
        }
    }
    //intialization of settings for gbutton
    private void initializeGPlusSettings(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    private void handleGPlusSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            //Fetch values
            String personName = acct.getDisplayName();
            String personPhotoUrl = acct.getPhotoUrl().toString();
            String email = acct.getEmail();
            String familyName = acct.getFamilyName();

            Toast.makeText(getApplicationContext(), "Google Login Success",Toast.LENGTH_SHORT).show();
            userName=personName;
            imageUrl=personPhotoUrl;

            boolean isInserted=db.insertDatagoogle(personName,email,personPhotoUrl,familyName);
            if(isInserted==true){
                Toast.makeText(getApplicationContext(), "Analysing your google profile",Toast.LENGTH_SHORT).show();

            }
            else{
                Toast.makeText(getApplicationContext(), "GO-DB connection error",Toast.LENGTH_SHORT).show();
            }
            EvaluateTask evaluateTask = new EvaluateTask();
            evaluateTask.execute();



            Log.e(TAG, "Name: " + personName +
                    ", email: " + email +
                    ", Image: " + personPhotoUrl +
                    ", Family Name: " + familyName);



        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //below line for google
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleGPlusSignInResult(result);
        }




    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached4 sign-in");
            GoogleSignInResult result = opr.get();
            handleGPlusSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            //  showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    //  hideProgressDialog();
                    handleGPlusSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    public class EvaluateTask extends AsyncTask<Void,Void,Integer> {

        ProgressDialog progress = new ProgressDialog(context);


        @Override
        protected void onPreExecute() {

            progress.setMessage("Analysing your profile");
            progress.setIndeterminate(true);
            progress.show();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            Evaluate evaluate = new Evaluate(db); //Evaluate class object
            return evaluate.getScore();

        }

        @Override
        protected void onPostExecute(Integer finalScore) {
            progress.dismiss();
            Intent i = new Intent(googleAct.this, result.class);

            i.putExtra("score", finalScore.toString());
            startActivity(i);
        }
    }

}
