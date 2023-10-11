package com.foodwastemanagement.Utility;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "AppPref";
    private static final String PUSER_ID = "UserID";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String COUNT_SERVICES = "CountServices";
    private static final String USER_ID = "userid";
    private static final String USER_TYPE = "UserType";

    private static final String USER_EMAIL = "useremail";
    private static final String USER_FIRSTNAME = "userfirstname";
    private static final String USER_LASTNAME = "userlastname";


    public void setUserId(String _userid) {
        editor.putString(USER_ID, _userid);
        editor.commit();
    }
    public String getUserId() {
        return pref.getString(USER_ID, "");
    }

    public void setUserEmail(String _useremail) {
        editor.putString(USER_EMAIL, _useremail);
        editor.commit();
    }
    public String getUserEmail() {
        return pref.getString(USER_EMAIL, "");
    }


    public void setUserFirstName(String _userfname) {
        editor.putString(USER_FIRSTNAME, _userfname);
        editor.commit();
    }
    public String getUserFirstName() {
        return pref.getString(USER_FIRSTNAME, "");
    }


    public void setUserLastName(String _userlname) {
        editor.putString(USER_LASTNAME, _userlname);
        editor.commit();
    }
    public String getUserLastName() {
        return pref.getString(USER_LASTNAME, "");
    }




    public void setUserType(String _usertype) {
        editor.putString(USER_TYPE, _usertype);
        editor.commit();
    }
    public String getUserType() {
        return pref.getString(USER_TYPE, "");
    }

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }
    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setuPassword(String _pass) {
        editor.putString("pass", _pass);
        editor.commit();
    }
    public String getuPassword() {
        return pref.getString("pass", "");
    }

    public void setPUSERID(String _userid) {
        editor.putString(PUSER_ID, _userid).commit();

    }
    public String getPUSERID() {
        return pref.getString(PUSER_ID, "");
    }

    public void setCountServices(String _CountServices) {
        editor.putString(COUNT_SERVICES, _CountServices);
        editor.commit();
    }
    public String getCountServices() {
        return pref.getString(COUNT_SERVICES, "");
    }



}
