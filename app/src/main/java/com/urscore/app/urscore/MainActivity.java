package com.urscore.app.urscore;


import android.database.Cursor;
import android.net.Uri;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    /*private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 420;*/
    //CallbackManager callbackManager;
    //LoginButton login;
    //private ImageView imgProfile,linkedinlogin;
   /* private GoogleApiClient googleApiClient;
    private com.google.android.gms.common.SignInButton googlebtn;*/
  ///ListView smslist;
    ///SimpleCursorAdapter adapter;

   /// ScrollView scroll;
/*

    private static final int PERMS_REQUEST_CODE = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
*/

       // FacebookSdk.sdkInitialize(getApplicationContext());
        //initializeControls();
       /* initializeGPlusSettings();*/

       /* if (!(hasPermissions())){
            requestPerms();
        }
*/

        //below line 89to94 and 154to167 for sms reading
      ///  ListView lv = (ListView)findViewById(R.id.smslist);
      ///  if(fetchInbox()!=null){
       ///     ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,fetchInbox());
       ///     lv.setAdapter(adapter);
     ///   }



        // Configure sign-in to request the user's ID, email address, and basic profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        /*GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();*/


        //below 3 lines for adding logo to action/title bar
      /*  ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
*/

        /*callbackManager = CallbackManager.Factory.create();
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
                }
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException exception) {

            }
        });*/



    }


   //for reading sms->
   /// public ArrayList<String> fetchInbox(){
    ///     ArrayList<String> sms=new ArrayList<String>();
    ///    Uri uri=Uri.parse("content://sms/inbox");
      ///  Cursor cursor=getContentResolver().query(uri,new String[]{"_id","address","date","body"}, null, null, null);

     ///   cursor.moveToFirst();
     ///   while(cursor.moveToNext()){
       ///     String address=cursor.getString(1);
       ///     String body=cursor.getString(3);

       ///     sms.add("Address=>"+address+"\n Sms=>"+body);
     ///   }
   ///  return sms;
  ///  }


    /*//facebook data request and intent transfer
    public void RequestData(){
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object,GraphResponse response) {

                JSONObject json = response.getJSONObject();
                try {
                    if(json != null){
                        final String name=json.getString("name");
                        final String email=json.getString("email");
                       // final String birthday=json.getString("id");
                       //uncomment for testing only--> //System.out.println("json"+json);


                        Button resbtn = (Button) findViewById(R.id.resbtn);
                        resbtn.setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
                            @Override
                            public void onClick(View v) {

                                Intent myIntent = new Intent(v.getContext(),result.class);
                                myIntent.putExtra("name",name);
                                myIntent.putExtra("email",email);
                              //  myIntent.putExtra("birthday",birthday);
                                startActivity(myIntent);
                            }});



                        //setting name to MainActivity from facebook
                        TextView nametv = (TextView) findViewById(R.id.nametv);
                        nametv.setText(name);
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
    }//facebook block ends here*/

   /* private void initializeControls(){
        //for linkedin
        *//*linkedinlogin = (ImageView)findViewById(R.id.linkedinlogin);
        linkedinlogin.setOnClickListener(this);
        imgProfile = (ImageView)findViewById(R.id.imgProfile);

        //setting as default on MainActivity intialization for linkedin
        linkedinlogin.setVisibility(View.VISIBLE);
        imgProfile.setVisibility(View.GONE);*//*

        //for google
        googlebtn = (SignInButton) findViewById(R.id.googlebtn);
        googlebtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            *//*case R.id.linkedinlogin:
                handlelinkedinlogin();
                break;*//*

            case R.id.googlebtn:
                signIn();
                break;
        }
    }*/


    /*//intialization of settings for gbutton
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

            Toast.makeText(getApplicationContext(), "Google linking SUCCESS!"+personName+personPhotoUrl+email+familyName+ result.getSignInAccount(),Toast.LENGTH_LONG).show();

            Log.e(TAG, "Name: " + personName +
                    ", email: " + email +
                    ", Image: " + personPhotoUrl +
                    ", Family Name: " + familyName);


        }
    }*/



   /* //linkedin login handling and data fetching from the function
    private void handlelinkedinlogin(){
        LISessionManager.getInstance(getApplicationContext()).init(this, buildScope(), new AuthListener() {
            @Override
            public void onAuthSuccess() {
                System.out.println("SUCCESS");
                Toast.makeText(getApplicationContext(), "Linked Login Success",Toast.LENGTH_LONG).show();
                // Authentication was successful.  You can now do other calls with the SDK.
               // linkedinlogin.setVisibility(View.GONE);
                //imgProfile.setVisibility(View.VISIBLE);
                fetchpersonalinfo();

            }

            @Override
            public void onAuthError(LIAuthError error) {
                // Handle authentication errors
                System.out.println("ERROR");
                Toast.makeText(getApplicationContext(), "Linked Login Failed"+ error.toString(),Toast.LENGTH_LONG).show();
                Log.e("PIYUSH",error.toString());
            }
        }, true);
    }


    // Build the list of member permissions our LinkedIn session requires
    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.W_SHARE, Scope.R_EMAILADDRESS);
    }*/

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //below line for facebook
        //callbackManager.onActivityResult(requestCode, resultCode, data);

        //below line for linkedin
       // LISessionManager.getInstance(getApplicationContext()).onActivityResult(this, requestCode, resultCode, data);
        //Intent intent = new Intent(MainActivity.this,result.class);
        //startActivity(intent);

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
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleGPlusSignInResult(result);
        } else {
            //  If the user has not previously signed in on this device or the sign-in has expired,
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
    }*/


    /*//fetching and parsing details from linkedin
    private void fetchpersonalinfo(){
        String url = "https://api.linkedin.com/v1/people/~:(id,first-name,last-name,positions:(title,),public-profile-url,picture-url,email-address,picture-urls::(original))";

        APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
        new APIHelper().getRequest(this, url, new ApiListener() {
            @Override
            public void onApiSuccess(ApiResponse apiResponse) {
                // Success!
                try {
                    JSONObject jsonObject = apiResponse.getResponseDataAsJson();
                    String firstname = jsonObject.getString("firstName");
                    String lasttname = jsonObject.getString("lastName");
                    String pictureURL = jsonObject.getString("pictureUrl");
                    String emailAddress = jsonObject.getString("emailAddress");

                    Picasso.with(getApplicationContext()).load(pictureURL).into(imgProfile);
                    System.out.println(apiResponse.getResponseDataAsJson());
                    Toast.makeText(getApplicationContext(), "Linked Hello"+ apiResponse.getResponseDataAsJson(),Toast.LENGTH_LONG).show();

                    imgProfile.setVisibility(View.VISIBLE);

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

    }*/






//}
