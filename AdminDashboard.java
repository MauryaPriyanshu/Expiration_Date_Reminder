package com.foodwastemanagement;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import com.foodwastemanagement.Utility.PrefManager;

public class AdminDashboard extends AppCompatActivity implements View.OnClickListener{
    private final static String TAG = "DBoard"; // debug

    Toolbar toolbar;
    DrawerLayout drawer_layout;
    ActionBarDrawerToggle mDrawerToggel;
    LinearLayout dash1,dash2,dash3,dash4;
    LinearLayout drawer_1,drawer_2,drawer_3,drawer_4;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_dashboard);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        initialise();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
         }

    void initialise() {
        // =============== Drawer Floating Icon Menu Start =====================
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerSetup();
        drawer_1 = (LinearLayout) findViewById(R.id.d1);
        drawer_1.setOnClickListener(this);
        drawer_2 = (LinearLayout) findViewById(R.id.d2);
        drawer_2.setOnClickListener(this);
        drawer_3 = (LinearLayout) findViewById(R.id.d3);
        drawer_3.setOnClickListener(this);
        drawer_4 = (LinearLayout) findViewById(R.id.d4);
        drawer_4.setOnClickListener(this);

        // =============== Drawer Floating Icon Menu End =====================
        dash1 = (LinearLayout) findViewById(R.id.dash_1);
        dash1.setOnClickListener(this);
        dash2 = (LinearLayout) findViewById(R.id.dash_2);
        dash2.setOnClickListener(this);
        dash3 = (LinearLayout) findViewById(R.id.dash_3);
        dash3.setOnClickListener(this);
        dash4 = (LinearLayout) findViewById(R.id.dash_4);
        dash4.setOnClickListener(this);

    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_menu_dash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_logout:
                *//*
                Intent i13=new Intent(ActivityDashboard.this,ActivityWebview.class);
                AllConstants.webViewUrl=AllConstants.urlAboutus;
                AllConstants.webViewTitle=AllConstants.titleaboutus;
                startActivity(i13);*//*
                break;
            default:
                break;
        }
        return true;
    }*/

    public void drawerSetup() {
        mDrawerToggel = new ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }
        };

        mDrawerToggel.setDrawerIndicatorEnabled(true);
        drawer_layout.setDrawerListener(mDrawerToggel);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "On Click");
        switch (v.getId()) {

            case R.id.dash_1:
                Intent idash1 = new Intent(this, AdminFoodReqList.class);
                startActivity(idash1);
                break;
            case R.id.dash_2:
//                Intent idash2 = new Intent(this, 01ResList.class);
//                startActivity(idash2);

                break;
            case R.id.dash_3:
                Intent idash3 = new Intent(this, ActivityUserDetaillist.class);
                startActivity(idash3);
                break;

            case R.id.dash_4:
//                Intent idash4 = new Intent(this, ResList.class);
//                startActivity(idash4);
                break;
            //   =========================== Drawer Menu Start ===========================

            case R.id.d1:
//             Intent iProject = new Intent(this, ResList.class);
//                startActivity(iProject);
                break;

            case R.id.d2:
//               Intent InDrawerRTISolution = new Intent(this, ResList.class);
//                startActivity(InDrawerRTISolution);
                break;
            case R.id.d3:
                Intent InDrawerRTINews = new Intent(this, ActivityUserDetaillist.class);
                startActivity(InDrawerRTINews);
                break;
            case R.id.d4:
//                Intent InDrawerRTIShare = new Intent(this, ResList.class);
//                startActivity(InDrawerRTIShare);
                break;

            //   =========================== Drawer Menu End ===========================

        }
    }

}
