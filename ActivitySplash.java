package com.foodwastemanagement;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.foodwastemanagement.Model.User_Accounts;
import com.foodwastemanagement.Utility.PrefManager;


public class ActivitySplash extends AppCompatActivity {
    private static final String TAG = "Splash Screen";
    SharedPreferences settings;
    PrefManager pref;
    String userDataString = "";
    User_Accounts usrData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        pref = new PrefManager(this);

        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    pref= new PrefManager(ActivitySplash.this);
                    if (pref.isFirstTimeLaunch()) {
                      startActivity(new Intent(ActivitySplash.this, ActivityWelcome.class));
                   Log.d("#11", "first time launch");

                    } else if (pref.getUserId().equals("")) {
                        Intent intent = new Intent(ActivitySplash.this, ActivityLogin.class);
                        Log.d("#11", "value: username:: password and username  is empty");
                        startActivity(intent);
                    } else if(pref.getUserType().equals("User")){
                        startActivity(new Intent(ActivitySplash.this, ActivityDashboard.class));
                    }else if(pref.getUserType().equals("Admin")){

                        }





                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause () {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

}
