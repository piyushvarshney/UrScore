package com.urscore.app.urscore;

import android.app.Notification;
import android.app.backup.FileBackupHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Piyush on 05-Jul-17.
 */




public class DBHelper extends SQLiteOpenHelper{
    private SQLiteDatabase db;
    private Context context;
    private static DBHelper dbHelper;

    public static final String TAG = "DBHelper";

    public static String fname;
    public static String gname;
    public static String lname;

    //columns of facebook table
    public static class FacebookContract implements BaseColumns {




    }
     public static final String TABLE_FB = "facebook";
    public static final String COLUMN_IDNO = "idNo";
    public static final String COLUMN_FBNAME = "fbname";
    public static final String COLUMN_FBID = "fbid";
    public static final String COLUMN_FBLINK = "fblink";
    public static final String COLUMN_FBEMAIL = "fbemail";
    public static final String COLUMN_FBPICTURE = "fbpicture";

    //columns of linkedin table
    public static final String TABLE_LINKEDIN = "linkedin";
    public static final int LIN_ID = 1;
    public static final String COLUMN_INID = "inid";
    public static final String COLUMN_INFNAME = "infname";
    public static final String COLUMN_INLNAME = "inlname";
    public static final String COLUMN_INTITLE = "intitle";
    public static final String COLUMN_INPROFILE = "inprofile";
    public static final String COLUMN_INPICTURE = "inpicture";
    public static final String COLUMN_INEMAIL = "inemail";

    //columns of google table
    public static final String TABLE_GOOGLE = "google";
    public static final int G_ID = 1;
//    public static final String COLUMN_GID = "gid";
    public static final String COLUMN_GNAME = "gname";
    public static final String COLUMN_GPICTURE = "gpicture";
    public static final String COLUMN_GEMAIL = "gemail";
    public static final String COLUMN_GFAMNAME = "gfamname";

    public static final String DATABASE_NAME = "urscore.db";
    public static final int DATABASE_VERSION = 1;

    // SQL statement of the facebook table creation
    private static final String SQL_CREATE_TABLE_FB = "CREATE TABLE " + TABLE_FB + "("
            + COLUMN_FBID + " TEXT, "
            + COLUMN_FBNAME + " TEXT, "
            + COLUMN_FBEMAIL + " TEXT, "
            + COLUMN_FBPICTURE + " TEXT, "
            + COLUMN_FBLINK + " TEXT"
            + ");";

    // SQL statement of the linkedin table creation
    private static final String SQL_CREATE_TABLE_LINKEDIN = "CREATE TABLE " + TABLE_LINKEDIN + "("
            + COLUMN_INID + " TEXT , "
            + COLUMN_INFNAME + " TEXT, "
            + COLUMN_INLNAME + " TEXT , "
            + COLUMN_INTITLE + " TEXT , "
            + COLUMN_INPROFILE + " TEXT , "
            + COLUMN_INPICTURE + " TEXT , "
            + COLUMN_INEMAIL + " TEXT "
            + ");";

