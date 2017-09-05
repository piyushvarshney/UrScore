package com.urscore.app.urscore;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.common.api.GoogleApiClient;

public class Evaluate {

    FacebookData facebookData;
    LinkedinData linkedinData;
    GoogleData googleData;
    private DBHelper dbHelper;
    static int score;
    static int nameScore, smsScore, emailScore, imageScore;


    public Evaluate(DBHelper dbHelper){
        this.dbHelper=dbHelper;
    }

    /*public String userName(){
        facebookData =  dbHelper.GetFbData();
        String username=facebookData.getFbname();
        return username ;
    }

    public String profilephoto(){
        facebookData=dbHelper.GetFbData();
        String profilephoto=facebookData.getFbpicture();
        return profilephoto;
    }*/

    public int getScore(){

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        score=0;
        nameScore = 50;
        smsScore=50;
        emailScore= 50;
        imageScore=50;

        facebookData =  dbHelper.GetFbData();
        linkedinData = dbHelper.GetLinkedinData();
        googleData = dbHelper.GetGoogleData();


        //name check (Max Score awarded here=10)
        String linkedInName= linkedinData.getInfname()+" "+linkedinData.getInlname();
        if(facebookData.getFbname()==linkedInName || facebookData.getFbname()==googleData.getGname() || googleData.getGname()==linkedInName )
        {
                nameScore =100;

            if(facebookData.getFbname()==linkedInName && facebookData.getFbname()==googleData.getGname()){
                nameScore= 150;
            }
        }

        //email check (Max Score awarded here=10)
        if(facebookData.getFbemail()==linkedinData.getInemail() || facebookData.getFbemail()==googleData.getGemail() || googleData.getGname()==linkedinData.getInemail() )
        {
            emailScore=100;

            if(facebookData.getFbemail()==linkedinData.getInemail() && facebookData.getFbemail()==googleData.getGemail()){
                emailScore=150;
            }
        }


        //check availability of profile picture
        if((facebookData.getFbpicture()!=null && linkedinData.getInpicture()!=null) || (facebookData.getFbpicture()!=null  &&  googleData.getGpicture()!=null) || (googleData.getGpicture()!=null && linkedinData.getInpicture()!=null) )
        {
            imageScore=100;

            if(facebookData.getFbpicture()!=null && linkedinData.getInpicture()!=null && googleData.getGpicture()!=null){
                imageScore=150;
            }
        }

        score=(nameScore+emailScore+imageScore+smsScore)*3;
        return score;

    }






}
