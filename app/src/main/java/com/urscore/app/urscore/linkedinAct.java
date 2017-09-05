package com.urscore.app.urscore;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class linkedinAct extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgProfile,linkedinlogin;
    DBHelper db;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linkedinact);

        TextView textv=(TextView)findViewById(R.id.textv);
        textv.setText(HomeActivity.name+", Now Login to Linkedin");

        context=this;
        db = DBHelper.getInstance(getApplicationContext());

        //below 3 lines for adding logo to action/title bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);

        initializeControls();

    }

    private void initializeControls(){
        //for linkedin
        linkedinlogin = (ImageView)findViewById(R.id.linkedinlogin);
        linkedinlogin.setOnClickListener(this);
       // imgProfile = (ImageView)findViewById(R.id.imgProfile);

        //setting as default on MainActivity intialization for linkedin
        linkedinlogin.setVisibility(View.VISIBLE);
        //imgProfile.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.linkedinlogin:
                handlelinkedinlogin();
                break;
        }
    }


    //linkedin login handling and data fetching from the function
    private void handlelinkedinlogin(){
        LISessionManager.getInstance(getApplicationContext()).init(this, buildScope(), new AuthListener() {
            @Override
            public void onAuthSuccess() {
                System.out.println("SUCCESS");
             //   Toast.makeText(getApplicationContext(), "Linkedin Login Success",Toast.LENGTH_SHORT).show();
                // Authentication was successful.  You can now do other calls with the SDK.
                // linkedinlogin.setVisibility(View.GONE);
                //imgProfile.setVisibility(View.VISIBLE);
                fetchpersonalinfo();


            }

            @Override
            public void onAuthError(LIAuthError error) {
                // Handle authentication errors
                System.out.println("ERROR");
                Toast.makeText(getApplicationContext(), "Linked Login Failed"+ error.toString(),Toast.LENGTH_SHORT).show();
                Log.e("PIYUSH",error.toString());
            }
        }, true);
    }


    // Build the list of member permissions our LinkedIn session requires
    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.W_SHARE, Scope.R_EMAILADDRESS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //below line for linkedin
        LISessionManager.getInstance(getApplicationContext()).onActivityResult(this, requestCode, resultCode, data);
        //Intent intent = new Intent(MainActivity.this,result.class);
        //startActivity(intent);

    }

    //fetching and parsing details from linkedin
    private void fetchpersonalinfo(){
        String url = "https://api.linkedin.com/v1/people/~:(id,first-name,last-name,positions:(title,),public-profile-url,picture-url,email-address,picture-urls::(original))";

        APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
        new APIHelper().getRequest(this, url, new ApiListener() {
            @Override
            public void onApiSuccess(ApiResponse apiResponse) {
                // Success!
                try {
                    JSONObject jsonObject = apiResponse.getResponseDataAsJson();
                   String id =jsonObject.getString("id");
                    //String title =jsonObject.getString("title");
                    String publicProfileUrl =jsonObject.getString("publicProfileUrl");
                    String firstname = jsonObject.getString("firstName");
                    String lasttname = jsonObject.getString("lastName");
                    String pictureURL = jsonObject.getString("pictureUrl");
                    String emailAddress = jsonObject.getString("emailAddress");

                    //Picasso.with(getApplicationContext()).load(pictureURL).into(imgProfile);
                    System.out.println(apiResponse.getResponseDataAsJson());
                  //  Toast.makeText(getApplicationContext(), "Linked Hello"+ apiResponse.getResponseDataAsJson(),Toast.LENGTH_LONG).show();

                    // imgProfile.setVisibility(View.VISIBLE);

                    boolean isInserted = db.insertDatalinkedin(id,firstname,lasttname,publicProfileUrl,pictureURL,emailAddress);
                    if(isInserted==true){
                        Toast.makeText(getApplicationContext(), "Analysing your linkedin profile",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(linkedinAct.this,googleAct.class);
                        startActivity(i);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "IN-DB connection error",Toast.LENGTH_SHORT).show();
                    }

                }
                catch(Exception e){
                    e.printStackTrace();
                }


            }

            @Override
            public void onApiError(LIApiError liApiError) {
                // Error making GET request!
                Log.e("PIYUSH",liApiError.getMessage());
            }
        });

    }
}
