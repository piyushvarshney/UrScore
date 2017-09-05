package com.urscore.app.urscore;

import android.*;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class result extends AppCompatActivity {
    File imagePath;
    int writeExternalStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //below 3 lines for adding logo to action/title bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);

        TextView tv1=(TextView) findViewById(R.id.tv1);
        TextView tv2=(TextView) findViewById(R.id.tv2);
        TextView tv3=(TextView) findViewById(R.id.tv3);
        TextView tv4=(TextView) findViewById(R.id.tv4);

        ImageView iv1=(ImageView) findViewById(R.id.iv1);
        ImageView iv2=(ImageView) findViewById(R.id.iv2);
        ImageView iv3=(ImageView) findViewById(R.id.iv3);
        ImageView iv4=(ImageView) findViewById(R.id.iv4);
        TextView nameView = (TextView) findViewById(R.id.nameView);
        nameView.setText(googleAct.userName);

        TextView score = (TextView) findViewById(R.id.score);
        score.setText(getIntent().getStringExtra("score"));

        ImageView imgProfile=(ImageView) findViewById(R.id.imgProfile);
        Picasso.with(getApplicationContext()).load(googleAct.imageUrl).into(imgProfile);



        if(Evaluate.score>1000){
            tv1.setText("Expert Profile Score");
            iv1.setImageResource(R.drawable.tick);
        }
        else
        {
            tv1.setText("Average Profile Score");
            iv1.setImageResource(R.drawable.alert);
        }

        if(Evaluate.nameScore>101){
            tv2.setText("Confirmed Social Identity");
            iv2.setImageResource(R.drawable.tick);
        }
        else{
            tv2.setText("Social Name Mismatch");
            iv2.setImageResource(R.drawable.alert);
        }

        if(Evaluate.emailScore>101){
            tv3.setText("Common E-mail Identity");
            iv3.setImageResource(R.drawable.tick);
        }
        else{
            tv3.setText("Multiple E-mail Identity");
            iv3.setImageResource(R.drawable.alert);
        }

        if (Evaluate.score>900){
            tv4.setText("Socially Active Profile");
            iv4.setImageResource(R.drawable.tick);
        }
        else{
            tv4.setText("Lower Social Activity");
            iv4.setImageResource(R.drawable.alert);
        }

        Button shareButton = (Button)findViewById(R.id.shareButton);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.

                    } else {

                        // No explanation needed, we can request the permission.

                        ActivityCompat.requestPermissions(this,
                                new String[]{android.Manifest.permission.READ_SMS},
                                writeExternalStorage);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                }
*/


                Bitmap bitmap = Bitmap.createBitmap(takeScreenshot());
                saveBitmap(bitmap);
                shareIt();
            }
        });

    }

    public Bitmap takeScreenshot() {
        View u = findViewById(R.id.shareableLayout);
        u.setDrawingCacheEnabled(true);
        return u.getDrawingCache();
    }

    public void saveBitmap(Bitmap bitmap) {
        imagePath = new File(Environment.getExternalStorageDirectory() + "/screenshot.png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }

    private void shareIt() {
        Uri uri = Uri.fromFile(imagePath);
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("image/*");
        String shareBody = "Look I just analysed my profile on UrScore";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "My Score on UrScore");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);

        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

/*

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if(requestCode==writeExternalStorage) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // permission was granted, yay! Do the
                // contacts-related task you need to do.

            } else {

                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
            return;
        }

        // other 'case' lines to check for other
        // permissions this app might request
    }
*/

}
