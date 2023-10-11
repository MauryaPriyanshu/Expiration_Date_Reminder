package com.foodwastemanagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foodwastemanagement.Adapters.ResFoodRequestRecyclerAdapter;
import com.foodwastemanagement.Adapters.AdminRecyclerAdapterFoodAction;
import com.foodwastemanagement.Model.Event;
import com.foodwastemanagement.Utility.PrefManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
public class AdminFoodReqList extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private static final String TAG = "ActivityEventList";
    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter adapter;
    FirebaseFirestore myDB;
    ArrayList<Event> datalist;
    Event evModel;
    MenuItem itemCount;
    PrefManager pref;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_food_request_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pref = new PrefManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        datalist = new ArrayList<Event>();
        myDB = FirebaseFirestore.getInstance();
        myDB.collection("Event")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                evModel = new Event();
                                evModel.seteDate(doc.getData().get("eDate").toString());
                                evModel.seteType(doc.getData().get("eType").toString());
                                evModel.seteTime(doc.getData().get("eTime").toString());
                                evModel.seteCount(doc.getData().get("eCount").toString());
                                evModel.seteMsg(doc.getData().get("eMessage").toString());
                                evModel.seteId(doc.getData().get("eId").toString());
                                evModel.seteStatus(doc.getData().get("eStatus").toString());
                                evModel.seteRemark(doc.getData().get("eRemark").toString());
                                evModel.seteUserID(doc.getData().get("eUserID").toString());
                                evModel.seteResID(doc.getData().get("eResID").toString());
                                datalist.add(evModel);
                                Log.d(TAG, "#22 all datalist the data is being set");
                            }

                            if(datalist.size()>=1) {
                                recyclerView.setHasFixedSize(true);
                                mLayoutManager = new LinearLayoutManager(AdminFoodReqList.this);
                                recyclerView.setLayoutManager(mLayoutManager);
                                Log.d(TAG, "#22 all datalist");
                                adapter = new AdminRecyclerAdapterFoodAction(datalist, AdminFoodReqList.this);
                                recyclerView.setAdapter(adapter);
                            }
                        } else {
                            Log.d("111", "Error getting documents: ", task.getException());
                        }
                    }
                });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(AdminFoodReqList.this, ResturentAddFoodRequest.class);
//                intent.putExtra("type", "add_data");
//                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
            finish();
    }


   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
       MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.admin_menu_event, menu);
       final MenuItem item = menu.findItem(R.id.action_search);
       itemCount=menu.findItem(R.id.action_count);

       final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
       searchView.setOnQueryTextListener(this);
       MenuItemCompat.setOnActionExpandListener(item,
               new MenuItemCompat.OnActionExpandListener() {
                   @Override
                   public boolean onMenuItemActionCollapse(MenuItem item) {
                       // Do something when collapsed
                       // adapter.setFilter(mCountryModel);
                       return true; // Return true to collapse action view
                   }

                   @Override
                   public boolean onMenuItemActionExpand(MenuItem item) {
                       // Do something when expanded
                       return true; // Return true to expand action view
                   }
               });
       return true;
   }

    @Override
    public boolean onQueryTextChange(String newText) {

        final List<Event> filteredModelList = filter(datalist, newText);
        recyclerView.setLayoutManager(new LinearLayoutManager(AdminFoodReqList.this));
        adapter = new ResFoodRequestRecyclerAdapter((ArrayList<Event>) filteredModelList, AdminFoodReqList.this);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        itemCount.setTitle("("+filteredModelList.size() + ")");
        return true;
    }

    private List<Event> filter(List<Event> models, String query) {
        query = query.toLowerCase();
        final List<Event> filteredModelList = new ArrayList<>();
        for (Event model : models) {
            final String text = model.geteDate().toLowerCase();
            if (text.contains(query)) {

                filteredModelList.add(model);
            }

        }
        return filteredModelList;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.nav_logout:
                PrefManager pref=new PrefManager(AdminFoodReqList.this);
                if(!pref.getUserId().equals("")) {
                    Log.d("#111", "user data string is not empty" );
                    SharedPreferences pref1= getSharedPreferences("AppPref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref1.edit();
                    editor.clear();
                    editor.commit();
                    Intent intent=new Intent(AdminFoodReqList.this,ActivityLogin.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
                break;
            default:
                break;
        }
        return true;
    }









}