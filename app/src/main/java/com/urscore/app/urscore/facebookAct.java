package com.urscore.app.urscore;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.linkedin.platform.LISessionManager;

import org.json.JSONObject;

public class facebookAct extends AppCompatActivity {

    CallbackManager callbackManager;
    LoginButton login;
    Context context;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebookact);

        //mydb = new DBHelper(getApplicationContext());

        TextView textView2=(TextView)findViewById(R.id.textView2);
        textView2.setText(HomeActivity.name+", Login from facebook first!");

        context=this;
        db = DBHelper.getInstance(getApplicationContext());
        FacebookSdk.sdkInitialize(this);

        //below 3 lines for adding logo to action/title bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);

        callbackManager = CallbackManager.Factory.create();
        login = (LoginButton)findViewById(R.id.login_button);
        login.setReadPermissions("public_profile email");

        if(AccessToken.getCurrentAccessToken() != null){
            RequestData();
            // share.setVisibility(View.VISIBLE);
            //details.setVisibility(View.VISIBLE);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(AccessToken.getCurrentAccessToken() != null) {
                    // share.setVisibility(View.INVISIBLE);
                    //details.setVisibility(View.INVISIBLE);

                }
            }
        });
        login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                if(AccessToken.getCurrentAccessToken() != null){
                    RequestData();
                    // share.setVisibility(View.VISIBLE);
                    //details.setVisibility(View.VISIBLE);
                  //  Toast.makeText(getApplicationContext(), "Facebook Login Success",Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getApplicationContext(), "Analysing your facebook profile",Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException exception) {

            }
        });



    }//onCreate method ends here

    //facebook data request and intent transfer
    public void RequestData(){
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                JSONObject json = response.getJSONObject();
                try {
                    if(json != null){
                        final String name=json.getString("name");
                        final String email=json.getString("email");
                        final String link =json.getString("link");
                        final String id=json.getString("id");
                        final String picture=json.getString("picture");

                        //uncomment for testing only--> //System.out.println("json"+json);

                        boolean isInserted=db.insertDatafb(id,name,email,picture,link);
                        if(isInserted==true){
                            Toast.makeText(getApplicationContext(), "Analysing your facebook profile",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(facebookAct.this,linkedinAct.class);
                            startActivity(i);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "FB-DB connection error",Toast.LENGTH_SHORT).show();
                        }

                      //  db.test();




                        //setting name to MainActivity from facebook
                        //TextView nametv = (TextView) findViewById(R.id.nametv);
                     //   nametv.setText(name);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }//facebook block ends here

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //below line for facebook
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
