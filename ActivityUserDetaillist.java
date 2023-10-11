package com.foodwastemanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import com.foodwastemanagement.Adapters.RecyclerAdapterUserDetailList;
import com.foodwastemanagement.Model.User_Accounts;

import java.util.ArrayList;


public class ActivityUserDetaillist extends AppCompatActivity {
    private static final String TAG = "ActivityUserDetaillist";
    RecyclerView recyclerView;
    Activity activity = this;
    String Typ;

    LinearLayout linearlayout;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter adapter;
    FirebaseFirestore myDB;
    ArrayList<User_Accounts> datalist;

    Intent i;
    User_Accounts uaList;
    private ProgressBar mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail_list);
        i = getIntent();
        Typ = i.getStringExtra("type");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        datalist = new ArrayList<User_Accounts>();

        myDB = FirebaseFirestore.getInstance();
        myDB.collection("UserReg")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot doc : task.getResult()) {

                                Log.d(TAG, "#22 all the data is being set");
                                uaList = new User_Accounts();
                                uaList.setUsername(doc.getData().get("uUsername").toString());
                                Log.d(TAG, "#22 all uUsername");

                                uaList.setPassword(doc.getData().get("uPassword").toString());
                                Log.d(TAG, "#22 all uPassword");

                                Log.d(TAG, "#22 all uId");

                                datalist.add(uaList);
                                Log.d(TAG, "#22 all datalist the data is being set");
                            }


                            Toast.makeText(getApplicationContext(), "data is  there", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "#22 order_items is greatet than one " + datalist.size());
                            recyclerView.setHasFixedSize(true);
                            mLayoutManager = new LinearLayoutManager(ActivityUserDetaillist.this);
                            recyclerView.setLayoutManager(mLayoutManager);
                            Log.d(TAG, "#22 all datalist setLayoutManager set");
                            adapter = new RecyclerAdapterUserDetailList(datalist, ActivityUserDetaillist.this);
                            recyclerView.setAdapter(adapter);
                              /*  }

                            }*/
                        } else {
                            Log.d("111", "Error getting documents: ", task.getException());
                        }
                    }
                });




    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ActivityUserDetaillist.this, AdminDashboard.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }



}