    // SQL statement of the google table creation
    private static final String SQL_CREATE_TABLE_GOOGLE = "CREATE TABLE " + TABLE_GOOGLE + "("
            + COLUMN_GNAME + " TEXT, "
            + COLUMN_GEMAIL + " TEXT, "
            + COLUMN_GPICTURE + " TEXT, "
            + COLUMN_GFAMNAME + " TEXT "
            + ");";

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
        db = getWritableDatabase();
    }

    public static DBHelper getInstance(Context context){

        if(dbHelper==null){
            dbHelper = new DBHelper(context);
        }
        return dbHelper;

    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        try{
        database.execSQL(SQL_CREATE_TABLE_FB);
        database.execSQL(SQL_CREATE_TABLE_LINKEDIN);
        database.execSQL(SQL_CREATE_TABLE_GOOGLE);
        }
        catch(SQLException e){
            Toast.makeText(context, ""+e,Toast.LENGTH_LONG).show();
            e.printStackTrace();

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            Log.w(TAG,
                    "Upgrading the database from version " + oldVersion + " to " + newVersion);
            // clear all data
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_FB);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_LINKEDIN);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_GOOGLE);

            // recreate the tables
            onCreate(db);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public boolean insertDatafb(String id, String name, String email, String picture, String link) {
        open();
        ContentValues contentValues = new ContentValues();
       // contentValues.put(COLUMN_IDNO, idNo);
        contentValues.put(COLUMN_FBID, id);
        contentValues.put(COLUMN_FBNAME, name);
        contentValues.put(COLUMN_FBEMAIL, email);
        contentValues.put(COLUMN_FBPICTURE, picture);
        contentValues.put(COLUMN_FBLINK, link);
        long result=db.insert(TABLE_FB, null, contentValues);
        long falseNum = -1;
        close();
        if(result==falseNum)
            return false;
        else
            return true;

    }




    public boolean insertDatalinkedin(String inid, String infname, String inlname, String inprofile, String inpicture, String inemail) {
        open();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_INID, inid);
        contentValues.put(COLUMN_INFNAME, infname);
        contentValues.put(COLUMN_INLNAME, inlname);
        /*contentValues.put(COLUMN_INTITLE, intitle);*/
        contentValues.put(COLUMN_INPROFILE, inprofile);
        contentValues.put(COLUMN_INPICTURE, inpicture);
        contentValues.put(COLUMN_INEMAIL, inemail);
        long result=db.insertWithOnConflict(TABLE_LINKEDIN, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
        long falseNum = -1;
        close();
        if(result==falseNum)
            return false;
        else
            return true;

    }

    public boolean insertDatagoogle(String name, String email, String picture, String famname) {
        open();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_GNAME, name);
        contentValues.put(COLUMN_GEMAIL, email);
        contentValues.put(COLUMN_GPICTURE, picture);
        contentValues.put(COLUMN_GFAMNAME, famname);
        long result=db.insertWithOnConflict(TABLE_GOOGLE, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
        long falseNum = -1;
        close();
        if(result==falseNum)
            return false;
        else
            return true;

    }

    public FacebookData GetFbData(){
        Cursor cursor = null;
        FacebookData facebookData = new FacebookData();

        try {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_FB,null);
            if(cursor.moveToFirst()){
                facebookData.setFbname(cursor.getString(cursor.getColumnIndex(COLUMN_FBNAME)));
                facebookData.setFbemail(cursor.getString(cursor.getColumnIndex(COLUMN_FBEMAIL)));
                facebookData.setFbid(cursor.getString(cursor.getColumnIndex(COLUMN_FBID)));
                facebookData.setFblink(cursor.getString(cursor.getColumnIndex(COLUMN_FBLINK)));
                facebookData.setFbpicture(cursor.getString(cursor.getColumnIndex(COLUMN_FBPICTURE)));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{
                cursor.close();
            }catch(Exception e){

            }
        }
        Log.e("DBHELPER","line 230");
        return facebookData;
    }



    public LinkedinData GetLinkedinData(){
        Cursor cursor = null;
        LinkedinData linkedinData = new LinkedinData();

        try {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_LINKEDIN,null);
            if(cursor.moveToFirst()){
                linkedinData.setInprofile(cursor.getString(cursor.getColumnIndex(COLUMN_INPROFILE)));
                linkedinData.setInfname(cursor.getString(cursor.getColumnIndex(COLUMN_INFNAME)));
                linkedinData.setInlname(cursor.getString(cursor.getColumnIndex(COLUMN_INLNAME)));
                linkedinData.setInemail(cursor.getString(cursor.getColumnIndex(COLUMN_INEMAIL)));
                linkedinData.setInpicture(cursor.getString(cursor.getColumnIndex(COLUMN_INPICTURE)));
                linkedinData.setIntitle(cursor.getString(cursor.getColumnIndex(COLUMN_INTITLE)));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{
                cursor.close();
            }catch(Exception e){

            }
        }
        Log.e("DBHALPER","line 230");
        return linkedinData;
    }

    public GoogleData GetGoogleData(){
        Cursor cursor = null;
        GoogleData googleData = new GoogleData();

        try {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_GOOGLE,null);
            if(cursor.moveToFirst()){
                googleData.setGname(cursor.getString(cursor.getColumnIndex(COLUMN_GNAME)));
                googleData.setGemail(cursor.getString(cursor.getColumnIndex(COLUMN_GEMAIL)));
                googleData.setGpicture(cursor.getString(cursor.getColumnIndex(COLUMN_GPICTURE)));
                googleData.setGfamname(cursor.getString(cursor.getColumnIndex(COLUMN_GFAMNAME)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{
                cursor.close();
            }catch(Exception e){

            }
        }

        /*if(fname==lname && fname==gname){
            //use score variable here>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        }*/
        Log.e("DBHALPER","line 230");
        return googleData;
    }




    public void open(){
        try {
            db = getWritableDatabase();
        } catch (Exception e) {
        }
    }

    public void close(){
        try {
            db.close();
        } catch (Exception e) {
        }
    }

   /* public Cursor getInfo(DatabaseOperations dop){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

    }

*/

}


